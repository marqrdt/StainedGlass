package org.marqrdt.stainedglass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends ThreadedGamePanel implements MouseListener {

	int panelWidth = 600;
	int panelHeight = 240;
	private GameBoard model;

	public GamePanel(GameBoard gameModel) {
		super(gameModel);
		super.setBackground(Color.WHITE);
		this.setModel(gameModel);
		this.getModel().addObserver(this);
		this.addMouseListener(this);
		int gutterX = 12; /*
						 * gutter between the inner edge of the frame and this
						 * component
						 */
		this.setSize(gameModel.getBoardWidth() * gameModel.getSquareSize(),
				gameModel.getBoardHeight() * gameModel.getSquareSize());
		this.setVisible(true);
	}

	public void paintComponent(Graphics g) {
		// int currentX = 0, currentY = 0;
		super.paintComponent(g);
		// java.util.Enumeration enumSquares =
		// getModel().squareVector.elements();
		// global variables boardWidth and boardHeight
		/*
		 * while( enumSquares.hasMoreElements() ) { Square sq = (Square)
		 * enumSquares.nextElement(); g.setColor( sq.getColor() ); g.fill3DRect(
		 * sq.getPoint().x, sq.getPoint().y, squareSize, squareSize, true ); //
		 * if this square is has been selected by a mouse click, draw a black //
		 * circle inside of it. if ( getModel().squareVector.indexOf( sq ) ==
		 * getModel().getSelectedIndex() ) { //System.out.println(
		 * "I clicked on Square " + getModel().getSelectedIndex() ); g.setColor(
		 * Color.BLACK ); g.fillOval( sq.getPoint().x + 8, sq.getPoint().y + 8,
		 * squareSize - 16, squareSize - 16 ); g.setColor( sq.getColor() ); } //
		 * global variable squareSize }
		 */
		// display each vertical column of squares
		for (int i = 0; i < this.getModel().getBoardWidth(); i++) {
			for (int j = 0; j < this.getModel().getBoardHeight(); j++) {
				Point currentPoint = new Point(i, j);
				Square sq = getModel().getSquareAtPoint(currentPoint);
				if (sq != null) {
					g.setColor(sq.getColor());
					// draw each square at the current (x,y) point.
					g.fill3DRect(sq.getPoint().x, sq.getPoint().y, this
							.getModel().getSquareSize(), this.getModel()
							.getSquareSize(), true);
					// if this square is has been selected by a mouse click,
					// draw a black
					// circle inside of it.
					if (getModel().getSelectedSquarePosition() != null
							&& getModel().getSelectedSquarePosition().equals(
									currentPoint)) {
						// System.out.println( "I clicked on Square " +
						// getModel().getSelectedIndex() );
						g.setColor(Color.BLACK);
						g.fillOval(sq.getPoint().x + 8, sq.getPoint().y + 8,
								this.getModel().getSquareSize() - 16, this
										.getModel().getSquareSize() - 16);
						g.setColor(sq.getColor());
					}
					// draw a gray circle inside of potentail legal moves
					// adjacent to the
					// currently selected square.
					if (getModel().getSelectedSquarePosition() != null
							&& getModel().isLegalMove(
									getModel().getSelectedSquarePosition(),
									currentPoint)) {
						if (getCurrentHightlightColor() != null) {
							g.setColor(getCurrentHightlightColor());
						}
						g.fillOval(sq.getPoint().x + 8, sq.getPoint().y + 8,
								this.getModel().getSquareSize() - 16, this
										.getModel().getSquareSize() - 16);
						g.setColor(sq.getColor());
					}
				}
				// global variable squareSize
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("You clicked on me");
		/*
		 * when the mouse is clicked, determine which square is under the mouse
		 * pointer
		 */
		Point squarePosition = getModel()
				.getSquarePositionAtPoint(e.getPoint());
		if (squarePosition != null) {
			// on a mouse click, stop the running thread...
			// stop();
			start();
			getModel().jump(squarePosition);
			// stop();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("The mouse entered me");
		Point squarePosition = getModel()
				.getSquarePositionAtPoint(e.getPoint());
		if (squarePosition != null) {

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void updateDisplay() {
		repaint();
	}

	public GameBoard getModel() {
		return model;
	}

	public void setModel(GameBoard model) {
		this.model = model;
	}
}