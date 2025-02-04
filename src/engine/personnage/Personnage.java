package engine.personnage;

import engine.map.Case;
import engine.utilitaire.GenerateurNom;

public abstract class Personnage {
	
	private Case caseActuel;
	private String name;
	private long tempsInvocation;
	private int vitesse;
	
	public Personnage(Case caseActuel) {
		this.caseActuel = caseActuel;
		this.name = GenerateurNom.genererNom();
		this.tempsInvocation = System.currentTimeMillis();
	}
	
	public Case getCase() {
		return caseActuel;
	}

	public void setCase(Case caseActuel) {
		this.caseActuel = caseActuel;
	}

	public String getName() {
		return name;
	}

	public long getTempsInvocation() {
		return tempsInvocation;
	}
	
	public long getVitesse() {
		return vitesse;
	}
	
	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}
}