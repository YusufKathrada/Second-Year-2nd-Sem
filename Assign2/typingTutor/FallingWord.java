package typingTutor;

import java.util.concurrent.atomic.AtomicBoolean;

public class FallingWord {
	private String word; // the word
	private int x; // position - width
	private int y; // postion - height
	private int maxY; // maximum height
	private int maxX;
	private boolean dropped;// flag for if user does not manage to catch word in time
	private AtomicBoolean visiable = new AtomicBoolean(true);
	private int xwidth;
	private int yHeight;
	private int fallingSpeed; // how fast this word is
	// need to make these static
	static int maxWait = 1000;
	static int minWait = 100;
	private boolean isHungry = false;
	private AtomicBoolean lowest = new AtomicBoolean(true); // set the falling
	// word to have a true/false boolean value

	public static WordDictionary dict;

	FallingWord() { // constructor with defaults
		word = "computer"; // a default - not used
		x = 10;
		y = 0;
		maxY = 300;
		dropped = false;
		fallingSpeed = (int) (Math.random() * (maxWait - minWait) + minWait);
	}

	FallingWord(String text) {
		this();
		this.word = text;
	}

	FallingWord(String text, int x, int maxY, int maxX, boolean isHungry) { // most commonly used constructor - sets it
																			// all.
		this(text);
		this.x = x; // only need to set x, word is at top of screen at start
		this.maxY = maxY;
		this.maxX = maxX;
		this.isHungry = isHungry;

	}

	public synchronized void setHeight(int y) {
		this.yHeight = y;
	}

	public synchronized void setWidth(int x) {
		this.xwidth = x;
	}

	public int getHeight() {
		return this.yHeight;
	}

	public int getWidth() {
		return this.xwidth;
	}

	public static void increaseSpeed() {
		minWait += 50;
		maxWait += 50;
	}

	public static void resetSpeed() {
		maxWait = 1000;
		minWait = 100;
	}

	// all getters and setters must be synchronized
	public synchronized void setY(int y) {
		if (y > maxY) {
			y = maxY;
			dropped = true; // user did not manage to catch this word
		}
		this.y = y;
	}

	public synchronized void setX(int x) {
		if (x > maxX) {
			x = maxX;
			dropped = true; // user did not manage to catch this word
		}
		this.x = x;
	}

	public synchronized void setWord(String text) {
		this.word = text;
	}

	public synchronized String getWord() {
		return word;
	}

	public synchronized int getX() {
		return x;
	}

	public synchronized int getY() {
		return y;
	}

	public synchronized int getSpeed() {
		return fallingSpeed;
	}

	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}

	public synchronized void resetPosY() {
		setY(0);
	}

	public synchronized void resetPosX() {
		setX(10);
	}

	// set the lowest value
	public synchronized void setLowest(boolean b) {
		this.lowest = new AtomicBoolean(b);
	}

	// get the lowest value boolean value
	public synchronized boolean getLowest() {
		return this.lowest.get();
	}

	public boolean isHungry() {
		return this.isHungry;
	}

	public synchronized void resetWord() {
		if (this.isHungry()) {
			resetPosX();
		} else {
			resetPosY();
		}
		word = dict.getNewWord();
		dropped = false;
		fallingSpeed = (int) (Math.random() * (maxWait - minWait) + minWait);
		// System.out.println(getWord() + " falling speed = " + getSpeed());
	}

	public synchronized boolean matchWord(String typedText) {
		// System.out.println("Matching against: "+text);
		// this is where there needs to be a comparision of the x and the y axis
		// each falling word needs a comparison
		// lowest is true by default
		if (typedText.equals(this.word)) {
			// resetWord();
			return true;
		} else
			return false;
	}

	public synchronized void drop(int inc) {
		setY(y + inc);
	}

	public synchronized void shift(int inc) {
		setX(x + inc);
	}

	public synchronized boolean dropped() {
		return dropped;
	}

	public synchronized void setVisiable(boolean y) {
		visiable.set(y);
	}

	public synchronized boolean getVisiable() {
		return visiable.get();
	}

}
