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
	public static List<ObstacleBuilder> genererObstaclesAleatoires(Settings settings) {
		
        List<ObstacleBuilder> obstacleBuilders = new ArrayList<>();
        
        // Génération des Lacs
        int nbLacs = getValeurAleatoire(settings.getElements_lacs_min(), settings.getElements_lacs_max());
        for (int i = 0; i < nbLacs; i++) {
            Obstacle lac = ObstacleFactory.getObstacle("Lac");
            int densiteLac = getValeurAleatoire(Settings.DENSITE_LAC_MIN, Settings.DENSITE_LAC_MAX);
            int totalCaseLac = getValeurAleatoire(settings.getCases_lacs_min(), settings.getCases_lacs_max());
            int nbCaseDensiteLac = Settings.NB_CASE_DENSITE_LAC;
            
            obstacleBuilders.add(new ObstacleBuilder(lac, densiteLac, totalCaseLac, nbCaseDensiteLac));
        }

        // Génération des Roches
        int nbRoches = getValeurAleatoire(settings.getElements_roches_min(), settings.getElements_roches_max());
        for (int i = 0; i < nbRoches; i++) {
        	Obstacle roche = ObstacleFactory.getObstacle("Roche");
            int densiteRoche = getValeurAleatoire(Settings.DENSITE_ROCHE_MIN, Settings.DENSITE_ROCHE_MAX);
            int totalCaseRoche = getValeurAleatoire(settings.getCases_roches_min(), settings.getCases_roches_min());
            int nbCaseDensiteRoche = Settings.NB_CASE_DENSITE_ROCHE;
        	
            obstacleBuilders.add(new ObstacleBuilder(roche, densiteRoche, totalCaseRoche, nbCaseDensiteRoche));
        }

        // Génération des Arbres
        Obstacle arbre = ObstacleFactory.getObstacle("Arbre");
        int densiteArbre = getValeurAleatoire(Settings.DENSITE_ARBRE_MIN, Settings.DENSITE_ARBRE_MAX);
        int totalCaseArbre = getValeurAleatoire(settings.getCases_arbres_min(), settings.getCases_arbres_max());
        int nbCaseDensiteArbre = Settings.NB_CASE_DENSITE_ARBRE;
        
        obstacleBuilders.add(new ObstacleBuilder(arbre, densiteArbre, totalCaseArbre, nbCaseDensiteArbre));

        return obstacleBuilders;
    }

	private static int getValeurAleatoire(int min, int max) {
	    return min + (int) (Math.random() * (max - min + 1));
	}
}