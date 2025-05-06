package engine.personnage;

import java.util.Collection;
import java.util.LinkedHashSet;

import engine.action.displacement.*;
import engine.action.vision.Vision;
import engine.map.Position;
import engine.personnage.interaction.GuardianInteraction;
import engine.personnage.interaction.PersonnageInteractionVisitor;

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
    
	@Override
	public void interact(Collection<Personnage> personnages) {
		for (Personnage p : personnages) {
	        interact(p);
	    }
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
    public void interact(Personnage other) {
        other.accept(new GuardianInteraction(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(PersonnageInteractionVisitor visitor) {
        visitor.visitGuardian(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void adaptBehavior() {
    	Personnage target = getTargetManager().getTarget();
        if (target != null) {
        	setDisplacement(new PursuitDisplacement(target));
        } else {
        	setDisplacement(new RandomDisplacement());
        }
    }
    
    /**
     * Implémentation concrète de {@link TargetManager} spécifique à un {@link Guardian}.
     * <p>
     * Cette classe gère la collection de cibles d'un gardien, en permettant d'ajouter, de vérifier et de récupérer les cibles de manière ordonnée.
     * Le gestionnaire utilise un {@link LinkedHashSet} pour préserver l'ordre d'insertion des cibles.
     * Le gardien sélectionne la première cible disponible de manière séquentielle.
     * </p>
     * 
     * <p>
     * Le gestionnaire permet au gardien de cibler uniquement des intrus, en s'assurant que seules les instances de {@link Intruder} 
     * peuvent être ajoutées à la liste des cibles.
     * </p>
     * 
     * @author AirSoks
     * @since 2025-05-05
     * @version 1.0
     */
    private final static class GuardianTargetManager extends TargetManager {

        /**
         * Constructeur de {@link GuardianTargetManager}.
         * Utilise un {@link LinkedHashSet} pour maintenir l'ordre d'insertion des cibles.
         * 
         * @implNote L'utilisation de {@link LinkedHashSet} garantit que les cibles sont parcourues dans l'ordre dans lequel elles ont été ajoutées.
         */
        public GuardianTargetManager() {
            super(new LinkedHashSet<>());
        }

        /**
         * Vérifie si un personnage est valide pour être ajouté comme cible.
         * Dans le cas d'un gardien, seules les instances d' {@link Intruder} sont considérées comme valides.
         * 
         * @param personnage Le personnage à vérifier
         * @return {@code true} si le personnage est un intrus, {@code false} sinon
         */
        @Override
        protected boolean isValid(Personnage personnage) {
            return (personnage != null && personnage instanceof Intruder);
        }

        /**
         * Récupère la première cible dans le gestionnaire, en fonction de l'ordre d'insertion dans le {@link LinkedHashSet}.
         * Si aucune cible n'est présente, cette méthode retourne {@code null}.
         * 
         * @return La première cible ajoutée ou {@code null} si aucune cible n'est disponible
         */
        @Override
        public Personnage getTarget() {
            if (!elements.isEmpty()) {
                return elements.iterator().next();
            }
            return null;
        }
    }
}
