package engine.action.displacement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import engine.message.error.*;
import engine.map.Direction;
import engine.map.Grid;
import engine.map.Position;
import engine.message.MessageError;
import engine.personnage.Personnage;
import engine.util.Either;

/**
 * Implémentation concrète de {@link AbstractDisplacement} qui calcule un déplacement aléatoire.
 *
 * Cette classe choisit une direction aléatoire et génère un chemin d'une seule case
 * correspondant à la nouvelle position visée.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public class RandomDisplacement extends AbstractDisplacement {

    /**
     * Crée un déplacement aléatoire associé à une grille donnée.
     *
     * @param grid la grille où le personnage se déplace
     */
    public RandomDisplacement(Grid grid) {
        super(grid);
    }

    /**
     * Calcule un mouvement aléatoire : sélectionne une direction au hasard et retourne
     * la nouvelle position qui en découle (si valide).
     *
     * Ce mouvement est un déplacement d'une seule case, basé sur une direction choisie aléatoirement.
     *
     * @param p le personnage concerné
     * @return un {@code Either} contenant la liste d'une seule position (le prochain point),
     *         ou une erreur si le calcul échoue.
     *         - En cas de succès, renvoie une liste contenant une position valide.
     *         - En cas d'erreur, renvoie une erreur liée à la direction ou à la position du personnage.
     */
    @Override
    public Either<MessageError, List<Position>> calculate(Personnage p) {
        if (p == null) {
            return Either.left(new NullClassError(Personnage.class));
        } Position position = p.getPosition();
        if (position == null) {
            return Either.left(new NullClassError(Position.class));
        }
        Direction randomDir = Direction.randomDirection();

        Either<MessageError, Position> positionEither = position.move(randomDir);
        if (positionEither.isLeft()) {
            return Either.left(positionEither.getLeft());
        }

        this.path = new ArrayList<>(Arrays.asList(positionEither.getRight()));
        return Either.right(path);
    }
}
