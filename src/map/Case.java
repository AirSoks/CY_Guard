package map;

import config.GameConfiguration;
import map.obstacle.Obstacle;

/**
 * Représente une case de la grille.
 */
public class Case {
	private Coordonnee position;
	private Obstacle obstacle;
	
	public Case(Coordonnee position) {
		this.position = position;
		this.obstacle = GameConfiguration.PLAINE; // On initialise les plaines comme obstacles par défaut, on les remplacera plus tard dans le code.
	}
	
	public Coordonnee getPosition() {
		return this.position;
	}
	
	public void setPosition(Coordonnee position) {
		this.position = position;
	}
	
	public Obstacle getObstacle() {
		return this.obstacle;
	}
	
	public void setObstacle(Obstacle obstacle) {
		this.obstacle = obstacle;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Case caseGrille = (Case) obj;
	    return this.position.equals(caseGrille.getPosition());
	}
}
