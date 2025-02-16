package engine.map;

/**
 * Cette classe represente les differentes directions possible dans le grille
 * Chaque direction est associée à un mouvement sur la ligne ou la colonne
 */
public enum Direction {
	HAUT(-1,0),
	BAS(1,0),
	GAUCHE(0,-1),
	DROITE(0,1);
	
	private final int deltaLigne, deltaColonne;
	
	Direction(int deltaLigne, int deltaColonne){
		this.deltaLigne = deltaLigne;
		this.deltaColonne = deltaColonne;
	}
	
	public Coordonnee getCoordonnee(Coordonnee coordonnee) {
		int nouvelleLigne = coordonnee.getLigne() + this.deltaLigne;
        int nouvelleColonne = coordonnee.getColonne() + this.deltaColonne;
		return new Coordonnee(nouvelleLigne, nouvelleColonne);
	}
}