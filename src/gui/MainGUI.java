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
import engine.map.Direction;
import engine.map.Grille;
import engine.map.generation.GrilleBuilder;
import engine.personnage.Gardien;
import engine.personnage.gestion.PersonnageApparition;
import engine.personnage.gestion.PersonnageManager;

public class MainGUI extends JFrame implements Runnable{
	
	private static Dimension preferredSize = new Dimension(GameConfiguration.WINDOW_WIDTH,GameConfiguration.WINDOW_HEIGHT);
	
	private Grille grille;
	private PersonnageManager personnages;
	
	private GameDisplay dashboard;
	
	public MainGUI(String title) throws HeadlessException {
		super(title);
		init();
	}

	private void init() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		GrilleBuilder mapBuilder = new GrilleBuilder();
	    this.grille = mapBuilder.getGrille();
	    
	    personnages = new PersonnageManager();
	    Gardien gardien = PersonnageApparition.apparitionGardien(grille);
	    
	    personnages.ajouterPersonnage(gardien);
	    personnages.setGardienActif(gardien);
	    
		dashboard = new GameDisplay(this.grille, personnages);
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
		    Gardien gardienActif = personnages.getGardienActif();
		    
		    if (gardienActif == null) {
                return;
            }
		    
		    switch (keyCode) {
			
		    case KeyEvent.VK_LEFT: // Flèche gauche
	        case KeyEvent.VK_Q:
	        case KeyEvent.VK_A:
	        	personnages.deplacerPersonnage(grille, gardienActif, Direction.GAUCHE);
	            break;
	        
	        case KeyEvent.VK_RIGHT: // Flèche droit
	        case KeyEvent.VK_D:
	        	personnages.deplacerPersonnage(grille, gardienActif, Direction.DROITE);
	            break;
	        
	        case KeyEvent.VK_UP: // Flèche haut
	        case KeyEvent.VK_Z:
	        case KeyEvent.VK_W:
	        	personnages.deplacerPersonnage(grille, gardienActif, Direction.HAUT);
	            break;
	        
	        case KeyEvent.VK_DOWN: // Flèche bas
	        case KeyEvent.VK_S:
	        	personnages.deplacerPersonnage(grille, gardienActif, Direction.BAS);
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
