package gui.event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import config.Settings;
import engine.map.Case;
import engine.map.Coordonnee;
import engine.map.Grille;
import engine.personnage.Gardien;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;
import engine.personnage.deplacement.DeplacementCase;
import engine.personnage.deplacement.DeplacementFactory;

/**
 * La classe ClicsControls étend MouseAdapter.
 * Elle est responsable de gérer les événements de clics de souris sur la grille de jeu.
 */
public class ClicsControls extends MouseAdapter {

	/**
	 * Grille de la simulation.
	 */
	private Grille grille;
	
	/**
	 * Gestionnaire des personnages.
	 */
	private PersonnageManager manager;
	
	private Personnage personnagePressed;
	
	private Settings settings;

    /**
     * Constructeur de la classe ClicsControls
     *
     * @param grille La grille de la simulation
     * @param manager Le gestionnaire des personnages
     */
    public ClicsControls(Grille grille, PersonnageManager manager, Settings settings) {
        this.grille = grille;
        this.manager = manager;
        this.settings = settings;
    }

    /**
     * Gère les clics de souris sur la grille.
     *
     * @param e L'événement de clic de souris
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int blockSize = settings.getBlock_size();
        int colonne = e.getX() / blockSize;
        int ligne = e.getY() / blockSize;
        
        Coordonnee coordonnee = new Coordonnee(ligne, colonne);
        Case c = grille.getCase(coordonnee);
        if (c == null) { return; }
        
        List<Gardien> gardiens = manager.getGardiens(coordonnee);
        if (gardiens == null || gardiens.isEmpty()) { return; }
        Gardien gardien = gardiens.get(0);
        Gardien gardienActif = manager.getGardienActif();

        if (gardienActif != null && gardienActif.equals(gardien)) {
        	manager.removeGardienActif();
        } else {
            manager.setGardienActif(gardien);
        }
    }

    /**
     * Gère les pressions de souris sur la grille.
     *
     * @param e L'événement de pression de souris
     */
	@Override
	public void mousePressed(MouseEvent e) {
        int blockSize = settings.getBlock_size();
        int colonne = e.getX() / blockSize;
        int ligne = e.getY() / blockSize;
        
        Coordonnee coordonnee = new Coordonnee(ligne, colonne);
        Case c = grille.getCase(coordonnee);
        if (c == null) { return; }
        
        List<Personnage> personnages = manager.getPersonnages(coordonnee);
        if (personnages == null || personnages.isEmpty()) { return; }
        personnagePressed = personnages.get(0);
	}

    /**
     * Gère les relâchements de souris sur la grille.
     *
     * @param e L'événement de relâchement de souris
     */
	@Override
	public void mouseReleased(MouseEvent e) {
        int blockSize = settings.getBlock_size();
        int colonne = e.getX() / blockSize;
        int ligne = e.getY() / blockSize;
        
        Coordonnee coordonnee = new Coordonnee(ligne, colonne);
        Case c = grille.getCase(coordonnee);
        
        if (c == null || personnagePressed == null) {
        	return;
        }

        DeplacementCase deplacementCase = (DeplacementCase) DeplacementFactory.getDeplacement("Case", manager, grille);
        if (personnagePressed.equals(manager.getGardienActif())) {
        	manager.setGardienActif(null);
        }
        deplacementCase.setCible(coordonnee);
        personnagePressed.setDeplacement(deplacementCase);
        
        personnagePressed = null;
	}
}