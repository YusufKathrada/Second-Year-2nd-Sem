package typingTutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class WordMover extends Thread {
	private FallingWord myWord;
	private AtomicBoolean done;
	private AtomicBoolean pause;
	private Score score;
	CountDownLatch startLatch; // so all can start at once

	WordMover(FallingWord word) {
		myWord = word;
	}

	WordMover(FallingWord word, WordDictionary dict, Score score,
			CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) {
		this(word);
		this.startLatch = startLatch;
		this.score = score;
		this.done = d;
		this.pause = p;
	}

	public void run() {

		// System.out.println(myWord.getWord() + " falling speed = " +
		// myWord.getSpeed());
		try {
			System.out.println(myWord.getWord() + " waiting to start ");
			startLatch.await();// the latch to hold the threads
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // wait for other threads to start
		System.out.println(myWord.getWord() + " started");
		while (!done.get()) {
			// animate the word
			while (!myWord.dropped() && !done.get()) {
				myWord.drop(10);
				try {
					sleep(myWord.getSpeed());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				;
				while (pause.get() && !done.get()) { // keep running the thread
				}
				;
			}
			// not done and the word reached the bottom
			if (!done.get() && myWord.dropped()) {
				score.missedWord();
				myWord.resetWord();
			}
			// if (myWord.getLowest() && !done.get()) {
			// // score.caughtWord(myWord.getWord().length());
			// myWord.resetWord();
			// }

			// if (myWord.getHungry() && !done.get()) {
			// myWord.resetWord();
			// }

			myWord.resetWord();

		}

	}
}
