package engine.displacement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import engine.error.*;
import engine.map.Direction;
import engine.map.Grid;
import engine.map.Position;
import engine.map.Position.PositionPair;
import engine.message.MessageError;
import engine.personnage.Personnage;
import engine.util.Outcome;
import engine.util.Either;
import engine.util.Unit;

/**
 * Classe abstraite fournissant une base pour les déplacements de personnages dans une grille.
 *
 * Cette implémentation gère :
 * - La mémorisation du chemin calculé ;
 * - Le déplacement pas à pas d'un personnage le long de ce chemin ;
 * - La vérification et le recalcul automatique du chemin si nécessaire.
 *
 * Les sous-classes doivent implémenter la méthode {@link #calculateMove(Personnage)} pour définir
 * la logique spécifique de calcul de chemin.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public abstract class AbstractDisplacement implements Displacement {

    private final Grid grid;
    private List<Position> path;

    /**
     * Crée une instance abstraite de déplacement liée à une grille spécifique.
     *
     * @param grid la grille de jeu où les déplacements ont lieu
     */
    public AbstractDisplacement(Grid grid) {
        this.grid = grid;
        this.path = new ArrayList<>();
    }

    /**
     * Vérifie si un chemin existe déjà ; sinon, demande à {@link #calculateMove(Personnage)} d'en générer un.
     *
     * Cette méthode vérifie si un chemin est déjà défini pour le personnage. Si ce n'est pas le cas, elle tente
     * de calculer un chemin valide pour ce dernier.
     *
     * @param p le personnage concerné par le déplacement
     * @return un {@code Outcome} indiquant le succès ou l'échec de l'initialisation du chemin.
     *         - Si un chemin valide est calculé, renvoie un succès avec la mise à jour du chemin.
     *         - Si le calcul échoue, renvoie un échec avec l'erreur associée.
     */
    private Outcome<Unit> checkOrUpdatePath(Personnage p) {
        if (p == null) {
            return Outcome.failure(new NullClassError(Personnage.class));
        }
        
        if (path == null || path.isEmpty()) {
            Either<MessageError, List<Position>> isNewMoveValide = calculateMove(p);

            if (isNewMoveValide.isLeft()) {
                return Outcome.failure(isNewMoveValide.getLeft());
            }

            List<Position> newPath = isNewMoveValide.getRight();
            if (newPath == null || newPath.isEmpty()) {
                return Outcome.failure(PathError.emptyPath());
            }
            path = new ArrayList<>(newPath);
        }
        
        return Outcome.success(Unit.get());
    }

    /**
     * Déplace le personnage d'une case dans la direction du prochain point du chemin.
     * Si aucun chemin n'est défini, tente d'en calculer un avant le déplacement.
     *
     * @param p le personnage à déplacer
     * @return un {@link Outcome} indiquant le résultat du déplacement.
     *         - Si le mouvement échoue (par exemple, si la direction n'est pas valide), renvoie un échec avec l'erreur.
     *         - Si le mouvement réussit, renvoie un succès avec la nouvelle position du personnage.
     */
    @Override
    public Outcome<PositionPair> move(Personnage p) {
    	Outcome<Unit> checkStatus = checkOrUpdatePath(p);
        if (checkStatus.isFailure()) {
            return Outcome.failure(checkStatus.getErrorMessage());
        }

        Either<MessageError, Direction> isDirAdjacent = Direction.adjacentDirection(p.getPosition(), path.get(0));
        if (isDirAdjacent.isLeft()) {
            this.path = new ArrayList<>();
            return Outcome.failure(isDirAdjacent.getLeft());
        }

        Outcome<PositionPair> status = grid.movePersonnage(p, isDirAdjacent.getRight());
        if (!status.isFailure()) {
            path.remove(0);
        }
        return status;
    }

    /**
     * Renvoie le chemin actuellement mémorisé.
     *
     * @return un {@code Either} contenant la liste des positions du chemin actuel,
     *         ou une erreur si aucun chemin n'est défini.
     *         - Si le chemin est vide ou inexistant, renvoie une erreur {@link PathError.emptyPath()}.
     */
    @Override
    public Either<MessageError, List<Position>> getPath() {
        if (path == null || path.isEmpty()) {
            return Either.left(PathError.emptyPath());
        }
        return Either.right(Collections.unmodifiableList(path));
    }
}