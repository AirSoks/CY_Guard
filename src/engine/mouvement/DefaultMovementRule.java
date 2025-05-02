package engine.mouvement;

import engine.error.*;
import engine.map.Cell;
import engine.map.Position;
import engine.personnage.Personnage;

/**
 * Implémentation par défaut de {@link MovementRule}.
 * <p>
 * Cette règle vérifie :
 * <ul>
 *     <li>Que tous les paramètres sont non-nuls (personnage, positions, cellules) ;</li>
 *     <li>Que la cellule de départ ou d'arrivée ne bloque pas le mouvement (via {@link Cell#blocksMovement()}).</li>
 * </ul>
 * Elle retourne :
 * <ul>
 *     <li>un {@link MovementStatus#failure(MessageError)} si une erreur est détectée (paramètre manquant) ;</li>
 *     <li>un {@link MovementStatus#blocking(MessageError)} si une des cellules bloque le mouvement ;</li>
 *     <li>un {@link MovementStatus#success()} si tout est valide.</li>
 * </ul>
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public class DefaultMovementRule implements MovementRule {

    /**
     * Vérifie les conditions pour autoriser un déplacement :
     * <ul>
     *     <li>Validation des paramètres (null-check) ;</li>
     *     <li>Vérification des blocages des cellules.</li>
     * </ul>
     *
     * @param p         Le personnage qui tente de se déplacer.
     * @param from      La position de départ.
     * @param to        La position d'arrivée.
     * @param fromCell  La cellule source.
     * @param toCell    La cellule destination.
     * @return Un {@link MovementStatus} reflétant la validité du déplacement.
     */
    @Override
    public MovementStatus isMoveAccepted(Personnage p, Position from, Position to, Cell fromCell, Cell toCell) {
        MessageError error = null;

        if (p == null) {
            error = new NullClassError(Personnage.class);
        } if (from == null) {
            MessageError e = new NullClassError(Position.class).with(() -> "parameter: from");
            error = (error == null) ? e : error.and(e);
        } if (to == null) {
            MessageError e = new NullClassError(Position.class).with(() -> "parameter: to");
            error = (error == null) ? e : error.and(e);
        } if (fromCell == null) {
            MessageError e = new NullClassError(Cell.class).with(() -> "parameter: fromCell");
            error = (error == null) ? e : error.and(e);
        } if (toCell == null) {
            MessageError e = new NullClassError(Cell.class).with(() -> "parameter: toCell");
            error = (error == null) ? e : error.and(e);
        }

        if (error != null) {
            return MovementStatus.failure(error);
        }

        if (fromCell.blocksMovement()) {
            return MovementStatus.blocking(CellError.blocksMovement(fromCell));
        } if (toCell.blocksMovement()) {
            return MovementStatus.blocking(CellError.blocksMovement(toCell));
        }

        return MovementStatus.success();
    }
}