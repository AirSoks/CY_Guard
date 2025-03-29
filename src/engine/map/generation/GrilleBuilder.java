package engine.map.generation;

import java.util.ArrayList;
import java.util.List;

import config.ConfigurationMapAleatoire;
import config.GameConfiguration;
import engine.map.Case;
import engine.map.Coordonnee;
import engine.map.Grille;
import engine.map.obstacle.Lac;
import engine.map.obstacle.Obstacle;
import engine.map.obstacle.ObstacleFactory;
import engine.map.obstacle.Plaine;

/**
 * Cette classe représente la construction de la grille
 *
 * @author GLP_19
 * @see MapProbaCoordonnee
 * @see Grile
 * @see ObstacleBuilder
 */
public class GrilleBuilder {
	
	/**
	 * La grille qui doit être construite
	 */
	private Grille grille;
	
	/**
	 * Les obstacles à construire avec leur spécificité
	 */
	private List<ObstacleBuilder> obstacleBuilders;

    private void initObstacleBuilders() {
    	this.obstacleBuilders = ConfigurationMapAleatoire.genererObstaclesAleatoires();
    }

	private void genererObstacles() {
		for (ObstacleBuilder builder : obstacleBuilders) {
            placerObstacles(builder);
        }
        remplissageTrou();
	}
	
    public GrilleBuilder() {
        this.grille = Grille.getInstance(GameConfiguration.NB_LIGNE, GameConfiguration.NB_COLONNE);
        this.obstacleBuilders = new ArrayList<>();
        initObstacleBuilders();
        genererObstacles();
    }

	public Grille getGrille() {
		return this.grille;
	}

	private List<Coordonnee> getListCoordonneeGrille() {
        List<Coordonnee> coordonnees = new ArrayList<>();
        for (int i = 0; i < GameConfiguration.NB_LIGNE; i++) {
            for (int j = 0; j < GameConfiguration.NB_COLONNE; j++) {
                Coordonnee position = new Coordonnee(i, j);
                if (grille.getCase(position).getObstacle() instanceof Plaine) {
                    coordonnees.add(position);
                }
            }
        }
        return coordonnees;
    }

	/**
	 * Place les obstacles sur la grille suivant les paramètres de chaque ObstacleBuilder
	 * On utilise aussi la classe MapProbaCoordonnee qui nous permet de les placer selon des probabilités
	 * 
	 * @param builder Les paramètres de l'obstacle à placer
	 */
	private void placerObstacles(ObstacleBuilder builder) {
		MapProbaCoordonnee mapProbaCoordonnee = builder.getMapProbaCoordonnee();
		List<Coordonnee> coordonnees = getListCoordonneeGrille();
		mapProbaCoordonnee.ajouterCoordonnes(100.0 / coordonnees.size(), coordonnees);

		int nbObstacle = builder.getNbObstacle();
		Obstacle obstacle =  builder.getObstacle();
		int densite = builder.getDensite();
		int nbCaseDensite = builder.getNbCaseDensiteObstacle();
		int obstaclesPlaces = 0;

        while (obstaclesPlaces < nbObstacle) {
    		// On prend une valeur aléatoire
    		Coordonnee coordonneeAleatoire = mapProbaCoordonnee.getCoordonneeAleatoire(mapProbaCoordonnee.getListeAleatoire());
    		if (coordonneeAleatoire != null && grille.getCase(coordonneeAleatoire) != null) {

    			// On change la case avec le nouvelle obstacle et on supprime la coordonnée de la map
    			grille.getCase(coordonneeAleatoire).setObstacle(obstacle);
    			mapProbaCoordonnee.supprimerCoordonnee(coordonneeAleatoire);


        		List<Coordonnee> coordonneeAdjacentes = getCoordonneeAdjacentes(coordonneeAleatoire, nbCaseDensite);
    			augmenterProbabilite(mapProbaCoordonnee, coordonneeAdjacentes, densite);
                obstaclesPlaces++;
        	}
        }
    }

    /**
     * Récupère les coordonnées (vide) adjacentes d'une coordonnée 
     * 
     * @param coordonnee La coordonnée à traiter
     * @param nbCaseDensiteObstacle Le nombre de case autour de la coordonnée à récupérer
     * @return Une liste de coordonnée adjacente
     */
    private List<Coordonnee> getCoordonneeAdjacentes(Coordonnee coordonnee, int nbCaseDensiteObstacle) {
		List<Coordonnee> coordonneeAdjacentes = new ArrayList<>();

		for (int i = -nbCaseDensiteObstacle; i <= nbCaseDensiteObstacle; i++) {
			for (int j = -nbCaseDensiteObstacle; j <= nbCaseDensiteObstacle; j++) {
				if (i == 0 && j == 0) { continue; }

				Coordonnee coordonneeAdjacente = new Coordonnee(coordonnee.getLigne() + i, coordonnee.getColonne() + j);
				Case caseAdjacente = grille.getCase(coordonneeAdjacente);
				if (caseAdjacente != null && caseAdjacente.getObstacle() instanceof Plaine){
					coordonneeAdjacentes.add(coordonneeAdjacente);
				}
            }
		}
		return coordonneeAdjacentes;
	}

    /**
     * Récupère les 4 coordonnées direct adjacentes
     * 
     * @param coordonnee La coordonnée à traiter
     * @return Une liste de coordonnée adjacente
     */
    private List<Coordonnee> getCoordonneeAdjacentes(Coordonnee coordonnee) {
    	List<Coordonnee> coordonnees = new ArrayList<>();
    	Coordonnee coord1 = new Coordonnee(0, 1);
        Coordonnee coord2 = new Coordonnee(-1, 0);
        Coordonnee coord3 = new Coordonnee(0, -1);
        Coordonnee coord4 = new Coordonnee(1, 0);
        List<Coordonnee> directions = new ArrayList<>();
        directions.add(coord1);
        directions.add(coord2);
        directions.add(coord3);
        directions.add(coord4);
    	for (Coordonnee direction : directions) {
    		Coordonnee coordonneeAdjacente = new Coordonnee(coordonnee.getLigne() + direction.getLigne(), coordonnee.getColonne() + direction.getColonne());
    		Case caseAdjacente = grille.getCase(coordonneeAdjacente);
			if (caseAdjacente != null){
				coordonnees.add(coordonneeAdjacente);
			}
    	}
    	return coordonnees;
    }

    /**
     * Augmente les probabilité d'une liste de coordonnée suivant la densité d'augmentattion
     * 
     * @param mapProbaCoordonnee La map de coordonnée utilisé pour l'obstacle
     * @param coordonnees Les coordonnées à augmenter
     * @param densite La densité à appliquer
     */
    private void augmenterProbabilite(MapProbaCoordonnee mapProbaCoordonnee, List<Coordonnee> coordonnees, int densite) {
        for (Coordonnee coordonnee : coordonnees) {
            Double probaActuelle = mapProbaCoordonnee.getProbabiliteFromCoordonnee(coordonnee);
            if (probaActuelle != null) {
                double nouvelleProbabilite = (probaActuelle) * (densite / 100.0);

                mapProbaCoordonnee.supprimerCoordonnee(coordonnee);
                mapProbaCoordonnee.ajouterCoordonne(nouvelleProbabilite, coordonnee);
            }
        }
    }

    /**
     * Rempli les case qui sont entouré de tout le côté par des obstacles infranchissables
     */
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
	    				CaseActuel.setObstacle(ObstacleFactory.getObstacle("Lac"));
	    			}else {
	    				CaseActuel.setObstacle(ObstacleFactory.getObstacle("Roche"));
	    			}
    			}
        	}
    	}
    }

    /**
     * Verifie si une case est entouré par des cases infranchissables
     * 
     * @param coordonneesAdjacentes Les coordonnées adjacentes à la coordonnée
     * @return true si la case est entouré, false sinon
     */
    private boolean caseEntoure(List<Coordonnee> coordonneesAdjacentes){
    	for (Coordonnee coordonnee : coordonneesAdjacentes) {
    		Case caseAdjacente = grille.getCase(coordonnee);
    		if (caseAdjacente != null && !caseAdjacente.getObstacle().isBloqueDeplacement()){
    			return false;
    		}
    	}
    	return true;
    }

    /**
     * Vérifie si la case est entouré par au moins 2 lac
     * 
     * @param coordonneesAdjacentes Les coordonnées adjacentes à la coordonnée
     * @return true si la case est entouré de lac, false sinon
     */
    private boolean caseEntoureLac(List<Coordonnee> coordonneesAdjacentes){
    	int nbLac = 0;
    	for (Coordonnee coordonnee : coordonneesAdjacentes) {
    		Case caseAdjacente = grille.getCase(coordonnee);
    		if (caseAdjacente != null && caseAdjacente.getObstacle() instanceof Lac) {
    			nbLac += 1;
    		}
    	}
    	if (nbLac >= 2) {
        	return true;
    	}
    	return false;
    }
}