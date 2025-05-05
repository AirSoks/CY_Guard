package engine.interaction;

import engine.personnage.Guardian;
import engine.personnage.Intruder;

/**
 * Visiteur d'interaction pour les intrus.
 * Centralise la logique d'interaction entre un intrus (source) et d'autres personnages.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public class IntruderInteraction implements PersonnageInteractionVisitor {
    private final Intruder source;

    /**
     * Constructeur.
     *
     * @param source L'intrus à l'origine de l'interaction
     */
    public IntruderInteraction(Intruder source) {
        this.source = source;
    }

    /**
     * Gère l'interaction entre un intrus et un gardien.
     * Permet la coopération ou l'évasion (ex : fuite face à un gardien).
     *
     * @param target Le gardien avec lequel interagir
     */
    @Override
    public void visitGuardian(Guardian target) {
        // On ajoute la position de l'intrus pour l'évasion
        if (source.getTargetManager().getTarget() instanceof Guardian) {
            // source.cooperate(target);
        }
    }

    /**
     * Gère l'interaction entre deux intrus.
     * Permet la coopération (ex : partage d'informations).
     *
     * @param target L'intrus avec lequel interagir
     */
    @Override
    public void visitIntruder(Intruder target) {
        // On récupère les gardiens vu de l'autre intrus.
    }
}
