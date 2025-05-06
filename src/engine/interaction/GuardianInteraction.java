package engine.interaction;

import java.util.Collection;

import engine.map.GridService;
import engine.personnage.Guardian;
import engine.personnage.Intruder;
import engine.personnage.Personnage;

/**
 * Visiteur d'interaction pour les gardiens.
 * Centralise la logique d'interaction entre un gardien (source) et d'autres abstractPersonnages.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public class GuardianInteraction implements PersonnageInteractionVisitor {
	
    private final GridService gridService;
    
    private final Guardian source;

    /**
     * Constructeur.
     *
     * @param source Le gardien à l'origine de l'interaction
     */
    public GuardianInteraction(Guardian source) {
        this.source = source;
        this.gridService = GridService.getInstance();
    }

    /**
     * Gère l'interaction entre deux gardiens.
     * Permet la coopération (partage de cibles).
     *
     * @param target Le gardien avec lequel interagir
     */
    @Override
    public void visitGuardian(Guardian target) {
    	Collection<Personnage> sTargets = source.getTargetManager().getAll();
    	target.getTargetManager().add(sTargets);
    }

    /**
     * Gère l'interaction entre un gardien et un intrus.
     * Permet la capture ou sa poursuite.
     *
     * @param target L'intrus avec lequel interagir
     */
    @Override
    public void visitIntruder(Intruder target) {
        if (source.getPosition().equals(target.getPosition())) {
        	gridService.getPersonnageManager().remove(target);
        } else {
        	source.getTargetManager().add(target);
        }
    }
}
