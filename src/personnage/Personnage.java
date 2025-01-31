package personnage;

import utilitaire.Coordonnee;
import utilitaire.GenerateurNom;

public abstract class Personnage {
	
	private Coordonnee position;
	private String name = GenerateurNom.genererNom();
	private long tempsInvocation;
	
	public Coordonnee getPosition() {
		return position;
	}

	public void setPosition(Coordonnee position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public long getTempsInvocation() {
		return tempsInvocation;
	}

	public Personnage(Coordonnee position, long tempsInvocation) {
		this.position = position;
		this.tempsInvocation = tempsInvocation;
	}
}