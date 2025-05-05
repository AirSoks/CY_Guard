package engine.personnage;

import java.util.HashSet;

import engine.action.displacement.Displacement;
import engine.action.vision.Vision;
import engine.interaction.IntruderInteraction;
import engine.interaction.PersonnageInteractionVisitor;
import engine.listManager.TargetManager;
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
    
    /**
     * Implémentation concrète de {@link TargetManager} pour gérer les cibles d'un {@link Intruder}.
     * <p>
     * Utilise un {@link HashSet} pour stocker les cibles, mais peut être personnalisé pour d'autres
     * structures de données si nécessaire. Ce gestionnaire choisit la cible la plus proche
     * parmi les gardiens dans la liste des cibles.
     * </p>
     * 
     * @author AirSoks
     * @since 2025-05-05
     * @version 1.0
     */
    public static class IntruderTargetManager extends TargetManager {

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
         * Vérifie si le personnage est un gardien valide pour être une cible pour l'intrus.
         *
         * @param personnage Le personnage à vérifier
         * @return {@code true} si le personnage est un {@link Guardian}, {@code false} sinon
         */
        @Override
        protected boolean isValidTarget(Personnage personnage) {
            return personnage instanceof Guardian;
        }

        /**
         * Récupère la cible la plus proche parmi les gardiens dans la liste des cibles.
         * <p>
         * Si aucun gardien n'est présent dans la liste, la méthode retourne {@code null}.
         * Le choix de la cible se fait en fonction de la distance entre l'intrus et chaque gardien.
         * </p>
         *
         * @return Le gardien le plus proche ou {@code null} si aucun gardien n'est présent.
         */
        @Override
        public Personnage getTarget() {
            Personnage closestGuardian = null;
            double minDistance = Double.MAX_VALUE;

            for (Personnage target : personnages) {
                if (target instanceof Guardian) {
                    double distance = intruder.getPosition().distanceTo(target.getPosition());
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestGuardian = target;
                    }
                }
            }

            return closestGuardian;
        }
    }

}