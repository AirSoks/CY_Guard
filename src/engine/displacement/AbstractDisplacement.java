package engine.displacement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import engine.error.*;
import engine.map.Direction;
import engine.map.Grid;
import engine.map.Position;
import engine.mouvement.MovementStatus;
import engine.personnage.Personnage;
import engine.util.Either;

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
     * @param p le personnage concerné par le déplacement
     * @return un {@code MovementStatus} indiquant le succès ou l'échec de l'initialisation du chemin
     */
    private MovementStatus checkOrUpdatePath(Personnage p) {
        if (p == null) {
            return MovementStatus.failure(new NullClassError(Personnage.class));
        }
        
        if (path == null || path.isEmpty()) {
            Either<MessageError, List<Position>> isNewMoveValide = calculateMove(p);

            if (isNewMoveValide.isLeft()) {
                return MovementStatus.failure(isNewMoveValide.getLeft());
            }

            List<Position> newPath = isNewMoveValide.getRight();
            if (newPath == null || newPath.isEmpty()) {
                return MovementStatus.failure(PathError.emptyPath());
            }
            path = new ArrayList<>(newPath);
        }
        
        return MovementStatus.success();
    }

    /**
     * Déplace le personnage d'une case dans la direction du prochain point du chemin.
     * Si aucun chemin n'est défini, tente d'en calculer un avant le déplacement.
     *
     * @param p le personnage à déplacer
     * @return un {@code MovementStatus} représentant le résultat du déplacement
     */
    @Override
    public MovementStatus move(Personnage p) {
        MovementStatus checkStatus = checkOrUpdatePath(p);
        if (checkStatus.isFailure()) {
            return checkStatus;
        }

        Either<MessageError, Direction> isDirBetween = Direction.between(p.getPosition(), path.get(0));
        if (isDirBetween.isLeft()) {
            this.path = new ArrayList<>();
            return MovementStatus.failure(isDirBetween.getLeft());
        }

        MovementStatus status = grid.movePersonnage(p, isDirBetween.getRight());
        if (!status.isFailure()) {
            path.remove(0);
        }
        return status;
    }

    /**
     * Renvoie le chemin actuellement mémorisé.
     *
     * @return un {@code Either} contenant la liste des positions du chemin actuel,
     *         ou une erreur si aucun chemin n'est défini
     */
    @Override
    public Either<MessageError, List<Position>> getPath() {
        if (path == null || path.isEmpty()) {
            return Either.left(PathError.emptyPath());
        }
        return Either.right(Collections.unmodifiableList(path));
    }
}