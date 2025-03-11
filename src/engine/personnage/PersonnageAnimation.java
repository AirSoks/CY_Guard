package engine.personnage;

import java.awt.Image;

import engine.map.Direction;
import engine.utilitaire.SimulationUtility;

public class PersonnageAnimation {
	
    private int animationFrame;
    
    private Direction direction;
    
    private Direction derniereDirection;
    
    private String typePersonnage;
    
    public PersonnageAnimation(Personnage personnage) {
    	this.animationFrame = 1;
    	this.derniereDirection = Direction.BAS;
    	typePersonnage = "i";
	    if (personnage instanceof Gardien) {
	    	typePersonnage = "g";
	    }
    }
    
	public Image getSprite() {
	    
	    
	    String fileName = "src/images/" + typePersonnage + getDerniereDirection() + getAnimationFrame() + ".png";
	    return SimulationUtility.readImage(fileName);
	}
    
    public int getAnimationFrame() {
        return animationFrame;
    }

    public void switchAnimationFrame() {
    	if (animationFrame == 1) {
	        animationFrame = 2;
	    } else {
	        animationFrame = 1;
	    }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        if (direction != null ) {
			this.derniereDirection = direction;
		}
    }
    
    public String getDerniereDirection() {
        return derniereDirection.name().toLowerCase();
    }
}
