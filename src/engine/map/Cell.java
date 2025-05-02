package engine.map;

import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import engine.error.*;
import engine.personnage.Personnage;
import engine.util.Either;
import engine.util.Unit;

/**
 * Représente une cellule dans une carte, pouvant contenir des personnages et un type d'obstacle.
 * <p>Chaque cellule peut avoir un obstacle qui bloque soit le mouvement, soit la vision, ou les deux. Les personnages peuvent être ajoutés ou retirés d'une cellule.</p>
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public class Cell {
    
    /**
     * Type d'obstacle présent dans la cellule.
     */
    private final ObstacleType obstacle;
    
    /**
     * Liste des personnages présents dans la cellule.
     */
    private final List<Personnage> personnages;
    
    /**
     * Verrou pour garantir un accès thread-safe aux personnages.
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Construit une cellule avec le type d'obstacle spécifié.
     *
     * @param obstacle Le type d'obstacle de la cellule
     */
    public Cell(ObstacleType obstacle) {
        this.obstacle = obstacle;
        this.personnages = new ArrayList<>();
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
     * Retourne une liste non modifiable des personnages présents dans la cellule.
     *
     * @return Liste des personnages dans la cellule
     */
    public List<Personnage> getPersonnages() {
        return Collections.unmodifiableList(personnages);
    }

    /**
     * Retourne une liste des personnages d'un type spécifique dans la cellule.
     *
     * @param type Le type de personnage à filtrer
     * @param <T> Le sous-type de {@link Personnage}
     * @return Liste non modifiable des personnages du type spécifié
     */
    public <T extends Personnage> List<T> getPersonnagesOfType(Class<T> type) {
        lock.lock();
        try {
            List<T> filtered = new ArrayList<>();
            for (Personnage p : personnages) {
                if (type.isInstance(p)) {
                    filtered.add(type.cast(p));
                }
            }
            return Collections.unmodifiableList(filtered);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Ajoute un personnage à la cellule.
     *
     * @param personnage Le personnage à ajouter
     */
    public void addPersonnage(Personnage personnage) {
        lock.lock();
        try {
            personnages.add(personnage);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Ajoute une liste de personnages à la cellule.
     *
     * @param personnages La liste des personnages à ajouter
     */
    public void addPersonnages(List<Personnage> personnages) {
        lock.lock();
        try {
            this.personnages.addAll(personnages);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Supprime un personnage de la cellule.
     *
     * @param personnage Le personnage à supprimer
     */
    public void removePersonnage(Personnage personnage) {
        lock.lock();
        try {
            personnages.remove(personnage);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Supprime une liste de personnages de la cellule.
     *
     * @param personnages La liste des personnages à supprimer
     */
    public void removePersonnages(List<Personnage> personnages) {
        lock.lock();
        try {
            this.personnages.removeAll(personnages);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Supprime tous les personnages d'un type donné de la cellule.
     *
     * @param type Le type de personnage à supprimer
     * @param <T> Le sous-type de {@link Personnage}
     */
    public <T extends Personnage> void removePersonnagesOfType(Class<T> type) {
        lock.lock();
        try {
            personnages.removeIf(p -> type.isInstance(p));
        } finally {
            lock.unlock();
        }
    }

    /**
     * Indique si l'obstacle de la cellule bloque la vision.
     *
     * @return true si la vision est bloquée, false sinon
     */
    public boolean blocksVision() {
        return obstacle.blocksVision();
    }

    /**
     * Indique si l'obstacle de la cellule bloque le déplacement.
     *
     * @return true si le déplacement est bloqué, false sinon
     */
    public boolean blocksMovement() {
        return obstacle.blocksMovement();
    }
    
    /**
     * Tente de transférer un personnage d'une cellule à une autre. 
     * Le transfert échoue si le personnage n'est pas présent dans la cellule de départ.
     *
     * @param from La cellule d'origine
     * @param to La cellule de destination
     * @param p Le personnage à transférer
     * @return Un {@link Either} contenant une erreur ou un objet {@link Unit} si le transfert est réussi
     */
    public static Either<MessageError,Unit> transferPersonnage(Personnage p, Cell from, Cell to) {
    	MessageError error = null;
    	
        if (from == null) {
        	MessageError e = new NullClassError(Cell.class).with(() -> "parameter: from");
        	error = (error == null) ? e : error.and(e);
        } if (to == null) {
        	MessageError e = new NullClassError(Cell.class).with(() -> "parameter: to");
        	error = (error == null) ? e : error.and(e);
        } if (p == null) {
        	MessageError e = new NullClassError(Personnage.class);
        	error = (error == null) ? e : error.and(e);
        }
        
        if (error != null) {
            return Either.left(error);
        }

        if (from == to) {
            return Either.right(Unit.get());
        }

        int hashFrom = System.identityHashCode(from);
        int hashTo = System.identityHashCode(to);

        Cell first = hashFrom < hashTo ? from : to;
        Cell second = from == first ? to : from;

        first.lock.lock();
        second.lock.lock();
        try {
            if (from.personnages.remove(p)) {
                to.personnages.add(p);
            } else {
                return Either.left(new PersonnageError(p, from));
            }
        } finally {
            second.lock.unlock();
            first.lock.unlock();
        }
        return Either.right(Unit.get());
    }
}
