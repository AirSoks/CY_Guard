package gui.affichage;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import config.GameConfiguration;
import engine.map.Coordonnee;
import engine.personnage.Intrus;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;
import engine.personnage.vision.Vision;

public class DessinerVision implements Dessiner {
	
    private PersonnageManager personnageManager;
    
    private boolean enabled = true;

    public DessinerVision(PersonnageManager personnageManager) {
        this.personnageManager = personnageManager;
    }

    @Override
    public void paint(Graphics g) {
        if (!enabled) return;

        int blockSize = GameConfiguration.BLOCK_SIZE;

        for (Personnage personnage : personnageManager.getPersonnages()) {
        	if (personnage instanceof Intrus) {
        		continue;
        	}
        	
            Vision vision = personnage.getVision();
            List<Coordonnee> coordonneesVu = vision.getMapPasCoordonnee().getAllCoordonnees();
            if (coordonneesVu == null ) {
            	continue;
            }
            
            g.setColor(new Color(0, 0, 255, 100));
            
            for (int i = 1; i < coordonneesVu.size(); i++) {
                Coordonnee coord = coordonneesVu.get(i);
                int x = coord.getColonne() * blockSize;
                int y = coord.getLigne() * blockSize;
                g.fillRect(x, y, blockSize, blockSize);
            }
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