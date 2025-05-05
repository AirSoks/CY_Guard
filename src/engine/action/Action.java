package engine.action;

/**
 * Représente une action générique exécutée par un {@code Personnage} ou autre entité.
 * <p>
 * Cette interface formalise trois grandes étapes :
 * <ul>
 *     <li>Le calcul préalable de l'action ;</li>
 *     <li>L'accès au résultat ou état actuel de l'action.</li>
 * </ul>
 * 
 * @param <T> le type du contexte (ex : {@code Personnage})
 * @param <C> le type du résultat calculé (souvent une {@code Either} avec {@code MessageError})
 * 
 * @author AirSoks
 * @since 2025-05-03
 * @version 1.0
 */
public interface Action<T, C> {

    /**
     * Calcule une action à partir de l'état donné.
     *
     * @param context le contexte ou l'entité concernée (par ex. un personnage).
     * @return le résultat du calcul (ex. chemin, vision, etc.).
     */
    C calculate(T context);

    /**
     * Retourne l'état actuel stocké (ex. dernier chemin, dernière vision...).
     *
     * @param context le contexte ou l'entité concernée (par ex. un personnage).
     * @return le dernier état connu de l'action.
     */
    C getCurrent();
}