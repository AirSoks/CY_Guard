package engine.personnage;

import engine.map.Direction;

public class PersonnageAnimation {
	
    private int animationFrame;
    
    private Direction direction;
    
    private Direction derniereDirection;
    
    public PersonnageAnimation() {
    	this.animationFrame = 1;
    	this.derniereDirection = Direction.BAS;
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
