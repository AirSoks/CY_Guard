package config;

import engine.map.generation.ObstacleBuilder;
import engine.map.obstacle.Obstacle;
import engine.map.obstacle.ObstacleFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsable de la génération aléatoire d'obstacles pour la map
 * Elle crée une liste d'obstacles de différents types (Lac, Roche, Arbre) avec des valeurs aléatoire basées sur les paramètres de config de simulation
 * 
 * @author GLP_19
 * @see ObstacleBuilder
 * @see Obstacle
 * @see ObstacleFactory
 * @see GameConfiguration
 */
public class ConfigurationMapAleatoire {

	/**
	 * Génère une liste d'obstacle aléatoire pour la map
	 * La liste contient des obstacles de type Lac, Arbre et Roche, avec des densités et des nombres totales des cases 
	 * générées aléatoirement dans les limites définies par la config du simulation.
	 * @return Une liste d'ObstacleBuilder, chacun représentant un obstacle configuré pour la map
	 */
	public static List<ObstacleBuilder> genererObstaclesAleatoires() {
        List<ObstacleBuilder> obstacleBuilders = new ArrayList<>();
        
        // Génération des Lacs
        int nbLacs = getValeurAleatoire(GameConfiguration.NB_LAC_MIN, GameConfiguration.NB_LAC_MAX);
        for (int i = 0; i < nbLacs; i++) {
            Obstacle lac = ObstacleFactory.getObstacle("Lac");
            int densiteLac = getValeurAleatoire(GameConfiguration.DENSITE_LAC_MIN, GameConfiguration.DENSITE_LAC_MAX);
            int totalCaseLac = getValeurAleatoire(GameConfiguration.TOTAL_CASE_LAC_MIN, GameConfiguration.TOTAL_CASE_LAC_MAX);
            int nbCaseDensiteLac = GameConfiguration.NB_CASE_DENSITE_LAC;
            
            obstacleBuilders.add(new ObstacleBuilder(lac, densiteLac, totalCaseLac, nbCaseDensiteLac));
        }

        // Génération des Roches
        int nbRoches = getValeurAleatoire(GameConfiguration.NB_ROCHE_MIN, GameConfiguration.NB_ROCHE_MAX);
        for (int i = 0; i < nbRoches; i++) {
        	Obstacle roche = ObstacleFactory.getObstacle("Roche");
            int densiteRoche = getValeurAleatoire(GameConfiguration.DENSITE_ROCHE_MIN, GameConfiguration.DENSITE_ROCHE_MAX);
            int totalCaseRoche = getValeurAleatoire(GameConfiguration.TOTAL_CASE_ROCHE_MIN, GameConfiguration.TOTAL_CASE_ROCHE_MAX);
            int nbCaseDensiteRoche = GameConfiguration.NB_CASE_DENSITE_ROCHE;
        	
            obstacleBuilders.add(new ObstacleBuilder(roche, densiteRoche, totalCaseRoche, nbCaseDensiteRoche));
        }

        // Génération des Arbres
        Obstacle arbre = ObstacleFactory.getObstacle("Arbre");
        int densiteArbre = getValeurAleatoire(GameConfiguration.DENSITE_ARBRE_MIN, GameConfiguration.DENSITE_ARBRE_MAX);
        int totalCaseArbre = getValeurAleatoire(GameConfiguration.TOTAL_CASE_ARBRE_MIN, GameConfiguration.TOTAL_CASE_ARBRE_MAX);
        int nbCaseDensiteArbre = GameConfiguration.NB_CASE_DENSITE_ARBRE;
        
        obstacleBuilders.add(new ObstacleBuilder(arbre, densiteArbre, totalCaseArbre, nbCaseDensiteArbre));

        return obstacleBuilders;
    }

	private static int getValeurAleatoire(int min, int max) {
	    return min + (int) (Math.random() * (max - min + 1));
	}
}
