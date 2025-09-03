package org.marqrdt.stainedglass;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

public class GameBoard extends java.util.Observable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private Vector squareVector = new Vector();
	private java.util.Stack undoStack = new java.util.Stack();
	private Square[][] squares;
	private Dimension gameBoardSize;
	private int boardWidth = 16; // the number of horizontal squares on the board
	private int boardHeight = 8; // the number of vertical squares on the board
	private int squareSize = 50; // the size in pixels of each square
	private Color colors[] = { Color.RED, Color.YELLOW, Color.ORANGE,
			Color.BLUE, Color.MAGENTA, Color.GREEN };
	// Point p;
	int currentX = 0, currentY = 0;
	Point selectedSquarePosition = null;
	Point previousSelectedSquarePosition = null;
	int numberOfMoves = 0;

	public GameBoard(int horizontalSize, int verticalSize, int squareSize) {
		this.setBoardHeight(verticalSize);
		this.setBoardWidth(horizontalSize);
		this.setSquareSize(squareSize);
		squares = new Square[this.getBoardWidth()][this.getBoardHeight()];
		for (int i = 0; i < this.getBoardWidth(); i++) {
			currentX = i * this.getSquareSize();
			for (int j = 0; j < this.getBoardHeight(); j++) {
				currentY = j * this.getSquareSize();
				Point p = new Point(currentX, currentY);
				Color squareColor = colors[(int) (Math.random() * colors.length)];
				// System.out.println( "adding a Square at " + i * boardWidth +
				// j);
				// squareVector.add( (i * boardWidth) + j, new Square(
				// squareColor, p ) );
				// squareVector.add( new Square( squareColor, p ) );
				squares[i][j] = new Square(squareColor, p);
			}
		}
	}

	// this method is called to reinitialize the game board, i.e. when
	// the user clicks on "New Game"
	public void reinit() {
		for (int i = 0; i < this.getBoardWidth(); i++) {
			currentX = i * this.getSquareSize();
			for (int j = 0; j < this.getBoardHeight(); j++) {
				currentY = j * this.getSquareSize();
				Point p = new Point(currentX, currentY);
				Color squareColor = colors[(int) (Math.random() * colors.length)];
				// System.out.println( "adding a Square at " + i * boardWidth +
				// j);
				// squareVector.add( (i * boardWidth) + j, new Square(
				// squareColor, p ) );
				// squareVector.add( new Square( squareColor, p ) );
				squares[i][j] = new Square(squareColor, p);
			}
		}
		previousSelectedSquarePosition = null;
		selectedSquarePosition = null;
		numberOfMoves = 0;
		setChanged();
		notifyObservers();
	}

	public Point getSquarePositionAtPoint(Point p) {
		int currentX = 0, currentY = 0;
		Rectangle rect;
		for (int i = 0; i < this.getBoardWidth(); i++) {
			for (int j = 0; j < this.getBoardHeight(); j++) {
				Square sq = squares[i][j];
				if (sq != null) {
					rect = new Rectangle(sq.getPoint().x, sq.getPoint().y,
							this.getSquareSize(), this.getSquareSize());
					if (rect.contains(p)) {
						return new Point(i, j);
					}
				}
			}
		}
		return null;
	}

	public Dimension getBoardSize() {
		return new Dimension(squares.length, squares[0].length);
	}

	public void jump(Point targetPosition) {
		// java.util.Enumeration enumSquares = squareVector.elements();
		if (targetPosition != null && targetPosition.x < squares.length
				&& targetPosition.y < squares[0].length) {
			Square targetSquare = squares[targetPosition.x][targetPosition.y];
			// if selectedSquarePosition is null, set it to targetPoint, notify
			// Observers and return.
			if (selectedSquarePosition == null) {
				System.out.println("the previous point is null, setting it to "
						+ targetPosition.toString());
				selectedSquarePosition = targetPosition;
				setChanged();
				notifyObservers();
				return; // nothing more to do, exit from this method.
			} else if (targetSquare == null) {
				System.out.println("the square at point "
						+ targetPosition.toString() + " is null");
				return;
			} else if (isLegalMove(selectedSquarePosition, targetPosition)) {
				Square sourceSquare = getSquareAtPoint(selectedSquarePosition);
				Square intermediateSquare = squares[selectedSquarePosition.x
						+ ((selectedSquarePosition.x - targetPosition.x) / -2)][selectedSquarePosition.y
						+ ((selectedSquarePosition.y - targetPosition.y) / -2)];
				System.out.println("jumping a "
						+ sourceSquare.getColor().toString() + " square at "
						+ selectedSquarePosition.toString() + " to a "
						+ targetSquare.getColor().toString() + " square at "
						+ targetSquare.point.toString());
				if (sourceSquare != null && intermediateSquare != null
						&& targetSquare != null) {
					GameColor newTargetSquareColor = sourceSquare.getColor()
							.sum(targetSquare.getColor());
					GameColor newIntermediateSquareColor = intermediateSquare
							.getColor().difference(sourceSquare.getColor());
					if (newTargetSquareColor != null
							&& newIntermediateSquareColor != null) {
						// push the SavedMove object moveState onto the undo
						// stack.
						SavedMove savedMove = new SavedMove(
								selectedSquarePosition,
								sourceSquare.getColor(), targetPosition);
						undoStack.push(savedMove);
						targetSquare.setColor(sourceSquare.getColor().sum(
								targetSquare.getColor()));
						intermediateSquare.setColor(newIntermediateSquareColor);
						// clear the source square-- set color to white.
						sourceSquare.setColor(new GameColor(Color.WHITE));
						// save the move in the undo stack.
					} else {
						System.out.println("the sum of "
								+ sourceSquare.getColor().toString() + " and "
								+ targetSquare.getColor().toString()
								+ " returns null");
					}
				}
				numberOfMoves++;
			} else {
				System.out.println("cannot jump square at "
						+ selectedSquarePosition.toString() + " to "
						+ targetPosition.toString());
			}
		} else {
			System.out.println("target point is null. This should not happen!");
		}
		// selectedSquarePosition = targetPosition;
		// set the selectedSquarePosition to null so that the end of one move
		// does not signal the beginning of the next and the user must select
		// the next source
		// square with a mouse click, even if he just landed on it.
		selectedSquarePosition = null;
		setChanged();
		notifyObservers();
	} // end method jump()

	public Dimension getGameBoardSize() {
		return gameBoardSize;
	}

	public void setGameBoardSize(Dimension gameBoardSize) {
		this.gameBoardSize = gameBoardSize;
	}

	public int getNumberOfMoves() {
		return numberOfMoves;
	}

	public void setNumberOfMoves(int numberOfMoves) {
		this.numberOfMoves = numberOfMoves;
	}

	/*
	 * Method getSelectedSquarePosition() returns a Point corresponding to the
	 * Square that the user has last clicked on. The Point is constructed from
	 * the coordinates of the Square based on its x,y position in the grid
	 * (boardWidth x boardHeight), set to 16 x 8 by default.
	 */
	public Point getSelectedSquarePosition() {
		return selectedSquarePosition;
	}

	public Square getSquareAtPoint(Point p) {
		if (p.x < squares.length && p.y < squares[0].length) {
			return squares[p.x][p.y];
		}
		return null;
	}

	/*
	 * Method isLegalMove drives the main application logic. It returns true if
	 * the source position can jump to the target position. All positional logic
	 * is performed within the function, but most color logic dealing with
	 * primary and secondary colors is outsourced the the GameColor methods
	 * sum() and difference(), which return null if the colors passed in cannot
	 * be added or subtracted from eachother. Method isLegalMove takes a (Point)
	 * sourcePosition and (Point) targetPosition as parameters.
	 */
	public boolean isLegalMove(Point sourcePosition, Point targetPosition) {
		// Source and target square cannot be the same.
		if (sourcePosition.equals(targetPosition)) {
			return false;
		}
		// A move cannot originate on a blank (white) square.
		if (getSquareAtPoint(sourcePosition).getColor().equals(Color.WHITE)) {
			return false;
		}
		// limit moves where the target square (newPosition) is 2 squares
		// N,NE,E,SE,S,SW,W,NW
		// from the source square (previousPosition).
		if ((targetPosition.x == sourcePosition.x + 2
				|| targetPosition.x == sourcePosition.x || targetPosition.x == sourcePosition.x - 2)
				&& (targetPosition.y == sourcePosition.y + 2
						|| targetPosition.y == sourcePosition.y || targetPosition.y == sourcePosition.y - 2)) {
			// get the square in between the source and target position
			Square inBetweenSquare = getSquareAtPoint(new Point(
					sourcePosition.x
							+ ((targetPosition.x - sourcePosition.x) / 2),
					sourcePosition.y
							+ ((targetPosition.y - sourcePosition.y) / 2)));
			if (inBetweenSquare == null) {
				return false;
			}
			// cannot jump over a white square
			else {
				if (inBetweenSquare.getColor().equals(Color.WHITE)) {
					return false;
				}
			}
			/*
			 * use the logic from adding colors together to determine if a a
			 * move is legal. If the sum of a target and destination color is
			 * null, the move is not legal. For example, adding together Blue
			 * and Magenta returns null, so a Blue square cannot jump to a
			 * Magenta square, etc.
			 */
			GameColor targetSquareColor = getSquareAtPoint(sourcePosition)
					.getColor()
					.sum(getSquareAtPoint(targetPosition).getColor());
			if (targetSquareColor != null
					&& inBetweenSquare.getColor().contains(
							getSquareAtPoint(sourcePosition).getColor())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Method getScore returns the number of moves the user has performed. It is
	 * not the number of squares cleared.
	 */
	public int getScore() {
		return numberOfMoves;
	}

	/*
	 * Method undo pops a SavedMove object off of the undo Stack and sets the
	 * corresponding GameBoard squares back to their saved values. The only
	 * information needed for rolling back a move is
	 */
	public void undo() {
		if (!undoStack.isEmpty()) {
			// pop the last saved move off of the stack
			SavedMove move = (SavedMove) undoStack.pop();
			// reset the source square's color
			getSquareAtPoint(move.savedSourcePosition).setColor(
					move.savedSourceColor);
			Square inBetweenSquare = getSquareAtPoint(new Point(
					move.savedSourcePosition.x
							+ ((move.savedTargetPosition.x - move.savedSourcePosition.x) / 2),
					move.savedSourcePosition.y
							+ ((move.savedTargetPosition.y - move.savedSourcePosition.y) / 2)));
			// subtract the source square's color from the target square. It
			// should have been added
			// in the move that you're trying to undo.
			getSquareAtPoint(move.savedTargetPosition).setColor(
					getSquareAtPoint(move.savedTargetPosition).getColor()
							.difference(move.savedSourceColor));
			// add the source square's color to the intermediate square. It
			// should have been subtracted
			// in the move that you're trying to undo..
			inBetweenSquare.setColor(move.savedSourceColor.sum(inBetweenSquare
					.getColor()));
			// getSquareAtPoint( move.savedSourcePosition ).setColor(
			// getSquareAtPoint(move.savedSourcePosition).getColor() );
			selectedSquarePosition = move.savedSourcePosition;
			// by the way, you don't get credit for a move if you undo it...
			if (numberOfMoves > 0) {
				numberOfMoves--;
			}
			setChanged();
			notifyObservers();
		}
	}

	public int getBoardHeight() {
		return boardHeight;
	}

	public void setBoardHeight(int boardHeight) {
		this.boardHeight = boardHeight;
	}

	public int getBoardWidth() {
		return boardWidth;
	}

	public void setBoardWidth(int boardWidth) {
		this.boardWidth = boardWidth;
	}

	public int getSquareSize() {
		return squareSize;
	}

	public void setSquareSize(int squareSize) {
		this.squareSize = squareSize;
	}
}