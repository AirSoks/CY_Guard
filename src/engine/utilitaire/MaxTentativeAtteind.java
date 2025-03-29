package engine.utilitaire;

/**
 * Cette exception défini un nombre maximum de tentative
 */
@SuppressWarnings("serial")
public class MaxTentativeAtteind extends RuntimeException {
	
	public MaxTentativeAtteind(int maxAttempts) {
	    super("Nombre maximal de tentatives atteint (" + maxAttempts + ")");
	}
}