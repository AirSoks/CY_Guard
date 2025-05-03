package engine.message.error;

import engine.map.Position.PositionPair;
import engine.message.MessageError;

/**
 * Représente un message d'erreur indiquant qu'un mouvement n'a pas été validé.
 * 
 * Par exemple : tentative de déplacement invalide d'une position à une autre.
 * 
 * @param positionPair le couple de positions concerné par le mouvement
 * 
 * @version 1.0
 * @since 2025-05-03
 */
public final class MoveError implements MessageError {

    private final PositionPair positionPair;

    /**
     * Construit un {@code MoveError} pour le mouvement spécifié.
     *
     * @param positionPair le couple de positions du mouvement non validé
     */
    public MoveError(PositionPair positionPair) {
        this.positionPair = positionPair;
    }

    /**
     * Retourne le message indiquant que le mouvement n'a pas été validé.
     *
     * @return un message d'erreur lisible.
     */
    @Override
    public String getMessage() {
        return "Movement from " + positionPair.getFrom() + " to " + positionPair.getTo() + " was not validated.";
    }
}