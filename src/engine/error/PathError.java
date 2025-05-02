package engine.error;

import java.util.Collections;
import java.util.List;

import engine.map.Position;

/**
 * Représente des erreurs spécifiques liées à un chemin ({@link List} de {@link Position}),
 * par exemple si le chemin est vide, contient des positions invalides ou est impraticable.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public final class PathError implements MessageError {

    /**
     * Type d'erreur de chemin.
     */
    public enum Type {
        EMPTY_PATH,
        INVALID_POSITIONS,
        IMPASSABLE_PATH
    }

    private final Type type;
    private final List<Position> path;
    private final Position invalidPosition;

    private PathError(Type type, List<Position> path, Position invalidPosition) {
        this.type = type;
        this.path = path;
        this.invalidPosition = invalidPosition;
    }

    /**
     * Crée une erreur indiquant que le chemin est vide.
     *
     * @return une instance de {@code PathError}
     */
    public static PathError emptyPath() {
        return new PathError(Type.EMPTY_PATH, Collections.emptyList(), null);
    }

    /**
     * Crée une erreur indiquant que le chemin contient une position invalide.
     *
     * @param path le chemin complet
     * @param invalidPosition la position invalide repérée
     * @return une instance de {@code PathError}
     */
    public static PathError invalidPositions(List<Position> path, Position invalidPosition) {
        return new PathError(Type.INVALID_POSITIONS, path, invalidPosition);
    }

    /**
     * Crée une erreur indiquant que le chemin est impraticable (obstacles, blocages, etc.).
     *
     * @param path le chemin impraticable
     * @return une instance de {@code PathError}
     */
    public static PathError impassablePath(List<Position> path) {
        return new PathError(Type.IMPASSABLE_PATH, path, null);
    }

    /**
     * Retourne le message d'erreur détaillé correspondant au type de l'erreur.
     *
     * @return un message décrivant l'erreur
     */
    @Override
    public String getMessage() {
        switch (type) {
            case EMPTY_PATH:
                return "The provided path is empty.";
            case INVALID_POSITIONS:
                return "The path contains an invalid position: " + invalidPosition + ". Full path: " + path;
            case IMPASSABLE_PATH:
                return "The path is impassable due to obstacles. Path: " + path;
            default:
                return "Unknown path error.";
        }
    }
}
