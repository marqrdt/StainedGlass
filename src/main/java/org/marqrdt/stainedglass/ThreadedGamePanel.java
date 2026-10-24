package org.marqrdt.stainedglass;

import java.awt.Color;

import javax.swing.Timer;

public class ThreadedGamePanel extends AbstractGamePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color currentHighlightColor = Color.LIGHT_GRAY;
	private final Timer blinkTimer;

	public ThreadedGamePanel(GameBoard gameModel) {
		super(gameModel);
		blinkTimer = new Timer(500, e -> {
			currentHighlightColor = (currentHighlightColor != null) ? null : Color.LIGHT_GRAY;
			repaint();
		});
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

	public Color getCurrentHightlightColor() {
		return currentHighlightColor;
	}
}
