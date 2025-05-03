package engine.message;

/**
 * Interface représentant un message de succès dans le moteur.
 * 
 * @author AirSoks
 * @since 2025-05-03
 * @version 1.0
 */
public interface MessageSuccess extends Message {

    /**
     * Combine ce message de succès avec un autre en les séparant par une virgule.
     * <p>Exemple : {@code "Succès A, Succès B"}</p>
     *
     * @param other l'autre message à combiner
     * @return une nouvelle {@code MessageSuccess} représentant la combinaison
     */
    default MessageSuccess and(MessageSuccess other) {
        if (other == null) return this;
        return () -> this.getMessage() + ", " + other.getMessage();
    }

    /**
     * Combine ce message de succès avec un autre en les séparant par une flèche (->).
     * <p>Exemple : {@code "Succès A -> Succès B"}</p>
     *
     * @param other l'autre message à combiner
     * @return une nouvelle {@code MessageSuccess} représentant la combinaison
     */
    default MessageSuccess then(MessageSuccess other) {
        if (other == null) return this;
        return () -> this.getMessage() + " -> " + other.getMessage();
    }

    /**
     * Combine ce message de succès avec un autre en les séparant par des deux-points (:).
     * <p>Exemple : {@code "Succès A : Succès B"}</p>
     *
     * @param other l'autre message à combiner
     * @return une nouvelle {@code MessageSuccess} représentant la combinaison
     */
    default MessageSuccess with(MessageSuccess other) {
        if (other == null) return this;
        return () -> this.getMessage() + " : " + other.getMessage();
    }
}