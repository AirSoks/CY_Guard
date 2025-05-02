package engine.map;

/**
 * Enumération représentant les différents types d'obstacles possibles sur la carte.
 * 
 * <p>Chaque type d'obstacle définit s'il bloque le mouvement et/ou la vision :</p>
 * <ul>
 *     <li>{@code PLAIN} : n'empêche ni le mouvement ni la vision.</li>
 *     <li>{@code TREE} : n'empêche pas le mouvement mais bloque la vision.</li>
 *     <li>{@code WATER} : empêche le mouvement mais n'affecte pas la vision.</li>
 *     <li>{@code WALL} : bloque à la fois le mouvement et la vision.</li>
 * </ul>
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public enum ObstacleType {
    
    /**
     * Terrain dégagé : aucune obstruction au mouvement ni à la vision.
     */
    PLAIN(false, false),
    
    /**
     * Arbre : n'empêche pas le mouvement mais bloque la vision.
     */
    TREE(false, true),
    
    /**
     * Eau : empêche le mouvement mais permet la vision.
     */
    WATER(true, false),
    
    /**
     * Mur : empêche à la fois le mouvement et la vision.
     */
    WALL(true, true);

    private final boolean blocksMovement;
    private final boolean blocksVision;

    /**
     * Construit un type d'obstacle avec ses caractéristiques.
     *
     * @param blocksMovement {@code true} si l'obstacle bloque le mouvement
     * @param blocksVision {@code true} si l'obstacle bloque la vision
     */
    ObstacleType(boolean blocksMovement, boolean blocksVision) {
        this.blocksMovement = blocksMovement;
        this.blocksVision = blocksVision;
    }

    /**
     * Indique si ce type d'obstacle bloque le mouvement.
     *
     * @return {@code true} si le mouvement est bloqué, sinon {@code false}
     */
    public boolean blocksMovement() {
        return blocksMovement;
    }

    /**
     * Indique si ce type d'obstacle bloque la vision.
     *
     * @return {@code true} si la vision est bloquée, sinon {@code false}
     */
    public boolean blocksVision() {
        return blocksVision;
    }
}
