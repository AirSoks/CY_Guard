package engine.map.obstacle;

import java.util.Arrays;
import java.util.List;

public class ObstacleFactory {
	
	private static List<Obstacle> obstacles = Arrays.asList(new Plaine(), new Roche(), new Lac(), new Arbre());
	
	public static Obstacle getObstacle(String name) {
        for (Obstacle obstacle : obstacles) {
        	if (obstacle.getType() ==  name) {
        		return obstacle;
        	}
        }
        return obstacles.getFirst(); // On récupère la Plaine par défaut
    }
}