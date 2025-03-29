package engine.personnage;

import engine.map.Coordonnee;
import engine.map.Direction;
import engine.personnage.deplacement.Deplacement;
import engine.personnage.vision.Vision;
import engine.utilitaire.GenerateurNom;

/**
 * Personnage est une classe abstraite représentant un personnage dans la grille
 * 
 * @author GLP_19
 * @see Gardien
 * @see Intrus
 * @see Coordonnee
 * @see Deplacement
 * 
 */
public abstract class Personnage {
	
	/**
	 * La coordonnée du personnage
	 */
	private Coordonnee coordonnee;
	
	/**
	 * Le nom du personnage
	 */
	private String name;
	
	/**
	 * Le temps d'incocation du personnage
	 */
	private long tempsInvocation;
	
    /**
     * Le déplacement du personnage
     */
    private Deplacement deplacement;
    
    /**
     * La vision du personnage
     */
	private Vision vision;

    /**
     * Les informations pour l'animation du personnage
     */
	private PersonnageAnimation animation;
    
	/**
     * La direction de son prochain déplacement
     */
    private Direction direction;
    
	public PersonnageAnimation getAnimation() {
		return animation;
	}

	public void setAnimation(PersonnageAnimation animation) {
		this.animation = animation;
	}

	public Personnage(Coordonnee coordonnee) {
		this.coordonnee = coordonnee;
		this.name = GenerateurNom.genererNom();
		this.tempsInvocation = System.currentTimeMillis();
		this.animation = new PersonnageAnimation(this);
	}

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        if (direction != null ) {
			this.animation.setDerniereDirection(direction);
		}
    }
	
	public Coordonnee getCoordonnee() {
		return coordonnee;
	}

	public void setCoordonnee(Coordonnee coordonnee) {
		this.coordonnee = coordonnee;
	}

	public String getName() {
		return name;
	}

	public long getTempsInvocation() {
		return tempsInvocation;
	}

	public Deplacement getDeplacement() {
		return deplacement;
	}

	public void setDeplacement(Deplacement deplacement) {
		this.deplacement = deplacement;
	}
	
	public void setVision(Vision vision) {
		this.vision = vision;
	}

	public void deplacer() {
		if (deplacement != null) {
			deplacement.deplacer(this);
		}
    }
	
	public void observer() {
		if (vision != null) {
			vision.observer(this);
		}
    }
}