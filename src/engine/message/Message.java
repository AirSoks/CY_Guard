package engine.message;

/**
 * Interface de base représentant un message dans le moteur.
 * <p>
 * Un message peut être de tout type (succès, erreur, etc.), et cette interface
 * fournit le contrat minimal : exposer le contenu du message sous forme de chaîne.
 * </p>
 * <p>
 * Les sous-types {@link MessageError} et {@link MessageSuccess} précisent respectivement
 * la nature du message et peuvent fournir des fonctionnalités spécifiques.
 * </p>
 * 
 * @author AirSoks
 * @since 2025-05-03
 * @version 1.0
 */
public interface Message {

    /**
     * Retourne le contenu du message sous forme de chaîne.
     *
     * @return le contenu du message
     */
    String getMessage();
}