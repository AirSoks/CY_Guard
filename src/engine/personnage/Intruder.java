package engine.personnage;

import java.util.Collection;
import java.util.HashSet;

import engine.action.displacement.*;
import engine.action.vision.Vision;
import engine.interaction.IntruderInteraction;
import engine.interaction.PersonnageInteractionVisitor;
import engine.map.Position;

/**
 * Représente un intrus dans le jeu qui implémente {@link Personnage}.
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
    
    /**
     * Constructeur pour initialiser le personnage avec une position, des cibles, un déplacement et une vision.
     * 
     * @param position La position initiale du personnage
     * @param TargetManager Les cibles du personnage
     * @param Displacement Le déplacement initiale du personnage
     * @param Vision La vision initiale du personnage
     */
    public Intruder(Position position, Displacement displacement, Vision vision) {
    	super(position, null, displacement, vision);
    	updateTargetManager();
    }
    
    private void updateTargetManager() {
    	this.setTargetManager(new IntruderTargetManager(this));
    }
    
    @Override
	public void interact(Collection<Personnage> personnages) {
    	getTargetManager().clear();
		for (Personnage p : personnages) {
			interact(p);
		}
	}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void interact(Personnage other) {
    	other.accept(new IntruderInteraction(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(PersonnageInteractionVisitor visitor) {
    	visitor.visitIntruder(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void adaptBehavior() {
    	Personnage target = getTargetManager().getTarget();
    	if (target != null) {
        	setDisplacement(new EscapeDisplacement(target));
    	} else {
    		setDisplacement(new RandomDisplacement());
    	}
    }
    
    /**
     * Implémentation concrète de {@link TargetManager} spécifique à un {@link Intruder}.
     * <p>
     * Cette classe gère la collection de cibles d'un intrus, en permettant d'ajouter, de vérifier et de récupérer les cibles 
     * de manière ordonnée. Le gestionnaire utilise un {@link HashSet} pour stocker les cibles, mais peut être personnalisé pour
     * d'autres structures de données si nécessaire.
     * </p>
     * <p>
     * Le gestionnaire permet à l'intrus de cibler uniquement les gardiens, en s'assurant que seules les instances de {@link Guardian}
     * peuvent être ajoutées à la liste des cibles. Lorsqu'une cible est demandée, l'intrus choisit le gardien le plus proche de sa position.
     * </p>
     * 
     * @author AirSoks
     * @since 2025-05-05
     * @version 1.0
     */
    private final static class IntruderTargetManager extends TargetManager {

        /** Référence à l'intrus pour qui ce gestionnaire de cibles est créé. */
        private final Intruder intruder;

        /**
         * Crée un gestionnaire de cibles pour un intrus avec une position de départ.
         * 
         * @param intruder L'intrus pour lequel le gestionnaire de cibles est créé
         */
        public IntruderTargetManager(Intruder intruder) {
        	super(new HashSet<>());
        	this.intruder = intruder;
        }

        /**
         * Vérifie si un personnage est valide pour être ajouté comme cible.
         * Dans le cas d'un intrus, seules les instances de {@link Guardian} sont considérées comme valides.
         * 
         * @param personnage Le personnage à vérifier
         * @return {@code true} si le personnage est un gardien, {@code false} sinon
         */
        @Override
        protected boolean isValid(Personnage personnage) {
        	return (personnage != null && personnage instanceof Guardian);
        }

        /**
         * Récupère la cible la plus proche parmi les gardiens dans la liste des cibles.
         * <p>
         * Si aucun gardien n'est présent dans la liste, la méthode retourne {@code null}.
         * Le choix de la cible se fait en fonction de la distance entre l'intrus et chaque gardien.
         * L'intrus sélectionne le gardien le plus proche afin de l'éviter.
         * </p>
         *
         * @return Le gardien le plus proche ou {@code null} si aucun gardien n'est présent.
         */
        @Override
        public Personnage getTarget() {
        	Personnage closestGuardian = null;
        	double minDistance = Double.MAX_VALUE;

         	// Parcours des cibles pour trouver le gardien le plus proche
        	for (Personnage target : elements) {
                double distance = intruder.getPosition().distanceTo(target.getPosition());
                if (distance < minDistance) {
                	minDistance = distance;
                    closestGuardian = target;
                }
         	}
        	return closestGuardian;
        }
    }
}
