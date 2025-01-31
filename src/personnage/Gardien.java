package personnage;

import java.util.LinkedList;

import utilitaire.Coordonnee;

public class Gardien extends Personnage {
	
	//	Un FIFO
	private LinkedList<Intrus> cibles;

	private int nbIntrusCapture;

	public Gardien(Coordonnee position, long tempsInvocation) {
		super(position, tempsInvocation);
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