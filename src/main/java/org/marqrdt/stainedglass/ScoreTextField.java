package org.marqrdt.stainedglass;

public class ScoreTextField extends AbstractScoreTextField {

	public ScoreTextField(GameBoard dataModel) {
		super(dataModel);
	}

	public void updateDisplay() {
		this.setText(Integer.toString(getModel().getScore()));
	}
}