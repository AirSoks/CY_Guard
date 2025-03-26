package engine.utilitaire;

/**
 * Cette exception défini un nombre maximum de tentative
 */
@SuppressWarnings("serial")
public class MaximumTentativeAtteind extends RuntimeException {
	
	public MaximumTentativeAtteind(int maxAttempts) {
	    super("Nombre maximal de tentatives atteint (" + maxAttempts + ")");
	}
}