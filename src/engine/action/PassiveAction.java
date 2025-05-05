package engine.action;

/**
 * Action passive : seulement calcul + résultat courant, pas d'exécution.
 *
 * @param <T> Le type du contexte (ex: Personnage).
 * @param <C> Le type du résultat courant.
 */
public interface PassiveAction<T, C> extends Action<T, C> {
}