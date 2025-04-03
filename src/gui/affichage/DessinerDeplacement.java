package gui.affichage;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import config.GameConfiguration;
import engine.map.Coordonnee;
import engine.personnage.Gardien;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;
import engine.personnage.deplacement.Deplacement;
import engine.personnage.deplacement.DeplacementPoursuite;

public class DessinerDeplacement implements Dessiner {
	
    private PersonnageManager personnageManager;
    
    public DessinerDeplacement(PersonnageManager personnageManager) {
        this.personnageManager = personnageManager;
    }

    @Override
    public void paint(Graphics g) {
    	
        int blockSize = GameConfiguration.BLOCK_SIZE;

        for (Personnage personnage : personnageManager.getPersonnages()) {
            Deplacement deplacement = personnage.getDeplacement();

            if (deplacement instanceof DeplacementPoursuite && personnage instanceof Gardien) {
            	DeplacementPoursuite poursuite = (DeplacementPoursuite) deplacement;

                List<Coordonnee> chemin = poursuite.getChemin();
                
                if (chemin == null || chemin.size() <= 1) {
                	continue;
                }

                g.setColor(new Color(255, 0, 0, 100));
                
                // Parcourir tous les points sauf le premier et dernier
                for (int i = 1; i < chemin.size(); i++) {
                    Coordonnee coord = chemin.get(i);
                    int x = coord.getColonne() * blockSize;
                    int y = coord.getLigne() * blockSize;
                    g.fillRect(x, y, blockSize, blockSize);
                }
            }
        }
    }
}