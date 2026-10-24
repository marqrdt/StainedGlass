package org.marqrdt.stainedglass;

import javax.swing.JTextField;

public abstract class AbstractScoreTextField extends JTextField implements
		GameBoardListener {
	GameBoard gameBoardModel;

	public AbstractScoreTextField(GameBoard dataModel) {
		super();
		gameBoardModel = dataModel;
		gameBoardModel.addGameBoardListener(this);
		this.setEditable(false);
		// this.setBackground( getParent() );
		this.setText(Integer.toString(gameBoardModel.getScore()));
	}

	public GameBoard getModel() {
		return gameBoardModel;
	}

	public void gameBoardChanged(GameBoard board) {
		updateDisplay();
	}

	protected abstract void updateDisplay();
}