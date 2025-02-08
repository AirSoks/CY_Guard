package engine.map.obstacle;

import java.util.Objects;

public abstract class Obstacle {
	private String type;
	private boolean bloqueVision;
	private boolean bloqueDeplacement;
	
	public Obstacle(String type, boolean isBloqueVision, boolean isBloqueDeplacement) {
		this.type = type;
		this.bloqueVision = isBloqueVision;
		this.bloqueDeplacement = isBloqueDeplacement;
	}

	public String getType() {
		return type;
	}

	public boolean isBloqueVision() {
		return bloqueVision;
	}

	public boolean isBloqueDeplacement() {
		return bloqueDeplacement;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

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