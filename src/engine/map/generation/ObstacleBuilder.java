package engine.map.generation;

import engine.map.obstacle.Obstacle;

/**
 * La classe utilitaire pour générer des obstacles
 * 
 * @author GLP_19
 * @see ConfigurationMapAleatoire
 * @see GrilleBuilder
 * @see MapProbaCoordonnee
 * 
 */
public class ObstacleBuilder {
	
	/**
	 * L'obstacle à générer
	 */
	private Obstacle obstacle;
	
	/**
	 * La densité de l'obstacle à créer
	 * Plus la densité est faible plus les obstacles seront dispersé
	 * Plus la densité est forte plus les obstacles seront rapproché 
	 */
	private int densite;
	
	/**
	 * Le nombre d'obstacle à construire
	 */
	private int nbObstacle;
	
	/**
	 * Le nombre de case adjacente où la densité va être appliqué
	 */
	private int nbCaseDensiteObstacle;
	
	/**
	 * La map de coordonnées avec leur probabilité
	 */
	private MapProbaCoordonnee mapProbaCoordonnee;

	public MapProbaCoordonnee getMapProbaCoordonnee() {
		return mapProbaCoordonnee;
	}

    public ObstacleBuilder(Obstacle obstacle, int densite, int nbObstacle, int nbCaseDensiteObstacle) {
    	this.obstacle = obstacle;
		this.densite = densite;
		this.nbObstacle = nbObstacle;
		this.nbCaseDensiteObstacle = nbCaseDensiteObstacle;
		this.mapProbaCoordonnee = new MapProbaCoordonnee();
	}

	public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public Obstacle getObstacle() {
		return obstacle;
	}

    public void setDensite(int densite) {
        this.densite = densite;
    }

    public int getDensite() {
		return densite;
	}

    public void setNbObstacle(int nbObstacle) {
        this.nbObstacle = nbObstacle;
    }

    public int getNbObstacle() {
		return nbObstacle;
	}

    public void setNbCaseDensiteObstacle(int nbCaseDensiteObstacle) {
        this.nbCaseDensiteObstacle = nbCaseDensiteObstacle;
    }

    public int getNbCaseDensiteObstacle() {
		return nbCaseDensiteObstacle;
	}
}
