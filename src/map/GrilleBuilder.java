package map;

import map.obstacle.Obstacle;
import config.GameConfiguration;

import java.util.ArrayList;
import java.util.List;

public class GrilleBuilder {
	private Grille grille;
	
	private MapProbaCoordonnee mapProbaCoordonnee;
	
    public GrilleBuilder() {
        this.grille = new Grille(GameConfiguration.NB_LIGNE, GameConfiguration.NB_COLONNE);
        this.mapProbaCoordonnee = new MapProbaCoordonnee();
        genererCarte();
    }
	
	private void genererCarte() {
		placerObstacles(GameConfiguration.LAC, GameConfiguration.NB_LAC, GameConfiguration.DENSITE_LAC, GameConfiguration.NB_CASE_DENSITE_LAC);
		placerObstacles(GameConfiguration.ROCHE, GameConfiguration.NB_ROCHE, GameConfiguration.DENSITE_ROCHE, GameConfiguration.NB_CASE_DENSITE_ROCHE);
		remplissageTrou();
		placerObstacles(GameConfiguration.ARBRE, GameConfiguration.NB_ARBRE, GameConfiguration.DENSITE_ARBRE ,GameConfiguration.NB_CASE_DENSITE_ARBRE);
	}
	
	private void placerObstacles(Obstacle obstacle, int nombreObstacles, int densite, int nbCaseDensiteObstacle) {
		initProba();
		int obstaclesPlaces = 0;
        while (obstaclesPlaces < nombreObstacles) {
        	
        	// On veut récupérer une valeur aléatoire entre 0 et la somme de toute les probas
        	double valeurAleatoire = getValeurAleatoire(mapProbaCoordonnee.getSommeProbabilite());
        	
        	// On récupère une liste de coordonnées avec cette valeur
        	List<Coordonnee> listeCoordonneeAleatoire = getListeFromValeurAleatoire(valeurAleatoire);
        	if (listeCoordonneeAleatoire != null && !listeCoordonneeAleatoire.isEmpty()) {
        		
        		// On prend aléatoirement une valeur de cette liste
        		Coordonnee coordonneeAleatoire = getCoordonneeAleatoire(listeCoordonneeAleatoire);
        		if (coordonneeAleatoire != null) {
        			
        			// On change la case avec le nouvelle obstacle et on supprime la coordonnée de la map
        			grille.getCase(coordonneeAleatoire).setObstacle(obstacle);
        			mapProbaCoordonnee.supprimerCoordonnee(coordonneeAleatoire);
        			
        			// On change la probabilité des cases adjacentes
        			List<Coordonnee> coordonneeAdjacentes = getCoordonneeAdjacentes(coordonneeAleatoire, nbCaseDensiteObstacle);
        			augmenterProbabilite(coordonneeAdjacentes, densite);
	                obstaclesPlaces++;
        		}
        	}
        }
    }
	
    private void initProba() {
    	mapProbaCoordonnee.reinitialiserMap();
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
        mapProbaCoordonnee.ajouterProbabilite(probaInitiale, coordonnees);
    }

    private List<Coordonnee> getListeFromValeurAleatoire(double valeurAleatoire) {
        double sommeProbabilite = 0.0;
        for (Double probabilite : mapProbaCoordonnee.getListeProbabilites()) {
            List<Coordonnee> coordonnees = mapProbaCoordonnee.getCoordonneesFromProbabilite(probabilite);
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
		int index = (int) getValeurAleatoire(coordonnees.size());
		return coordonnees.get(index);
	}
    
    private List<Coordonnee> getCoordonneeAdjacentes(Coordonnee coordonnee, int nbCaseDensiteObstacle) {
		List<Coordonnee> coordonneeAdjacentes = new ArrayList<>();
		int nbCaseDensite = nbCaseDensiteObstacle;

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
   
    /* Fonctionne pas
    private List<Coordonnee> getCoordonneesAdjacentes(Coordonnee coordonnee, int nbCaseDensiteObstacle) {
    	int nbCase = nbCaseDensiteObstacle;
    	List<Coordonnee> coordonneeAdjacentes = new ArrayList<>();
    	List<Coordonnee> coordonneesToCheck = new ArrayList<>(List.of(coordonnee));
    	for (int i = 0; i <= nbCase; i++) {
    		List<Coordonnee> tempCoordonneesToCheck = new ArrayList<>();
    		for (Coordonnee coordonneeToCheck : coordonneesToCheck) {
    			tempCoordonneesToCheck.addAll(getCoordonneeAdjacentes(coordonneeToCheck));
    		}
    		coordonneeAdjacentes.addAll(coordonneesToCheck);
    		coordonneesToCheck.addAll(tempCoordonneesToCheck);
    		coordonneesToCheck.removeAll(coordonneeAdjacentes);
    	}
    	coordonneeAdjacentes.remove(coordonnee);
    	return coordonneeAdjacentes;
	}
	*/    
    private List<Coordonnee> getCoordonneeAdjacentes(Coordonnee coordonnee) {
    	List<Coordonnee> coordonnees = new ArrayList<>();
    	List<Coordonnee> directions = new ArrayList<>(List.of(new Coordonnee(0,1), new Coordonnee(-1,0), new Coordonnee(0,-1), new Coordonnee(1,0)));
    	for (Coordonnee direction : directions) {
    		Coordonnee coordonneeAdjacente = new Coordonnee(coordonnee.getLigne() + direction.getLigne(), coordonnee.getColonne() + direction.getColonne());
    		Case caseAdjacente = grille.getCase(coordonneeAdjacente);
			if (caseAdjacente != null){
				coordonnees.add(coordonneeAdjacente);
			}
    	}
    	return coordonnees;
    }

    private void augmenterProbabilite(List<Coordonnee> coordonnees, int densite) {
        for (Coordonnee coordonnee : coordonnees) {
            Double probaActuelle = mapProbaCoordonnee.getProbabiliteFromCoordonnee(coordonnee);
            if (probaActuelle != null) {
                double nouvelleProbabilite = probaActuelle * (1 + (densite / 100.0));
                
                mapProbaCoordonnee.supprimerCoordonnee(coordonnee);
                mapProbaCoordonnee.ajouterProbabilite(nouvelleProbabilite, coordonnee);
            }
        }
    }
    
    private void remplissageTrou() {
    	int nbLigne = getGrille().getNbLigne();
    	int nbColonne = getGrille().getNbColonne();
    	for (int i = 0; i < nbLigne ;i++) {
    		for (int j = 0; j < nbColonne ;j++) {
    			Coordonnee coordonnee = new Coordonnee(i,j);
    			List<Coordonnee> Coordonnees = getCoordonneeAdjacentes(coordonnee);
    			Case CaseActuel = grille.getCase(coordonnee);
    			if ((CaseActuel.getObstacle().equals(GameConfiguration.PLAINE) || CaseActuel.getObstacle().equals(GameConfiguration.ARBRE))){
	    			if (caseEntoure(Coordonnees)) {
	    				if (caseEntoureLac(Coordonnees)) {
	    					CaseActuel.setObstacle(GameConfiguration.LAC);
	    				}else {
	    					CaseActuel.setObstacle(GameConfiguration.ROCHE);
	    				}
	    			}
    			}
        	}
    	}
    }
    
    private boolean caseEntoure(List<Coordonnee> coordonneesAdjacentes){
    	for (Coordonnee coordonnee : coordonneesAdjacentes) {
    		Case caseAdjacente = grille.getCase(coordonnee);
    		if (caseAdjacente != null && (caseAdjacente.getObstacle().equals(GameConfiguration.PLAINE) || caseAdjacente.getObstacle().equals(GameConfiguration.ARBRE))){
    			return false;
    		}
    	}
    	return true;
    }
    
    private boolean caseEntoureLac(List<Coordonnee> coordonneesAdjacentes){
    	int nbLac = 0;
    	for (Coordonnee coordonnee : coordonneesAdjacentes) {
    		Case caseAdjacente = grille.getCase(coordonnee);
    		if (caseAdjacente != null && caseAdjacente.getObstacle().equals(GameConfiguration.LAC)) {
    			nbLac += 1;
    		}
    	}
    	if (nbLac >= 2) {
        	return true;
    	}
    	return false;
    }

	private static double getValeurAleatoire(double value) {
	    return (double) Math.random() * value;
	}
	
	public Grille getGrille() {
		return this.grille;
	}
}