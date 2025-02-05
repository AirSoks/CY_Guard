package engine.map;

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
	
	public void initProba(Grille grille) {
		if (grille == null) {
			return;
		}
        List<Coordonnee> coordonnees = new ArrayList<>();
        for (int i = 0; i < GameConfiguration.NB_LIGNE; i++) {
            for (int j = 0; j < GameConfiguration.NB_COLONNE; j++) {
                Coordonnee position = new Coordonnee(i, j);
                if (grille.getCase(position).getObstacle().equals(GameConfiguration.PLAINE)) {
                    coordonnees.add(position);
                }
            }
        }
        double probaInitiale = 100.0 / coordonnees.size();
        ajouterProbabilite(probaInitiale, coordonnees);
    }
	
    public Coordonnee getCoordonneeAleatoire() {
    	List<Coordonnee> coordonnees = getListeAleatoire();
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
    
	public static double getValeurAleatoire(double value) {
	    return (double) Math.random() * value;
	}
}
