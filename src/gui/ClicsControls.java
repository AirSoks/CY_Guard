package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import config.GameConfiguration;
import engine.map.Case;
import engine.map.Coordonnee;
import engine.map.Grille;
import engine.personnage.Gardien;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;
import engine.personnage.deplacement.DeplacementCase;
import engine.personnage.deplacement.DeplacementFactory;

public class ClicsControls extends MouseAdapter {

	/**
	 * Grille du simulation
	 */
	private Grille grille;
	
	/**
	 * Gestionnaire des personnages
	 */
	private PersonnageManager manager;
	
	private Personnage personnagePressed;

    public ClicsControls(Grille grille, PersonnageManager manager) {
        this.grille = grille;
        this.manager = manager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int blockSize = GameConfiguration.BLOCK_SIZE;
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

	@Override
	public void mousePressed(MouseEvent e) {
        int blockSize = GameConfiguration.BLOCK_SIZE;
        int colonne = e.getX() / blockSize;
        int ligne = e.getY() / blockSize;
        
        Coordonnee coordonnee = new Coordonnee(ligne, colonne);
        Case c = grille.getCase(coordonnee);
        if (c == null) { return; }
        
        List<Personnage> personnages = manager.getPersonnages(coordonnee);
        if (personnages == null || personnages.isEmpty()) { return; }
        personnagePressed = personnages.get(0);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
        int blockSize = GameConfiguration.BLOCK_SIZE;
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