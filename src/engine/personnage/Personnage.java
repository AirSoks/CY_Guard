package engine.personnage;

import java.awt.Image;

import engine.map.Coordonnee;
import engine.personnage.deplacement.Deplacement;
import engine.personnage.deplacement.StrategieDeplacement;
import engine.utilitaire.GenerateurNom;
import engine.utilitaire.SimulationUtility;

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
	 * La vitesse du personnage
	 */
	private int vitesse;
	
    /**
     * Le déplacement du personnage
     */
    private Deplacement deplacement;
	
	public Personnage(Coordonnee coordonnee) {
		this.coordonnee = coordonnee;
		this.name = GenerateurNom.genererNom();
		this.tempsInvocation = System.currentTimeMillis();
	}
	
	public Image getSprite() {
	    String type = "i";
	    if (this instanceof Gardien) {
	        type = "g";
	    }

	    String direction = "BAS";
	    int frame = 1;
	    
	    if (deplacement instanceof StrategieDeplacement strat) {
	    	if (strat.getDernierDirection() != null ) {
	    		direction = strat.getDernierDirection();
	    	}
	    	else {
	    		direction = "BAS";
	    	}
	        frame = strat.getAnimationFrame();
	    }

	    String fileName = "src/images/" + type + direction.toLowerCase() + frame + ".png";
	    return SimulationUtility.readImage(fileName);
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
	
	public int getVitesse() {
		return vitesse;
	}
	
	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public Deplacement getDeplacement() {
		return deplacement;
	}

	public void setDeplacement(Deplacement deplacement) {
		this.deplacement = deplacement;
	}
	
	public void deplacer() {
		if (deplacement != null) {
			deplacement.deplacer(this);
		}
    }
}