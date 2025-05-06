package engine.util.message.sucess;

import engine.map.Position.PositionPair;
import engine.util.message.MessageSuccess;

/**
 * Représente un message de succès pour un mouvement, qui peut être validé ou totalement réussi.
 * 
 * Ce message distingue :
 * <ul>
 *     <li>Un mouvement qui a été validé (validation réussie),</li>
 *     <li>Un mouvement qui a été exécuté avec succès (réussi réellement).</li>
 * </ul>
 * 
 * @version 1.0
 * @since 2025-05-03
 */
public final class MoveSuccess implements MessageSuccess {

    /**
     * Type de succès pour le mouvement.
     */
    public enum Type {
        VALIDATION_SUCCESS,
        MOVE_SUCCESS
    }

    private final Type type;
    private final PositionPair positionPair;

    private MoveSuccess(Type type, PositionPair positionPair) {
        this.type = type;
        this.positionPair = positionPair;
    }

    /**
     * Crée un message indiquant que le mouvement a été validé.
     *
     * @param positionPair le couple de positions concerné
     * @return une instance de {@code MoveSuccess}
     */
    public static MoveSuccess validation(PositionPair positionPair) {
        return new MoveSuccess(Type.VALIDATION_SUCCESS, positionPair);
    }

    /**
     * Crée un message indiquant que le mouvement a été réussi.
     *
     * @param positionPair le couple de positions concerné
     * @return une instance de {@code MoveSuccess}
     */
    public static MoveSuccess success(PositionPair positionPair) {
        return new MoveSuccess(Type.MOVE_SUCCESS, positionPair);
    }

    /**
     * Retourne le message décrivant le succès selon son type.
     *
     * @return un message de succès lisible.
     */
    @Override
    public String getMessage() {
        switch (type) {
            case VALIDATION_SUCCESS:
                return "Movement from " + positionPair.getFrom() + " to " + positionPair.getTo() + " has been validated.";
            case MOVE_SUCCESS:
                return "Movement from " + positionPair.getFrom() + " to " + positionPair.getTo() + " was executed successfully.";
            default:
                return "Unknown movement success.";
        }
    }
}