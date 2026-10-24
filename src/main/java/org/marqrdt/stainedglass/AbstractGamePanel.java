package org.marqrdt.stainedglass;

import javax.swing.JPanel;

/* The abstract method for the main game panel. Implements GameBoardListener so
 * that its subclasses can listen for changes to the data model. */
public abstract class AbstractGamePanel extends JPanel implements
		GameBoardListener {
	GameBoard gameBoardModel; // the data model that this class listens to for
								// changes

	public AbstractGamePanel(GameBoard dataModel) {
		super();
		this.gameBoardModel = dataModel;
	}

	public GameBoard getModel() {
		return gameBoardModel;
	}

	public void gameBoardChanged(GameBoard board) {
		updateDisplay();
	}

	protected abstract void updateDisplay();
}
