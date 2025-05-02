package engine.error;

/**
 * Interface représentant un message d'erreur dans le moteur.
 * <p>
 * Fournit également des méthodes utilitaires pour combiner plusieurs erreurs sous forme lisible.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public interface MessageError {

    /**
     * Retourne le message d'erreur sous forme de chaîne.
     *
     * @return le message d'erreur.
     */
    String getMessage();
    
    /**
     * Combine cette erreur avec une autre en les séparant par une virgule.
     * <p>Exemple : {@code "Erreur A, Erreur B"}</p>
     *
     * @param other l'autre erreur à combiner.
     * @return une nouvelle {@code MessageError} représentant la combinaison.
     */
    default MessageError and(MessageError other) {
        if (other == null) return this;
        return () -> this.getMessage() + ", " + other.getMessage();
    }

    /**
     * Combine cette erreur avec une autre en les séparant par une flèche (->).
     * <p>Exemple : {@code "Erreur A -> Erreur B"}</p>
     *
     * @param other l'autre erreur à combiner.
     * @return une nouvelle {@code MessageError} représentant la combinaison.
     */
    default MessageError then(MessageError other) {
        if (other == null) return this;
        return () -> this.getMessage() + " -> " + other.getMessage();
    }

    /**
     * Combine cette erreur avec une autre en les séparant par des deux-points (:).
     * <p>Exemple : {@code "Erreur A : Erreur B"}</p>
     *
     * @param other l'autre erreur à combiner.
     * @return une nouvelle {@code MessageError} représentant la combinaison.
     */
    default MessageError with(MessageError other) {
        if (other == null) return this;
        return () -> this.getMessage() + " : " + other.getMessage();
    }
}