package engine.personnage;

import java.util.ArrayList;
import java.util.List;

import config.Settings;
import engine.map.Coordonnee;
import engine.map.Grille;
import engine.personnage.deplacement.DeplacementFactory;
import engine.personnage.vision.Vision;
import engine.utilitaire.MaxTentativeAtteind;

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
	
	private Settings settings;
	
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
    
    /**
     * Les gardien et intrus initial de la simulation
     */
    private int nbIntrusInitial, nbGardienInitial;

	public static void initInstance(Grille grille, Settings settings) {
        instance = new PersonnageManager(grille, settings);
    }
	
    public static PersonnageManager getInstance() {
    	if (instance == null) {
			throw new IllegalStateException("PersonnageManager non initialisée");
		}
		return instance;
	}

	private PersonnageManager(Grille grille, Settings settings) {
		this.settings = settings;
		this.grille = grille;
	}

	public void retirerPersonnage(Personnage personnage) {
		if (personnage != null && personnages.contains(personnage)) {
	        this.personnages.remove(personnage);
	    }
	}

    public Gardien getGardienActif() {
		return gardienActif;
	}

	public void setGardienActif(Gardien newGardienActif){
		if (newGardienActif == null) {
			return;
		}
		removeGardienActif();
		newGardienActif.setDeplacement(DeplacementFactory.getDeplacement("Manuel", this, grille));
		newGardienActif.setDirection(null);
		gardienActif = newGardienActif;
	}
	
	public void removeGardienActif() {
		if (this.gardienActif != null) {
			setDefautDeplacement(gardienActif);
		}
		this.gardienActif = null;
	}
	
	
	public void initPersonnages() {
		this.gardienActif = null;
		if (personnages != null) {
			personnages = new ArrayList<>();
		}
	    ajouterGardien(settings.getGardien());
	    ajouterIntrus(settings.getIntrus());
	}
	
	/**
	 * Déplace tout les personnages de la grille
	 */
	public void actionPersonnages() {
		deplacerIntrus();
		deplacerGardiens();
		observerIntrus();
		observerGardiens();
		reSpawnIntrus();
    }
	
	private void deplacerIntrus() {
		for (Intrus intrus : getIntrus()) {
        	if (intrus != null) {
        		intrus.deplacer();
        		intrus.observer();
        	}
        }
	}
	
	private void observerIntrus() {
		for (Intrus intrus : getIntrus()) {
        	if (intrus != null) {
        		intrus.observer();
        	}
        }
	}
	
	private void deplacerGardiens() {
		for (Gardien gardien : getGardiens()) {
        	if (gardien != null) {
            	gardien.deplacer();
        	}
        }
	}
	
	private void observerGardiens() {
		for (Gardien gardien : getGardiens()) {
        	if (gardien != null) {
        		gardien.observer();
        	}
        }
	}
	
	private void reSpawnIntrus() {
		if (!settings.getApparitionIntrus()) {
			return;
		}
		List<Intrus> nbIntrus = getIntrus();
		if (nbIntrusInitial > nbIntrus.size()) {
			ajouterIntrus();
		}
	}

    public List<Personnage> getPersonnages() {
        return new ArrayList<>(personnages);
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
    
    public List<Intrus> getIntrus() {
        return getIntrus(null);
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

    public List<Gardien> getGardiens() {
        return getGardiens(null);
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
     * Ajoute des gardiens sur la grille
     * 
     * @param nombreGardien Le nombre de gardiens à ajouter
     * @return Le gardien
     */
    public List<Gardien> ajouterGardien(int nombreGardien) {
    	nbGardienInitial = nombreGardien;
    	List<Gardien> listGardiens = new ArrayList<>();
    	for (int i = 0; i < nombreGardien ; i++) {
    		Gardien gardien = ajouterGardien();
    		if (gardien != null ) {
    			listGardiens.add(gardien);
    		}
    	}
		return listGardiens;
	}
	
    /**
     * Ajoute un gardien sur la grille
     * 
     * @return Le gardien
     */
    public Gardien ajouterGardien() {
        Coordonnee coordonnee;
        try {
            coordonnee = grille.getCoordonneeAleatoireValide("DEPLACEMENT");
        } catch (MaxTentativeAtteind e) {
            return null;
        }
		Gardien gardien = new Gardien(coordonnee);
		setDefautDeplacement(gardien);
		Vision vision = getVision();
		gardien.setVision(vision);
		personnages.add(gardien);
		return gardien;
	}
    
    /**
     * Ajoute des intrus sur la grille
     * 
     * @param nombreGardien Le nombre de gardiens à ajouter
     * @return Le gardien
     */
    public List<Intrus> ajouterIntrus(int nombreIntrus) {
    	nbIntrusInitial = nombreIntrus;
    	List<Intrus> listIntrus = new ArrayList<>();
    	for (int i = 0; i < nombreIntrus ; i++) {
    		Intrus intrus = ajouterIntrus();
    		if (intrus != null ) {
    			listIntrus.add(intrus);
    		}
    	}
		return listIntrus;
	}
    
    /**
     * Ajoute un intrus sur la grille
     * 
     * @return L'intrus
     */
	public Intrus ajouterIntrus() {
        Coordonnee coordonnee;
        try {
            coordonnee = grille.getCoordonneeAleatoireValide("DEPLACEMENT");
        } catch (MaxTentativeAtteind e) {
            return null;
        }
		Intrus intrus = new Intrus(coordonnee);
		setDefautDeplacement(intrus);
		Vision vision = getVision();
		intrus.setVision(vision);
		personnages.add(intrus);
		return intrus;
	}
	
	public void setDefautDeplacement(Personnage personnage) {
	    if (personnage == null) { return; }
	    
	    if (personnage instanceof Gardien) {
	        personnage.setDeplacement(DeplacementFactory.getDeplacement(Settings.GARDIEN_DEFAUT_DEPLACEMENT, this, grille));
	    } else if (personnage instanceof Intrus) {
	        personnage.setDeplacement(DeplacementFactory.getDeplacement(Settings.INTRUS_DEFAUT_DEPLACEMENT, this, grille));
	    }
	}
	
	private Vision getVision() {
		Vision.initInstance(this, grille, settings.getVision());
		Vision vision = Vision.getInstance();
		return vision;
	}
}