package engine.personnage.gestion;

import java.util.ArrayList;
import java.util.List;

import engine.map.Coordonnee;
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

	public void retirerPersonnage(Personnage personnage) {
        this.personnages.remove(personnage);
    }

    public Gardien getGardienActif() {
		return gardienActif;
	}

	public void setGardienActif(Gardien gardienActif) {
		this.gardienActif = gardienActif;
	}

    public List<Personnage> getPersonnages() {
        return this.personnages;
    }
    
    public List<Intrus> getIntrus() {
        return getIntrus(null);
    }

    public List<Gardien> getGardiens() {
        return getGardiens(null);
    }

    public List<Personnage> getPersonnages(Coordonnee coordonnee) {
        List<Personnage> result = new ArrayList<>();
        for (Personnage personnage : this.personnages) {
            if (coordonnee == null || personnage.getCoordonnee().equals(coordonnee)) {
                result.add(personnage);
            }
        }
        return result;
    }

    public List<Intrus> getIntrus(Coordonnee coordonnee) {
        List<Intrus> intrus = new ArrayList<>();
        for (Personnage personnage : getPersonnages(coordonnee)) {
            if (personnage instanceof Intrus) {
                intrus.add((Intrus) personnage);
            }
        }
        return intrus;
    }

    public List<Gardien> getGardiens(Coordonnee coordonnee) {
        List<Gardien> gardiens = new ArrayList<>();
        for (Personnage personnage : getPersonnages(coordonnee)) {
            if (personnage instanceof Gardien) {
                gardiens.add((Gardien) personnage);
            }
        }
        return gardiens;
    }
}