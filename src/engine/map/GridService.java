package engine.map;

import engine.personnage.Personnage;
import engine.personnage.mouvement.MovementRule;
import engine.util.Either;
import engine.util.Outcome;
import engine.util.Unit;
import engine.util.message.MessageError;
import engine.util.message.error.NullClassError;
import engine.util.message.error.PersonnageError;
import engine.util.message.error.PositionError;
import engine.util.message.sucess.MoveSuccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Fournit les opérations métier pour interagir avec une grille :
 * gestion des déplacements, vérification de cohérence et gestion des personnages.
 * 
 * Cette classe est indépendante et fonctionne avec une grille injectée.
 * 
 * @author AirSoks
 * @since 2025-05-06
 * @version 1.0
 */
public class GridService {
	
	private static GridService instance;
	
    private Grid grid;
    private MovementRule movementRule;
    private GridPersonnageManager personnageManager;

    /**
     * Crée un GridService lié à une grille et une règle de mouvement.
     *
     * @param grid La grille à manipuler.
     * @param movementRule La règle définissant les mouvements autorisés.
     */
    private GridService(Grid grid, MovementRule movementRule) {
        this.grid = grid;
        this.movementRule = movementRule;
        this.personnageManager = new GridPersonnageManager();
    }

    public static void initialize(Grid grid, MovementRule rule) {
        instance = new GridService(grid, rule);
    }

    public static GridService getInstance() {
        return instance;
    }

    /**
     * Retourne le gestionnaire des personnages associé à cette grille.
     *
     * @return le GridPersonnageManager.
     */
    public GridPersonnageManager getPersonnageManager() {
        return personnageManager;
    }
    
    public Grid getGrid() {
    	return grid;
    }

    /**
     * Vérifie si une position est valide (dans les limites de la grille).
     *
     * @param position La position à vérifier.
     * @return un Either avec une erreur si hors limites, ou Unit si OK.
     */
    public Either<MessageError, Unit> isInBounds(Position position) {
        if (position == null) {
            return Either.left(new NullClassError(Position.class));
        }
        if (position.x() < 0 || position.x() >= grid.getWidth() ||
            position.y() < 0 || position.y() >= grid.getHeight()) {
            return Either.left(PositionError.outOfBounds(position));
        }
        return Either.right(Unit.get());
    }

    /**
     * Vérifie la cohérence d'un personnage : s'il est bien présent dans sa cellule.
     *
     * @param p Le personnage à vérifier.
     * @return un Either avec erreur si incohérence, sinon Unit.
     */
    public Either<MessageError, Unit> verifyPersonnagePositionCoherence(Personnage p) {
        if (p == null) {
            return Either.left(new NullClassError(Personnage.class));
        }
        Position position = p.getPosition();
        Cell cell = grid.getCell(position.x(), position.y());
        if (cell == null) {
            return Either.left(PositionError.outOfBounds(position));
        }
        if (cell.getPersonnages().contains(p)) {
            return Either.right(Unit.get());
        } else {
            return Either.left(new PersonnageError(p, cell));
        }
    }

    /**
     * Tente de déplacer un personnage vers une position cible.
     *
     * @param p  Le personnage à déplacer.
     * @param to La destination.
     * @return un Outcome décrivant le succès ou l'échec du déplacement.
     */
    public Outcome<Position.PositionPair> movePersonnage(Personnage p, Position to) {
        if (p == null || to == null) {
            return Outcome.failure(null, new NullClassError(Personnage.class));
        }
        Position from = p.getPosition();
        Position.PositionPair posPair = new Position.PositionPair(from, to);

        Either<MessageError, Unit> coherenceCheck = verifyPersonnagePositionCoherence(p);
        if (coherenceCheck.isLeft()) {
            return Outcome.failure(posPair, coherenceCheck.getLeft());
        }

        Outcome<Position.PositionPair> isMoveAccepted = movementRule.isMoveAccepted(
            p, from, to,
            grid.getCell(from.x(), from.y()),
            grid.getCell(to.x(), to.y())
        );

        if (isMoveAccepted.isFailure()) {
            return isMoveAccepted;
        }

        Cell fromCell = grid.getCell(from.x(), from.y());
        Cell toCell = grid.getCell(to.x(), to.y());

        if (!Cell.transferPersonnage(p, toCell, fromCell)) {
            return Outcome.failure(posPair, new PersonnageError(p, fromCell));
        }

        p.setPosition(to);
        return Outcome.success(posPair, MoveSuccess.success(posPair));
    }
    
    /**
     * Tente de déplacer un personnage dans une direction donnée.
     *
     * @param p Le personnage à déplacer.
     * @param direction La direction du mouvement.
     * @return Un {@link Outcome} représentant le résultat du déplacement (succès ou échec).
     */
    public Outcome<Position.PositionPair> movePersonnage(Personnage p, Direction direction) {
    	if (p == null) {
            return Outcome.failure(null, new NullClassError(Personnage.class));
        }Position from = p.getPosition();
    	if (from == null) {
            return Outcome.failure(null, new NullClassError(Position.class).with(() -> "parameter: from"));
        } if (direction == null) {
            return Outcome.failure(null, new NullClassError(Direction.class));
        }
        
        Either<MessageError, Position> positionEither = from.move(direction);
        if (positionEither.isLeft()) {
        	return Outcome.failure(null, positionEither.getLeft());
        } else {
            return movePersonnage(p, positionEither.getRight());
        }
    }
    
    public final class GridPersonnageManager {

        private GridPersonnageManager() { }

        /**
         * Retourne tous les personnages présents sur la grille.
         *
         * @return Une collection non modifiable de tous les personnages.
         */
        public Collection<Personnage> getAll() {
            Collection<Personnage> result = new ArrayList<>();
            for (int x = 0; x < grid.getWidth(); x++) {
                for (int y = 0; y < grid.getHeight(); y++) {
                    Cell cell = grid.getCell(x, y);
                    result.addAll(cell.getPersonnages());
                }
            }
            return Collections.unmodifiableCollection(result);
        }

        /**
         * Retourne tous les personnages d’un type spécifique présents sur la grille.
         *
         * @param <E>  Le type des personnages ciblés.
         * @param type La classe du type de personnages.
         * @return Une collection non modifiable des personnages du type donné.
         */
        public <E extends Personnage> Collection<E> getByType(Class<E> type) {
            Collection<E> result = new ArrayList<>();
            for (int x = 0; x < grid.getWidth(); x++) {
                for (int y = 0; y < grid.getHeight(); y++) {
                    for (Personnage p : grid.getCell(x, y).getPersonnages()) {
                        if (type.isInstance(p)) {
                            result.add(type.cast(p));
                        }
                    }
                }
            }
            return Collections.unmodifiableCollection(result);
        }

        /**
         * Ajoute un personnage sur la grille à sa position actuelle.
         *
         * @param personnage Le personnage à ajouter.
         * @return true si l'ajout a réussi, false sinon.
         */
        public boolean add(Personnage personnage) {
            if (personnage == null || personnage.getPosition() == null) return false;
            Cell cell = grid.getCell(personnage.getPosition().x(), personnage.getPosition().y());
            if (cell == null) return false;
            return cell.getPersonnages().add(personnage);
        }

        /**
         * Ajoute plusieurs personnages sur la grille.
         *
         * @param personnages Les personnages à ajouter.
         * @return true si au moins un ajout a réussi, false sinon.
         */
        public boolean addAll(Collection<? extends Personnage> personnages) {
            boolean modified = false;
            for (Personnage p : personnages) {
                if (add(p)) {
                    modified = true;
                }
            }
            return modified;
        }

        /**
         * Supprime un personnage de la grille.
         *
         * @param personnage Le personnage à supprimer.
         * @return true si la suppression a réussi, false sinon.
         */
        public boolean remove(Personnage personnage) {
            if (personnage == null || personnage.getPosition() == null) return false;
            Cell cell = grid.getCell(personnage.getPosition().x(), personnage.getPosition().y());
            if (cell == null) return false;
            return cell.getPersonnages().remove(personnage);
        }

        /**
         * Supprime plusieurs personnages de la grille.
         *
         * @param personnages Les personnages à supprimer.
         * @return true si au moins une suppression a réussi, false sinon.
         */
        public boolean removeAll(Collection<? extends Personnage> personnages) {
            boolean modified = false;
            for (Personnage p : personnages) {
                if (remove(p)) {
                    modified = true;
                }
            }
            return modified;
        }

        /**
         * Supprime tous les personnages d’un type donné.
         *
         * @param <E>  Le type des personnages.
         * @param type La classe du type à supprimer.
         * @return true si au moins une suppression a réussi, false sinon.
         */
        public <E extends Personnage> boolean removeByType(Class<E> type) {
            Collection<E> toRemove = new ArrayList<>(getByType(type));
            return removeAll(toRemove);
        }

        /**
         * Vide totalement la grille de tous les personnages.
         */
        public void clear() {
            for (int x = 0; x < grid.getWidth(); x++) {
                for (int y = 0; y < grid.getHeight(); y++) {
                    grid.getCell(x, y).getPersonnages().clear();
                }
            }
        }
    }
}
