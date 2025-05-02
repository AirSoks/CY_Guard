package engine.error;

import engine.map.Cell;
import engine.personnage.Personnage;

/**
 * Représente une erreur lorsque le personnage n'est pas trouvé dans une cellule.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public final class PersonnageError implements MessageError {
    private final Personnage personnage;
    private final Cell cell;

    /**
     * Constructeur pour initialiser l'erreur avec le personnage et la cellule concernés.
     *
     * @param p le personnage
     * @param cell la cellule où le personnage devrait être trouvé
     */
    public PersonnageError(Personnage p, Cell cell) {
        this.personnage = p;
        this.cell = cell;
    }

    /**
     * Retourne le message d'erreur détaillant le problème de la présence du personnage dans la cellule.
     *
     * @return un message d'erreur indiquant que le personnage n'a pas été trouvé dans la cellule
     */
    @Override
    public String getMessage() {
        return "Character " + personnage + " not found in cell: " + cell;
    }
}
