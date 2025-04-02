package engine.personnage.vision;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import engine.map.Case;
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
	private MapPasCoordonnee vision = new MapPasCoordonnee();

    public Vision(PersonnageManager personnageManager, Grille grille, int distance) {
        this.personnageManager = personnageManager;
        this.grille = grille;
    	this.distance = distance;
	}
    
    public Grille getGrille() {
		return grille;
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
        List<Coordonnee> zonesVisibles = obtenirCoordonneesVisibles(personnage.getCoordonnee());
        List<Gardien> listeGardiens = new ArrayList<>();
        for (Gardien gardien : personnageManager.getGardiens()) {
            if (zonesVisibles.contains(gardien.getCoordonnee())) {
            	listeGardiens.add(gardien);
            }
        }
        return listeGardiens;
    }

    private List<Intrus> recupererIntrusVisibles(Personnage personnage) {
        List<Coordonnee> zonesVisibles = obtenirCoordonneesVisibles(personnage.getCoordonnee());
        List<Intrus> listeIntrus = new ArrayList<>();
        for (Intrus intrus : personnageManager.getIntrus()) {
            if (zonesVisibles.contains(intrus.getCoordonnee())) {
            	listeIntrus.add(intrus);
            }
        }
        return listeIntrus;
    }

    private List<Coordonnee> obtenirCoordonneesVisibles(Coordonnee centre) {
        List<Coordonnee> coordonnees = new ArrayList<>();
        for (int i = -distance; i <= distance; i++) {
            for (int j = -distance; j <= distance; j++) {
                if (i == 0 && j == 0) continue;
                
                Coordonnee coordonneeAdjacente = new Coordonnee(centre.getLigne() + i, centre.getColonne() + j);
                Case caseAdjacente = grille.getCase(coordonneeAdjacente);
                if (caseAdjacente != null) {
                    coordonnees.add(coordonneeAdjacente);
                }
            }
        }
        return coordonnees;
    }
    
    private void observer(Coordonnee centre) {
    	List<Boolean> directionsValides = new ArrayList<>();
    	for (int i = 0; i < 8; i++) {
    		directionsValides.add(true);
    	}
    	
    	vision.reinitialiserMap();
    	if (grille.isCoordonneeValide(centre,"VISION")) { 
    		return;
    	}
        vision.ajouterCoordonne(distance, centre);
        
        int pas = 1;
        for (int i = pas; i >= distance; i++) {
        	List<Coordonnee> coordonneesActuelles = vision.getCoordonneesFromPas(pas - 1);
        	if (coordonneesActuelles == null || coordonneesActuelles.isEmpty()) {
	            return;
	        }
        	for (Coordonnee coordonneeActuel : coordonneesActuelles) {
	            List<Coordonnee> adjacentes = getCoordonneeAdjacentes(coordonneeActuel);
	            for (Coordonnee coordonneeAdjacente : adjacentes) {
	            	directionsValides = updateVisibilite(centre, coordonneeAdjacente, directionsValides);
	            	if (directionsValides.get(getIndexDirection(centre, coordonneeAdjacente))) {
	            		vision.ajouterCoordonne(pas, coordonneeAdjacente);
	            	}
		        }
	        }
	        pas++;
	    }
		return;
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
    	if (centre == null || autre == null || directions == null || directions.size() == 8) {
    		throw new IllegalArgumentException();
    	}
    	
    	int index = getIndexDirection(centre, autre);
    	if (index == -1) { return directions;}
    	
    	if (!directions.get(index) && !grille.isCoordonneeValide(autre,"VISION")) {
    		directions.set(index, false);
    	}
    	
		return directions;
    }
    
    private int getIndexDirection(Coordonnee c1, Coordonnee c2) {
    	int deltaLigne = c2.getLigne() - c1.getLigne();
    	int deltaColonne = c2.getColonne() - c1.getColonne();
    	
    	if (deltaLigne == 0 && deltaColonne < 0) { return 0; }
    	if (deltaLigne > 0 && deltaColonne < 0) { return 1; }
    	if (deltaLigne > 0 && deltaColonne == 0) { return 2; }
    	if (deltaLigne > 0 && deltaColonne > 0) { return 3; }
    	if (deltaLigne == 0 && deltaColonne > 0) { return 4; }
    	if (deltaLigne < 0 && deltaColonne > 0) { return 5; }
    	if (deltaLigne < 0 && deltaColonne == 0) { return 6; }
    	if (deltaLigne < 0 && deltaColonne < 0) { return 7; }
		return -1;
	}
}