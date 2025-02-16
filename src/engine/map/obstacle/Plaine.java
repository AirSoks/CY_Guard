package engine.map.obstacle;

/**
 * Represente un obstacle de type "Plaine"
 * Une plaine ne bloque ni la vision ni le deplacement
 * 
 * @author GLP_19
 * @see Obstacle
 */
public class Plaine extends Obstacle {
	
	public Plaine() {
        super("Plaine", false, false);
    }
}
