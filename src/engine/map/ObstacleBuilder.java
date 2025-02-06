package engine.map;

import engine.map.obstacle.Obstacle;

public class ObstacleBuilder {
	private Obstacle obstacle;
	private int densite;
	private int nbElement;
	private int nbObstacle;
	private int nbCaseDensiteObstacle;
	private MapProbaCoordonnee mapProbaCoordonnee;
	
	public MapProbaCoordonnee getMapProbaCoordonnee() {
		return mapProbaCoordonnee;
	}

    public ObstacleBuilder(Obstacle obstacle, int densite, int nbObstacle, int nbCaseDensiteObstacle) {
		this(obstacle, densite, nbObstacle, nbCaseDensiteObstacle, 0);
	}
    
    public ObstacleBuilder(Obstacle obstacle, int densite, int nbObstacle, int nbCaseDensiteObstacle, int nbElement) {
    	this.obstacle = obstacle;
		this.densite = densite;
		this.nbObstacle = nbObstacle;
		this.nbCaseDensiteObstacle = nbCaseDensiteObstacle;
		this.mapProbaCoordonnee = new MapProbaCoordonnee();
    	this.nbElement = nbElement;
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
    
    public void setNbElement(int nbElement) {
        this.nbElement = nbElement;
    }
    
    public int getNbElement() {
		return nbElement;
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
