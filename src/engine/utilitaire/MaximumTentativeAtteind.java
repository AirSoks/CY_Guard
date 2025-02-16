package engine.utilitaire;

/**
 * Cette exception défini un nombre maximum de tentative
 */
public class MaximumTentativeAtteind extends RuntimeException {
	
	public MaximumTentativeAtteind(int maxAttempts) {
	    super("Nombre maximal de tentatives atteint (" + maxAttempts + ")");
	}
}