package engine.personnage;

import java.util.List;

import engine.interaction.PersonnageInteractionVisitor;
import engine.listManager.TargetManager;
import engine.action.displacement.Displacement;
import engine.map.Position;
import engine.map.Position.PositionPair;
import engine.message.MessageError;
import engine.message.error.*;
import engine.util.Either;
import engine.util.Outcome;
import engine.action.vision.Vision;

/**
 * Représente un personnage dans le jeu.
 * Chaque personnage possède une position, un mécanisme de déplacement (Displacement),
 * une vision (Vision) et une liste de personnages cibles (Targets).
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
    	this.targetManager = targetManager;
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
    public Outcome<PositionPair> move() {
        if (displacement == null) {
            return Outcome.failure(null, new MoveError(null).with(new NullClassError(Displacement.class)));
        }
        return displacement.execute(this);
    }
    
    /**
     * Permet au personnage de voir les cellules autour de lui grâce à sa vision.
     * 
     * @return Soit une erreur de message si la vision est nulle, soit une carte des positions et cellules visibles
     */
    public Either<MessageError, List<Position>> see() {
        if (vision == null) {
            return Either.left(new NullClassError(Vision.class));
        }
        return vision.calculate(this);
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
}
