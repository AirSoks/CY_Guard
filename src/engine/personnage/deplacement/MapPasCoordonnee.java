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
		return new HashMap<>(mapPasCoordonnee);
	}

	public MapPasCoordonnee() {
        this.mapPasCoordonnee = new HashMap<>();
    }
	
	public List<Integer> getListePas() {
        return new ArrayList<>(mapPasCoordonnee.keySet());
    }

	public List<Coordonnee> getCoordonneesFromPas(int pas) {
		List<Coordonnee> coordonnees = mapPasCoordonnee.get(pas);
		if (coordonnees == null || coordonnees.isEmpty()) {
	        return null;
		}
		return new ArrayList<>(coordonnees);
    }
	
	public List<Coordonnee> getAllCoordonnees() {
		List<Coordonnee> coordonnees = new ArrayList<>();
		for (int i = 0; i <= getListePas().size(); i++) {
			List<Coordonnee> liste = getCoordonneesFromPas(i);
	        if (liste != null) {
	            coordonnees.addAll(liste);
	        }
		}
		return coordonnees;
	}

	/**
	 * Ajoute une coordonnée dans la map associée à un pas
	 * 
	 * @param pas Le pas de la coordonnée
	 * @param coordonnee La coordonnée à ajouter
	 */
	public void ajouterCoordonne(int pas, Coordonnee coordonnee) {
	    if (pas >= 0 && coordonnee != null && !coordonneeIsDejaVu(coordonnee)) {
	        List<Coordonnee> coordonnees = mapPasCoordonnee.getOrDefault(pas, new ArrayList<>());
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
				ajouterCoordonne(pas, coordonnee);
            }
        }
    }
	
	public boolean coordonneeIsDejaVu(Coordonnee coordonnee) {
	    if (coordonnee == null) { return false; }
	    
	    List<Integer> listePas = getListePas();
	    if (listePas == null || listePas.isEmpty()) { return false; }
	    
	    for (int pas : listePas) {
	    	List<Coordonnee> coordonnees = getCoordonneesFromPas(pas);
	    	if (coordonnees != null && coordonnees.contains(coordonnee)) {
	    		return true;
	    	}
	    }
	    return false;
	}

	public void reinitialiserMap() {
		this.mapPasCoordonnee = new HashMap<>();
	}
}