package config;

import engine.map.obstacle.Arbre;
import engine.map.obstacle.Lac;
import engine.map.obstacle.Plaine;
import engine.map.obstacle.Roche;

public class GameConfiguration {
	
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 800;
	
	public static final int BLOCK_SIZE = 25;
	
	public static final int GAME_SPEED = 1000;

	public static final int VITESSE_INTRUS = 10;
	public static final int VITESSE_GARDIENS = 12;
	
	public static final int DIFFICULTE = 3; // Choisir entre 1 et 3 (3 étant le plus difficile)
	
	public static final int NB_LIGNE = 32;
	public static final int NB_COLONNE = 32;
	
	public static final int DENSITE_ROCHE = 50;
	public static final int NB_ROCHE = (NB_LIGNE*NB_COLONNE)/(12*(4 - DIFFICULTE));
	public static final int NB_CASE_DENSITE_ROCHE = 4;
	
	public static final int DENSITE_ARBRE = 5;
	public static final int NB_ARBRE = (NB_LIGNE*NB_COLONNE)/(15*(4 - DIFFICULTE));
	public static final int NB_CASE_DENSITE_ARBRE = 0;	
	
	public static final int DENSITE_LAC = 500;
	public static final int NB_LAC = (NB_LIGNE*NB_COLONNE)/(8*(4 - DIFFICULTE));	
	public static final int NB_CASE_DENSITE_LAC = 2;
	
	public static final Plaine PLAINE = new Plaine();
	public static final Arbre ARBRE = new Arbre();
	public static final Roche ROCHE = new Roche();	
	public static final Lac LAC = new Lac();
	
	public static final boolean PERMET_DEPLACEMENT_DIAGONNAL = true;
}
