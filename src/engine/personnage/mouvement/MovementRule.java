package engine.personnage.mouvement;

import engine.map.Cell;
import engine.map.Position;
import engine.map.Position.PositionPair;
import engine.personnage.Personnage;
import engine.util.Outcome;

/**
 * Interface représentant une règle de déplacement pour les abstractPersonnages sur la grille.
 * <p>
 * Chaque implémentation peut définir ses propres critères pour autoriser ou refuser un déplacement.
 * Cela permet de rendre le moteur flexible : on peut par exemple avoir des règles différentes
 * pour des abstractPersonnages volants, rampants, ou soumis à des conditions spéciales.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public interface MovementRule {
    
	/**
     * Vérifie si le déplacement est accepté selon cette règle.
     *
     * @param p        le personnage qui tente de se déplacer
     * @param from     la position de départ
     * @param to       la position d'arrivée
     * @param fromCell la cellule source correspondant à la position de départ
     * @param toCell   la cellule destination correspondant à la position d'arrivée
     * @return Un {@link Outcome} contenant toujours la paire {@link PositionPair}
	 *         avec le succès ou l'échec détaillé.
     */
	Outcome<PositionPair> isMoveAccepted(Personnage p, Position from, Position to, Cell fromCell, Cell toCell);
}