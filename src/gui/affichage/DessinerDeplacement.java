package gui.affichage;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import config.GameConfiguration;
import engine.map.Coordonnee;
import engine.personnage.Gardien;
import engine.personnage.Intrus;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;
import engine.personnage.deplacement.Deplacement;
import engine.personnage.deplacement.DeplacementPoursuite;

public class DessinerDeplacement implements Dessiner {
	
    private PersonnageManager personnageManager;
    private boolean enabled = true;

    public DessinerDeplacement(PersonnageManager personnageManager) {
        this.personnageManager = personnageManager;
    }

    @Override
    public void paint(Graphics g) {
    	if (!enabled) return;
        int blockSize = GameConfiguration.BLOCK_SIZE;

        for (Personnage personnage : personnageManager.getPersonnages()) {
            Deplacement deplacement = personnage.getDeplacement();

            if (deplacement instanceof DeplacementPoursuite && personnage instanceof Gardien) {
            	DeplacementPoursuite poursuite = (DeplacementPoursuite) deplacement;
            	Gardien gardien = (Gardien) personnage;
            	
            	Intrus cible = poursuite.cibleAccessible(gardien);
                if (cible == null) {
                    return;
                }
                
            	poursuite.trouverChemin(cible);
                List<Coordonnee> chemin = poursuite.getChemin();
                
                g.setColor(new Color(255, 0, 0, 100));
                for (Coordonnee coord : chemin) {
                    int x = coord.getColonne() * blockSize;
                    int y = coord.getLigne() * blockSize;
                    g.fillRect(x, y, blockSize, blockSize);
                }
            } 
//            else if (deplacement instanceof DeplacementManuel) {
//
//                Direction direction = personnage.getDirection();
//                if (direction != null) {
//                    Coordonnee cible = direction.getCoordonnee(personnage.getCoordonnee());
//                    int x = cible.getColonne() * blockSize;
//                    int y = cible.getLigne() * blockSize;
//
//                    g.setColor(new Color(0, 255, 0, 100));
//                    g.fillRect(x, y, blockSize, blockSize);
//          	}
//      	}
        }
    }

    @Override
    public void activer() {
        this.enabled = true;
    }

    @Override
    public void desactiver() {
        this.enabled = false;
    }

    @Override
    public boolean isActiver() {
        return this.enabled;
    }
}