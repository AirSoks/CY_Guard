package gui.event;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import engine.map.Direction;
import engine.personnage.Gardien;
import engine.personnage.PersonnageManager;
import engine.personnage.deplacement.DeplacementManuel;
import gui.panel.GameDisplay;

/**
 * Classe interne pour gérer les contrôles clavier.
 */
public class KeyControls extends KeyAdapter {
	
	private PersonnageManager manager;
	private GameDisplay dashboard;
	
	/**
	 * Constructeur de la classe KeyControls
	 *
	 * @param manager Le gestionnaire des personnages
	 * @param dashboard Le tableau de bord du jeu
	 */
	public KeyControls(PersonnageManager manager, GameDisplay dashboard) {
		this.manager = manager;
		this.dashboard = dashboard;
	}

	/**
	 * Gère les événements de pression de touche.
	 *
	 * @param e L'événement de pression de touche
	 */
	@Override
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

        case KeyEvent.VK_RIGHT: // Flèche droite
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