package org.marqrdt.stainedglass;

import javax.swing.Timer;

public class ThreadedGamePanel extends AbstractGamePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int TICK_INTERVAL_MS = 100;
	private final Timer blinkTimer;

	public ThreadedGamePanel(GameBoard gameModel) {
		super(gameModel);
		blinkTimer = new Timer(TICK_INTERVAL_MS, e -> repaint());
	}

	public void start() {
		if (!blinkTimer.isRunning()) {
			blinkTimer.start();
		}
	}

	public void stop() {
		blinkTimer.stop();
	}

	public void updateDisplay() {
		repaint();
	}
}
