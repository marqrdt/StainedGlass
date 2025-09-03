package org.marqrdt.stainedglass;

import java.awt.Color;

public class GameColor extends Color {
	public GameColor(Color c) {
		super(c.getRGB());
	}

	public boolean isPrimaryColor() {
		return (this.equals(Color.BLUE)) || this.equals(Color.RED)
				|| this.equals(Color.YELLOW);
	}

	public boolean contains(GameColor c) {
		if (this.isPrimaryColor() && c.isPrimaryColor()) {
			return true;
		}
		// a color always contains itself
		if (this.equals(c)) {
			return true;
		}
		// Magenta (purple) contains red and blue
		else if (this.getRGB() == Color.MAGENTA.getRGB()) {
			if (c.getRGB() == Color.BLUE.getRGB()
					|| c.getRGB() == Color.RED.getRGB()) {
				return true;
			}
		}
		// and so on...
		else if (this.getRGB() == Color.ORANGE.getRGB()) {
			if (c.getRGB() == Color.YELLOW.getRGB()
					|| c.getRGB() == Color.RED.getRGB()) {
				return true;
			}
		} else if (this.getRGB() == Color.GREEN.getRGB()) {
			if (c.getRGB() == Color.YELLOW.getRGB()
					|| c.getRGB() == Color.BLUE.getRGB()) {
				return true;
			}
		}
		return false;
	}

	public GameColor difference(GameColor c) {
		/*
		 * use this method to set the color of a square that was jumped over. //
		 * jumping a primary color over a primary color always turns it white.
		 * Any color, // primary or composite, can jump over itself.
		 */
		if ((this.isPrimaryColor() && c.isPrimaryColor()) || this.equals(c)) {
			return new GameColor(Color.WHITE);
		}
		if (this.equals(Color.ORANGE)) {
			if (c.equals(Color.RED)) {
				return new GameColor(Color.YELLOW);
			}
			if (c.equals(Color.YELLOW)) {
				return new GameColor(Color.RED);
			}
		}
		if (this.equals(Color.GREEN)) {
			if (c.equals(Color.BLUE)) {
				return new GameColor(Color.YELLOW);
			}
			if (c.equals(Color.YELLOW)) {
				return new GameColor(Color.BLUE);
			}
		}
		if (this.equals(Color.MAGENTA)) {
			if (c.equals(Color.RED)) {
				return new GameColor(Color.BLUE);
			}
			if (c.equals(Color.BLUE)) {
				return new GameColor(Color.RED);
			}
		}
		return new GameColor(Color.WHITE);
	}

	public GameColor sum(Color c) {
		// adding a color to itself returns that color.
		if (this.equals(c)) {
			return this;
		}
		if (this.equals(Color.RED)) {
			if (c.equals(Color.YELLOW)) {
				return new GameColor(Color.ORANGE);
			}
			if (c.equals(Color.BLUE)) {
				return new GameColor(Color.MAGENTA);
			}
		}
		if (this.equals(Color.BLUE)) {
			if (c.equals(Color.YELLOW)) {
				return new GameColor(Color.GREEN);
			}
			if (c.equals(Color.RED)) {
				return new GameColor(Color.MAGENTA);
			}
		}
		if (this.equals(Color.YELLOW)) {
			if (c.equals(Color.RED)) {
				return new GameColor(Color.ORANGE);
			}
			if (c.equals(Color.BLUE)) {
				return new GameColor(Color.GREEN);
			}
		}
		// adding someColor to WHITE returns someColor.
		if (c.equals(Color.WHITE)) {
			return this;
		}
		return null;
	}
}