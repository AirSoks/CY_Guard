package engine.action.displacement;

import java.util.List;

import engine.action.ActiveAction;
import engine.map.Position;
import engine.map.Position.PositionPair;
import engine.message.MessageError;
import engine.personnage.Personnage;
import engine.util.Outcome;
import engine.util.Either;

/**
 * Définit le comportement de déplacement d'un personnage.
 * <p>
 * Cette interface formalise les méthodes nécessaires pour :
 * <ul>
 *     <li>Calculer un chemin que le personnage doit emprunter ;</li>
 *     <li>Exécuter le déplacement du personnage selon la logique définie ;</li>
 *     <li>Accéder au chemin actuellement prévu ou stocké.</li>
 * </ul>
 * <p>
 * Les implémentations peuvent aller de déplacements basiques (un pas simple)
 * à des déplacements complexes (pathfinding, comportements conditionnels, etc.).
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.1
 */
public interface Displacement extends ActiveAction<Personnage, Either<MessageError, List<Position>>, Outcome<PositionPair>> {

    /**
     * Calcule un chemin que doit suivre le personnage spécifié.
     *
     * @param p Le personnage pour lequel le chemin doit être calculé.
     * @return Un {@code Either} contenant :
     *         <ul>
     *             <li>Une liste de {@link Position} représentant le chemin calculé en cas de succès ;</li>
     *             <li>Un {@link MessageError} décrivant la cause de l'échec (par exemple : personnage nul, impossibilité de calculer un chemin) en cas d'erreur.</li>
     *         </ul>
     */
	@Override
    Either<MessageError, List<Position>> calculate(Personnage personnage);

    /**
     * Exécute le déplacement du personnage selon la logique propre à l'implémentation.
     * <p>
     * Le déplacement s'appuie sur le chemin préalablement calculé.
     *
     * @param p Le personnage à déplacer.
     * @return Un {@link Outcome} contenant toujours la {@link PositionPair} (direction du mouvement) :
     *         <ul>
     *             <li>En cas de succès : la paire reflète la position avant et après déplacement ;</li>
     *             <li>En cas d'échec : la paire peut refléter la tentative de déplacement même si celui-ci est bloqué, accompagnée d'une erreur décrivant la raison.</li>
     *         </ul>
     */
	@Override
    Outcome<PositionPair> execute(Personnage personnage);

    /**
     * Retourne le chemin actuellement stocké ou calculé pour le déplacement.
     * <p>
     * Permet d'accéder au chemin sans déclencher de nouveau calcul.
     *
     * @return Un {@code Either} contenant :
     *         <ul>
     *             <li>Une liste de {@link Position} si un chemin est disponible ;</li>
     *             <li>Un {@link MessageError} si aucun chemin n'a été défini ou si une erreur est survenue.</li>
     *         </ul>
     */
	@Override
    Either<MessageError, List<Position>> getCurrent();
}