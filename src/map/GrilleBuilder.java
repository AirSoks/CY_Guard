package map;

import map.obstacle.Obstacle;
import utilitaire.Coordonnee;
import config.GameConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapBuilder {
	private Grille grille;
	private Map<Double, List<Coordonnee>> mapProbaCoordonnee = new HashMap<>();
	
	private List<Double> getListeProbabilites() {
        return new ArrayList<>(mapProbaCoordonnee.keySet());
    }
	
	private List<Coordonnee> getCoordonneesFromProbabilite(Double probabilite) {
        return mapProbaCoordonnee.get(probabilite);
    }
	
	
	// Faut que j'utilise ça
	private void ajouterProbabilite(Double probabilite, List<Coordonnee> coordonnees) {
        if (probabilite != null && coordonnees != null && !coordonnees.isEmpty()) {
            mapProbaCoordonnee.put(probabilite, coordonnees);
        }
    }
	
	private Double getProbabiliteFromCoordonnee(Coordonnee coordonnee) {
	    for (Double probabilite : getListeProbabilites()) {
	        List<Coordonnee> coordonnees = getCoordonneesFromProbabilite(probabilite);
	        if (coordonnees.contains(coordonnee)) {
	            return probabilite;
	        }
	    }
	    return null;
	}
	
	private void supprimerCoordonnee(Coordonnee coordonnee) {
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
	
	private double getSommeProbabilite() {
	    double sommeProbabilite = 0.0;
	    for (Double probabilite : getListeProbabilites()) {
	        List<Coordonnee> coordonnees = getCoordonneesFromProbabilite(probabilite);
	        sommeProbabilite += probabilite * coordonnees.size();
	    }
	    return sommeProbabilite;
	}
	
    public MapBuilder() {
        this.grille = new Grille(GameConfiguration.NB_LIGNE, GameConfiguration.NB_COLONNE);
        genererCarte();
    }
	
	private void genererCarte() {
		placerObstacles(GameConfiguration.LAC, GameConfiguration.NB_LAC, GameConfiguration.DENSITE_LAC);
		placerObstacles(GameConfiguration.ROCHE, GameConfiguration.NB_ROCHE, GameConfiguration.DENSITE_ROCHE);
		placerObstacles(GameConfiguration.ARBRE, GameConfiguration.NB_ARBRE, GameConfiguration.DENSITE_ARBRE);
	}
	
	private void placerObstacles(Obstacle obstacle, int nombreObstacles, int densite) {
		initProba();
		int obstaclesPlaces = 0;
        while (obstaclesPlaces < nombreObstacles) {
        	List<Coordonnee> listeCoordonneeAleatoire = getListeFromValeurAleatoire(getValeurAleatoire(getSommeProbabilite()));
        	if (listeCoordonneeAleatoire != null && !listeCoordonneeAleatoire.isEmpty()) {
        		Coordonnee coordonneeAleatoire = getCoordonneeAleatoire(listeCoordonneeAleatoire);
        		if (coordonneeAleatoire != null) {
        			grille.getCase(coordonneeAleatoire).setObstacle(obstacle);
        			supprimerCoordonnee(coordonneeAleatoire);
        			List<Coordonnee> coordonneeAdjacentes = getCasesAdjacentes(coordonneeAleatoire);
        			augmenterProbabilite(coordonneeAdjacentes, densite);
	                obstaclesPlaces++;
        		}
        	}
        }
    }
	
    private void initProba() {
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
        mapProbaCoordonnee = new HashMap<>();
        mapProbaCoordonnee.put(probaInitiale, coordonnees);
    }

    private List<Coordonnee> getListeFromValeurAleatoire(double valeurAleatoire) {
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
    
    private Coordonnee getCoordonneeAleatoire(List<Coordonnee> coordonnees) {
		if (coordonnees == null || coordonnees.isEmpty()) {
			return null;
		}
		int index = (int) (Math.random() * coordonnees.size());
		return coordonnees.get(index);
	}
    
    private List<Coordonnee> getCasesAdjacentes(Coordonnee coordonnee) {
		List<Coordonnee> coordonneeAdjacentes = new ArrayList<>();
		int nbCaseDensite = GameConfiguration.NB_CASE_DENSITE;

		for (int i = -nbCaseDensite; i <= nbCaseDensite; i++) {
			for (int j = -nbCaseDensite; j <= nbCaseDensite; j++) {
				if (i == 0 && j == 0) { continue; }
				
				Coordonnee coordonneeAdjacente = new Coordonnee(coordonnee.getLigne() + i, coordonnee.getColonne() + j);
				Case caseAdjacente = grille.getCase(coordonneeAdjacente);
				if (caseAdjacente != null && caseAdjacente.getObstacle().equals(GameConfiguration.PLAINE)){
					coordonneeAdjacentes.add(coordonneeAdjacente);
				}
            }
		}
		return coordonneeAdjacentes;
	}

    private void augmenterProbabilite(List<Coordonnee> coordonnees, int densite) {
        for (Coordonnee coordonnee : coordonnees) {
            Double probaActuelle = getProbabiliteFromCoordonnee(coordonnee);
            if (probaActuelle != null) {
                double nouvelleProbabilite = probaActuelle * (1 + densite / 100.0);
                
                supprimerCoordonnee(coordonnee);
                List<Coordonnee> listeNouvelleProbabilite = getCoordonneesFromProbabilite(nouvelleProbabilite);
                if (listeNouvelleProbabilite == null) { 
                	listeNouvelleProbabilite = new ArrayList<>();
                }
                listeNouvelleProbabilite.add(coordonnee);
                mapProbaCoordonnee.put(nouvelleProbabilite, listeNouvelleProbabilite);
            }
        }
    }

	private static double getValeurAleatoire(double value) {
	    return (double) Math.random() * value;
	}
	
	public Grille getGrille() {
		return this.grille;
	}
}