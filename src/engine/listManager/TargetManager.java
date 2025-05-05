package engine.listManager;

import java.util.Set;
import engine.personnage.Personnage;

/**
 * Classe abstraite qui fournit une implémentation de base de {@link ListManager}.
 * Permet de choisir la structure Set sous-jacente via le constructeur.
 * <p>
 * Cette classe sert de gestionnaire pour une collection de personnages cibles.
 * Elle permet de définir des comportements spécifiques pour ajouter, retirer ou 
 * filtrer les cibles à travers la méthode abstraite {@link #getTarget()}.
 * </p>
 * 
 * @author AirSoks
 * @since 2025-05-05
 * @version 1.0
 */
public abstract class TargetManager extends AbstractPersonnageManager {

    /**
     * Constructeur permettant de fournir un Set personnalisé pour stocker les cibles.
     *
     * @param setImpl L'implémentation concrète du Set (HashSet, LinkedHashSet, etc.)
     */
    protected TargetManager(Set<Personnage> setImpl) {
        super(setImpl);
    }

    /**
     * Méthode abstraite pour récupérer la cible appropriée.
     * Chaque sous-classe doit implémenter cette méthode pour définir son propre comportement
     * de sélection de cible.
     *
     * @return La cible choisie (un personnage) ou {@code null} si aucune cible n'est disponible.
     */
    abstract public Personnage getTarget();
}
