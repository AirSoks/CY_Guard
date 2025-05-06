package engine.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import engine.util.Either;
import engine.util.message.MessageError;
import engine.util.message.error.NullClassError;
import engine.util.message.error.PositionError;

/**
 * Représente les quatre directions cardinales possibles sur une grille : HAUT, BAS, GAUCHE, DROITE.
 * Chaque direction est définie par un décalage en X et en Y.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public enum Direction {
	
    /**
     * Direction vers le haut (deltaX = 0, deltaY = -1).
     */
    UP(0, -1),

    /**
     * Direction vers le bas (deltaX = 0, deltaY = 1).
     */
    DOWN(0, 1),

    /**
     * Direction vers la gauche (deltaX = -1, deltaY = 0).
     */
    LEFT(-1, 0),

    /**
     * Direction vers la droite (deltaX = 1, deltaY = 0).
     */
    RIGHT(1, 0);

    private final int deltaX;
    private final int deltaY;

    /**
     * Liste statique contenant toutes les directions.
     * Peut être utilisée pour des itérations ou des opérations.
     */
    public static final List<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));

    /**
     * Construit une direction avec les décalages en X et Y donnés.
     *
     * @param deltaX décalage en X
     * @param deltaY décalage en Y
     */
    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Retourne le décalage horizontal (X) associé à cette direction.
     *
     * @return le décalage en X
     */
    public int deltaX() {
        return deltaX;
    }

    /**
     * Retourne le décalage vertical (Y) associé à cette direction.
     *
     * @return le décalage en Y
     */
    public int deltaY() {
        return deltaY;
    }

    /**
     * Détermine la direction nécessaire pour aller d'une position à une position adjacente.
     *
     * @param from la position de départ
     * @param to la position d'arrivée (doit être adjacente)
     * @return un {@code Either} contenant la direction si trouvée,
     *         sinon un message d'erreur approprié :
     *         <ul>
     *             <li>{@link NullClassError} si un des paramètres est {@code null}</li>
     *             <li>{@link PositionError#samePosition(Position)} si les deux positions sont identiques</li>
     *             <li>{@link PositionError#nonAdjacent(Position, Position)} si les positions ne sont pas adjacentes</li>
     *         </ul>
     */
    public static Either<MessageError, Direction> adjacentDirection(Position from, Position to) {
        if (from == null) {
            return Either.left(new NullClassError(Position.class).with(() -> "parameter: from"));
        } if (to == null) {
            return Either.left(new NullClassError(Position.class).with(() -> "parameter: to"));
        }

        if (from == to) {
            return Either.left(PositionError.samePosition(from));
        }

        int dx = to.x() - from.x();
        int dy = to.y() - from.y();

        for (Direction direction : Direction.values()) {
            if (direction.deltaX == dx && direction.deltaY == dy) {
                return Either.right(direction);
            }
        }
        return Either.left(PositionError.nonAdjacent(from, to));
    }
    
    /**
     * Donne la direction principale pour aller d'une position à une autre, même si elles ne sont pas adjacentes.
     *
     * @param from la position de départ
     * @param to la position d'arrivée
     * @return un {@code Either} contenant la direction si trouvée,
     *         sinon un message d'erreur approprié :
     *         <ul>
     *             <li>{@link NullClassError} si un des paramètres est {@code null}</li>
     *             <li>{@link PositionError#samePosition(Position)} si les deux positions sont identiques</li>
     *         </ul>
     */
    public static Either<MessageError, Direction> mainDirectionTo(Position from, Position to) {
        if (from == null) {
            return Either.left(new NullClassError(Position.class).with(() -> "parameter: from"));
        }
        if (to == null) {
            return Either.left(new NullClassError(Position.class).with(() -> "parameter: to"));
        } if (from == to) {
            return Either.left(PositionError.samePosition(from));
        }

        int dx = to.x() - from.x();
        int dy = to.y() - from.y();

        if (Math.abs(dx) >= Math.abs(dy)) {
            // Déplacement principal horizontal
            if (dx > 0) {
                return Either.right(Direction.RIGHT);
            } else {
                return Either.right(Direction.LEFT);
            }
        } else {
            // Déplacement principal vertical
            if (dy > 0) {
                return Either.right(Direction.DOWN);
            } else {
                return Either.right(Direction.UP);
            }
        }
    }

    /**
     * Retourne une liste des directions disponibles dans un ordre aléatoire.
     * Utile pour parcourir des directions de manière non déterministe.
     *
     * @return une liste mélangée de directions
     */
    public static List<Direction> randomDirections() {
        List<Direction> directions = Direction.directions;
        Collections.shuffle(directions);
        return directions;
    }

    /**
     * Retourne une direction aléatoire parmi celles disponibles.
     *
     * @return une direction aléatoire
     */
    public static Direction randomDirection() {
        return randomDirections().get(0);
    }
}
