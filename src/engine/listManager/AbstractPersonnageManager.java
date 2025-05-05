package engine.listManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import engine.personnage.Personnage;

/**
 * Classe abstraite représentant un gestionnaire de liste de personnages.
 * Fournit des méthodes de base pour ajouter, retirer et filtrer des personnages,
 * tout en laissant la possibilité aux sous-classes de définir leurs propres critères de validité via {@link #isValidTarget}.
 * <p>
 * Les listes retournées sont non modifiables afin d'assurer l'intégrité interne des données.
 * </p>
 * 
 * @since 2025-05-05
 * @version 1.0
 * @author AirSoks
 */
public abstract class AbstractPersonnageManager implements PersonnageManager {

    /** La collection interne des personnages gérés par ce manager. */
    protected final Set<Personnage> personnages;

    /**
     * Constructeur permettant de fournir une implémentation personnalisée du Set.
     *
     * @param setImpl L'implémentation concrète du Set (par exemple : HashSet, LinkedHashSet, etc.)
     */
    protected AbstractPersonnageManager(Set<Personnage> setImpl) {
        this.personnages = setImpl;
    }

    /**
     * Vérifie si le personnage donné est un candidat valide pour être ajouté.
     * Cette méthode doit être implémentée par les sous-classes afin de définir
     * leurs propres critères de validité.
     *
     * @param personnage Le personnage à vérifier.
     * @return {@code true} si le personnage peut être ajouté ; {@code false} sinon.
     */
    protected abstract boolean isValidTarget(Personnage personnage);

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Personnage> getPersonnages() {
        return Collections.unmodifiableList(new ArrayList<>(personnages));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Personnage> getPersonnages(Personnage personnage) {
        List<Personnage> result = new ArrayList<>(personnages);
        result.remove(personnage);
        return Collections.unmodifiableList(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends Personnage> List<E> getPersonnagesOfType(Class<E> type) {
        List<E> filtered = new ArrayList<>();
        for (Personnage p : personnages) {
            if (type.isInstance(p)) {
                filtered.add(type.cast(p));
            }
        }
        return Collections.unmodifiableList(filtered);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Le personnage n'est ajouté que s'il est jugé valide par {@link #isValidTarget(Personnage)}.
     * </p>
     */
    @Override
    public void addPersonnage(Personnage personnage) {
        if (isValidTarget(personnage)) {
            personnages.add(personnage);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Chaque personnage de la liste est ajouté uniquement s'il est jugé valide par {@link #isValidTarget(Personnage)}.
     * </p>
     */
    @Override
    public void addPersonnages(List<Personnage> personnages) {
        for (Personnage p : personnages) {
            addPersonnage(p);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePersonnage(Personnage personnage) {
        personnages.remove(personnage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePersonnages(List<Personnage> personnages) {
        this.personnages.removeAll(personnages);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends Personnage> void removePersonnagesOfType(Class<E> type) {
        personnages.removeIf(type::isInstance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAll() {
        personnages.clear();
    }
}
