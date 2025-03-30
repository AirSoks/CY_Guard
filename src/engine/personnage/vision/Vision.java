package engine.personnage.vision;

import java.util.ArrayList;
import java.util.List;

import engine.map.Case;
import engine.map.Coordonnee;
import engine.map.Grille;
import engine.personnage.Gardien;
import engine.personnage.Intrus;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;

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

    public Vision(PersonnageManager personnageManager, Grille grille, int distance) {
        this.personnageManager = personnageManager;
        this.grille = grille;
    	this.distance = distance;
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
}