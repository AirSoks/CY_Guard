package gui.dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import config.Settings;
import engine.map.Coordonnee;
import engine.personnage.Gardien;
import engine.personnage.Intrus;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;
import engine.utilitaire.SimulationUtility;

/**
 * La classe DessinerPersonnages implémente l'interface Dessiner et DPerformanceElement.
 * Elle est responsable du dessin des personnages (Gardiens et Intrus) sur la grille de jeu.
 */
public class DessinerPersonnages implements Dessiner, DessinPerformance {

    private Settings settings;
    private PersonnageManager personnageManager;
    
    private boolean performanceMode = false;
    
    private Map<String, Image> image = new HashMap<>();

    /**
     * Constructeur de la classe DessinerPersonnages
     *
     * @param personnageManager Le gestionnaire des personnages
     */
    public DessinerPersonnages(PersonnageManager personnageManager, Settings settings) {
        this.personnageManager = personnageManager;
        this.settings = settings;
    }
    
    /**
     * Obtient l'image correspondante à un chemin donné.
     * 
     * @param path Le chemin de l'image
     * @return L'image correspondante
     */
    public Image getImage(String path) {
        if (!image.containsKey(path)) {
            image.put(path, SimulationUtility.readImage(path));
        }
        return image.get(path);
    }

    /**
     * Dessine les personnages sur la grille de jeu.
     *
     * @param g L'objet Graphics utilisé pour dessiner
     */
    @Override
    public void paint(Graphics g) {
    	
        int blockSize = settings.getBlock_size();

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
	    	            g.setColor(new Color(0, 90, 255));
	    	        } else if (personnage instanceof Intrus) {
	    	            g.setColor(new Color(255, 182, 0));
	    	        }
	
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
		return "PERSONNAGES";
	}

    /**
     * Active ou désactive le mode performance.
     *
     * @param etat L'état d'activation
     */
	@Override
	public void setPerformance(Boolean etat) {
		this.performanceMode = etat;
	}
}