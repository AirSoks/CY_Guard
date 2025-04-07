package gui.dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import config.GameConfiguration;
import engine.map.Coordonnee;
import engine.personnage.Intrus;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;

/**
 * La classe DessinerVision implémente l'interface Dessiner et DOptionnelElement.
 * Elle est responsable du dessin des zones de vision des personnages sur la grille de jeu.
 */
public class DessinerVision implements Dessiner, DOptionnelElement {
	
    private PersonnageManager personnageManager;
    
    private boolean dessiner = true;

    /**
     * Constructeur de la classe DessinerVision
     *
     * @param personnageManager Le gestionnaire des personnages
     */
    public DessinerVision(PersonnageManager personnageManager) {
        this.personnageManager = personnageManager;
    }

    /**
     * Dessine les zones de vision des personnages sur la grille de jeu.
     *
     * @param g L'objet Graphics utilisé pour dessiner
     */
    @Override
    public void paint(Graphics g) {
    	
    	if (!dessiner) { return; }

        int blockSize = GameConfiguration.BLOCK_SIZE;

        for (Personnage personnage : personnageManager.getPersonnages()) {
            List<Coordonnee> coordonneesVu = personnage.getCoordonneesVu();
            if (coordonneesVu == null ) {
            	continue;
            }
            if (personnage instanceof Intrus) {
            	g.setColor(new Color(255, 182, 0, 100));
        	}
            else {
                g.setColor(new Color(0, 90, 255, 100));
            }
            
            for (int i = 1; i < coordonneesVu.size(); i++) {
                Coordonnee coord = coordonneesVu.get(i);
                int x = coord.getColonne() * blockSize;
                int y = coord.getLigne() * blockSize;
                g.fillRect(x, y, blockSize, blockSize);
            }
        }
    }

    /**
     * Obtient le nom de l'élément à dessiner.
     *
     * @return Le nom de l'élément
     */
	@Override
	public String getNom() {
		return "VISION";
	}
	
    /**
     * Active ou désactive le dessin des zones de vision.
     *
     * @param etat L'état d'activation
     */
	@Override
	public void setActive(Boolean etat) {
		this.dessiner = etat;
	}
}