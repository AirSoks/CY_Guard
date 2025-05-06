package engine.map;

import java.util.HashMap;
import java.util.Map;

/**
 * Représente une grille de jeu composée de cellules.
 * Elle agit uniquement comme structure de données et ne contient aucune logique métier.
 * 
 * @author AirSoks
 * @since 2025-05-06
 * @version 2.0
 */
public class Grid {

    private final int width;
    private final int height;
    private final Cell[][] cells;

    /**
     * Crée une nouvelle grille avec la largeur et la hauteur spécifiées.
     * Initialise chaque cellule avec l'obstacle par défaut (PLAIN).
     *
     * @param width  Largeur de la grille (nombre de colonnes).
     * @param height Hauteur de la grille (nombre de lignes).
     */
    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(ObstacleType.PLAIN);
            }
        }
    }

    /**
     * Retourne la largeur de la grille.
     * 
     * @return la largeur.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retourne la hauteur de la grille.
     * 
     * @return la hauteur.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retourne la cellule située à une position donnée.
     *
     * @param x Coordonnée X.
     * @param y Coordonnée Y.
     * @return la cellule correspondante, ou null si hors limites.
     */
    public Cell getCell(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }
        return cells[x][y];
    }

    /**
     * Retourne une entrée aléatoire de la grille (position + cellule).
     * 
     * @return une entrée Map contenant la position et la cellule.
     */
    public Map.Entry<Position, Cell> getRandomEntry() {
        int randomX = (int) (Math.random() * width);
        int randomY = (int) (Math.random() * height);
        Position position = new Position(randomX, randomY);
        Cell cell = cells[randomX][randomY];
        return new HashMap.SimpleEntry<>(position, cell);
    }
}
