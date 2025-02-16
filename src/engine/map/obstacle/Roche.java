package engine.map.obstacle;

/**
 * Represente un obstacle de type "Roche"
 * Une roche bloque la vision et le deplacement
 * 
 * @author GLP_19
 * @see Obstacle
 */
public class Roche extends Obstacle {
	
	public Roche() {
        super("Roche", true, true);
    }
}
