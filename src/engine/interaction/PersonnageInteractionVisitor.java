package engine.interaction;

import engine.personnage.Guardian;
import engine.personnage.Intruder;

/**
 * Interface définissant les méthodes de visite pour chaque type de personnage.
 * Permet de centraliser la logique d'interaction via le pattern Visitor.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public interface PersonnageInteractionVisitor {
	
    /**
     * Visite un gardien.
     *
     * @param guardian Le gardien à visiter 
     */
    void visitGuardian(Guardian guardian);

    /**
     * Visite un intrus.
     *
     * @param intruder L'intrus à visiter
     */
    void visitIntruder(Intruder intruder);
}