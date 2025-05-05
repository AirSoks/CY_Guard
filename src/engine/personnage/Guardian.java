package engine.personnage;

import java.util.LinkedHashSet;

import engine.action.displacement.Displacement;
import engine.action.vision.Vision;
import engine.interaction.GuardianInteraction;
import engine.interaction.PersonnageInteractionVisitor;
import engine.listManager.TargetManager;
import engine.map.Position;

/**
 * Représente un gardien dans le jeu qui implémente {@link Personnage}.
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
    public Guardian(Position position) {
        super(position);
    }
    
    /**
     * Constructeur pour initialiser le personnage avec une position, des cibles, un déplacement et une vision.
     * 
     * @param position La position initiale du personnage
     * @param TargetManager Les cibles du personnage
     * @param Displacement Le déplacement initiale du personnage
     * @param Vision La vision initiale du personnage
     */
    public Guardian(Position position, Displacement displacement, Vision vision) {
    	super(position, null, displacement, vision);
    	updateTargetManager();
    }
    
    private void updateTargetManager() {
    	this.setTargetManager(new GuardianTargetManager());
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
    
    /**
     * Implémentation concrète de {@link TargetManager} pour gérer les cibles d'un {@link Guardian}.
     * <p>
     * Utilise un {@link LinkedHashSet} pour préserver l'ordre d'insertion des cibles.
     * Ce gestionnaire permet à un gardien de maintenir une collection ordonnée de personnages,
     * et sélectionne la première cible disponible de manière séquentielle.
     * </p>
     * 
     * @author AirSoks
     * @since 2025-05-05
     * @version 1.0
     */
    public static class GuardianTargetManager extends TargetManager {

        /**
         * Crée un gestionnaire de cibles pour un gardien, utilisant un {@link LinkedHashSet}.
         */
        public GuardianTargetManager() {
            super(new LinkedHashSet<>());
        }

        /**
         * Vérifie si le personnage fourni est une cible valide (seulement un {@link Intruder}).
         *
         * @param personnage Le personnage à vérifier
         * @return {@code true} si le personnage est un {@link Intruder}, {@code false} sinon
         */
        @Override
        protected boolean isValidTarget(Personnage personnage) {
            return personnage instanceof Intruder;
        }

        /**
         * Récupère la première cible présente dans le gestionnaire.
         * Le comportement est basé sur l'ordre d'insertion dans le {@link LinkedHashSet}.
         *
         * @return La première cible ou {@code null} si aucune cible n'est présente.
         */
        @Override
        public Personnage getTarget() {
            if (!personnages.isEmpty()) {
                return personnages.iterator().next();
            }
            return null;
        }
    }

}
