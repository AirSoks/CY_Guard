package gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import config.GameConfiguration;
import engine.map.Coordonnee;
import engine.map.Grille;
import engine.map.generation.GrilleBuilder;
import engine.personnage.Gardien;
import engine.personnage.PersonnageManager;

public class MainGUI extends JFrame implements Runnable{
	
	private static Dimension preferredSize = new Dimension(GameConfiguration.WINDOW_WIDTH,GameConfiguration.WINDOW_HEIGHT);
	
	private Grille grille;
	private Gardien gardien;
	
	private GameDisplay dashboard;
	
	private PersonnageManager manager;
	
	public MainGUI(String title) throws HeadlessException {
		super(title);
		init();
	}

	private void init() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		GrilleBuilder mapBuilder = new GrilleBuilder();
	    this.grille = mapBuilder.getGrille();
	    
	    Coordonnee position = grille.getCase(0, 0).getPosition();
	    this.gardien = new Gardien(position);
	    
	    this.manager = new PersonnageManager(grille, this.gardien);
	    
		dashboard = new GameDisplay(this.grille, gardien);
		dashboard.setPreferredSize(preferredSize);
		contentPane.add(dashboard,BorderLayout.CENTER);
		
		JTextField textField = new JTextField();
        textField.addKeyListener(new KeyControls());
        contentPane.add(textField, BorderLayout.SOUTH);
		
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
		    int keyCode = e.getKeyCode();
		    switch (keyCode) {
			
		    case KeyEvent.VK_LEFT: // Flèche gauche
	        case KeyEvent.VK_Q:
	        case KeyEvent.VK_A:
	            manager.moveLeftGardien();
	            break;
	        
	        case KeyEvent.VK_RIGHT: // Flèche droit
	        case KeyEvent.VK_D:
	            manager.moveRightGardien();
	            break;
	        
	        case KeyEvent.VK_UP: // Flèche haut
	        case KeyEvent.VK_Z:
	        case KeyEvent.VK_W:
	            manager.moveUpGardien();
	            break;
	        
	        case KeyEvent.VK_DOWN: // Flèche bas
	        case KeyEvent.VK_S:
	            manager.moveDownGardien();
	            break;
			}
			dashboard.repaint();
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}

	}
	
}
