package config;

public class GameConfiguration {
	
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 800;
	
	public static final int BLOCK_SIZE = 25;
	
	public static final int GAME_SPEED = 25;

	public static final int VITESSE_INTRUS = 10;
	public static final int VITESSE_GARDIENS = 12;
	
	public static final int DIFFICULTE = 3; // Choisir entre 1 et 3 (3 étant le plus difficile)
	
	public static final int NB_LIGNE = 32;
	public static final int NB_COLONNE = 32;
	
	public static final int NB_LAC_MIN = 2;
	public static final int NB_LAC_MAX = 3;
	public static final int DENSITE_LAC_MIN = 20000;
	public static final int DENSITE_LAC_MAX = 20000;
	public static final int TOTAL_CASE_LAC_MIN = (NB_LIGNE*NB_COLONNE)/(40*(4 - DIFFICULTE));
	public static final int TOTAL_CASE_LAC_MAX = (NB_LIGNE*NB_COLONNE)/(10*(4 - DIFFICULTE));
	public static final int NB_CASE_DENSITE_LAC = 2;
	
	public static final int NB_ROCHE_MIN = 3;
	public static final int NB_ROCHE_MAX = 6;
	public static final int DENSITE_ROCHE_MIN = 250;
	public static final int DENSITE_ROCHE_MAX = 500;
	public static final int TOTAL_CASE_ROCHE_MIN = (NB_LIGNE*NB_COLONNE)/(80*(4 - DIFFICULTE));
	public static final int TOTAL_CASE_ROCHE_MAX = (NB_LIGNE*NB_COLONNE)/(25*(4 - DIFFICULTE));
	public static final int NB_CASE_DENSITE_ROCHE = 3;
	
	public static final int DENSITE_ARBRE_MIN = 1;
	public static final int DENSITE_ARBRE_MAX = 1;
	public static final int TOTAL_CASE_ARBRE_MIN = (NB_LIGNE*NB_COLONNE)/(17*(4 - DIFFICULTE));
	public static final int TOTAL_CASE_ARBRE_MAX = (NB_LIGNE*NB_COLONNE)/(10*(4 - DIFFICULTE));
	public static final int NB_CASE_DENSITE_ARBRE = 0;
	
	public static final boolean PERMET_DEPLACEMENT_DIAGONNAL = true;
	public static final int NB_CASES_VISION = 20;
}