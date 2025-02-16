package engine.map.obstacle;

/**
 * Represente un obstacle de type "Lac"
 * Un lac bloque le deplacement mais ne bloque pas la vision
 * 
 * @author GLP_19
 */
public class Lac extends Obstacle {
	
	public Lac() {
        super("Lac", false, true);
    }
}
