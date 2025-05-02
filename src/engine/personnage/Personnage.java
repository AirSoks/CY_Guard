package engine.personnage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import engine.error.*;
import engine.interaction.PersonnageInteractionVisitor;
import engine.displacement.Displacement;
import engine.map.Cell;
import engine.map.Position;
import engine.mouvement.MovementStatus;
import engine.util.Either;
import engine.vision.Vision;

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
     * Le mécanisme de déplacement du personnage 
     */
    private Displacement displacement;
    
    /** 
     * Le mécanisme de vision du personnage 
     */
    private Vision vision;
    
    /** 
     * Set des personnages cibles 
     */
    protected final Set<Personnage> targets = new HashSet<>();

    /**
     * Constructeur pour initialiser le personnage avec une position donnée.
     * 
     * @param position La position initiale du personnage
     */
    public Personnage(Position position) {
        this.position = position;
    }

    /**
     * Déplace le personnage en utilisant son mécanisme de déplacement.
     * 
     * @return Le statut du mouvement (succès ou échec)
     */
    public MovementStatus move() {
        if (displacement == null) {
            return MovementStatus.failure(new NullClassError(Displacement.class));
        }
        return displacement.move(this);
    }
    
    /**
     * Permet au personnage de voir les cellules autour de lui grâce à sa vision.
     * 
     * @return Soit une erreur de message si la vision est nulle, soit une carte des positions et cellules visibles
     */
    public Either<MessageError, Map<Position, Cell>> see() {
        if (vision == null) {
            return Either.left(new NullClassError(Vision.class));
        }
        return vision.see(this);
    }

    /**
     * Récupère la position actuelle du personnage.
     * 
     * @return La position du personnage
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Modifie la position du personnage.
     * 
     * @param position La nouvelle position du personnage
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Récupère le Set des personnages cibles du personnage.
     * 
     * @return Un Set des personnages cibles
     */
    public Set<Personnage> getTargets() {
        return Collections.unmodifiableSet(targets);
    }

    /**
     * Ajoute un personnage au Set des cibles du personnage.
     * Chaque sous-classe doit définir sa propre logique pour déterminer qui peut être une cible valide.
     *
     * @param target Le personnage à ajouter au Set des cibles
     */
    public abstract void addTarget(Personnage target);

    /**
     * Retire un personnage du Set des cibles du personnage.
     * 
     * @param target Le personnage à retirer du Set des cibles
     */
    public void removeTarget(Personnage target) {
        if (target != null) {
            targets.remove(target);
        }
    }
    
    /**
     * Méthode abstraite pour obtenir la cible suivante.
     * 
     * @return La cible suivante
     */
    public abstract Personnage getTarget();

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
