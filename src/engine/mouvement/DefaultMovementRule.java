package engine.mouvement;

import engine.map.Cell;
import engine.map.Position;
import engine.map.Position.PositionPair;
import engine.message.*;
import engine.message.error.*;
import engine.message.success.*;
import engine.personnage.Personnage;
import engine.util.Outcome;

/**
 * Implémentation par défaut de la règle de mouvement {@link MovementRule}.
 * <p>
 * Cette règle vérifie les conditions préalables au déplacement d'un personnage d'une position à une autre :
 * <ul>
 *     <li>Validation des paramètres d'entrée pour s'assurer qu'ils ne sont pas nuls (personnage, positions, cellules).</li>
 *     <li>Vérification que les cellules de départ et d'arrivée ne bloquent pas le mouvement (via {@link Cell#blocksMovement()}).</li>
 * </ul>
 * En fonction de l'état des vérifications, cette règle retourne :
 * Un {@link Outcome} contenant la paire {@link PositionPair} avec le succès ou l'échec détaillé.
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
	 * @return Un {@link Outcome} contenant toujours la paire {@link PositionPair}
	 *         avec le succès ou l'échec détaillé.
	 */
    @Override
    public Outcome<PositionPair> isMoveAccepted(Personnage p, Position from, Position to, Cell fromCell, Cell toCell) {
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
        
        PositionPair pPaire = (from != null && to != null) ? new PositionPair(from, to) : null;

        if (error != null) {
            return Outcome.failure(pPaire, error);
        }

        if (fromCell.blocksMovement()) {
            return Outcome.failure(pPaire, CellError.blocksMovement(fromCell));
        } if (toCell.blocksMovement()) {
            return Outcome.failure(pPaire, CellError.blocksMovement(toCell));
        }

        return Outcome.success(pPaire, MoveSuccess.validation(pPaire));
    }
}