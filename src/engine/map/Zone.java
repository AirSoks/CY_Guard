package engine.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import engine.personnage.Personnage;


/**
 * Représente une zone sur la grille : un ensemble de positions.
 * Fournit des méthodes pour récupérer les personnages présents dans cette zone.
 *
 * @author AirSoks
 * @since 2025-05-06
 * @version 1.0
 */
public class Zone {

    private final GridService gridService;
    private final Set<Position> positions;

    /**
     * Crée une nouvelle zone liée à une grille.
     *
     * @param grid La grille associée.
     * @param positions Les positions qui composent cette zone.
     */
    public Zone(Collection<Position> positions) {
        this.gridService = GridService.getInstance();
        this.positions = new HashSet<>(positions);
    }
    
    public Zone() {
    	this(Collections.emptySet());
    }

    /**
     * Retourne toutes les positions couvertes par cette zone.
     *
     * @return Une collection non modifiable des positions.
     */
    public Collection<Position> getPositions() {
        return Collections.unmodifiableSet(positions);
    }

    /**
     * Récupère tous les personnages présents dans cette zone.
     *
     * @return Une collection non modifiable des personnages présents.
     */
    public Collection<Personnage> getPersonnages() {
        List<Personnage> result = new ArrayList<>();
        for (Position pos : positions) {
            Cell cell = gridService.getGrid().getCell(pos.x(), pos.y());
            if (cell != null) {
                result.addAll(cell.getPersonnages());
            }
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Récupère tous les personnages d'un type spécifique présents dans cette zone.
     *
     * @param type Le type de personnage.
     * @param <E>  Le type étendu de Personnage.
     * @return Une collection non modifiable des personnages du type donné.
     */
    public <E extends Personnage> Collection<E> getPersonnagesByType(Class<E> type) {
        List<E> result = new ArrayList<>();
        for (Position pos : positions) {
            Cell cell = gridService.getGrid().getCell(pos.x(), pos.y());
            if (cell != null) {
                for (Personnage p : cell.getPersonnages()) {
                    if (type.isInstance(p)) {
                        result.add(type.cast(p));
                    }
                }
            }
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Ajoute une position à la zone.
     *
     * @param position La position à ajouter.
     * @return true si la position a été ajoutée, false si elle était déjà présente.
     */
    public boolean addPosition(Position position) {
        return positions.add(position);
    }

    /**
     * Retire une position de la zone.
     *
     * @param position La position à retirer.
     * @return true si la position était présente et a été retirée.
     */
    public boolean removePosition(Position position) {
        return positions.remove(position);
    }

    /**
     * Vide toutes les positions de la zone.
     */
    public void clear() {
        positions.clear();
    }
}
