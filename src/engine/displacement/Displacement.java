package engine.displacement;

import java.util.List;

import engine.error.*;
import engine.map.Position;
import engine.mouvement.MovementStatus;
import engine.personnage.Personnage;
import engine.util.Either;

/**
 * Représente un comportement de déplacement pour un personnage.
 * 
 * Cette interface définit les méthodes nécessaires pour :
 * - Calculer un chemin que doit suivre un personnage ;
 * - Déplacer le personnage selon la logique propre à l'implémentation ;
 * - Récupérer le chemin prévu ou calculé.
 * 
 * Les implémentations peuvent varier : déplacements simples (par exemple un pas),
 * déplacements intelligents (pathfinding), ou déplacements conditionnels.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public interface Displacement {

    /**
     * Calcule un chemin de déplacement pour le personnage spécifié.
     * 
     * @param p Le personnage pour lequel le chemin doit être calculé.
     * @return Un {@code Either} contenant la liste des positions représentant le chemin à parcourir,
     *         ou un message d'erreur s'il y a un problème (par exemple, personnage null ou impossibilité de calculer un chemin).
     */
    Either<MessageError, List<Position>> calculateMove(Personnage p);

    /**
     * Exécute le déplacement du personnage selon la logique définie par l'implémentation.
     * Peut utiliser le chemin préalablement calculé ou appliquer une logique directe.
     *
     * @param p Le personnage à déplacer.
     * @return Un {@code MovementStatus} représentant le résultat du déplacement (succès ou échec, avec détails).
     */
    MovementStatus move(Personnage p);

    /**
     * Récupère le chemin actuellement stocké ou calculé par le déplacement.
     * Utile pour accéder au chemin prévu sans recalculer.
     *
     * @return Un {@code Either} contenant la liste des positions si disponible,
     *         ou un message d'erreur si aucun chemin n'est défini ou disponible.
     */
    Either<MessageError, List<Position>> getPath();
}