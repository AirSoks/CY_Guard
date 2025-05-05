package engine.listManager;

import java.util.List;
import engine.personnage.Personnage;

/**
 * Interface représentant un gestionnaire de liste de personnages.
 * Définit les opérations de gestion, d'ajout, de retrait et de filtrage des personnages.
 * Permet aux implémentations concrètes de gérer dynamiquement des ensembles de personnages,
 * en fournissant des méthodes pour récupérer ou modifier la liste.
 * 
 * @author AirSoks
 * @since 2025-05-05
 * @version 1.0
 */
public interface PersonnageManager {

    /**
     * Récupère la liste complète des personnages gérés.
     *
     * @return Une liste non modifiable contenant tous les personnages actuellement gérés.
     */
    List<Personnage> getPersonnages();

    /**
     * Récupère la liste des personnages en excluant un personnage spécifique.
     *
     * @param personnage Le personnage à exclure de la liste retournée.
     * @return Une liste non modifiable contenant tous les personnages sauf celui spécifié.
     */
    List<Personnage> getPersonnages(Personnage personnage);

    /**
     * Récupère la liste des personnages d'un type spécifique.
     *
     * @param <E>  Le type de personnage ciblé.
     * @param type La classe du type de personnage à filtrer.
     * @return Une liste non modifiable contenant uniquement les personnages du type spécifié.
     */
    <E extends Personnage> List<E> getPersonnagesOfType(Class<E> type);

    /**
     * Ajoute un personnage à la liste des personnages gérés.
     *
     * @param personnage Le personnage à ajouter.
     */
    void addPersonnage(Personnage personnage);

    /**
     * Ajoute plusieurs personnages à la liste des personnages gérés.
     *
     * @param personnages La liste des personnages à ajouter.
     */
    void addPersonnages(List<Personnage> personnages);

    /**
     * Retire un personnage de la liste des personnages gérés.
     *
     * @param personnage Le personnage à retirer.
     */
    void removePersonnage(Personnage personnage);

    /**
     * Retire plusieurs personnages de la liste des personnages gérés.
     *
     * @param personnages La liste des personnages à retirer.
     */
    void removePersonnages(List<Personnage> personnages);

    /**
     * Retire tous les personnages d'un type spécifique de la liste.
     *
     * @param <E>  Le type de personnage ciblé.
     * @param type La classe du type de personnage à retirer.
     */
    <E extends Personnage> void removePersonnagesOfType(Class<E> type);

    /**
     * Retire tous les personnages actuellement gérés (vide la liste).
     */
    void removeAll();
}
