package engine.map;

import engine.map.obstacle.Obstacle;

import java.util.Objects;

/**
 * Représente une case de la grille
 * 
 * @author GLP_19
 * @see Coordonne
 * @see Obstacle
 * @see Grille
 */
public class Case {
	
	/**
	 * La coordonnée de la case dans la grille
	 */
	private Coordonnee position;
	
	/**
	 * L'obstacle présent sur cette case 
	 */
	private Obstacle obstacle;
	
	public Case(Coordonnee position, Obstacle obstacle) {
		this.position = position;
		this.obstacle = obstacle;
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
	public int hashCode() {
		return Objects.hash(position);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Case other = (Case) obj;
		return Objects.equals(position, other.position);
	}
	
	@Override
	public String toString() {
	    return "[Coordonnée=" + position + ", Obstacle=" + obstacle + "]";
	}
}