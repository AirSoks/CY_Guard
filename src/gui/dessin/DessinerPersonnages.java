package gui.dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import config.GameConfiguration;
import engine.map.Coordonnee;
import engine.personnage.Gardien;
import engine.personnage.Intrus;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;
import engine.utilitaire.SimulationUtility;

public class DessinerPersonnages implements Dessiner {
	
    private PersonnageManager personnageManager;
    
    private boolean dessiner = true;
    private boolean performanceMode = false;
    
    private Map<String, Image> image = new HashMap<>();

    public DessinerPersonnages(PersonnageManager personnageManager) {
        this.personnageManager = personnageManager;
    }
    
    public Image getImage(String path) {
        if (!image.containsKey(path)) {
            image.put(path, SimulationUtility.readImage(path));
        }
        return image.get(path);
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
	                String spritePath = personnage.getAnimation().getSpritePath();
	                Image sprite = getImage(spritePath);
	                g.drawImage(sprite, x, y, blockSize, blockSize, null);
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
	public String getNom() {
		return "PERSONNAGES";
	}

	@Override
	public void setActive(Boolean etat) {
		this.dessiner = etat;
	}

	@Override
	public void setPerformance(Boolean etat) {
		this.performanceMode = etat;
	}
}