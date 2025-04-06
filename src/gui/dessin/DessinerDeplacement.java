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

public class DessinerDeplacement implements Dessiner, DOptionnelElement {
	
    private PersonnageManager personnageManager;
    
    private boolean dessiner = true;
    
    public DessinerDeplacement(PersonnageManager personnageManager) {
        this.personnageManager = personnageManager;
    }

    @Override
	public void paint(Graphics g) {
    	
    	if (!dessiner) return;
    	
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

	@Override
	public String getNom() {
		return "DEPLACEMENT";
	}

	@Override
	public void setActive(Boolean etat) {
		this.dessiner = etat;
	}
}