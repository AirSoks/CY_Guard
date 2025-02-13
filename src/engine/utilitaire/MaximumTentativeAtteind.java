package engine.utilitaire;

public class MaximumTentativeAtteind extends RuntimeException {
	
	public MaximumTentativeAtteind(int maxAttempts) {
	    super("Nombre maximal de tentatives atteint (" + maxAttempts + ")");
	}
}