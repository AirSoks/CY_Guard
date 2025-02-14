package engine.personnage.gestion;

import engine.map.Case;
import engine.map.Coordonnee;
import engine.map.Grille;
import engine.personnage.Gardien;
import engine.personnage.Intrus;
import engine.utilitaire.MaximumTentativeAtteind;

public class PersonnageApparition {
	
	public static Gardien apparitionGardien(Grille grille) {
		Coordonnee coordonnee = getCoordonneeAleatoireValide(grille);
		Gardien gardien = new Gardien(coordonnee);
		return gardien;
	}
	
	public static Intrus apparitionIntrus(Grille grille) {
		Coordonnee coordonnee = getCoordonneeAleatoireValide(grille);
		Intrus intrus = new Intrus(coordonnee);
		return intrus;
	}

	private static Coordonnee getCoordonneeAleatoire(int nbLigne, int nbColonne) {
	    int ligneAleatoire =  (int) (Math.random() * nbLigne);
	    int colonneAleatoire =  (int) (Math.random() * nbColonne);
	    return new Coordonnee(ligneAleatoire, colonneAleatoire);
	}
	
	private static Coordonnee getCoordonneeAleatoireValide(Grille grille) {
	    int tentativeMax = 2*grille.getNbLigne()*grille.getNbColonne();
	    for (int i = 0; i < tentativeMax; i++) {
	        Coordonnee coordonnee = getCoordonneeAleatoire(grille.getNbLigne(), grille.getNbColonne());
	        if (isCoordonneeValide(grille, coordonnee)) {
	            return coordonnee;
	        }
	    }
	    throw new MaximumTentativeAtteind(tentativeMax);
	}
	
	private static Boolean isCoordonneeValide(Grille grille, Coordonnee coordonnee) {
		Case c = grille.getCase(coordonnee);
		if (c != null && !c.getObstacle().isBloqueDeplacement()) {
			return true;
		}
		return false;
	}
}