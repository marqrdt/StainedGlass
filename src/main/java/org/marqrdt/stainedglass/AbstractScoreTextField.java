package org.marqrdt.stainedglass;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextField;

public abstract class AbstractScoreTextField extends JTextField implements
		Observer {
	GameBoard gameBoardModel;

	public AbstractScoreTextField(GameBoard dataModel) {
		super();
		gameBoardModel = dataModel;
		gameBoardModel.addObserver(this);
		this.setEditable(false);
		// this.setBackground( getParent() );
		this.setText(Integer.toString(gameBoardModel.getScore()));
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