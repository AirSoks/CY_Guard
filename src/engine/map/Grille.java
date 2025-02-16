package engine.map;

import engine.map.obstacle.ObstacleFactory;

/**
 * Cette classe représente la grille du jeu.
 * 
 * @author GLP_19
 * @see Case
 * @see Coordonnee 
 * @see Obstacle
 */
public class Grille {
	
	/**
	 * Utilisation d'un singleton
	 */
	private static Grille instance;
	
	/**
	 * La matrice de case qui représente la grille
	 */
	private Case[][] grille;
	
	/**
	 * Le nombre de ligne de cette grille
	 */
	private int nbLigne;
	
	/**
	 * Le nombre de colonne de cette grille
	 */
	private int nbColonne;
	
	private Grille(int nbLigne, int nbColonne) {
		init(nbLigne, nbColonne);
		for (int lineIndex = 0; lineIndex < nbLigne; lineIndex++) {
			for (int columnIndex = 0; columnIndex < nbColonne; columnIndex++) {
				
				Coordonnee position = new Coordonnee(lineIndex, columnIndex);
				grille[lineIndex][columnIndex] = new Case(position, ObstacleFactory.getObstacle("Plaine"));
			}
		}
	}
	
	public static Grille getInstance(int nbLigne, int nbColonne) {
		if (instance == null) {
			instance = new Grille(nbLigne, nbColonne);
		}
		return instance;
	}
	
	private void init(int nbLigne, int nbColonne) {
		this.nbLigne = nbLigne;	
		this.nbColonne = nbColonne;
		this.grille = new Case[nbLigne][nbColonne];
	}
	
	public int getNbLigne() {
		return nbLigne;
	}
	
	public int getNbColonne() {
		return nbColonne;
	}
	
	public Case getCase(Coordonnee position) {
		int ligne = position.getLigne();
		int colonne = position.getColonne();
		if (ligne >= 0 && ligne < nbLigne && colonne >= 0 && colonne < nbColonne) { 
			return grille[ligne][colonne];
		}
		return null;
	}
	
	public Case getCase(int ligne, int colonne) {
		Coordonnee position = new Coordonnee(ligne, colonne);
		return getCase(position);
	}
	
	public Case[][] getGrille() {
		return this.grille;
	}
	
	public void setGrille(Case[][] grille) {
		this.grille = grille;
	} 
	
}