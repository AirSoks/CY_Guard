package engine.map.generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.map.Coordonnee;

/**
 * Cette classe représente une map de probabilité pour chaque coordonnée
 * Elle les classes par liste pour faciliter leur accès.
 * 
 * @author GLP_19
 * @see Coordonnée
 * @see GrilleBuilder
 * 
 */
public class MapProbaCoordonnee {

	/**
	 * La map de coordonnée associé à une probabilité
	 */
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

	/**
	 * Ajoute une coordonnée dans la map associé à une probabilité
	 * 
	 * @param probabilite La probabilité de la coordonnée
	 * @param coordonnee La coordonnée à ajouter
	 */
	public void ajouterCoordonne(Double probabilite, Coordonnee coordonnee) {
        if (probabilite != null && coordonnee != null) {
        	List<Coordonnee> coordonnees = getCoordonneesFromProbabilite(probabilite);
            if (coordonnees == null) {
            	coordonnees = new ArrayList<>();
            }
            coordonnees.add(coordonnee);
            mapProbaCoordonnee.put(probabilite, coordonnees);
        }
    }
	
	/**
	 * Ajoute une liste de coordonnées dans la map associé à une probabilité
	 * 
	 * @param probabilite La probabilité de la coordonnée
	 * @param coordonnees La liste de coordonnée à ajouter
	 */
	public void ajouterCoordonnes(Double probabilite, List<Coordonnee> coordonnees) {
        if (probabilite != null && coordonnees != null && !coordonnees.isEmpty()) {
			for (Coordonnee coordonnee : coordonnees) {
				ajouterCoordonne(probabilite, coordonnee);
            }
        }
    }

	/**
	 * Recupère la probabilité associé à une coordonnée
	 * 
	 * @param coordonnee La coordonnée qui associe une probabilité
	 * @return La probabilité de la coordonnée 
	 */
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

	/**
	 * Supprime une coordonnée de la map
	 * 
	 * @param coordonnee La coordonnée à supprimer
	 */
	public void supprimerCoordonnee(Coordonnee coordonnee) {
		if (coordonnee == null) {
			return;
		}
		for (Double probabilite : getListeProbabilites()) {
	        List<Coordonnee> coordonnees = getCoordonneesFromProbabilite(probabilite);
	        if (coordonnees.remove(coordonnee)) {
	            if (coordonnees.isEmpty()) {
	                mapProbaCoordonnee.remove(probabilite);
	            }
	            break;
	        }
	    }
	}

	/**
	 * Renvoie la somme total des probabilités de chaque coordonnée de la map
	 * 
	 * @return La somme des probabilité de chaque coordonnées 
	 */
	public double getSommeProbabilite() {
	    double sommeProbabilite = 0.0;
	    for (Double probabilite : getListeProbabilites()) {
	        List<Coordonnee> coordonnees = getCoordonneesFromProbabilite(probabilite);
	        sommeProbabilite += probabilite * coordonnees.size();
	    }
	    return sommeProbabilite;
	}

    /**
     * Récupère une coordonnée aléatoire de la liste
     * 
     * @param coordonnees La liste de coordonnées que l'on doit traiter
     * @return La coordonnée aléatoire 
     */
    public Coordonnee getCoordonneeAleatoire(List<Coordonnee> coordonnees) {
		if (coordonnees == null || coordonnees.isEmpty()) {
			return null;
		}
		int index = (int) getValeurAleatoire(coordonnees.size());
		return coordonnees.get(index);
	}

	/**
	 * Récupère une liste de coordonnées qui est récupérer selon la probabilité de chaque liste dans la map
	 * 
	 * @return La liste aléatoire (en fonction des probabilités)
	 */
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
	    return Math.random() * value;
	}
}