package engine.interaction;

import engine.personnage.Guardian;
import engine.personnage.Intruder;

/**
 * Visiteur d'interaction pour les gardiens.
 * Centralise la logique d'interaction entre un gardien (source) et d'autres personnages.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public class GuardianInteraction implements PersonnageInteractionVisitor {
    private final Guardian source;

    /**
     * Constructeur.
     *
     * @param source Le gardien à l'origine de l'interaction
     */
    public GuardianInteraction(Guardian source) {
        this.source = source;
    }

    /**
     * Gère l'interaction entre deux gardiens.
     * Permet la communication d'informations (ex : partage de cibles).
     *
     * @param target Le gardien avec lequel interagir
     */
    @Override
    public void visitGuardian(Guardian target) {
        // Communication entre gardiens
    }

    /**
     * Gère l'interaction entre un gardien et un intrus.
     * Permet la capture ou la poursuite de l'intrus.
     *
     * @param target L'intrus avec lequel interagir
     */
    @Override
    public void visitIntruder(Intruder target) {
        // Capture ou poursuite
        if (source.getPosition().equals(target.getPosition())) {
            // source.capture(target);
        }
    }
}
