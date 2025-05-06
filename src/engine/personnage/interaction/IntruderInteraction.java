package engine.personnage.interaction;

import engine.personnage.Guardian;
import engine.personnage.Intruder;
import engine.personnage.Personnage;

/**
 * Visiteur d'interaction pour les intrus.
 * Centralise la logique d'interaction entre un intrus (source) et d'autres abstractPersonnages.
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
     * Permet l'évasion de l'intrus fasse au gardien.
     *
     * @param target Le gardien avec lequel interagir
     */
    @Override
    public void visitGuardian(Guardian target) {
    	source.getTargetManager().add(target);
    }

    /**
     * Gère l'interaction entre deux intrus.
     * Permet la coopération (partage de cibles).
     *
     * @param target L'intrus avec lequel interagir
     */
    @Override
    public void visitIntruder(Intruder target) {
    	Personnage sTarget = source.getTargetManager().getTarget();
    	target.getTargetManager().add(sTarget);
    }
}
