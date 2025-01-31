package environnement.obstacle;

public abstract class Obstacle {
	private String type;
	private boolean BloqueVision;
	private boolean BloqueDeplacement;
	
	public Obstacle(String type, boolean isBloqueVision, boolean isBloqueDeplacement) {
		this.type = type;
		this.BloqueVision = isBloqueVision;
		this.BloqueDeplacement = isBloqueDeplacement;
	}

	public String getType() {
		return type;
	}

	public boolean isBloqueVision() {
		return BloqueVision;
	}

	public boolean isBloqueDeplacement() {
		return BloqueDeplacement;
	}
}