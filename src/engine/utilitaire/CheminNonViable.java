package engine.utilitaire;

/**
 * Cette exception défini un chemin non viable 
 */
@SuppressWarnings("serial")
public class CheminNonViable extends RuntimeException {
	
    public CheminNonViable(int pas) {
        super("Aucun chemin trouvé au pas " + pas);
    }
}