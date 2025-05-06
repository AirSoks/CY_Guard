package engine.util.message.error;

import engine.map.Position;
import engine.util.message.MessageError;

/**
 * Représente une erreur liée à une position, avec différents types d'erreurs de position.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public final class PositionError implements MessageError {

    /**
     * Types d'erreurs qui peuvent se produire concernant une position.
     */
    public enum Type {
        INVALID_POSITION,
        POSITION_NOT_FOUND,
        OUT_OF_BOUNDS,
        NON_ADJACENT_POSITIONS,
        SAME_POSITION,
        NEGATIVE_POSITION
    }

    private final Type type;
    private final Position position1;
    private final Position position2;

    /**
     * Constructeur pour initialiser l'erreur de position avec le type d'erreur et les positions concernées.
     *
     * @param type le type de l'erreur
     * @param p1 la première position impliquée dans l'erreur
     * @param p2 la deuxième position, peut être null pour certains types d'erreurs
     */
    private PositionError(Type type, Position p1, Position p2) {
        this.type = type;
        this.position1 = p1;
        this.position2 = p2;
    }

    /**
     * Crée une erreur lorsque la position est invalide.
     *
     * @param p la position invalide
     * @return une instance de PositionError
     */
    public static PositionError invalidPosition(Position p) {
        return new PositionError(Type.INVALID_POSITION, p, null);
    }

    /**
     * Crée une erreur lorsque la position ne peut pas être trouvée.
     *
     * @param p la position introuvable
     * @return une instance de PositionError
     */
    public static PositionError positionNotFound(Position p) {
        return new PositionError(Type.POSITION_NOT_FOUND, p, null);
    }

    /**
     * Crée une erreur lorsque la position est hors des limites de la grille.
     *
     * @param p la position hors limite
     * @return une instance de PositionError
     */
    public static PositionError outOfBounds(Position p) {
        return new PositionError(Type.OUT_OF_BOUNDS, p, null);
    }

    /**
     * Crée une erreur lorsque les positions ne sont pas adjacentes.
     *
     * @param from la position de départ
     * @param to la position d'arrivée
     * @return une instance de PositionError
     */
    public static PositionError nonAdjacent(Position from, Position to) {
        return new PositionError(Type.NON_ADJACENT_POSITIONS, from, to);
    }

    /**
     * Crée une erreur lorsque la position source et la position destination sont identiques.
     *
     * @param p la position source et destination
     * @return une instance de PositionError
     */
    public static PositionError samePosition(Position p) {
        return new PositionError(Type.SAME_POSITION, p, null);
    }

    /**
     * Crée une erreur lorsque la position a des coordonnées négatives.
     *
     * @param p la position avec des coordonnées négatives
     * @return une instance de PositionError
     */
    public static PositionError negPosition(Position p) {
        return new PositionError(Type.NEGATIVE_POSITION, p, null);
    }

    /**
     * Retourne le message d'erreur détaillant le problème de position.
     *
     * @return un message d'erreur décrivant l'erreur liée à la position
     */
    @Override
    public String getMessage() {
        switch (type) {
            case INVALID_POSITION : 
                return "Invalid position for character: " + position1;
            case POSITION_NOT_FOUND : 
                return "Unable to find cell for position: " + position1;
            case OUT_OF_BOUNDS : 
                return "Position out of bounds: " + position1;
            case NON_ADJACENT_POSITIONS : 
                return "Positions not adjacent: " + position1 + " -> " + position2;
            case SAME_POSITION : 
                return "Source and destination are the same position: " + position1;
            case NEGATIVE_POSITION : 
                return "Negative position: " + position1;
            default: 
                return "Unknown position error";
        }
    }
}