package engine.map.obstacle;

import java.util.Objects;

/**
 * Cette classe abstraite represente un obstacle dans la simulation
 * Un obstacle a un type, et peut bloquer la vision ou le deplacement
 * 
 * @author GLP_19
 * @see ObstacleFactory
 */
public abstract class Obstacle {
	
	/**
	 * Le type de l'obstacle (roche, arbre, lac ou plaine)
	 */
	private String type;
	
	/**
	 * Indique si l'obstacle bloque la vision. Si true, la vision est bloquée
	 */
	private boolean bloqueVision;
	
	/**
	 * Indique si l'obstacle bloque le déplacement. Si true, le déplacement est bloqué
	 */
	private boolean bloqueDeplacement;
	
	public Obstacle(String type, boolean isBloqueVision, boolean isBloqueDeplacement) {
		this.type = type;
		this.bloqueVision = isBloqueVision;
		this.bloqueDeplacement = isBloqueDeplacement;
	}

	/**
	 * Obtient le type de l'obstacle
	 * 
	 * @return Le type de l'obstacle
	 */
	public String getType() {
		return type;
	}

	/**
	 * Vérifie si l'obstacle bloque la vision
	 * @return true si l'obstacle bloque la vision, sinon false
	 */
	public boolean isBloqueVision() {
		return bloqueVision;
	}

	/**
	 * Vérifie si l'obstacle bloque le deplacement
	 * @return true si l'obstacle bloque le deplacement, sinon false
	 */
	public boolean isBloqueDeplacement() {
		return bloqueDeplacement;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

	/**
	 * Vérifie si cet obstacle est egal à un autre objet
	 * @param obj L'objet a comparer
	 * @return true si l'obstacle est égal à l'objet donnée, sinon false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Obstacle other = (Obstacle) obj;
		return Objects.equals(type, other.type);
	}
}