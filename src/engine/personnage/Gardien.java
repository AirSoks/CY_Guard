package engine.personnage;

import config.GameConfiguration;
import engine.map.Coordonnee;

import java.util.LinkedList;

/**
 * Gardien est une classe représentant un personnage gardien
 * 
 * @author GLP_19
 * @see Personnage
 * @see Intrus
 */
public class Gardien extends Personnage {
	
	/**
	 * Les intrus ciblé par ce gardien
	 */
	private LinkedList<Intrus> cibles;

	/**
	 * Le nombre d'intru capturé
	 */
	private int nbIntrusCapture;

	public Gardien(Coordonnee coordonnee) {
		super(coordonnee);
		this.cibles = new LinkedList<>();
		setVitesse(GameConfiguration.VITESSE_GARDIENS);
	}
	
    public int getNbIntrusCapture() {
        return nbIntrusCapture;
    }

    public void addNbIntrusCapture() {
        nbIntrusCapture++;
    }

    public void ajouterCible(Intrus cible) {
        if (cible != null && !cibles.contains(cible)) {
            this.cibles.addLast(cible);
        }
    }

    public Intrus getPremiereCible() {
        return this.cibles.peekFirst();
    }

    public Intrus retirerPremiereCible() {
        return this.cibles.pollFirst();
    }
    
    public LinkedList<Intrus> getCibles() {
        return this.cibles;
    }
}