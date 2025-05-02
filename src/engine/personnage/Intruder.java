package engine.personnage;

import engine.interaction.IntruderInteraction;
import engine.interaction.PersonnageInteractionVisitor;
import engine.map.Position;

/**
 * Représente un intrus dans le jeu.
 * Un intrus est un personnage qui évite les gardiens en choisissant de fuir le gardien le plus proche.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public class Intruder extends Personnage {

    /**
     * Constructeur pour initialiser un intrus avec une position de départ.
     * 
     * @param startPosition La position de départ de l'intrus
     */
    public Intruder(Position startPosition) {
        super(startPosition);
    }
    
    @Override
    public void addTarget(Personnage target) {
        if (target instanceof Guardian && target != null && target != this) {
        	targets.add(target);
        }
    }

    /**
     * Choisit la cible la plus proche parmi les gardiens de la liste des cibles.
     * Si aucun gardien n'est présent, retourne null.
     * 
     * @return Le gardien le plus proche, ou null s'il n'y en a pas.
     */
    @Override
    public Personnage getTarget() {
        Personnage closestGuardian = null;
        double minDistance = Double.MAX_VALUE;

        // Parcours de toutes les cibles pour trouver le gardien le plus proche
        for (Personnage target : getTargets()) {
            if (target instanceof Guardian) {
                double distance = this.getPosition().distanceTo(target.getPosition());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestGuardian = target;
                }
            }
        }

        return closestGuardian;
    }
    
    /**
     * Déclenche une interaction entre Intruder et un autre personnage.
     * Utilise le pattern Visitor pour centraliser la logique d'interaction.
     *
     * @param other Le personnage avec lequel interagir
     */
    @Override
    public void interact(Personnage other) {
        other.accept(new IntruderInteraction(this));
    }

    /**
     * Accepte un visiteur d'interaction.
     * Appelle la méthode appropriée du visiteur en fonction d'Intruder.
     *
     * @param visitor Le visiteur à accepter
     */
    @Override
    public void accept(PersonnageInteractionVisitor visitor) {
        visitor.visitIntruder(this);
    }
}