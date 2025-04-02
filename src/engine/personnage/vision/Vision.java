package engine.personnage.vision;

import java.util.ArrayList;
import java.util.List;

import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
import engine.personnage.Gardien;
import engine.personnage.Intrus;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;
import engine.personnage.deplacement.MapPasCoordonnee;

public class Vision {
	
	/*
	 * La distance de case pour voir les personnage
	 */
	private int distance;
	
    /**
     * La grille du jeu
     */
    private Grille grille;

	/**
     * La liste des personnages
     */
    private PersonnageManager personnageManager;

	/**
     * La liste des coordonnees visibles 
     */
	private MapPasCoordonnee mapPasCoordonnee = new MapPasCoordonnee();

    public Vision(PersonnageManager personnageManager, Grille grille, int distance) {
        this.personnageManager = personnageManager;
        this.grille = grille;
    	this.distance = distance;
	}

    public int getDistance() {
		return distance;
	}

	public MapPasCoordonnee getMapPasCoordonnee() {
		return mapPasCoordonnee;
	}
	
    public void observer(Personnage personnage) {
        if (personnage instanceof Gardien) {
        	Gardien gardien = (Gardien) personnage;
            List<Intrus> intrusVisibles = recupererIntrusVisibles(gardien);
            for (Intrus intrus : intrusVisibles) {
                if (!gardien.getCibles().contains(intrus)) {
                    gardien.ajouterCible(intrus);
                }
            }
        } 
        else if (personnage instanceof Intrus) {
        	Intrus intrus = (Intrus) personnage;
            List<Gardien> gardiensVisibles = recupererGardiensVisibles(intrus);
            for (Gardien gardien : gardiensVisibles) {
                intrus.ajouterCible(gardien);
            }
        }
    }

	private List<Gardien> recupererGardiensVisibles(Personnage personnage) {
    	observer(personnage.getCoordonnee());
        List<Coordonnee> zonesVisibles = mapPasCoordonnee.getAllCoordonnees();
        List<Gardien> listeGardiens = new ArrayList<>();
        for (Gardien gardien : personnageManager.getGardiens()) {
            if (zonesVisibles.contains(gardien.getCoordonnee())) {
            	listeGardiens.add(gardien);
            }
        }
        return listeGardiens;
    }

	private List<Intrus> recupererIntrusVisibles(Personnage personnage) {
	    observer(personnage.getCoordonnee());

	    List<Coordonnee> zonesVisibles = mapPasCoordonnee.getAllCoordonnees();
	    List<Intrus> listeIntrus = new ArrayList<>();
	    
	    for (Intrus intrus : personnageManager.getIntrus()) {
	        if (zonesVisibles.contains(intrus.getCoordonnee())) {
	            listeIntrus.add(intrus);
	        }
	    }
	    return listeIntrus;
	}
    
	private void observer(Coordonnee centre) {
	    List<Boolean> directionsValides = new ArrayList<>();
	    for (int i = 0; i < 8; i++) {
	        directionsValides.add(true);
	    }

	    mapPasCoordonnee.reinitialiserMap();
	    if (!grille.isCoordonneeValide(centre, "VISION")) {
	        return;
	    }
	    mapPasCoordonnee.ajouterCoordonne(0, centre);

	    int pas = 1;
	    for (int i = pas; i <= distance; i++) {
	        List<Coordonnee> coordonneesActuel = mapPasCoordonnee.getCoordonneesFromPas(pas - 1);
	        if (coordonneesActuel == null || coordonneesActuel.isEmpty()) {
	            return;
	        }
	        for (Coordonnee coordonneeActuel : coordonneesActuel) {
	            List<Coordonnee> adjacentes = getCoordonneeAdjacentes(coordonneeActuel);
	            
	            for (Coordonnee coordonneeAdjacente : adjacentes) {
	                directionsValides = updateVisibilite(centre, coordonneeAdjacente, directionsValides);
	                System.out.println(directionsValides);
	                
	                int index = getIndexDirection(centre, coordonneeAdjacente);
	                if (index == -1 || directionsValides.get(index)) {
	                	mapPasCoordonnee.ajouterCoordonne(pas, coordonneeAdjacente);
	                }
	            }
	        }
	        pas++;
	    }
	}
    
    /**
     * Récupère les coordonnées adjacentes d'une coordonnée visible
     * 
     * @param coordonnee La coordonnée à traiter
     * @return Une liste de coordonnée adjacente
     */
    private List<Coordonnee> getCoordonneeAdjacentes(Coordonnee coordonnee) {
        List<Coordonnee> coordonneeAdjacentes = new ArrayList<>();
        
        for (Direction direction : Direction.values()) {
            Coordonnee coordonneeAdjacente = direction.getCoordonnee(coordonnee);
            
            if (grille.isCoordonneeValide(coordonneeAdjacente, "VISION")) {
            	coordonneeAdjacentes.add(coordonneeAdjacente);
            }
        }
        return coordonneeAdjacentes;
    }
    
    public List<Boolean> updateVisibilite(Coordonnee centre, Coordonnee autre, List<Boolean> directions){
    	if (centre == null || autre == null || directions == null || directions.size() != 8) {
    	    throw new IllegalArgumentException();
    	}
    	
    	int index = getIndexDirection(centre, autre);
    	if (index == -1) { return directions;}
    	
    	if (directions.get(index) && !grille.isCoordonneeValide(autre, "VISION")) {
    	    directions.set(index, false);
    	}
    	
		return directions;
    }
    
    private int getIndexDirection(Coordonnee c1, Coordonnee c2) {
    	int deltaLigne = c2.getLigne() - c1.getLigne();
    	int deltaColonne = c2.getColonne() - c1.getColonne();
    	
    	if (deltaLigne == 0 && deltaColonne < 0) { return 0; } //GAUCHE
    	if (deltaLigne > 0 && deltaColonne < 0) { return 1; } // BAS GAUCHE
    	if (deltaLigne > 0 && deltaColonne == 0) { return 2; } // BAS
    	if (deltaLigne > 0 && deltaColonne > 0) { return 3; } // BAS DROITE
    	if (deltaLigne == 0 && deltaColonne > 0) { return 4; } // DROITE
    	if (deltaLigne < 0 && deltaColonne > 0) { return 5; } // HAUT DROITE
    	if (deltaLigne < 0 && deltaColonne == 0) { return 6; } // HAUT
    	if (deltaLigne < 0 && deltaColonne < 0) { return 7; } // HAUT GAUCHE
		return -1; // La coordonnée n'est ni dans la croix ni dans une diagonale
	}
}