package engine.action;

/**
 * Action active : en plus du calcul, elle peut exécuter une action concrète.
 *
 * @param <T> Le type du contexte (ex: Personnage).
 * @param <C> Le type du résultat courant.
 * @param <R> Le type du résultat d'exécution (ex: Outcome<?>).
 */
public interface ActiveAction<T, C, R> extends Action<T, C> {

    /**
     * Exécute l'action de manière concrète.
     *
     * @param context le contexte à utiliser (ex: un personnage)
     * @return le résultat de l'exécution
     */
    R execute(T context);
}