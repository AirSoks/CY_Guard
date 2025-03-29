package engine.personnage.deplacement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.map.Coordonnee;

/**
 * Cette classe représente une map de pas pour chaque coordonnée
 * Elle les classes par liste pour faciliter leur accès.
 * 
 * @author GLP_19
 * @see Coordonnée
 * @see GrilleBuilder
 * 
 */
public class MapPasCoordonnee {

	/**
	 * La map de coordonnée associé à un pas
	 */
	private Map<Integer, List<Coordonnee>> mapPasCoordonnee = new HashMap<>();

	public Map<Integer, List<Coordonnee>> getMapPasCoordonnee() {
		return mapPasCoordonnee;
	}

	public MapPasCoordonnee() {
        this.mapPasCoordonnee = new HashMap<>();
    }
	
	public List<Integer> getListePas() {
        return new ArrayList<>(mapPasCoordonnee.keySet());
    }

	public List<Coordonnee> getCoordonneesFromPas(int pas) {
        return mapPasCoordonnee.get(pas);
    }

	/**
	 * Ajoute une coordonnée dans la map associé à un pas
	 * 
	 * @param probabilite Le pas de la coordonnée
	 * @param coordonnee La coordonnée à ajouter
	 */
	public void ajouterCoordonne(int pas, Coordonnee coordonnee) {
        if (pas >= 0 && coordonnee != null) {
        	List<Coordonnee> coordonnees = getCoordonneesFromPas(pas);
            if (coordonnees == null) {
            	coordonnees = new ArrayList<>();
            }
            coordonnees.add(coordonnee);
            mapPasCoordonnee.put(pas, coordonnees);
        }
    }
	
	/**
	 * Ajoute une liste de coordonnées dans la map associé à un pas
	 * 
	 * @param probabilite La probabilité de la coordonnée
	 * @param coordonnees La liste de coordonnée à ajouter
	 */
	public void ajouterCoordonnes(int pas, List<Coordonnee> coordonnees) {
        if (pas >= 0 && coordonnees != null && !coordonnees.isEmpty()) {
			for (Coordonnee coordonnee : coordonnees) {
				if (!coordonneeIsDejaVu(coordonnee)) {
					ajouterCoordonne(pas, coordonnee);
				}
            }
        }
    }
	
	public boolean coordonneeIsDejaVu(Coordonnee coordonnee) {
	    for (List<Coordonnee> coordonnees : mapPasCoordonnee.values()) {
	        if (coordonnees.contains(coordonnee)) {
	            return true;
	        }
	    }
	    return false;
	}

	public void reinitialiserMap() {
		this.mapPasCoordonnee = new HashMap<>();
	}
}