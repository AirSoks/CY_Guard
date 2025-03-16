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

/**
 * Cette classe abstraite représente un déplacement type d'un personnage
 * 
 * @author GLP_19
 * @see Grille
 * @see PersonnageManager
 * @see Personnage
 * @see Deplacement
 */
public abstract class StrategieDeplacement implements Deplacement {
	
    /**
     * La grille sur laquelle le personnage bouge
     */
    private Grille grille;
    
    /**
     * La liste des personnages, si contact
     */
    private PersonnageManager personnages;
    
    protected void updateAnimation(Personnage personnage, Direction direction) {
        personnage.setDirection(direction);
        if (direction != null) {
            personnage.getAnimation().switchAnimationFrame();
        }
    }

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

	public Boolean isCoordonneeValide(Coordonnee coordonnee) {
		Case c = grille.getCase(coordonnee);
		if (c != null && !c.getObstacle().isBloqueDeplacement()) {
			return true;
		}
		return false;
	}

	/**
	 * Vérifie si un contact à eu lieu entre un gardien et un intrus sur la coordonnée
	 * 
	 * @param coordonnee La coordonnée où vérifier le contact
	 */
	public void contactPersonnage(Coordonnee coordonnee) {
		List<Gardien> listeGardien = personnages.getGardiens(coordonnee);
		List<Intrus> listeIntrus = personnages.getIntrus(coordonnee);
		if (listeGardien.size() >= 1 && listeIntrus.size() >= 1) {
			for (Intrus intrus : listeIntrus) {
				personnages.retirerPersonnage(intrus);
				listeGardien.get(0).addNbIntrusCapture();
			}
		}
	}
}
