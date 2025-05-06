package engine.personnage;

import java.util.Collection;
import java.util.HashSet;

import engine.action.displacement.Displacement;
import engine.map.Position;
import engine.map.Position.PositionPair;
import engine.map.Zone;
import engine.personnage.interaction.PersonnageInteractionVisitor;
import engine.util.Either;
import engine.util.Outcome;
import engine.util.message.MessageError;
import engine.util.message.error.MoveError;
import engine.util.message.error.NullClassError;
import engine.action.vision.Vision;

/**
 * Représente un personnage dans le jeu.
 * Chaque personnage possède une position, un mécanisme de déplacement (Displacement),
 * une vision (Vision) et une liste de abstractPersonnages cibles (Targets).
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public abstract class Personnage {

    /** 
     * La position actuelle du personnage 
     */
    private Position position;
    
    /** 
     * Le mécanisme de ciblages du personnage 
     */
    private TargetManager targetManager;
    
    /** 
     * Le mécanisme de déplacement du personnage 
     */
    private Displacement displacement;
    
    /** 
     * Le mécanisme de vision du personnage 
     */
    private Vision vision;
    
    /**
     * Constructeur pour initialiser le personnage avec une position, des cibles, un déplacement et une vision.
     * 
     * @param position La position initiale du personnage
     * @param TargetManager Les cibles du personnage
     * @param Displacement Le déplacement initiale du personnage
     * @param Vision La vision initiale du personnage
     */
    public Personnage(Position position, TargetManager targetManager, Displacement displacement, Vision vision) {
    	this.position = position;
    	if (targetManager == null) { this.targetManager = new EmptyTargetManager(); } 
    	else { this.targetManager = targetManager; } 
    	this.displacement = displacement;
    	this.vision = vision;
    }
    
    /**
     * Constructeur pour initialiser le personnage avec une position donnée.
     * 
     * @param position La position initiale du personnage
     */
    public Personnage(Position position) {
    	this(position, null, null, null);
    }
    
    /**
     * Déplace le personnage en utilisant son mécanisme de déplacement.
     * 
     * @return Le statut du mouvement (succès ou échec)
     */
    public final Outcome<PositionPair> move() {
        if (displacement == null) {
            return Outcome.failure(null, new MoveError(null).with(new NullClassError(Displacement.class)));
        }
        return displacement.execute(this);
    }
    
    /**
     * Permet au personnage de voir les positions autour de lui grâce à sa vision.
     * 
     * Une fois la vision calculée, le personnage déclenche également des interactions avec tous les
     * personnages visibles (en appelant {@link #interact(Collection)}).
     * 
     * @return Soit une erreur de message si la vision est nulle, soit une carte des positions et cellules visibles
     */
    public final Either<MessageError, Zone> see() {
        if (vision == null) {
            return Either.left(new NullClassError(Vision.class));
        }
        Either<MessageError, Zone> view = vision.calculate(this);
        if (view.isLeft()) {
        	return view;
        }
        this.interact(view.getRight().getPersonnages());
        return view;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    public TargetManager getTargetManager() {
        return targetManager;
    }
    
    public void setTargetManager(TargetManager targetManager) {
		this.targetManager = targetManager;
	}

	public Displacement getDisplacement() {
		return displacement;
	}

	public void setDisplacement(Displacement displacement) {
		this.displacement = displacement;
	}

	public Vision getVision() {
		return vision;
	}

	public void setVision(Vision vision) {
		this.vision = vision;
	}

	/**
	 * Déclenche une interaction entre ce personnage et tous les autres personnages.
	 *
	 * @param personnages La liste des personnages
	 */
	public abstract void interact(Collection<Personnage> personnages);
	
    /**
     * Déclenche une interaction entre ce personnage et un autre personnage.
     * Utilise le pattern Visitor pour centraliser la logique d'interaction.
     *
     * @param other Le personnage avec lequel interagir
     */
    public abstract void interact(Personnage other);

    /**
     * Accepte un visiteur d'interaction.
     * Appelle la méthode appropriée du visiteur en fonction du type de ce personnage.
     *
     * @param visitor Le visiteur à accepter
     */
    public abstract void accept(PersonnageInteractionVisitor visitor);
    
    /**
     * Adapte le comportement de ce personnage après la phase d'interactions.
     * Cette méthode est appelée après que toutes les interactions du tour ont été traitées,
     * permettant au personnage de recalculer ses objectifs et d'apater ses stratégies.
     */
    public abstract void adaptBehavior();
    
    
    /**
     * Implémentation par défaut de {@link TargetManager} utilisée lorsqu'aucune gestion de cibles n'est fournie.
     * <p>
     * Cette classe empêche toute interaction avec des cibles :
     * <ul>
     *   <li>Impossible d'ajouter des cibles : {@link #isValid(Personnage)} retourne toujours {@code false}.</li>
     *   <li>Impossible de récupérer une cible : {@link #getTarget()} retourne toujours {@code null}.</li>
     * </ul>
     * </p>
     * <p>
     * Elle agit comme un gestionnaire "vide" afin d'éviter des {@code null} dans la logique métier 
     * lorsque la gestion des cibles n'est pas nécessaire ou pas configurée.
     * </p>
     * 
     * @implNote Cette implémentation utilise un {@link HashSet} vide en interne, mais sa logique interdit toute modification.
     * @author AirSoks
     * @since 2025-05-06
     * @version 1.0
     */
    private final static class EmptyTargetManager extends TargetManager {
        
    	public EmptyTargetManager() {
            super(new HashSet<>());
        }

        /**
         * Toujours invalide : aucun personnage ne peut être ajouté à ce gestionnaire.
         *
         * @param personnage Le personnage à vérifier
         * @return Toujours {@code false}
         */
        @Override
        protected boolean isValid(Personnage personnage) {
            return false;
        }

        /**
         * Aucune cible n'est disponible dans ce gestionnaire.
         *
         * @return Toujours {@code null}
         */
        @Override
        public Personnage getTarget() {
            return null;
        }
    }
}
