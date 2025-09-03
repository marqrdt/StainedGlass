package org.marqrdt.stainedglass;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Created on Feb 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author marqrdt
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */

/*
 * This program is a port of a game that I used to play on my old Mac. The
 * machine will still boot, even after 10+ years, but I decided it was time to
 * port it to a modern OS. The game is simple. An x by y board (here, 8 x 16) is
 * populated with squares, each with a random color chosen from the three
 * primary colors (blue, red and yellow) as well as the combinations of any two
 * of the primary colors (purple, green, orange). The objective of the game is
 * to leave only one primary-colored square in as few moves as possible. The
 * rules for clearing squares are as follows: -If the current square (indicated
 * by a black dot in the center of the square) is a primary color, it may jump
 * over any primary color or a composite color (green, purple or orange)
 * containing the current square's color. It may only land on a primary color,
 * turning that square's color to the sum of the two colors, or an empty square.
 * Jumping over a composite color removes that color from the composite color.
 * For example, jumping a blue square over a green square tumrs the green square
 * yellow. -If the current square is a compositie color, it may only jump over a
 * square of the same color and land on an empty square or a square of the smae
 * color.
 * 
 * @author marqrdt
 */
public class StainedGlassGame extends JFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null; // @jve:decl-index=0:visual-constraint="63,682"
	private JFrame jFrame = null; // @jve:decl-index=0:visual-constraint="323,503"
	private JPanel jPanel = null;
	private JButton jButton = null; // @jve:decl-index=0:visual-constraint="32,20"
	private JLabel jLabel = null; // @jve:decl-index=0:visual-constraint="469,32"
	/* global variables for game parameters */
	// an array of all primary colors (red, yellow, blue)
	// and combinations of any two (orange, magenta, green)
	private Color colors[] = { Color.RED, Color.YELLOW, Color.ORANGE,
			Color.BLUE, Color.MAGENTA, Color.GREEN };
	// private Color colors[] = { Color.RED, Color.YELLOW, new
	// Color(Color.RED.getRGB() + Color.YELLOW.getRGB()), Color.BLUE,
	// new Color(Color.RED.getRGB() + Color.BLUE.getRGB()), new
	// Color(Color.BLUE.getRGB() + Color.YELLOW.getRGB()) };
	int defaultBoardWidth = 16; // the number of horizontal squares on the board
	int defaultBoardHeight = 8; // the number of vertical squares on the board
	int defaultSquareSize = 50; // the size in pixels of each square
	String gamePropFile = "stainedGlass";
	Properties gameProps = GameProperties.loadProperties(gamePropFile, Thread
			.currentThread().getClass().getClassLoader());

	public StainedGlassGame() {
		super("Stained Glass");
		JFrame.setDefaultLookAndFeelDecorated(true);
		// initialize();
		// setSize( new Dimension( boardWidth * squareSize, boardHeight *
		// squareSize ) );
		// Box buttonBox contains the buttons used to control the game
		// and to display score data.
		Box buttonBox = javax.swing.Box.createHorizontalBox();
		GameBoard gameSquares = new GameBoard(Integer.parseInt(gameProps
				.getProperty("boardWidth", String.valueOf(defaultBoardWidth))),
				Integer.parseInt(gameProps.getProperty("boardHeight",
						String.valueOf(defaultBoardHeight))),
				Integer.parseInt(gameProps.getProperty("squareSize",
						String.valueOf(defaultSquareSize))));
		buttonBox.setAlignmentX(Box.CENTER_ALIGNMENT);
		buttonBox.add(Box.createHorizontalStrut(5));
		buttonBox.add(new JLabel("Score"));
		buttonBox.add(Box.createHorizontalStrut(5));
		buttonBox.add(new ScoreTextField(gameSquares));
		buttonBox.add(Box.createHorizontalStrut(5));
		buttonBox.add(getNewGameButton(gameSquares));
		buttonBox.add(Box.createHorizontalStrut(5));
		buttonBox.add(new JLabel("Hi Score"));
		buttonBox.add(Box.createHorizontalStrut(5));
		buttonBox.add(getUndoButton(gameSquares));
		GamePanel gamePanel = (GamePanel) getJPanel(gameSquares);
		/*
		 * explanation of determining the horizontal size of the Board
		 * Component: (number of squares = 8) * (size of each square) + (number
		 * of spaces in between squares (boardWidth-1 = 7)) * (size of space
		 * between squares) The same applies for the vertical size.
		 */

		// this.getContentPane().setLayout( new BorderLayout() );
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(new BorderDecorator(buttonBox),
				BorderLayout.NORTH);
		// this.getContentPane().add( buttonBox );
		this.getContentPane().add(new BorderDecorator(gamePanel),
				BorderLayout.CENTER);
		// this.getContentPane().add( gamePanel );

		// gamePanel.setSize( boardWidth * squareSize + (boardWidth - 1 * 2),
		// boardHeight * squareSize + (boardHeight - 1 * 2) );
		// this.setSize( 600, 800 );
		// gamePanel.updateDisplay();
		System.out.println(gamePanel.getWidth());
		System.out.println(gamePanel.getHeight());
		this.setSize(gamePanel.getWidth(),
				gamePanel.getHeight() + buttonBox.getHeight());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	// private void initialize() {
	// this.setSize( boardWidth * squareSize, boardHeight * squareSize +
	// buttonBox. );
	//
	// }
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	/*
	 * private JPanel getJContentPane() { if (jContentPane == null) {
	 * jContentPane = new JPanel(); jContentPane.setLayout(new BorderLayout());
	 * jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH); } return
	 * jContentPane; }
	 */
	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	/*
	 * private JFrame getJFrame() { if (jFrame == null) { jFrame = new JFrame();
	 * jFrame.setTitle("jFrame"); jFrame.setSize(600, 300); } return jFrame; }
	 */
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel(GameBoard dataModel) {
		if (jPanel == null) {
			jPanel = new GamePanel(dataModel);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getNewGameButton(GameBoard gameSquares) {
		final GameBoard game = gameSquares;
		JButton newGameButton;
		newGameButton = new JButton();
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				game.reinit();
			}
		});
		// newGameButton.setSize(150, 50);
		newGameButton.setText("New game");
		return newGameButton;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getUndoButton(GameBoard gameSquares) {
		final GameBoard game = gameSquares;
		JButton undoButton;
		undoButton = new JButton();
		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				game.undo();
			}
		});
		// jButton.setSize(150, 50);
		undoButton.setText("Undo");
		return undoButton;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new JLabel();
			jLabel.setSize(184, 44);
			jLabel.setText("Moves");
		}
		return jLabel;
	}

	public int getDefaultBoardWidth() {
		return defaultBoardWidth;
	}

	public void setDefaultBoardWidth(int defaultBoardWidth) {
		this.defaultBoardWidth = defaultBoardWidth;
	}

	/* 
	 * 
	 * */
	// pretty strighforward run loop...
	public static void main(String args[]) {
		StainedGlassGame app = new StainedGlassGame();
		// app.setSize(600,800);
		// app.show();
		app.setVisible(true);
	}
}
