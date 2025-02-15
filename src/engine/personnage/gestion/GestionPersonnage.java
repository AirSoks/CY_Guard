package engine.personnage.gestion;

import engine.map.Direction;
import engine.map.Grille;
import engine.personnage.Gardien;
import engine.personnage.Intrus;
import engine.personnage.Personnage;

public class GestionPersonnage {

	private Grille grille;
    private PersonnageManager personnageManager;
    
    public PersonnageManager getPersonnageManager() {
		return personnageManager;
	}

	public void setPersonnageManager(PersonnageManager personnageManager) {
		this.personnageManager = personnageManager;
	}

	public GestionPersonnage(Grille grille, PersonnageManager personnageManager) {
        this.grille = grille;
        this.personnageManager = personnageManager;
    }
    
    public Gardien ajouterGardien() {
    	Gardien gardien = PersonnageApparition.apparitionGardien(grille);
    	personnageManager.ajouterPersonnage(gardien);
        return gardien;
    }

    public Intrus ajouterIntrus() {
    	Intrus intrus = PersonnageApparition.apparitionIntrus(grille);
    	personnageManager.ajouterPersonnage(intrus);
        return intrus;
    }

    public boolean deplacerPersonnage(Personnage personnage, Direction direction) {	
        if (PersonnageDeplacement.deplacerPersonnage(grille, personnage, direction)) {
        	PersonnageDeplacement.contactPersonnage(personnageManager, personnage.getCoordonnee());
        	return true;
        }
        return false;
    }
}