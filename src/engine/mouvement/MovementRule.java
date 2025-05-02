package engine.mouvement;

import engine.map.Cell;
import engine.map.Position;
import engine.personnage.Personnage;

/**
 * Interface représentant une règle de déplacement pour les personnages sur la grille.
 * <p>
 * Chaque implémentation peut définir ses propres critères pour autoriser ou refuser un déplacement.
 * Cela permet de rendre le moteur flexible : on peut par exemple avoir des règles différentes
 * pour des personnages volants, rampants, ou soumis à des conditions spéciales.
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
     * @return {@link MovementStatus} indiquant si le déplacement est autorisé ou refusé,
     *         et éventuellement la raison en cas d'échec
     */
    MovementStatus isMoveAccepted(Personnage p, Position from, Position to, Cell fromCell, Cell toCell);
}