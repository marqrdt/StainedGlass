package org.marqrdt.stainedglass;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class GamePanel extends ThreadedGamePanel implements MouseListener {

	// caches the per-color/per-state square icons loaded from
	// META-INF/icons so that they are only read from the classpath once.
	private static final Map<String, Image> ICON_CACHE = new HashMap<String, Image>();

	int panelWidth = 600;
	int panelHeight = 240;
	private GameBoard model;

	public GamePanel(GameBoard gameModel) {
		super(gameModel);
		super.setBackground(Color.WHITE);
		this.setModel(gameModel);
		this.getModel().addGameBoardListener(this);
		this.addMouseListener(this);
		int gutterX = 12; /*
						 * gutter between the inner edge of the frame and this
						 * component
						 */
		this.setSize(gameModel.getBoardWidth() * gameModel.getSquareSize(),
				gameModel.getBoardHeight() * gameModel.getSquareSize());
		this.setVisible(true);
	}

	public Dimension getPreferredSize() {
		return new Dimension(getModel().getBoardWidth()
				* getModel().getSquareSize(), getModel().getBoardHeight()
				* getModel().getSquareSize());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		int squareSize = this.getModel().getSquareSize();
		// display each vertical column of squares
		for (int i = 0; i < this.getModel().getBoardWidth(); i++) {
			for (int j = 0; j < this.getModel().getBoardHeight(); j++) {
				Point currentPoint = new Point(i, j);
				Square sq = getModel().getSquareAtPoint(currentPoint);
				if (sq == null) {
					continue;
				}

				boolean blinkOn = getCurrentHightlightColor() != null;
				boolean isSelected = getModel().getSelectedSquarePosition() != null
						&& getModel().getSelectedSquarePosition().equals(
								currentPoint);
				boolean showTargetIndicator = !isSelected
						&& getModel().getSelectedSquarePosition() != null
						&& getModel().isLegalMove(
								getModel().getSelectedSquarePosition(),
								currentPoint) && blinkOn;

				String colorName = sq.getColor().getIconColorName();
				if (colorName != null) {
					String state = isSelected ? "selected"
							: (showTargetIndicator ? "target" : "unselected");
					Image icon = getSquareIcon(colorName, state);
					if (icon != null) {
						g2d.drawImage(icon, sq.getPoint().x, sq.getPoint().y,
								squareSize, squareSize, this);
					} else {
						// fall back to a flat fill if the icon resource is missing.
						g2d.setColor(sq.getColor());
						g2d.fillRect(sq.getPoint().x, sq.getPoint().y,
								squareSize, squareSize);
					}
				} else if (isSelected || showTargetIndicator) {
					// an empty (white) square has no icon asset, but it can
					// still be a legal move's source or target, so draw a
					// simple indicator dot directly so the move is not
					// hidden from the player.
					int inset = squareSize / 8;
					g2d.setColor(isSelected ? Color.BLACK
							: getCurrentHightlightColor());
					g2d.fillOval(sq.getPoint().x + inset, sq.getPoint().y
							+ inset, squareSize - 2 * inset, squareSize - 2
							* inset);
				}
			}
		}
	}

	private static Image getSquareIcon(String colorName, String state) {
		String key = colorName + "_" + state;
		if (!ICON_CACHE.containsKey(key)) {
			String path = "META-INF/icons/" + colorName + "_square_" + state
					+ ".png";
			URL url = GamePanel.class.getClassLoader().getResource(path);
			ICON_CACHE.put(key, (url != null) ? new ImageIcon(url).getImage()
					: null);
		}
		return ICON_CACHE.get(key);
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