package engine.map.obstacle;

import java.util.Arrays;
import java.util.List;

/**
 * Cette classe permet de récupérer un obstacle par son type (nom)
 * 
 * @author GLP_19
 * @see Obstacle
 */
public class ObstacleFactory {
	
	/**
	 * Liste des obstacles
	 * Contient des instances des différents types d'obstacles
	 */
	private static List<Obstacle> obstacles = Arrays.asList(new Plaine(), new Roche(), new Lac(), new Arbre());
	
	/**
	 * Retourne un obstacle en fonction de son nom
	 * 
	 * @param name Le nom de l'obstacle à récupérer
	 * @return L'obstacle correspondant au nom donnée
	 */
	public static Obstacle getObstacle(String name) {
        for (Obstacle obstacle : obstacles) {
        	if (obstacle.getType() ==  name) {
        		return obstacle;
        	}
        }
        return obstacles.getFirst(); // On récupère la Plaine par défaut
    }
}