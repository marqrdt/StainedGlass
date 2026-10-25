package org.marqrdt.stainedglass;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class StainedGlassJFrame extends JFrame {

	private JPanel contentPane;
	private JLabel moveCounterLabel;
	private GameBoard gameSquares;
	private String appVersion = "unknown";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LookAndFeelSupport.installNimbus();
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

		String gamePropFile = "META-INF/stainedGlass.properties";
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
		this.appVersion = gameProps.getProperty("version", appVersion);
		this.gameSquares = new GameBoard(Integer.parseInt(gameProps
				.getProperty("boardWidth", String.valueOf(defaultBoardWidth))),
				Integer.parseInt(gameProps.getProperty("boardHeight",
						String.valueOf(defaultBoardHeight))),
				Integer.parseInt(gameProps.getProperty("squareSize",
						String.valueOf(defaultSquareSize))));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFileMenu = new JMenu("File");
		menuBar.add(mnFileMenu);

		JMenuItem newGameNewMenuItem = new JMenuItem("New game...");
		newGameNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
		newGameNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameSquares.reinit();
			}
		});
		mnFileMenu.add(newGameNewMenuItem);

		JMenuItem quitMenuItem = new JMenuItem("Quit");
		quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
		quitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFileMenu.add(quitMenuItem);

		JMenu mnEditMenu = new JMenu("Edit");
		menuBar.add(mnEditMenu);

		JMenuItem undoMenuItem = new JMenuItem("Undo");
		undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
		undoMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameSquares.undo();
			}
		});
		mnEditMenu.add(undoMenuItem);

		JMenu mnHelpMenu = new JMenu("Help");
		menuBar.add(mnHelpMenu);

		JMenuItem aboutMenuItem = new JMenuItem("About StainedGlass");
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAboutDialog();
			}
		});
		mnHelpMenu.add(aboutMenuItem);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBorder(new EmptyBorder(4, 8, 4, 8));
		contentPane.add(toolBar, BorderLayout.SOUTH);

		JLabel lblNewLabel = new JLabel("Moves:");
		toolBar.add(lblNewLabel);
		toolBar.add(Box.createHorizontalStrut(6));

		moveCounterLabel = new JLabel(String.valueOf(gameSquares.getScore()));
		toolBar.add(moveCounterLabel);

		gameSquares.addGameBoardListener(new GameBoardListener() {
			public void gameBoardChanged(GameBoard board) {
				moveCounterLabel.setText(String.valueOf(board.getScore()));
			}
		});

		// JPanel panel = new JPanel();
		JPanel gamePanel = getGamePanel();

		contentPane.add(gamePanel, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
	}

	private void showAboutDialog() {
		String message = "StainedGlass\n"
				+ "Version: " + appVersion + "\n\n"
				+ "A Java port of a horribly addictive game for old\n"
				+ "Motorola 68K Macs.\n\n"
				+ "Author: marqrdt";
		JOptionPane.showMessageDialog(this, message, "About StainedGlass",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
