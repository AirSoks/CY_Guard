package engine.personnage;

import engine.map.Coordonnee;
import engine.personnage.deplacement.Deplacement;
import engine.utilitaire.GenerateurNom;

public abstract class Personnage {
	
	private Coordonnee coordonnee;
	private String name;
	private long tempsInvocation;
	private int vitesse;
    private Deplacement deplacement;
	
	public Personnage(Coordonnee coordonnee) {
		this.coordonnee = coordonnee;
		this.name = GenerateurNom.genererNom();
		this.tempsInvocation = System.currentTimeMillis();
	}
	
	public Coordonnee getCoordonnee() {
		return coordonnee;
	}

	public void setCoordonnee(Coordonnee coordonnee) {
		this.coordonnee = coordonnee;
	}

	public String getName() {
		return name;
	}

	public long getTempsInvocation() {
		return tempsInvocation;
	}
	
	public int getVitesse() {
		return vitesse;
	}
	
	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public Deplacement getDeplacement() {
		return deplacement;
	}

	public void setDeplacement(Deplacement deplacement) {
		this.deplacement = deplacement;
	}
	
	public void deplacer() {
		deplacement.deplacer(this);
    }
}