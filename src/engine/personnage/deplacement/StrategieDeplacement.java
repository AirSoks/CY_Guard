package engine.personnage.deplacement;

import java.util.List;

import engine.map.Case;
import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
import engine.personnage.Gardien;
import engine.personnage.Intrus;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;

public abstract class StrategieDeplacement implements Deplacement {
    private Grille grille;
    private PersonnageManager personnages;

	public StrategieDeplacement(PersonnageManager personnages, Grille grille) {
        this.personnages = personnages;
        this.grille = grille;
    }

    public Grille getGrille() {
		return grille;
	}

	public void setGrille(Grille grille) {
		this.grille = grille;
	}

	public PersonnageManager getPersonnage() {
		return personnages;
	}

	public void setPersonnage(PersonnageManager personnages) {
		this.personnages = personnages;
	}

	public List<Personnage> getPersonnages() {
        return personnages.getPersonnages();
    }

	public Boolean isCoordonneeValide(Coordonnee coordonnee) {
		Case c = grille.getCase(coordonnee);
		if (c != null && !c.getObstacle().isBloqueDeplacement()) {
			return true;
		}
		return false;
	}

	public void contactPersonnage(Coordonnee coordonnee) {
		List<Gardien> listeGardien = personnages.getGardiens(coordonnee);
		List<Intrus> listeIntrus = personnages.getIntrus(coordonnee);
		if (listeGardien.size() >= 1 && listeIntrus.size() >= 1) {
			for (Intrus intrus : listeIntrus) {
				personnages.retirerPersonnage(intrus);
			}
		}
	}
}