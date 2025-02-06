package engine.map;

import config.GameConfiguration;
import engine.map.obstacle.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class GrilleBuilder {
	private Grille grille;
	private List<ObstacleBuilder> obstacleBuilders;
	
    public GrilleBuilder() {
        this.grille = new Grille(GameConfiguration.NB_LIGNE, GameConfiguration.NB_COLONNE);
        this.obstacleBuilders = new ArrayList<>();
        initObstacleBuilders();
        genererObstacles();
    }
    
    private void initObstacleBuilders() {
        obstacleBuilders.add(new ObstacleBuilder(GameConfiguration.LAC, GameConfiguration.DENSITE_LAC, GameConfiguration.NB_LAC, GameConfiguration.NB_CASE_DENSITE_LAC,2));
        //obstacleBuilders.add(new ObstacleBuilder(GameConfiguration.ROCHE, GameConfiguration.DENSITE_ROCHE, GameConfiguration.NB_ROCHE, GameConfiguration.NB_CASE_DENSITE_ROCHE));
        //obstacleBuilders.add(new ObstacleBuilder(GameConfiguration.ARBRE, GameConfiguration.DENSITE_ARBRE, GameConfiguration.NB_ARBRE, GameConfiguration.NB_CASE_DENSITE_ARBRE));
    }
	
	private void genererObstacles() {
		for (ObstacleBuilder builder : obstacleBuilders) {
            placerObstacles(builder);
        }
        remplissageTrou();
	}
	
	private void placerObstacles(ObstacleBuilder builder) {
		MapProbaCoordonnee mapProbaCoordonnee = builder.getMapProbaCoordonnee();
		int nbElement = builder.getNbElement();
		List<Coordonnee> coordonnees = getListCoordonneeGrille();
		if (nbElement == 0) {
			mapProbaCoordonnee.ajouterProbabilite(100.0 / coordonnees.size(), coordonnees);
        	System.out.println(mapProbaCoordonnee.getListeProbabilites());
		} else {
			mapProbaCoordonnee.ajouterProbabilite(0.0 , coordonnees);
			initNbElement(builder);
		}
		
		int nbObstacle = builder.getNbObstacle();
		Obstacle obstacle =  builder.getObstacle();
		int densite = builder.getDensite();
		int nbCaseDensite = builder.getNbCaseDensiteObstacle();
		int obstaclesPlaces = 0;
		
        while (obstaclesPlaces < 50) {
    		// On prend une valeur aléatoire
    		Coordonnee coordonneeAleatoire = mapProbaCoordonnee.getCoordonneeAleatoire(mapProbaCoordonnee.getListeAleatoire());
    		if (coordonneeAleatoire != null && grille.getCase(coordonneeAleatoire) != null) {
    			
    			// On change la case avec le nouvelle obstacle et on supprime la coordonnée de la map
    			grille.getCase(coordonneeAleatoire).setObstacle(obstacle);
    			mapProbaCoordonnee.supprimerCoordonnee(coordonneeAleatoire);
    			
    			// On change la probabilité des cases adjacentes
    			if (nbElement == 0) {
        			List<Coordonnee> coordonneeAdjacentes = getCoordonneeAdjacentes(coordonneeAleatoire, nbCaseDensite);
    				augmenterProbabilite(mapProbaCoordonnee, coordonneeAdjacentes, densite);
    			}
    			else {
        			List<Coordonnee> coordonneeAdjacentes = getCoordonneeAdjacentes(coordonneeAleatoire);
    				augmenterProbabilite(mapProbaCoordonnee, coordonneeAdjacentes, densite);
    			}
                obstaclesPlaces++;
        	}
        }
    }
	
	private void initNbElement(ObstacleBuilder builder) {
		MapProbaCoordonnee mapProbaCoordonnee = builder.getMapProbaCoordonnee();
		int nbElement = builder.getNbElement();
		Obstacle obstacle =  builder.getObstacle();
		int nbCaseDensite = builder.getNbCaseDensiteObstacle();
		
		int nbElementPlaces = 0;
		while (nbElementPlaces < nbElement) {
    		// On prend une valeur aléatoire
    		Coordonnee coordonneeAleatoire = mapProbaCoordonnee.getCoordonneeAleatoire(mapProbaCoordonnee.getCoordonneesFromProbabilite(0.0));
    		if (coordonneeAleatoire != null && grille.getCase(coordonneeAleatoire) != null) {
    			
    			// On change la case avec le nouvelle obstacle et on supprime la coordonnée de la map
    			grille.getCase(coordonneeAleatoire).setObstacle(obstacle);
    			mapProbaCoordonnee.supprimerCoordonnee(coordonneeAleatoire);
    			
    			// On change la probabilité des cases adjacentes
    			List<Coordonnee> coordonneeAdjacentes = getCoordonneeAdjacentes(coordonneeAleatoire, nbCaseDensite);
    			augmenterProbabilite(mapProbaCoordonnee, coordonneeAdjacentes, 1.0);
    			nbElementPlaces++;
        	}
        }
	}
	
	private List<Coordonnee> getListCoordonneeGrille() {
        List<Coordonnee> coordonnees = new ArrayList<>();
        for (int i = 0; i < GameConfiguration.NB_LIGNE; i++) {
            for (int j = 0; j < GameConfiguration.NB_COLONNE; j++) {
                Coordonnee position = new Coordonnee(i, j);
                if (grille.getCase(position).getObstacle().equals(GameConfiguration.PLAINE)) {
                    coordonnees.add(position);
                }
            }
        }
        return coordonnees;
        
        //this.probabiliteDefaut = 100.0 / coordonnees.size();
        //(this.probabiliteDefaut, coordonnees);
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

    private void augmenterProbabilite(MapProbaCoordonnee mapProbaCoordonnee, List<Coordonnee> coordonnees, int densite) {
        for (Coordonnee coordonnee : coordonnees) {
            Double probaActuelle = mapProbaCoordonnee.getProbabiliteFromCoordonnee(coordonnee);
            if (probaActuelle != null) {
                double nouvelleProbabilite = (probaActuelle + 1 ) * (densite / 100.0);
                
                mapProbaCoordonnee.supprimerCoordonnee(coordonnee);
                mapProbaCoordonnee.ajouterProbabilite(nouvelleProbabilite, coordonnee);
            }
        }
    }
    
    private void augmenterProbabilite(MapProbaCoordonnee mapProbaCoordonnee, List<Coordonnee> coordonnees, double nouvelleProbabilite) {
        for (Coordonnee coordonnee : coordonnees) {
            mapProbaCoordonnee.supprimerCoordonnee(coordonnee);
            mapProbaCoordonnee.ajouterProbabilite(nouvelleProbabilite, coordonnee);
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
	
	public Grille getGrille() {
		return this.grille;
	}
}