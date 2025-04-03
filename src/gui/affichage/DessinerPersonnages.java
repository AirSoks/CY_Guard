package gui.affichage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import config.GameConfiguration;
import engine.map.Coordonnee;
import engine.personnage.Gardien;
import engine.personnage.Intrus;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;

public class DessinerPersonnages implements Dessiner {
	
    private PersonnageManager personnageManager;
    
    private boolean dessiner = true;
    private boolean performanceMode = false;

    public DessinerPersonnages(PersonnageManager personnageManager) {
        this.personnageManager = personnageManager;
    }

    @Override
    public void paint(Graphics g) {
        if (!dessiner) return;

        int blockSize = GameConfiguration.BLOCK_SIZE;

        for (Personnage personnage : personnageManager.getPersonnages()) {
            if (personnage != null) {
                Coordonnee coordonnee = personnage.getCoordonnee();
                int x = coordonnee.getColonne() * blockSize;
                int y = coordonnee.getLigne() * blockSize;
                
                if (!performanceMode) { 
	                Image sprite = personnage.getAnimation().getSprite();
	
	                if (sprite != null) {
	                    g.drawImage(sprite, x, y, blockSize, blockSize, null);
	                }
                }
                else {
	                if (personnage instanceof Gardien) {
	    	            g.setColor(Color.pink);
	    	        } else if (personnage instanceof Intrus) {
	    	            g.setColor(Color.red);
	    	        }
	
	    	        g.fillRect(x, y, blockSize, blockSize);
	    	    }
            }
        }
    }
    
    @Override
    public void activer() {
        this.dessiner = true;
    }

    @Override
    public void desactiver() {
        this.dessiner = false;
    }

	@Override
	public void activerPerformance() {
        this.performanceMode = true;
	}

	@Override
	public void desactiverPerformance() {
        this.performanceMode = false;
	}
}