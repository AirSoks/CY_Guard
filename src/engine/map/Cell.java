package engine.map;

import java.util.HashSet;
import java.util.Set;

import engine.personnage.Personnage;

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
    private final Set<Personnage> personnages;

    /**
     * Construit une cellule avec le type d'obstacle spécifié.
     *
     * @param obstacle Le type d'obstacle de la cellule
     */
    public Cell(ObstacleType obstacle) {
        this.obstacle = obstacle;
        this.personnages = new HashSet<Personnage>();
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
     * Retourne les personnages de la cellule.
     *
     * @return Le gestionnaire des personnages
     */
    public Set<Personnage> getPersonnages() {
        return personnages;
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
     * </p>
     *
     * @param p    Le personnage à transférer
     * @param from La cellule source d'où retirer le personnage
     * @param to   La cellule destination où ajouter le personnage
     * @return {@code true} si le transfert a réussi ; {@code false} sinon (paramètre null ou personnage absent).
     */
    public static boolean transferPersonnage(Personnage p, Cell from, Cell to) {
        if (from == null || to == null || p == null) {
            return false;
        }

        if (from == to) {
            return true;
        }
        Set<Personnage> fromManager = from.getPersonnages();
        Set<Personnage> toManager = to.getPersonnages();

        if (fromManager.contains(p)) {
            fromManager.remove(p);
            toManager.add(p);
            return true;
        } else {
            return false;
        }
    }
}
