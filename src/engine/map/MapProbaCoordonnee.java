package engine.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public void ajouterProbabilite(Double nouvelleProbabilite, List<Coordonnee> nouvelleCoordonnees) {
        if (nouvelleProbabilite != null && nouvelleCoordonnees != null && !nouvelleCoordonnees.isEmpty()) {
			for (Coordonnee coordonnee : nouvelleCoordonnees) {
            	ajouterProbabilite(nouvelleProbabilite, coordonnee);
            }
        }
    }
	
	public void ajouterProbabilite(Double nouvelleProbabilite, Coordonnee nouvelleCoordonnees) {
        if (nouvelleProbabilite != null && nouvelleCoordonnees != null) {
        	List<Coordonnee> coordonnees = getCoordonneesFromProbabilite(nouvelleProbabilite);
            if (coordonnees == null) { 
            	coordonnees = new ArrayList<>();
            }
            coordonnees.add(nouvelleCoordonnees);
            mapProbaCoordonnee.put(nouvelleProbabilite, coordonnees);
        }
    }
	
	public Double getProbabiliteFromCoordonnee(Coordonnee coordonnee) {
		if (coordonnee == null) {
			return null;
		}
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
	
    public Coordonnee getCoordonneeAleatoire(List<Coordonnee> coordonnees) {
		if (coordonnees == null || coordonnees.isEmpty()) {
			return null;
		}
		int index = (int) getValeurAleatoire(coordonnees.size());
		return coordonnees.get(index);
	}
	
	public List<Coordonnee> getListeAleatoire() {
		double valeurAleatoire = getValeurAleatoire(getSommeProbabilite());
        double sommeProbabilite = 0.0;
        for (Double probabilite : getListeProbabilites()) {
            List<Coordonnee> coordonnees = getCoordonneesFromProbabilite(probabilite);
            sommeProbabilite += probabilite * coordonnees.size();
            if (valeurAleatoire <= sommeProbabilite) {
                return coordonnees;
            }
        }
        return null;
    }
    
	private static double getValeurAleatoire(double value) {
	    return (double) Math.random() * value;
	}
}
