package engine.map.obstacle;

/**
 * Represente un obstacle de type "Arbre"
 * Un arbre bloque la vision mais ne bloque pas le deplacement
 * 
 * @author GLP_19
 * @see Obstacle
 */
public class Arbre extends Obstacle{
	
	public Arbre() {
        super("Arbre", true, false);
    }
}