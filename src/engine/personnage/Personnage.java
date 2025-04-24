package engine.personnage;

import java.util.List;

import engine.map.Coordonnee;
import engine.map.Direction;
import engine.personnage.deplacement.Deplacement;
import engine.personnage.vision.Vision;
import engine.utilitaire.ChronoSimulation;
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
    
    /**
     * Les coordonnées visibles pas le personnage
     */
    private List<Coordonnee> coordonneesVu;
    
	public PersonnageAnimation getAnimation() {
		return animation;
	}

	public void setAnimation(PersonnageAnimation animation) {
		this.animation = animation;
	}

	public Personnage(Coordonnee coordonnee) {
		this.coordonnee = coordonnee;
		this.name = GenerateurNom.genererNom();
		this.tempsInvocation = ChronoSimulation.getInstance().getSimulationSecond();
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

	public Vision getVision() {
		return vision;
	}

	public List<Coordonnee> getCoordonneesVu() {
		return coordonneesVu;
	}

	public void setCoordonneesVu(List<Coordonnee> coordonneesVu) {
		this.coordonneesVu = coordonneesVu;
	}

	public void deplacer() {
		if (deplacement != null) {
			deplacement.deplacer(this);
		}
    }
	
	public List<Personnage> observer() {
		if (vision != null) {
			return vision.observer(this);
		}
		return null;
    }

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
	    return "[Name=" + name + "]";
	}
	
}