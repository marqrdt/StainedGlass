package org.marqrdt.stainedglass;

import java.awt.Color;

public class ThreadedGamePanel extends AbstractGamePanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Thread t;
	Color currentHighlightColor = Color.LIGHT_GRAY;

	public ThreadedGamePanel(GameBoard gameModel) {
		super(gameModel);
	}

	public void run() {
		Thread myThread = Thread.currentThread();
		while (t == myThread) {
			try {
				Thread.sleep(500);
				if (currentHighlightColor != null) {
					currentHighlightColor = null;
				} else {
					currentHighlightColor = Color.LIGHT_GRAY;
				}
			} catch (InterruptedException exc) {
				System.out.println("I was interrupted");
			}
			repaint();
		}
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, "Highlight");
			t.start();
		}
	}

	public void stop() {
		t = null;
	}

	public void updateDisplay() {
		repaint();
	}

	public Thread getThread() {
		return t;
	}

	public Color getCurrentHightlightColor() {
		return currentHighlightColor;
	}
}
