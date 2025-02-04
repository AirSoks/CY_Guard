package engine.personnage;

import config.GameConfiguration;
import engine.map.Case;

import java.util.LinkedList;

public class Gardien extends Personnage {
	
	//	Un FIFO 
	private LinkedList<Intrus> cibles;

	private int nbIntrusCapture;

	public Gardien(Case caseActuelle) {
		super(caseActuelle);
		setVitesse(GameConfiguration.VITESSE_GARDIENS);
	}
	
	public int getNbIntrusCapture() {
		return nbIntrusCapture;
	}

	public void setNbIntrusCapture(int nbIntrusCapture) {
		this.nbIntrusCapture = nbIntrusCapture;
	}
	
	public void ajouterCible(Intrus cible) {
        if (cible != null) {
            this.cibles.addLast(cible);
        }
    }

    public Intrus getPremiereCible() {
        return this.cibles.peekFirst();
    }

    public Intrus retirerPremiereCible() {
    	return this.cibles.pollFirst();
    }
}