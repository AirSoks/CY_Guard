package engine.map.generation;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import config.ConfigurationMapAleatoire;
import config.Settings;
import engine.map.Case;
import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
import engine.map.obstacle.Lac;
import engine.map.obstacle.Obstacle;
import engine.map.obstacle.ObstacleFactory;
import engine.map.obstacle.Plaine;
import engine.utilitaire.MaxTentativeAtteind;
import log.LoggerUtility;

/**
 * Cette classe représente la construction de la grille
 *
 * @author GLP_19
 * @see MapProbaCoordonnee
 * @see Grile
 * @see ObstacleBuilder
 */
public class GrilleBuilder {
	
	private static Logger logger = LoggerUtility.getLogger(GrilleBuilder.class, "html");
	
	/**
	 * La grille qui doit être construite
	 */
	private Grille grille;
	
	/**
     * Les settings de la simulation
     */
	private Settings settings;
	
	/**
	 * Les obstacles à construire avec leur spécificité
	 */
	private List<ObstacleBuilder> obstacleBuilders;
	
	public GrilleBuilder(int nbLigne, int nbColonne, Settings settings) {
        this.settings = settings;
        this.grille = new Grille(nbLigne, nbColonne);
        logger.info("Construction de la grille de jeu (hauteur=" + settings.getHauteur() + ", largeur=" + settings.getLargeur() + ")");
    }

    public Grille build() {
    	logger.info("Début de la construction de la grille");
        this.grille.genererTerrain();
        logger.debug("Terrain généré : " + grille.getNbLigne() + "×" + grille.getNbColonne());
        this.obstacleBuilders = ConfigurationMapAleatoire.genererObstaclesAleatoires(settings);
        genererObstacles();
        logger.info("Construction de la grille terminée");
        return grille;
    }

    public void redimensionner(int nouvelleLigne, int nouvelleColonne) {
    	logger.info("Redimensionnement de la grille vers " + nouvelleLigne + "×" + nouvelleColonne);
        this.grille.redimensionner(nouvelleLigne, nouvelleColonne);
    }

	private void genererObstacles() {
		logger.debug("Début de la génération des obstacles");
        for (ObstacleBuilder builder : obstacleBuilders) {
        	logger.debug("Placement de " + builder.getNbObstacle() + " obstacles de type " + builder.getObstacle().getType());
            placerObstacles(builder);
        }
        remplissageTrou();
        logger.debug("Fin de la génération des obstacles");
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
	    Obstacle obstacle = builder.getObstacle();
	    int densite = builder.getDensite();
	    int nbCaseDensite = builder.getNbCaseDensiteObstacle();
	    int obstaclesPlaces = 0;

	    int maxAttempts = grille.getNbLigne() * grille.getNbColonne() * 2;
	    int attempts = 0;

	    while (obstaclesPlaces < nbObstacle) {
	        attempts++;
	        
	        // Vérification du nombre de tentatives
	        if (attempts > maxAttempts) {
	            throw new MaxTentativeAtteind(maxAttempts);
	        }

	        Coordonnee coordonneeAleatoire = mapProbaCoordonnee.getCoordonneeAleatoire(mapProbaCoordonnee.getListeAleatoire());
	        if (grille.isCoordonneeValide(coordonneeAleatoire, "INGRILLE")) {
	            // Placement de l'obstacle
	            grille.getCase(coordonneeAleatoire).setObstacle(obstacle);
	            mapProbaCoordonnee.supprimerCoordonnee(coordonneeAleatoire);

	            // Mise à jour des probabilités adjacentes
	            List<Coordonnee> coordonneeAdjacentes = getCoordonneeAdjacentes(coordonneeAleatoire, nbCaseDensite);
	            augmenterProbabilite(mapProbaCoordonnee, coordonneeAdjacentes, densite);
	            
	            obstaclesPlaces++;
	            attempts = 0; // Réinitialisation du compteur après un placement réussi
	        }
	    }
	}

	private List<Coordonnee> getListCoordonneeGrille() {
        List<Coordonnee> coordonnees = new ArrayList<>();
        int nbLigne = grille.getNbLigne();
        int nbColonne = grille.getNbColonne();
        
        for (int i = 0; i < nbLigne; i++) {
            for (int j = 0; j < nbColonne; j++) {
                Coordonnee position = new Coordonnee(i, j);
                if (grille.getCase(position).getObstacle() instanceof Plaine) {
                    coordonnees.add(position);
                }
            }
        }
        return coordonnees;
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
     * Récupère les 4 coordonnées direct adjacentes
     * 
     * @param coordonnee La coordonnée à traiter
     * @return Une liste de coordonnée adjacente
     */
    private List<Coordonnee> getCoordonneeAdjacentes(Coordonnee coordonnee) {
    	List<Coordonnee> coordonnees = new ArrayList<>();
        
        for (Direction direction : Direction.values()) {
            Coordonnee coordonneeAdjacente = direction.getCoordonnee(coordonnee);
            Case caseAdjacente = getGrille().getCase(coordonneeAdjacente);
			if (caseAdjacente != null){
				coordonnees.add(coordonneeAdjacente);
			}
    	}
    	return coordonnees;
    }

    /**
     * Verifie si une case est entouré par des cases infranchissables
     * 
     * @param coordonneesAdjacentes Les coordonnées adjacentes à la coordonnée
     * @return true si la case est entouré, false sinon
     */
    private boolean caseEntoure(List<Coordonnee> coordonneesAdjacentes){
    	for (Coordonnee coordonnee : coordonneesAdjacentes) {
    		
    		if (grille.isCoordonneeValide(coordonnee, "DEPLACEMENT")){
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

	public Grille getGrille() {
		return grille;
	}
}