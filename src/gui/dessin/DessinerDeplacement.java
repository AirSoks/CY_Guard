package gui.dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import config.GameConfiguration;
import engine.map.Coordonnee;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;
import engine.personnage.deplacement.Deplacement;
import engine.personnage.deplacement.DeplacementCase;
import engine.personnage.deplacement.DeplacementPoursuite;

/**
 * La classe DessinerDeplacement implémente l'interface Dessiner et DOptionnelElement.
 * Elle est responsable du dessin des déplacements des personnages sur la grille de jeu.
 */
public class DessinerDeplacement implements Dessiner, DOptionnelElement {
	
    private PersonnageManager personnageManager;
    private boolean dessiner = true;
    
    /**
     * Constructeur de la classe DessinerDeplacement
     *
     * @param personnageManager Le gestionnaire des personnages
     */
    public DessinerDeplacement(PersonnageManager personnageManager) {
        this.personnageManager = personnageManager;
    }

    /**
     * Dessine les déplacements des personnages sur la grille de jeu.
     *
     * @param g L'objet Graphics utilisé pour dessiner
     */
    @Override
	public void paint(Graphics g) {
    	
    	if (!dessiner) { return; }
    	
        int blockSize = GameConfiguration.BLOCK_SIZE;

        for (Personnage personnage : personnageManager.getPersonnages()) {
            Deplacement deplacement = personnage.getDeplacement();

            if (deplacement instanceof DeplacementPoursuite) {
            	DeplacementPoursuite poursuite = (DeplacementPoursuite) deplacement;

                List<Coordonnee> chemin = poursuite.getChemin();
                
                if (chemin == null || chemin.size() <= 1) {
                	continue;
                }
                
                g.setColor(new Color(255, 0, 0, 100));
                
                for (int i = 1; i < chemin.size(); i++) {
                    Coordonnee coord = chemin.get(i);
                    int x = coord.getColonne() * blockSize;
                    int y = coord.getLigne() * blockSize;
                    g.fillRect(x, y, blockSize, blockSize);
                }
            }
            
            if (deplacement instanceof DeplacementCase) {
            	DeplacementCase poursuite = (DeplacementCase) deplacement;

                List<Coordonnee> chemin = poursuite.getChemin();
                
                if (chemin == null || chemin.size() <= 1) {
                	continue;
                }
                
                g.setColor(new Color(255, 0, 0, 100));
                
                for (int i = 1; i < chemin.size(); i++) {
                    Coordonnee coord = chemin.get(i);
                    int x = coord.getColonne() * blockSize;
                    int y = coord.getLigne() * blockSize;
                    g.fillRect(x, y, blockSize, blockSize);
                }
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
		return "DEPLACEMENT";
	}

    /**
     * Active ou désactive le dessin des déplacements.
     *
     * @param etat L'état d'activation
     */
	@Override
	public void setActive(Boolean etat) {
		this.dessiner = etat;
	}
}