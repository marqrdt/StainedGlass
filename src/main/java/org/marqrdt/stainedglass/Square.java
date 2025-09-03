package org.marqrdt.stainedglass;

import java.awt.Color;
import java.awt.Point;

public class Square extends Object {
	private GameColor colorOfThisSquare;
	Point point;

	public Square() {
		this.colorOfThisSquare = new GameColor(Color.BLUE);
	}

	public Square(Color c) {
		this.colorOfThisSquare = new GameColor(c);
		setPoint(new Point(0, 0));
	}

	public Square(Color c, Point p) {
		this.setColor(c);
		this.setPoint(p);
	}

	public GameColor getColor() {
		return this.colorOfThisSquare;
	}

	public void setColor(Color colorToSet) {
		this.colorOfThisSquare = new GameColor(colorToSet);
	}

	public Point getPoint() {
		return this.point;
	}

	public void setPoint(Point pointIn) {
		this.point = pointIn;
	}

	public boolean equals(Square sq) {
		return this.getPoint().equals(sq.getPoint());
	}
}
