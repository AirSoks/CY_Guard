package engine.error;

import engine.map.Cell;
import engine.map.Position;

/**
 * Représente différentes erreurs liées à une {@link Cell} sur la carte,
 * comme des problèmes d'accès, de blocage ou d'invalidité.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public final class CellError implements MessageError {

    /**
     * Type d'erreur spécifique pour une cellule.
     */
    public enum Type {
        UNABLE_TO_ACCESS,
        BLOCKS_VISION,
        BLOCKS_MOVEMENT,
        INVALID_CELL
    }

    private final Type type;
    private final Position from;
    private final Position to;
    private final Cell cell;

    private CellError(Type type, Position from, Position to, Cell cell) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.cell = cell;
    }

    /**
     * Crée une erreur indiquant qu'une cellule n'est pas accessible depuis une autre position.
     *
     * @param from la position de départ
     * @param to la position cible
     * @return une instance de {@code CellError}
     */
    public static CellError unableToAccess(Position from, Position to) {
        return new CellError(Type.UNABLE_TO_ACCESS, from, to, null);
    }

    /**
     * Crée une erreur indiquant que la cellule bloque la vision.
     *
     * @param cell la cellule concernée
     * @return une instance de {@code CellError}
     */
    public static CellError blocksVision(Cell cell) {
        return new CellError(Type.BLOCKS_VISION, null, null, cell);
    }

    /**
     * Crée une erreur indiquant que la cellule bloque le mouvement.
     *
     * @param cell la cellule concernée
     * @return une instance de {@code CellError}
     */
    public static CellError blocksMovement(Cell cell) {
        return new CellError(Type.BLOCKS_MOVEMENT, null, null, cell);
    }

    /**
     * Crée une erreur indiquant que la cellule est invalide.
     *
     * @param cell la cellule concernée
     * @return une instance de {@code CellError}
     */
    public static CellError invalidCell(Cell cell) {
        return new CellError(Type.INVALID_CELL, null, null, cell);
    }

    /**
     * Retourne le message d'erreur correspondant au type de l'erreur.
     *
     * @return un message lisible décrivant l'erreur
     */
    @Override
    public String getMessage() {
        switch (type) {
            case UNABLE_TO_ACCESS:
                return "Unable to access cell: " + from + " to " + to;
            case BLOCKS_VISION:
                return "The cell at position " + cell + " blocks vision.";
            case BLOCKS_MOVEMENT:
                return "The cell at position " + cell + " blocks movement.";
            case INVALID_CELL:
                return "The cell at position " + cell + " is invalid.";
            default:
                return "Unknown cell error.";
        }
    }
}
