package engine.personnage;

import engine.map.Case;
import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
import engine.utilitaire.MaximumTentativeAtteind;

public class PersonnageManager {
	private Grille grille;
		
	public PersonnageManager(Grille grille) {
		this.grille = grille;
	}
	
	public Gardien spawnGardien() {
		Coordonnee coordonnee = getCoordonneeAleatoireValide();
		Gardien gardien = new Gardien(coordonnee);
		return gardien;
	}
	
	public Intrus spawnIntrus() {
		Coordonnee coordonnee = getCoordonneeAleatoireValide();
		Intrus intrus = new Intrus(coordonnee);
		return intrus;
	}
	
	public boolean deplacerPersonnage(Personnage personnage, Direction direction) {
		Coordonnee nouvellePosition = direction.getCoordonnee(personnage.getCoordonnee());
		if (isCoordonneeValide(nouvellePosition)) {
			personnage.setCoordonnee(nouvellePosition);
			return true;
		}
		return false;
	}
	
	private Coordonnee getCoordonneeAleatoireValide() {
	    int tentativeMax = 2*grille.getNbLigne()*grille.getNbColonne();
	    for (int i = 0; i < tentativeMax; i++) {
	        Coordonnee coordonnee = getCoordonneeAleatoire(grille.getNbLigne(), grille.getNbColonne());
	        if (isCoordonneeValide(coordonnee)) {
	            return coordonnee;
	        }
	    }
	    throw new MaximumTentativeAtteind(tentativeMax);
	}
	
	private Boolean isCoordonneeValide(Coordonnee coordonnee) {
		Case c = grille.getCase(coordonnee);
		if (c != null && !c.getObstacle().isBloqueDeplacement()) {
			return true;
		}
		return false;
	}
	
	private static Coordonnee getCoordonneeAleatoire(int nbLigne, int nbColonne) {
	    int ligneAleatoire =  (int) (Math.random() * nbLigne);
	    int colonneAleatoire =  (int) (Math.random() * nbColonne);
	    return new Coordonnee(ligneAleatoire, colonneAleatoire);
	}
}