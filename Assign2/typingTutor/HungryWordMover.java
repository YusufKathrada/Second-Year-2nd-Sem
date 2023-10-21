package typingTutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class HungryWordMover extends Thread {
    private FallingWord hungryWord;
    private AtomicBoolean done;
    private AtomicBoolean pause;
    private Score score;
    CountDownLatch startLatch; 
    private final static int borderWidth = 25;
    private static int height;
    private FallingWord[] otherWords;

   //constructor
    HungryWordMover(FallingWord word) {
        hungryWord = word;
    }
   //overloaded constructor
    HungryWordMover(FallingWord word, WordDictionary dict, Score score,
            CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p, int sizeOfY, FallingWord[] array) {
        this(word);
        this.startLatch = startLatch;
        this.score = score;
        this.done = d;
        this.pause = p;
        this.height = sizeOfY;
        this.otherWords = array;
    }

    public void run() {

        hungryWord.setY((height - borderWidth) / 2);
        
        try {

            System.out.println(hungryWord.getWord() + " hungry word waiting to start ");
            startLatch.await();     // Wait for threads 
            
            
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } 
        
        System.out.println(hungryWord.getWord() + " Hungry word started!");
        
        while (!done.get()) {
            hungryWord.setVisiable(false);
            
            try {
                sleep((int) (Math.random() * (hungryWord.maxWait - hungryWord.minWait) * 7 + hungryWord.minWait * 7));
                System.out.println("Hello " + Math.random() * (hungryWord.maxWait - hungryWord.minWait) * 7
                + hungryWord.minWait * 7);
                        
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            hungryWord.setVisiable(true);

            while (!hungryWord.dropped() && !done.get()) {

                for (int i = 0; i < otherWords.length - 1; i++) {
                
                     /*if ((hungryWord.getX() < otherWords[i].getX()
                           && otherWords[i].getX() - hungryWord.getX() <= (hungryWord.getWidth/2) + (otherWords.getWidth/2) ) ){
                        
                     }*/
                     
                     // Need .getWidth() method for HungryWord                        
                    
                    // Collision 1
                    if ((hungryWord.getX() < otherWords[i].getX())
                            && (otherWords[i].getX() < hungryWord.getX() + hungryWord.getWidth())) {
                        
                        if (((hungryWord.getY() < otherWords[i].getY())
                                && (otherWords[i].getY() < hungryWord.getY() + hungryWord.getHeight()))) {
                            
                            System.out.println("In the danger zone");
                            score.missedWord();
                            otherWords[i].resetWord();
                        }
                        
                        else if (((hungryWord.getY() < otherWords[i].getY() + otherWords[i].getHeight())
                                && (otherWords[i].getY() + otherWords[i].getHeight() < hungryWord.getY()
                                        + hungryWord.getHeight()))) {
                            
                            score.missedWord();
                            otherWords[i].resetWord();
                        }
                    }

                    
                    // Collsion 2
                    else if ((hungryWord.getX() < otherWords[i].getX() + otherWords[i].getWidth())
                            && (otherWords[i].getX() + otherWords[i].getWidth() < hungryWord.getX()
                                    + hungryWord.getWidth())) {
                        
                        if (((hungryWord.getY() < otherWords[i].getY())
                                && (otherWords[i].getY() < hungryWord.getY() + hungryWord.getHeight()))) {
                            
                            System.out.println("In the danger zone");
                            score.missedWord();
                            otherWords[i].resetWord();
                        }
                        
                        else if (((hungryWord.getY() < otherWords[i].getY() + otherWords[i].getHeight())
                                && (otherWords[i].getY() + otherWords[i].getHeight() < hungryWord.getY()
                                        + hungryWord.getHeight()))) {

                            score.missedWord();
                            otherWords[i].resetWord();
                        }
                    }

                    // Collision 3
                    else if ((otherWords[i].getX() < hungryWord.getX())
                            && (hungryWord.getX() < otherWords[i].getX() + otherWords[i].getWidth())) {
                        
                        if (((otherWords[i].getY() < hungryWord.getY())
                                && (hungryWord.getY() < otherWords[i].getY() + otherWords[i].getHeight()))) {
                            
                            score.missedWord();
                            otherWords[i].resetWord();
                        }
                    }

                }
                
                hungryWord.shift(25);

                try {
                    sleep(hungryWord.getSpeed());
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                while (pause.get() && !done.get()) {}
            }
            
            if (!done.get() && hungryWord.dropped()) {
                score.missedWord();
                hungryWord.resetWord();
            }
        

        }
    }
}