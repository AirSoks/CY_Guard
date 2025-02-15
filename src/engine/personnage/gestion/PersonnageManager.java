package engine.personnage.gestion;

import java.util.ArrayList;
import java.util.List;

import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
import engine.personnage.Gardien;
import engine.personnage.Intrus;
import engine.personnage.Personnage;

public class PersonnageManager {
	private static PersonnageManager instance;
    
    private List<Personnage> personnages = new ArrayList<>();
    private Gardien gardienActif;
    
    public static PersonnageManager getInstance() {
		if (instance == null) {
			instance = new PersonnageManager();
		}
		return instance;
	}
    
    public void ajouterPersonnage(Personnage personnage) {
        this.personnages.add(personnage);
    }

    public Gardien getGardienActif() {
		return gardienActif;
	}

	public void setGardienActif(Gardien gardienActif) {
		this.gardienActif = gardienActif;
	}

	public void retirerPersonnage(Personnage personnage) {
        this.personnages.remove(personnage);
    }

    public List<Personnage> getPersonnages() {
        return this.personnages;
    }

    public List<Personnage> getPersonnages(Coordonnee coordonnee) {
        if (coordonnee == null) {
            return null;
        }
        List<Personnage> result = new ArrayList<>();
        for (Personnage personnage : this.personnages) {
            if (personnage.getCoordonnee().equals(coordonnee)) {
                result.add(personnage);
            }
        }
        return result;
    }

    public List<Intrus> getIntrus() {
        List<Intrus> intrus = new ArrayList<>();
        for (Personnage personnage : this.personnages) {
            if (personnage instanceof Intrus) {
                intrus.add((Intrus) personnage);
            }
        }
        return intrus;
    }

    public List<Gardien> getGardiens() {
        List<Gardien> gardiens = new ArrayList<>();
        for (Personnage personnage : this.personnages) {
            if (personnage instanceof Gardien) {
                gardiens.add((Gardien) personnage);
            }
        }
        return gardiens;
    }
    
    public Gardien ajouterGardien(Grille grille) {
        Gardien gardien = PersonnageApparition.apparitionGardien(grille);
        this.ajouterPersonnage(gardien);
        return gardien;
    }

    public Intrus ajouterIntrus(Grille grille) {
        Intrus intrus = PersonnageApparition.apparitionIntrus(grille);
        this.ajouterPersonnage(intrus);
        return intrus;
    }

    public boolean deplacerPersonnage(Grille grille, Personnage personnage, Direction direction) {	
        if (PersonnageDeplacement.deplacerPersonnage(grille, personnage, direction)) {
        	PersonnageDeplacement.contactPersonnage(instance, personnage.getCoordonnee());
        	return true;
        }
        return false;
    }
}