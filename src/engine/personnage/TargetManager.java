package engine.personnage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Classe abstraite qui gère une collection de personnages cibles avec des règles personnalisées.
 * <p>
 * Elle permet d'ajouter, retirer, filtrer et consulter des personnages cibles,
 * en s'appuyant sur un Set sous-jacent. Les sous-classes doivent définir les critères de validité
 * via {@link #isValid(Personnage)} et la logique de sélection via {@link #getTarget()}.
 * </p>
 * 
 * @author AirSoks
 * @since 2025-05-05
 * @version 2.1
 */
public abstract class TargetManager {

    protected final Set<Personnage> elements;

    /**
     * Crée un nouveau TargetManager avec une implémentation de Set personnalisée.
     *
     * @param setImpl Le Set à utiliser pour stocker les cibles (par ex. HashSet, LinkedHashSet).
     * @throws NullPointerException si {@code setImpl} est {@code null}.
     */
    protected TargetManager(Set<Personnage> setImpl) {
    	if (setImpl == null) setImpl = Collections.emptySet();
        this.elements = setImpl;
    }
    
    protected TargetManager() {
    	this(Collections.emptySet());
    }

    /**
     * Ajoute un personnage à la collection s'il est valide.
     *
     * @param personnage Le personnage à ajouter.
     * @return {@code true} si le personnage a été ajouté ; {@code false} sinon.
     * @throws NullPointerException si {@code personnage} est {@code null}.
     */
    public boolean add(Personnage personnage) {
        if (personnage == null) {
            throw new NullPointerException("Le personnage ne peut pas être null");
        }
        if (isValid(personnage)) {
            return elements.add(personnage);
        }
        return false;
    }

    /**
     * Ajoute plusieurs personnages à la collection.
     *
     * @param personnages La collection de personnages à ajouter.
     * @return {@code true} si au moins un personnage a été ajouté ; {@code false} sinon.
     * @throws NullPointerException si {@code personnages} est {@code null}.
     */
    public boolean add(Collection<? extends Personnage> personnages) {
        boolean modified = false;
        for (Personnage p : personnages) {
            if (add(p)) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Retire un personnage de la collection.
     *
     * @param personnage Le personnage à retirer.
     * @return {@code true} si le personnage a été retiré ; {@code false} sinon.
     */
    public boolean remove(Personnage personnage) {
        return elements.remove(personnage);
    }

    /**
     * Retire plusieurs personnages de la collection.
     *
     * @param personnages La collection de personnages à retirer.
     * @return {@code true} si au moins un personnage a été retiré ; {@code false} sinon.
     * @throws NullPointerException si {@code personnages} est {@code null}.
     */
    public boolean remove(Collection<? extends Personnage> personnages) {
        return elements.removeAll(personnages);
    }

    /**
     * Retire tous les personnages d’un type spécifique.
     *
     * @param <E>  Le type des personnages ciblés (sous-type de Personnage).
     * @param type La classe du type de personnages à retirer.
     * @return {@code true} si au moins un personnage a été retiré ; {@code false} sinon.
     * @throws NullPointerException si {@code type} est {@code null}.
     */
    public <E extends Personnage> boolean remove(Class<E> type) {
        return elements.removeIf(type::isInstance);
    }

    /**
     * Vide complètement la collection de personnages.
     */
    public void clear() {
        elements.clear();
    }

    /**
     * Récupère un Set non modifiable de tous les personnages.
     *
     * @return Un Set non modifiable des personnages actuellement stockés.
     */
    public Set<Personnage> getAll() {
        return Collections.unmodifiableSet(elements);
    }

    /**
     * Récupère une collection non modifiable de tous les personnages d’un type spécifique.
     *
     * @param <E>  Le type des personnages ciblés (sous-type de Personnage).
     * @param type La classe du type de personnages à récupérer.
     * @return Une collection non modifiable contenant uniquement les personnages du type spécifié.
     * @throws NullPointerException si {@code type} est {@code null}.
     */
    public <E extends Personnage> Collection<E> get(Class<E> type) {
        Collection<E> filtered = new ArrayList<>();
        for (Personnage e : elements) {
            if (type.isInstance(e)) {
                filtered.add(type.cast(e));
            }
        }
        return Collections.unmodifiableCollection(filtered);
    }

    /**
     * Vérifie si le personnage donné est valide pour être ajouté.
     * Cette méthode doit être implémentée par les sous-classes.
     *
     * @param personnage Le personnage à vérifier.
     * @return {@code true} si le personnage peut être ajouté ; {@code false} sinon.
     */
    protected abstract boolean isValid(Personnage personnage);

    /**
     * Méthode abstraite pour récupérer la cible appropriée.
     * Chaque sous-classe doit implémenter cette méthode pour définir sa propre logique.
     *
     * @return La cible choisie (un personnage) ou {@code null} si aucune cible n'est disponible.
     */
    public abstract Personnage getTarget();
}
