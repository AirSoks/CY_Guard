package config;

import engine.map.generation.ObstacleBuilder;
import engine.map.obstacle.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationMapAleatoire {

	public static List<ObstacleBuilder> genererObstaclesAleatoires() {
        List<ObstacleBuilder> obstacleBuilders = new ArrayList<>();

        int nbLacs = getValeurAleatoire(GameConfiguration.NB_LAC_MIN, GameConfiguration.NB_LAC_MAX);
        for (int i = 0; i < nbLacs; i++) {
            Obstacle lac = GameConfiguration.LAC;
            int densiteLac = getValeurAleatoire(GameConfiguration.DENSITE_LAC_MIN, GameConfiguration.DENSITE_LAC_MAX);
            int totalCaseLac = getValeurAleatoire(GameConfiguration.TOTAL_CASE_LAC_MIN, GameConfiguration.TOTAL_CASE_LAC_MAX);
            int nbCaseDensiteLac = GameConfiguration.NB_CASE_DENSITE_LAC;
            
            obstacleBuilders.add(new ObstacleBuilder(lac, densiteLac, totalCaseLac, nbCaseDensiteLac));
        }

        int nbRoches = getValeurAleatoire(GameConfiguration.NB_ROCHE_MIN, GameConfiguration.NB_ROCHE_MAX);
        for (int i = 0; i < nbRoches; i++) {
        	Obstacle roche = GameConfiguration.ROCHE;
            int densiteRoche = getValeurAleatoire(GameConfiguration.DENSITE_ROCHE_MIN, GameConfiguration.DENSITE_ROCHE_MAX);
            int totalCaseRoche = getValeurAleatoire(GameConfiguration.TOTAL_CASE_ROCHE_MIN, GameConfiguration.TOTAL_CASE_ROCHE_MAX);
            int nbCaseDensiteRoche = GameConfiguration.NB_CASE_DENSITE_ROCHE;
        	
            obstacleBuilders.add(new ObstacleBuilder(roche, densiteRoche, totalCaseRoche, nbCaseDensiteRoche));
        }

        Obstacle arbre = GameConfiguration.ARBRE;
        int densiteArbre = getValeurAleatoire(GameConfiguration.DENSITE_ARBRE_MIN, GameConfiguration.DENSITE_ARBRE_MAX);
        int totalCaseArbre = getValeurAleatoire(GameConfiguration.TOTAL_CASE_ARBRE_MIN, GameConfiguration.TOTAL_CASE_ARBRE_MAX);
        int nbCaseDensiteArbre = GameConfiguration.NB_CASE_DENSITE_ARBRE;
        
        obstacleBuilders.add(new ObstacleBuilder(arbre, densiteArbre, totalCaseArbre, nbCaseDensiteArbre));

        return obstacleBuilders;
    }
	
	private static int getValeurAleatoire(int min, int max) {
	    return min + (int) (Math.random() * (max - min));
	}
}
