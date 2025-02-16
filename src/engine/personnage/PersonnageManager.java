package engine.personnage;

import java.util.ArrayList;
import java.util.List;

import engine.map.Case;
import engine.map.Coordonnee;
import engine.map.Grille;
import engine.personnage.deplacement.DeplacementAleatoire;
import engine.personnage.deplacement.DeplacementManuel;
import engine.utilitaire.MaximumTentativeAtteind;

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
    
    public static PersonnageManager getInstance(Grille grille) {
		if (instance == null) {
			instance = new PersonnageManager(grille);
		}
		return instance;
	}

	public PersonnageManager(Grille grille) {
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
			DeplacementAleatoire deplacementAleatoire = new DeplacementAleatoire(this, grille);
			this.gardienActif.setDeplacement(deplacementAleatoire);
		}
		
		this.gardienActif = newGardienActif;
		DeplacementManuel deplacementManuel = new DeplacementManuel(this, grille);
		this.gardienActif.setDeplacement(deplacementManuel);
	}

    public List<Personnage> getPersonnages() {
        return this.personnages;
    }
    
    public List<Intrus> getIntrus() {
        return getIntrus(null);
    }

    public List<Gardien> getGardiens() {
        return getGardiens(null);
    }

    public List<Personnage> getPersonnages(Coordonnee coordonnee) {
        List<Personnage> result = new ArrayList<>();
        for (Personnage personnage : this.personnages) {
            if (coordonnee == null || personnage.getCoordonnee().equals(coordonnee)) {
                result.add(personnage);
            }
        }
        return result;
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
            	gardien.deplacer();
        	}
        }
        for (Intrus intrus : getIntrus()) {
        	if (intrus != null) {
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
		Coordonnee coordonnee = getCoordonneeAleatoireValide();
		Gardien gardien = new Gardien(coordonnee);
		DeplacementAleatoire deplacement = new DeplacementAleatoire(this, grille);
		gardien.setDeplacement(deplacement);
		personnages.add(gardien);
		return gardien;
	}
    
    /**
     * Ajoute un intrus sur la grille
     * 
     * @return L'intrus
     */
	public Intrus ajouterIntrus() {
		Coordonnee coordonnee = getCoordonneeAleatoireValide();
		Intrus intrus = new Intrus(coordonnee);
		DeplacementAleatoire deplacement = new DeplacementAleatoire(this, grille);
		intrus.setDeplacement(deplacement);
		personnages.add(intrus);
		return intrus;
	}
	
	/**
	 * Récupère une coordonnée aléatoire valide sur la grille
	 * 
	 * @return Une coordonnées aléatoire valide
	 */
	private Coordonnee getCoordonneeAleatoireValide() {
	    int tentativeMax = 2*grille.getNbLigne()*grille.getNbColonne();
	    for (int i = 0; i < tentativeMax; i++) {
	        Coordonnee coordonnee = getCoordonneeAleatoire(grille.getNbLigne(), grille.getNbColonne());
	        if (isCoordonneeValide(coordonnee)) {
	            return coordonnee;
	        }
	    }
	    throw new MaximumTentativeAtteind(tentativeMax);
	}

	private Coordonnee getCoordonneeAleatoire(int nbLigne, int nbColonne) {
	    int ligneAleatoire =  (int) (Math.random() * nbLigne);
	    int colonneAleatoire =  (int) (Math.random() * nbColonne);
	    return new Coordonnee(ligneAleatoire, colonneAleatoire);
	}
	
	/**
	 * Verifie si la coordonnée est valide pour y placer un personnage
	 * 
	 * @param coordonnee La coordonnée à vérifier
	 * @return true si la coordonnée ne bloque pas l'apparition, false sinon
	 */
	private Boolean isCoordonneeValide(Coordonnee coordonnee) {
		Case c = grille.getCase(coordonnee);
		if (c != null && !c.getObstacle().isBloqueDeplacement()) {
			return true;
		}
		return false;
	}
}