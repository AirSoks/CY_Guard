package engine.map;

import java.util.Objects;

import engine.error.*;
import engine.util.Either;

/**
 * Représente une position immuable sur une grille 2D, définie par des coordonnées (x, y).
 * 
 * <p>Cette classe fournit des méthodes utilitaires pour déplacer une position selon une {@link Direction},
 * et surcharge {@code equals}, {@code hashCode} et {@code toString} pour une utilisation fiable dans des collections
 * ou pour du débogage.</p>
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public final class Position {
    
    /**
     * Coordonnée horizontale (axe X).
     */
    private final int x;
    
    /**
     * Coordonnée verticale (axe Y).
     */
    private final int y;

    /**
     * Construit une nouvelle position avec les coordonnées spécifiées.
     *
     * @param x la coordonnée X
     * @param y la coordonnée Y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retourne la coordonnée X de cette position.
     *
     * @return la valeur de X
     */
    public int x() {
        return x;
    }

    /**
     * Retourne la coordonnée Y de cette position.
     *
     * @return la valeur de Y
     */
    public int y() {
        return y;
    }

    /**
     * Tente de créer une nouvelle position en appliquant la direction donnée.
     * 
     * @param direction la direction à appliquer
     * @return un {@code Either} contenant la nouvelle position ou un message d'erreur si {@code direction} est {@code null}
     */
    public Either<MessageError, Position> move(Direction direction) {
        if (direction == null) {
            return Either.left(new NullClassError(Direction.class));
        }

        int newX = x + direction.deltaX();
        int newY = y + direction.deltaY();

        return Either.right(new Position(newX, newY));
    }

    /**
     * Vérifie si cette position est valide (aucune coordonnée négative).
     *
     * @return un {@code Either} contenant cette position si valide, ou un message d'erreur sinon
     */
    public Either<MessageError, Position> isPositive() {
        if (x >= 0 && y >= 0) {
            return Either.right(this);
        } else {
            return Either.left(PositionError.negPosition(this));
        }
    }
    
    /**
     * Calcule la distance entre deux positions.
     * 
     * @param other La position à comparer
     * @return La distance entre cette position et `other`
     */
    public double distanceTo(Position other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    /**
     * Calcule le code de hachage basé sur les coordonnées X et Y.
     *
     * @return le code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Vérifie l'égalité avec un autre objet.
     * Deux positions sont égales si elles ont les mêmes coordonnées X et Y.
     *
     * @param obj l'objet à comparer
     * @return {@code true} si les positions sont identiques, {@code false} sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        return x == other.x && y == other.y;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de cette position.
     *
     * @return une chaîne sous la forme {@code Position{x=X, y=Y}}
     */
    @Override
    public String toString() {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }
}
