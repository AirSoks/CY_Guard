package gui;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import config.GameConfiguration;
import map.Grille;
import map.GrilleBuilder;
import personnage.PersonnageManager;

public class MainGUI extends JFrame implements Runnable{
	
	private static Dimension preferredSize = new Dimension(GameConfiguration.WINDOW_WIDTH,GameConfiguration.WINDOW_HEIGHT);
	
	private Grille grille;
	
	private GameDisplay dashboard;
	
	private PersonnageManager manager;
	
	public MainGUI(String title) throws HeadlessException {
		super(title);
		this.grille = new Grille(GameConfiguration.WINDOW_WIDTH,GameConfiguration.WINDOW_HEIGHT);
		init();
	}

	private void init() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		GrilleBuilder mapBuilder = new GrilleBuilder();
	    this.grille = mapBuilder.getGrille();
		
		dashboard = new GameDisplay(this.grille);
		
		dashboard.setPreferredSize(preferredSize);
		contentPane.add(dashboard,BorderLayout.CENTER);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setPreferredSize(preferredSize);
		setResizable(false);
		
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(GameConfiguration.GAME_SPEED);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			dashboard.repaint();
		}
		
	}
	
	public class KeyControls implements KeyListener{
		
		@Override
		public void keyPressed(KeyEvent e) {
			char keychar = e.getKeyChar();
			switch (keychar) {
			
			case 'a':
				manager.moveLeftGardien();
				break;
			
			case 'd':
				manager.moveRightGardien();
				break;
				
			case 'w':
				manager.moveUpGardien();
				break;
				
			case 's':
				manager.moveDownGardien();
				break;
			default:
				break;
			}
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}

	}
}
