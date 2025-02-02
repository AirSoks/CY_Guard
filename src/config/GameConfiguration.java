package config;

import map.obstacle.Plaine;
import map.obstacle.Arbre;
import map.obstacle.Roche;
import map.obstacle.Lac;

public class GameConfiguration {
	
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 800;
	
	public static final int BLOCK_SIZE = 30;
	
	public static final int GAME_SPEED = 1000;

	public static final int VITESSE_INTRUS = 10;
	public static final int VITESSE_GARDIENS = 12;
	
	public static final int DIFFICULTE = 1;
	public static final int NB_CASE_DENSITE = 1;
	
	public static final int NB_LIGNE = 27;
	public static final int NB_COLONNE = 27;
	
	public static final int DENSITE_ROCHE = 150;
	public static final int NB_ROCHE = (NB_LIGNE*NB_COLONNE)/(8*DIFFICULTE);
	
	public static final int DENSITE_ARBRE = 5;
	public static final int NB_ARBRE = (NB_LIGNE*NB_COLONNE)/(5*DIFFICULTE);	
	
	public static final int DENSITE_LAC = 500;
	public static final int NB_LAC = (NB_LIGNE*NB_COLONNE)/(8*DIFFICULTE);	
	
	public static final Plaine PLAINE = new Plaine();
	public static final Arbre ARBRE = new Arbre();
	public static final Roche ROCHE = new Roche();	
	public static final Lac LAC = new Lac();
	
	public static final boolean PERMET_DEPLACEMENT_DIAGONNAL = true;
}
