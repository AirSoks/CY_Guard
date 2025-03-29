package engine.personnage;

import java.awt.Image;

import engine.map.Direction;
import engine.utilitaire.SimulationUtility;

/**
 * Gère l'animation et les sprites d'un personnage en fonction de sa direction et de sa position.
 * Utilise une séquence d'images pour simuler l'animation du déplacement.
 *
 * @author GLP_19
 * @see Personnage
 * @see Direction
 */
public class PersonnageAnimation {
	
	/**
     * Frame actuelle de l'animation (1 ou 2)
     */
    private int animationFrame;
    
    /**
     * Dernière direction prise par le personnage
     */
    private Direction derniereDirection;
    
    /**
     * Type de personnage ('g' pour Gardien, 'i' pour Intrus)
     */
    private String typePersonnage;
    
    public PersonnageAnimation(Personnage personnage) {
    	this.animationFrame = 1;
    	this.derniereDirection = Direction.BAS;
    	typePersonnage = "i";
	    if (personnage instanceof Gardien) {
	    	typePersonnage = "g";
	    }
    }
    
    /**
     * Récupère l'image du sprite correspondant à l'état actuel
     * @return L'image à afficher pour le personnage
     */
	public Image getSprite() {
	    String fileName = "src/images/sprites/" + typePersonnage + getDerniereDirection() + getAnimationFrame() + ".png";
	    return SimulationUtility.readImage(fileName);
	}
    
    public int getAnimationFrame() {
        return animationFrame;
    }

    /**
     * Alterne entre les deux frames d'animation
     */
    public void switchAnimationFrame() {
    	if (animationFrame == 1) {
	        animationFrame = 2;
	    } else {
	        animationFrame = 1;
	    }
    }
    
    public String getDerniereDirection() {
        return derniereDirection.name().toLowerCase();
    }

	public void setDerniereDirection(Direction derniereDirection) {
		this.derniereDirection = derniereDirection;
	}
}
