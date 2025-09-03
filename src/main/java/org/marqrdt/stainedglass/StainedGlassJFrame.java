package org.marqrdt.stainedglass;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class StainedGlassJFrame extends JFrame {

	private JPanel contentPane;
	private JTextField moveCounterTextField;
	private JTextField hiScoretextField;
	private GameBoard gameSquares;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StainedGlassJFrame frame = new StainedGlassJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JPanel getGamePanel() {
		return new GamePanel(gameSquares);
	}

	/**
	 * Create the frame.
	 */
	public StainedGlassJFrame() throws IOException {
		Color colors[] = { Color.RED, Color.YELLOW, Color.ORANGE, Color.BLUE,
				Color.MAGENTA, Color.GREEN };
		// private Color colors[] = { Color.RED, Color.YELLOW, new
		// Color(Color.RED.getRGB() + Color.YELLOW.getRGB()), Color.BLUE,
		// new Color(Color.RED.getRGB() + Color.BLUE.getRGB()), new
		// Color(Color.BLUE.getRGB() + Color.YELLOW.getRGB()) };
		int defaultBoardWidth = 16; // the number of horizontal squares on the
									// board
		int defaultBoardHeight = 8; // the number of vertical squares on the
									// board
		int defaultSquareSize = 50; // the size in pixels of each square
		// Print out resources on classpath for debugging
		Pattern pattern = Pattern.compile(".*");
		Iterator<String> resourceIt = ResourceList.getResources(pattern).iterator();
		while ( resourceIt.hasNext() ) {
			System.out.println( resourceIt.next() );
		}
		//
		
		String gamePropFile = "stainedGlass.properties";
		//String gamePropFile = "/Users/marqrdt/Documents/workspace-sts-3.2.0.RELEASE/StainedGlass/src/META-INF/stainedGlass.properties";
		//Properties gameProps = GameProperties.loadProperties(gamePropFile,
		//		Thread.currentThread().getClass().getClassLoader());
		Properties gameProps = new Properties();
		InputStream in = this.getClass().getClassLoader().getResourceAsStream( gamePropFile);
		//InputStream in = new FileInputStream(gamePropFile);
		
		if ( in == null ) {
			throw new FileNotFoundException("property file '" + gamePropFile + "' not found in the classpath");
		} else {
			gameProps.load( in );
		}
		this.gameSquares = new GameBoard(Integer.parseInt(gameProps
				.getProperty("boardWidth", String.valueOf(defaultBoardWidth))),
				Integer.parseInt(gameProps.getProperty("boardHeight",
						String.valueOf(defaultBoardHeight))),
				Integer.parseInt(gameProps.getProperty("squareSize",
						String.valueOf(defaultSquareSize))));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFileMenu = new JMenu("File");
		menuBar.add(mnFileMenu);

		JMenuItem newGameNewMenuItem = new JMenuItem("New game...");
		mnFileMenu.add(newGameNewMenuItem);
		JMenu mnEditMenu = new JMenu("Edit");
		menuBar.add(mnEditMenu);
		JMenu mnGameMenu = new JMenu("Game");
		menuBar.add(mnGameMenu);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("Moves");
		toolBar.add(lblNewLabel);

		moveCounterTextField = new JTextField();
		toolBar.add(moveCounterTextField);
		moveCounterTextField.setColumns(10);
		moveCounterTextField.setEditable(false);

		JLabel hiScoreLabel = new JLabel("Hi Score");
		toolBar.add(hiScoreLabel);

		hiScoretextField = new JTextField();
		toolBar.add(hiScoretextField);
		hiScoretextField.setColumns(10);
		hiScoretextField.setEditable(false);

		// JPanel panel = new JPanel();
		JPanel gamePanel = getGamePanel();
		;

		contentPane.add(gamePanel, BorderLayout.CENTER);
	}

}
