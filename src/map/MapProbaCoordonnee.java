package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.GameConfiguration;

public class MapProbaCoordonnee {
	private Map<Double, List<Coordonnee>> mapProbaCoordonnee = new HashMap<>();
	
    public MapProbaCoordonnee() {
        this.mapProbaCoordonnee = new HashMap<>();
    }
	
	public List<Double> getListeProbabilites() {
        return new ArrayList<>(mapProbaCoordonnee.keySet());
    }
	
	public List<Coordonnee> getCoordonneesFromProbabilite(Double probabilite) {
        return mapProbaCoordonnee.get(probabilite);
    }
	
	public void ajouterProbabilite(Double probabilite, List<Coordonnee> nouvelleCoordonnees) {
        if (probabilite != null && nouvelleCoordonnees != null && !nouvelleCoordonnees.isEmpty()) {
			for (Coordonnee coordonnee : nouvelleCoordonnees) {
            	ajouterProbabilite(probabilite, coordonnee);
            }
        }
    }
	
	public void ajouterProbabilite(Double probabilite, Coordonnee nouvelleCoordonnees) {
        if (probabilite != null && nouvelleCoordonnees != null) {
        	List<Coordonnee> coordonnees = getCoordonneesFromProbabilite(probabilite);
            if (coordonnees == null) { 
            	coordonnees = new ArrayList<>();
            }
            coordonnees.add(nouvelleCoordonnees);
            mapProbaCoordonnee.put(probabilite, coordonnees);
        }
    }
	
	public Double getProbabiliteFromCoordonnee(Coordonnee coordonnee) {
	    for (Double probabilite : getListeProbabilites()) {
	        List<Coordonnee> coordonnees = getCoordonneesFromProbabilite(probabilite);
	        if (coordonnees.contains(coordonnee)) {
	            return probabilite;
	        }
	    }
	    return null;
	}
	
	public void supprimerCoordonnee(Coordonnee coordonnee) {
		if (coordonnee == null) {
			return;
		}
		for (Double probabilite : getListeProbabilites()) {
	        List<Coordonnee> coordonnees = getCoordonneesFromProbabilite(probabilite);
	        if (coordonnees.remove(coordonnee) == true) {
	            if (coordonnees.isEmpty()) {
	                mapProbaCoordonnee.remove(probabilite);
	            }
	            break;
	        }
	    }
	}
	
	public double getSommeProbabilite() {
	    double sommeProbabilite = 0.0;
	    for (Double probabilite : getListeProbabilites()) {
	        List<Coordonnee> coordonnees = getCoordonneesFromProbabilite(probabilite);
	        sommeProbabilite += probabilite * coordonnees.size();
	    }
	    return sommeProbabilite;
	}
	
	public boolean isEmpty() {
        return mapProbaCoordonnee.isEmpty();
    }
	
	public void reinitialiserMap() {
		if (!mapProbaCoordonnee.isEmpty()) {
			mapProbaCoordonnee = new HashMap<>();
		}
	}
}
