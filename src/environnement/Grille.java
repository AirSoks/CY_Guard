package environnement;

import personnage.Gardien;
import personnage.Intrus;
import utilitaire.Coordonnee;

import java.util.HashMap;

public class Grille {
	
	private Case[][] cases;
	private int nbLigne;
	private int nbColonne;
	
	private HashMap<Case, Intrus> intrus = new HashMap<Case, Intrus>();
	private HashMap<Case, Gardien> gardiens = new HashMap<Case, Gardien>();
	
	public Grille(int nbLigne, int nbColonne) {
		init(nbLigne, nbColonne);
		for (int lineIndex = 0; lineIndex < nbLigne; lineIndex++) {
			for (int columnIndex = 0; columnIndex < nbColonne; columnIndex++) {
				
				Coordonnee position = new Coordonnee(nbLigne, nbColonne);
				cases[lineIndex][columnIndex] = new Case(position);
			}
		}
	}
	
	private void init(int nbLigne, int nbColonne) {
		this.nbLigne = nbLigne;	
		this.nbColonne = nbColonne;
		this.cases = new Case[nbLigne][nbColonne];
	}
	
	public int getNbLigne() {
		return nbLigne;
	}
	
	public int getNbColonne() {
		return nbColonne;
	}
	
	public HashMap<Case, Intrus> getIntrus() {
		return this.intrus;
	}
	
	public void setIntrus(HashMap<Case, Intrus> intrus) {
		this.intrus = intrus;
	}
	
	public HashMap<Case, Gardien> getGardiens() {
		return this.gardiens;
	}
	
	public void setGardiens(HashMap<Case, Gardien> gardiens) {
		this.gardiens = gardiens;
	}
	
	public Case getCasefromCoordonnee(Coordonnee position) {
		int ligne = position.getLigne();
		int colonne = position.getColonne();
		if (ligne >= 0 && ligne < nbLigne && colonne >= 0 && colonne < nbColonne) { 
			return cases[ligne][colonne];
		}
		return null;
	}
	
	public Case[][] getCases() {
		return this.cases;
	}
	
	public void setCases(Case[][] cases) {
		this.cases = cases;
	}
}