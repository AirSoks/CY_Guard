package engine.personnage;

import engine.interaction.GuardianInteraction;
import engine.interaction.PersonnageInteractionVisitor;
import engine.map.Position;

/**
 * Représente un gardien dans le jeu.
 * Un gardien est un personnage qui poursuit la première cible qu'il rencontre dans la liste des cibles.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public class Guardian extends Personnage {

    /**
     * Constructeur pour initialiser un gardien avec une position de départ.
     * 
     * @param startPosition La position de départ du gardien
     */
    public Guardian(Position startPosition) {
        super(startPosition);
    }
    
    @Override
    public void addTarget(Personnage target) {
        if (target instanceof Intruder && target != null && target != this) {
            targets.add(target);
        }
    }

    /**
     * Retourne la première cible de la liste des cibles, ou null si la liste est vide.
     * 
     * @return La première cible, ou null si la liste est vide
     */
    @Override
    public Personnage getTarget() {
        if (!getTargets().isEmpty()) {
            return getTargets().iterator().next();
        }
        return null;
    }
    
    /**
     * Déclenche une interaction entre Guardian et un autre personnage.
     * Utilise le pattern Visitor pour centraliser la logique d'interaction.
     *
     * @param other Le personnage avec lequel interagir
     */
    @Override
    public void interact(Personnage other) {
        other.accept(new GuardianInteraction(this));
    }

    /**
     * Accepte un visiteur d'interaction.
     * Appelle la méthode appropriée du visiteur en fonction de Guardian.
     *
     * @param visitor Le visiteur à accepter
     */
    @Override
    public void accept(PersonnageInteractionVisitor visitor) {
        visitor.visitGuardian(this);
    }
}
