package map.obstacle;

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
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Obstacle obstacle = (Obstacle) obj;
	    return this.type.equals(obstacle.getType());
	}
}