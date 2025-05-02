package engine.mouvement;

import engine.error.MessageError;

/**
 * Classe représentant le résultat d'une tentative de déplacement.
 * <p>
 * Elle encapsule :
 * <ul>
 *     <li>le succès ou l'échec du déplacement ;</li>
 *     <li>un éventuel statut de cellule bloquante ;</li>
 *     <li>un message d'erreur si le déplacement échoue.</li>
 * </ul>
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public class MovementStatus {
    private final boolean success;
    private final boolean isBlockingCell;
    private final MessageError error;

    private MovementStatus(boolean success, boolean isBlockingCell, MessageError error) {
        this.success = success;
        this.isBlockingCell = isBlockingCell;
        this.error = error;
    }

    /**
     * Indique si le déplacement a été un succès.
     *
     * @return true si le mouvement est accepté, false sinon
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Indique si la cellule d'arrivée est bloquante (ex : un obstacle spécifique bloque partiellement le passage).
     * Cela peut signifier que le mouvement a réussi mais rencontre un élément qui doit être traité.
     *
     * @return true si la cellule est bloquante, false sinon
     */
    public boolean isBlockingCell() {
        return isBlockingCell;
    }

    /**
     * Indique si le déplacement a échoué.
     *
     * @return true si le mouvement a échoué, false si c'est un succès
     */
    public boolean isFailure() {
        return !success;
    }

    /**
     * Retourne l'erreur associée au mouvement, s'il y en a une.
     *
     * @return le {@link MessageError} ou null si aucune erreur
     */
    public MessageError getError() {
        return error;
    }

    /**
     * Crée un {@link MovementStatus} indiquant un succès simple (aucune cellule bloquante).
     *
     * @return un status de succès
     */
    public static MovementStatus success() {
        return new MovementStatus(true, false, null);
    }

    /**
     * Crée un {@link MovementStatus} indiquant un succès mais avec une cellule bloquante (ex : obstacle spécial).
     *
     * @param error le message décrivant pourquoi la cellule est considérée comme bloquante
     * @return un status indiquant un blocage malgré le succès
     */
    public static MovementStatus blocking(MessageError error) {
        return new MovementStatus(true, true, error);
    }

    /**
     * Crée un {@link MovementStatus} indiquant un échec du déplacement.
     *
     * @param error le message d'erreur associé
     * @return un status d'échec
     */
    public static MovementStatus failure(MessageError error) {
        return new MovementStatus(false, false, error);
    }
}