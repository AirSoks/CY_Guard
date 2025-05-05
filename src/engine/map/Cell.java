package engine.map;

import java.util.HashSet;

import engine.message.error.*;
import engine.listManager.AbstractPersonnageManager;
import engine.listManager.PersonnageManager;
import engine.message.MessageError;
import engine.personnage.Personnage;
import engine.util.Either;
import engine.util.Unit;

/**
 * Représente une cellule dans une carte, pouvant contenir des personnages et un type d'obstacle.
 * <p>
 * Chaque cellule peut avoir un obstacle qui bloque soit le mouvement, soit la vision, ou les deux.
 * Les personnages peuvent être ajoutés ou retirés d'une cellule via un gestionnaire dédié.
 * </p>
 * <p>
 * Cette classe fournit également une méthode statique pour transférer un personnage d'une cellule à une autre,
 * tout en gérant les cas d'erreurs (paramètres nuls, personnage absent...).
 * </p>
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.1
 */
public class Cell {
    
    /**
     * Type d'obstacle présent dans la cellule.
     */
    private final ObstacleType obstacle;
    
    /**
     * Gestionnaire des personnages présents dans la cellule.
     */
    private final CellPersonnageManager personnageManager;

    /**
     * Construit une cellule avec le type d'obstacle spécifié.
     *
     * @param obstacle Le type d'obstacle de la cellule
     */
    public Cell(ObstacleType obstacle) {
        this.obstacle = obstacle;
        this.personnageManager = new CellPersonnageManager();
    }

    /**
     * Retourne le type d'obstacle de la cellule.
     *
     * @return Le type d'obstacle de la cellule
     */
    public ObstacleType getObstacle() {
        return obstacle;
    }

    /**
     * Retourne le gestionnaire des personnages de la cellule.
     *
     * @return Le gestionnaire des personnages
     */
    public PersonnageManager getPersonnageManager() {
        return personnageManager;
    }

    /**
     * Indique si l'obstacle de la cellule bloque la vision.
     *
     * @return {@code true} si la vision est bloquée ; {@code false} sinon
     */
    public boolean blocksVision() {
        return obstacle.blocksVision();
    }

    /**
     * Indique si l'obstacle de la cellule bloque le déplacement.
     *
     * @return {@code true} si le déplacement est bloqué ; {@code false} sinon
     */
    public boolean blocksMovement() {
        return obstacle.blocksMovement();
    }
    
    /**
     * Tente de transférer un personnage d'une cellule à une autre.
     * <p>
     * Le transfert échoue si l'un des paramètres est {@code null} ou si le personnage n'est pas présent dans la cellule source.
     * La méthode est thread-safe grâce à la synchronisation des cellules source et destination.
     * </p>
     *
     * @param p    Le personnage à transférer
     * @param from La cellule source d'où retirer le personnage
     * @param to   La cellule destination où ajouter le personnage
     * @return Un {@link Either} contenant :
     * <ul>
     *     <li>à gauche : un {@link MessageError} en cas d'erreur (paramètre null ou personnage absent) ;</li>
     *     <li>à droite : un objet {@link Unit} si le transfert est réussi.</li>
     * </ul>
     */
    public static Either<MessageError, Unit> transferPersonnage(Personnage p, Cell from, Cell to) {
        MessageError error = null;

        if (from == null) {
            MessageError e = new NullClassError(Cell.class).with(() -> "parameter: from");
            error = (error == null) ? e : error.and(e);
        }
        if (to == null) {
            MessageError e = new NullClassError(Cell.class).with(() -> "parameter: to");
            error = (error == null) ? e : error.and(e);
        }
        if (p == null) {
            MessageError e = new NullClassError(Personnage.class);
            error = (error == null) ? e : error.and(e);
        }

        if (error != null) {
            return Either.left(error);
        }

        if (from == to) {
            return Either.right(Unit.get());
        }

        synchronized (from) {
            synchronized (to) {
                PersonnageManager fromManager = from.getPersonnageManager();
                PersonnageManager toManager = to.getPersonnageManager();

                if (fromManager.getPersonnages().contains(p)) {
                    fromManager.removePersonnage(p);
                    toManager.addPersonnage(p);
                } else {
                    return Either.left(new PersonnageError(p, from));
                }
            }
        }
        return Either.right(Unit.get());
    }
    
    /**
     * Implémentation concrète de {@link AbstractPersonnageManager} pour gérer les personnages dans une cellule.
     * <p>
     * Cette implémentation accepte tous les personnages sans restriction particulière
     * (tout personnage non nul est accepté).
     * </p>
     * Utilise un {@link HashSet} pour stocker les personnages, garantissant l'unicité
     * des éléments.
     * 
     * @author AirSoks
     * @since 2025-05-05
     * @version 1.0
     */
    public class CellPersonnageManager extends AbstractPersonnageManager {

        /**
         * Crée un gestionnaire pour la cellule avec un {@link HashSet} vide.
         */
        public CellPersonnageManager() {
            super(new HashSet<>());
        }

        /**
         * Vérifie si le personnage fourni est valide pour être ajouté à la cellule.
         * <p>
         * Ici, tout personnage non nul est considéré comme valide.
         * </p>
         *
         * @param personnage Le personnage à vérifier
         * @return {@code true} si le personnage est non nul ; {@code false} sinon
         */
        @Override
        protected boolean isValidTarget(Personnage personnage) {
            return personnage != null;
        }
    }
}
