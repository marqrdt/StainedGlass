package org.marqrdt.stainedglass;

import java.util.Observable;

import javax.swing.JPanel;

/* The abstract method for the main game panel. Implements Observer so that its
 * subclasses can listen for changes to the data model. */
public abstract class AbstractGamePanel extends JPanel implements
		java.util.Observer {
	GameBoard gameBoardModel; // the data model that this class listens to for
								// changes

	public AbstractGamePanel(GameBoard dataModel) {
		super();
		this.gameBoardModel = dataModel;
	}

	public GameBoard getModel() {
		return gameBoardModel;
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		updateDisplay();
	}

	protected abstract void updateDisplay();
}
