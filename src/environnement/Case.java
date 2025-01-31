package environnement;

import environnement.obstacle.Obstacle;
import environnement.obstacle.Plaine;
import utilitaire.Coordonnee;

/**
 * Représente une case de la grille.
 */
public class Case {
	private Coordonnee position;
	private Obstacle obstacle;
	
	public Case(Coordonnee position) {
		this.position = position;
		this.obstacle = new Plaine(); // On initialise les plaines comme obstacles par défaut, on les remplacera plus tard dans le code.
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
}
