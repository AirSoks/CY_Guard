package engine.map;

import engine.map.obstacle.ObstacleFactory;
import engine.utilitaire.MaxTentativeAtteind;

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
	private Case[][] cases;

	/**
	 * Le nombre de ligne de cette grille
	 */
	private int nbLigne;
	
	/**
	 * Le nombre de colonne de cette grille
	 */
	private int nbColonne;

	public static void initInstance(int nbLigne, int nbColonne) {
        instance = new Grille(nbLigne, nbColonne);
    }
	
	private Grille(int nbLigne, int nbColonne) {
		init(nbLigne, nbColonne);
	}
	
	public static Grille getInstance() {
		if (instance == null) {
			throw new IllegalStateException("Grille non initialisée");
		}
		return instance;
	}
	
	public void genererTerrain() {
        for (int i = 0; i < nbLigne; i++) {
            for (int j = 0; j < nbColonne; j++) {
                Coordonnee position = new Coordonnee(i, j);
                cases[i][j] = new Case(position, ObstacleFactory.getObstacle("Plaine"));
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
	
	/**
	 * Récupère une coordonnée aléatoire avec un filtre de vérification.
	 * 
	 * @param filtreType Le type de filtre (VISION, DEPLACEMENT, DEUX).
	 * @return Une coordonnées aléatoire valide
	 */
	public Coordonnee getCoordonneeAleatoireValide(String filtreType) {
	    int tentativeMax = 2 * nbLigne * nbColonne;
	    
	    for (int i = 0; i < tentativeMax; i++) {
	        Coordonnee coordonnee = getCoordonneeAleatoire();
	        if (isCoordonneeValide(coordonnee, filtreType)) {
	            return coordonnee;
	        }
	    }
	    
	    throw new MaxTentativeAtteind(tentativeMax);
	}

	public Coordonnee getCoordonneeAleatoire() {
	    int ligneAleatoire =  (int) (Math.random() * nbLigne);
	    int colonneAleatoire =  (int) (Math.random() * nbColonne);
	    Coordonnee coordonnee = new Coordonnee(ligneAleatoire, colonneAleatoire);
	    return coordonnee;
	}
	
	/**
	 * Vérifie si une case est valide selon le type de filtre donné.
	 *
	 * @param c La case à vérifier.
	 * @param filtreType Le type de filtre (VISION, DEPLACEMENT, DEUX).
	 * @return true si la case respecte les conditions, false sinon.
	 */
	public boolean isCoordonneeValide(Coordonnee position, String filtreType) {
		Case c = getCase(position);
		if (c == null) { return false; }
		if (filtreType == null) { 
			return true; 
		}
		
	    switch (filtreType.toUpperCase()) {
	        case "VISION":
	            return !c.getObstacle().isBloqueVision();
	        case "DEPLACEMENT":
	            return !c.getObstacle().isBloqueDeplacement();
	        case "DEUX":
	            return !c.getObstacle().isBloqueVision() && !c.getObstacle().isBloqueDeplacement();
	        case "INGRILLE":
	        default:
	            return true;
	    }
	}
	
	public Case getCase(int ligne, int colonne) {
		Coordonnee position = new Coordonnee(ligne, colonne);
		return getCase(position);
	}
	
	public Case getCase(Coordonnee position) {
		if (position == null) {
			return null;
		}
		if (isCoordonneeInGrille(position)) { 
			int ligne = position.getLigne();
			int colonne = position.getColonne();
			return cases[ligne][colonne];
		}
		return null;
	}
	
	/**
	 * Verifie si la coordonnée est présente sur la grille
	 * 
	 * @param coordonnee La coordonnée à vérifier
	 * @return true si la coordonnée est présente, false sinon
	 */
	private Boolean isCoordonneeInGrille(Coordonnee coordonnee) {
		if (coordonnee == null) {
			return false;
		}
		int ligne = coordonnee.getLigne();
		int colonne = coordonnee.getColonne();
		if (ligne >= 0 && ligne < nbLigne && colonne >= 0 && colonne < nbColonne) { 
			return true;
		}
		return false;
	}
	
	public Case[][] getGrille() {
		return this.cases;
	}
	
	public void setGrille(Case[][] grille) {
		this.cases = grille;
	}
}