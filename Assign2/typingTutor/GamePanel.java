package typingTutor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
//import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	private AtomicBoolean done; // REMOVE
	private AtomicBoolean started; // REMOVE
	private AtomicBoolean won; // REMOVE

	private FallingWord[] words;
	private int noWords;
	private final static int borderWidth = 25; // appearance - border

	GamePanel(FallingWord[] words, int maxY,
			AtomicBoolean d, AtomicBoolean s, AtomicBoolean w) {
		this.words = words; // shared word list
		noWords = words.length; // only need to do this once
		done = d; // REMOVE
		started = s; // REMOVE
		won = w; // REMOVE
	}

	public void paintComponent(Graphics g) {
		int width = getWidth() - borderWidth * 2;
		int height = getHeight() - borderWidth * 2;
		g.clearRect(borderWidth, borderWidth, width, height);
		g.setColor(Color.lightGray);// the active space
		g.fillRect(borderWidth, 0, width, borderWidth); // look at this method for the boarder
		g.fillRect(width + borderWidth, 0, borderWidth, height);
		g.fillRect(0, 0, borderWidth, height);
		g.setColor(Color.pink); // change colour of pen
		g.fillRect(0, height, width + borderWidth * 2, borderWidth * 2); // draw danger zone

		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.PLAIN, 26));
		// draw the words
		if (!started.get()) {
			g.setFont(new Font("Arial", Font.BOLD, 18));
			g.drawString("Type all the words before they hit the red zone,press enter after each one.", borderWidth * 4,
					height / 2);

		} else if (!done.get()) {
			for (int i = 0; i < noWords - 1; i++) {
				if (!(words[i].isHungry())) {
					words[i].setHeight(g.getFontMetrics().getHeight());
					words[i].setWidth(g.getFontMetrics().stringWidth(words[i].getWord()));
					// set the visiable property
					g.drawString(words[i].getWord(), words[i].getX() - words[i].getWidth(), words[i].getY());
					// g.setColor(Color.green);}

				}
			}

			if (words[noWords - 1].isHungry()) {
				words[noWords - 1].setHeight(g.getFontMetrics().getHeight());
				words[noWords - 1].setWidth(g.getFontMetrics().stringWidth(words[noWords - 1].getWord()));
				if (words[noWords - 1].getVisiable()) {
					g.setColor(Color.green);
					g.drawString(words[noWords - 1].getWord(), words[noWords - 1].getX() + borderWidth,
							words[noWords - 1].getY());
				}

			}
			g.setColor(Color.lightGray); // change colour of pen
			g.fillRect(borderWidth, 0, width, borderWidth); // look at this method for the boarder
			g.fillRect(width + borderWidth, 0, borderWidth, height);
			g.fillRect(0, 0, borderWidth, height);

		} else {
			if (won.get()) {
				g.setFont(new Font("Arial", Font.BOLD, 36));
				g.drawString("Well done!", (width - (4 * borderWidth)) / 2, height / 2);
			} else {
				g.setFont(new Font("Arial", Font.BOLD, 36));
				g.drawString("Game over!", (width - (4 * borderWidth)) / 2, height / 2);
			}
		}
	}

	public int getValidXpos() {
		int width = getWidth() - borderWidth * 4;
		int x = (int) (Math.random() * width);
		return x;
	}

	public int getValidYpos() {
		int height = getHeight() - borderWidth * 4;
		int y = (int) (Math.random() * height);
		return y;
	}

	public void run() {
		while (true) {
			repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			;
		}
	}

}
