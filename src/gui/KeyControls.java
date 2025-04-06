package gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import engine.map.Direction;
import engine.personnage.Gardien;
import engine.personnage.PersonnageManager;
import engine.personnage.deplacement.DeplacementManuel;

/**
 * Classe interne pour gérer les contrôles clavier
 */
public class KeyControls extends KeyAdapter{
	
	private PersonnageManager manager;
	
	private GameDisplay dashboard;
	
	public KeyControls(PersonnageManager manager, GameDisplay dashboard) {
		this.manager = manager;
		this.dashboard = dashboard;
	}

	public void keyPressed(KeyEvent e) {
	    int keyCode = e.getKeyCode();
	    Gardien gardienActif = manager.getGardienActif();
	    if (gardienActif == null) { 
	    	return; 
	    }
	    if (!(gardienActif.getDeplacement() instanceof DeplacementManuel)) { return; }

	    switch (keyCode) {
	    case KeyEvent.VK_LEFT: // Flèche gauche
        case KeyEvent.VK_Q:
        case KeyEvent.VK_A:
        	gardienActif.setDirection(Direction.GAUCHE);
            break;

        case KeyEvent.VK_RIGHT: // Flèche droit
        case KeyEvent.VK_D:
        	gardienActif.setDirection(Direction.DROITE);
            break;

        case KeyEvent.VK_UP: // Flèche haut
        case KeyEvent.VK_Z:
        case KeyEvent.VK_W:
        	gardienActif.setDirection(Direction.HAUT);
            break;

        case KeyEvent.VK_DOWN: // Flèche bas
        case KeyEvent.VK_S:
        	gardienActif.setDirection(Direction.BAS);
            break;
		}
		dashboard.repaint();
	}
}