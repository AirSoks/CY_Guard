package engine.personnage;

import java.util.ArrayList;
import java.util.List;

import config.GameConfiguration;
import engine.map.Coordonnee;
import engine.map.Grille;
import engine.personnage.deplacement.DeplacementFactory;
import engine.personnage.vision.Vision;

/**
 * Cette classe sert à la gestion des personnages
 * 
 * @author GLP_19
 * @see Personnage
 * @see Intrus
 * @see Gardien
 * @see Grille
 */
public class PersonnageManager {
	
	/**
	 * Utilisation d'un singleton
	 */
	private static PersonnageManager instance;
    
    /**
     * La liste des personnages présent dans le jeu
     */
    private List<Personnage> personnages = new ArrayList<>();
    
    /**
     * La grille du jeu
     */
    private Grille grille;
    
    /**
     * Le gardien actif, qui peut être controlé par le joueur
     */
    private Gardien gardienActif;

	public static void initInstance(Grille grille) {
        instance = new PersonnageManager(grille);
    }
	
    public static PersonnageManager getInstance() {
    	if (instance == null) {
			throw new IllegalStateException("PersonnageManager non initialisée");
		}
		return instance;
	}

	private PersonnageManager(Grille grille) {
		this.grille = grille;
	}

	public void retirerPersonnage(Personnage personnage) {
        this.personnages.remove(personnage);
    }

    public Gardien getGardienActif() {
		return gardienActif;
	}

	public void setGardienActif(Gardien newGardienActif) {
		if (this.gardienActif != null) {
			this.gardienActif.setDeplacement(DeplacementFactory.getDeplacement("Aleatoire", this, grille));
		}
		
		this.gardienActif = newGardienActif;
		this.gardienActif.setDeplacement(DeplacementFactory.getDeplacement("Manuel", this, grille));
	}

    public List<Personnage> getPersonnages() {
        return new ArrayList<>(personnages);
    }
    
    public List<Intrus> getIntrus() {
        return getIntrus(null);
    }

    public List<Gardien> getGardiens() {
        return getGardiens(null);
    }

    public List<Personnage> getPersonnages(Coordonnee coordonnee) {
        List<Personnage> personnages = new ArrayList<>();
        for (Personnage personnage : this.personnages) {
            if (coordonnee == null || personnage.getCoordonnee().equals(coordonnee)) {
            	personnages.add(personnage);
            }
        }
        return personnages;
    }

    public List<Intrus> getIntrus(Coordonnee coordonnee) {
        List<Intrus> intrus = new ArrayList<>();
        for (Personnage personnage : getPersonnages(coordonnee)) {
            if (personnage instanceof Intrus) {
                intrus.add((Intrus) personnage);
            }
        }
        return intrus;
    }

    public List<Gardien> getGardiens(Coordonnee coordonnee) {
        List<Gardien> gardiens = new ArrayList<>();
        for (Personnage personnage : getPersonnages(coordonnee)) {
            if (personnage instanceof Gardien) {
                gardiens.add((Gardien) personnage);
            }
        }
        return gardiens;
    }
	
	/**
	 * Déplace tout les personnages de la grille
	 */
	public void deplacerPersonnages() {
        for (Gardien gardien : getGardiens()) {
        	if (gardien != null) {
        		gardien.observer();
            	gardien.deplacer();
        	}
        }
        for (Intrus intrus : getIntrus()) {
        	if (intrus != null) {
        		intrus.observer();
        		intrus.deplacer();
        	}
        }
    }
	
    /**
     * Ajoute un gardien sur la grille
     * 
     * @return Le gardien
     */
    public Gardien ajouterGardien() {
		Coordonnee coordonnee = grille.getCoordonneeAleatoireValide("DEPLACEMENT");
		Gardien gardien = new Gardien(coordonnee);
		gardien.setDeplacement(DeplacementFactory.getDeplacement("Poursuite", this, grille));
		Vision vision = new Vision(this, grille, GameConfiguration.NB_CASES_VISION);
		gardien.setVision(vision);
		personnages.add(gardien);
		return gardien;
	}
    
    /**
     * Ajoute un intrus sur la grille
     * 
     * @return L'intrus
     */
	public Intrus ajouterIntrus() {
		Coordonnee coordonnee = grille.getCoordonneeAleatoireValide("DEPLACEMENT");
		Intrus intrus = new Intrus(coordonnee);
		intrus.setDeplacement(DeplacementFactory.getDeplacement("Aleatoire", this, grille));
		Vision vision = new Vision(this, grille, GameConfiguration.NB_CASES_VISION);
		intrus.setVision(vision);
		personnages.add(intrus);
		return intrus;
	}
}