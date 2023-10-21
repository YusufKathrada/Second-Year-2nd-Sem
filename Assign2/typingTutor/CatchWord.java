package typingTutor;

import java.util.concurrent.atomic.AtomicBoolean;

//Thread to monitor the word that has been typed.
public class CatchWord extends Thread {
	String target;
	static AtomicBoolean done; // REMOVE
	static AtomicBoolean pause; // REMOVE
	private static FallingWord[] words; // list of words
	private static int noWords; // how many
	private static Score score; // user score

	CatchWord(String typedWord) {
		target = typedWord;
	}

	public static void setWords(FallingWord[] wordList) {
		words = wordList;
		noWords = words.length;
	}

	public static void setScore(Score sharedScore) {
		score = sharedScore;
	}

	// there is a problem with these flags
	public static void setFlags(AtomicBoolean d, AtomicBoolean p) {
		done = d;
		pause = p;
	}

	public void run() {
		System.out.println("Calling the run");

	   FallingWord lowest = words[0];
		for (int r = 0; r < noWords; r++) {

			if (words[r].getWord().equals(target)) {
				if ((words[r].isHungry())) {
					lowest = words[r];
					break;
				}

				if (words[r].getY() > lowest.getY()) {
					// lowest.setLowest(false);
					lowest = words[r];

				}
			}
		}

		if (lowest.isHungry()) {
			lowest.setVisiable(false);
			score.caughtWord(target.length());
			lowest.resetWord();
			lowest.setX(-100);
			score.caughtWord(target.length());

			try {
				sleep((int) (Math.random() * (lowest.maxWait - lowest.minWait) * 7 + lowest.minWait * 7));
				System.out.println("Hello " + Math.random() * (lowest.maxWait - lowest.minWait) * 7
						+ lowest.minWait * 7);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// lowest.resetWord();
			lowest.setVisiable(true);

		}

		else if (lowest.matchWord(target)) {
			score.caughtWord(target.length());
			lowest.resetWord();

		}
	}

}
