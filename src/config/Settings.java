package config;

public class Settings {

	public static final int DENSITE_LAC_MIN = 20000;
	public static final int DENSITE_LAC_MAX = 20000;
	public static final int NB_CASE_DENSITE_LAC = 2;
	
	public static final int DENSITE_ROCHE_MIN = 250;
	public static final int DENSITE_ROCHE_MAX = 500;
	public static final int NB_CASE_DENSITE_ROCHE = 3;
	
	public static final int DENSITE_ARBRE_MIN = 1;
	public static final int DENSITE_ARBRE_MAX = 1;
	public static final int NB_CASE_DENSITE_ARBRE = 0;
	
    public final static String INTRUS_DEFAUT_DEPLACEMENT = "Aleatoire";
    public final static String GARDIEN_DEFAUT_DEPLACEMENT = "Poursuite";
	
	public final static int NB_LARGEUR_MAX = 50;
	public final static int NB_LARGEUR_MIN = 15;
	public final static int NB_HAUTEUR_MAX = 50;
	public final static int NB_HAUTEUR_MIN = 15;

	public final static int NB_INTRUS_MAX = 30;
	public final static int NB_INTRUS_MIN = 1;
	public final static int NB_GARDIEN_MAX = 10;
	public final static int NB_GARDIEN_MIN = 1;
	
	public final static int NB_VISION_MAX = 10;
	public final static int NB_VISION_MIN = 1;
	
	public final static int SPEED_LENT_VALUE = 800;
	public final static int SPEED_NORMAL_VALUE = 450;
	public final static int SPEED_RAPIDE_VALUE = 100;
	
	public final static String SPEED_LENT = "Lent";
	public final static String SPEED_NORMAL = "Normal";
	public final static String SPEED_RAPIDE = "Rapide";
	
	private int largeur, hauteur, intrus, gardien, vision;
	private int cases_lacs_max, cases_lacs_min, cases_arbres_min, cases_arbres_max, cases_roches_min, cases_roches_max;
	private int elements_lacs_max, elements_lacs_min, elements_arbres_max, elements_arbres_min, elements_roches_max, elements_roches_min;
	private Boolean apparitionIntrus, communicationGardien;
	private int speed, zoom;
	
	private int block_size;
	
	public Settings() {
        defautSettings();
    }
	
    public void resetToDefault() {
        defautSettings();
    }
	
	public void setDebutant() {
		setDifficulte(20,20,2,2,5);
	}
	
	public void setIntermediaire() {
		setDifficulte(30,30,5,2,5);
	}
	
	public void setDifficile() {
		setDifficulte(35,35,10,3,5);
	}
	
	public void setExtraterestre() {
		setDifficulte(40,40,15,5,8);
	}
	
	private void setDifficulte(int largeur, int hauteur, int intrus, int gardien, int vision) {
		setLargeur(largeur);
		setHauteur(hauteur);
		setIntrus(intrus);
		setGardien(gardien);
		setVision(vision);
	}
	
	public void defautSettings() {
		setIntermediaire();
		setLimitsObstacles(largeur, hauteur);
		setCommunicationGardien(false);
		setApparitionIntrus(false);
		setSpeed(SPEED_NORMAL_VALUE);
		block_size = 25;
	}
	
	public void setLimitsObstacles(int largeur, int hauteur) {
		int totalCases = largeur*hauteur;

		cases_lacs_min = totalCases/40;
		cases_lacs_max = totalCases/30;
		cases_arbres_min = totalCases/17;
		cases_arbres_max = totalCases/10;
		cases_roches_min = totalCases/70;
		cases_roches_max = totalCases/20;

		elements_lacs_min = totalCases/400;
		elements_lacs_max = totalCases/300;
		elements_arbres_min = 1;
		elements_arbres_max = 1;
		elements_roches_min = totalCases/400;
		elements_roches_max = totalCases/200;
	}
	
	public void setLargeur(int largeur) {
	    if (largeur < NB_LARGEUR_MIN) {
	        this.largeur = NB_LARGEUR_MIN;
	    } else if (largeur > NB_LARGEUR_MAX) {
	        this.largeur = NB_LARGEUR_MAX;
	    } else {
	        this.largeur = largeur;
	    }
	}

	public void setHauteur(int hauteur) {
	    if (hauteur < NB_HAUTEUR_MIN) {
	        this.hauteur = NB_HAUTEUR_MIN;
	    } else if (hauteur > NB_HAUTEUR_MAX) {
	        this.hauteur = NB_HAUTEUR_MAX;
	    } else {
	        this.hauteur = hauteur;
	    }
	}

	public void setIntrus(int intrus) {
	    if (intrus < NB_INTRUS_MIN) {
	        this.intrus = NB_INTRUS_MIN;
	    } else if (intrus > NB_INTRUS_MAX) {
	        this.intrus = NB_INTRUS_MAX;
	    } else {
	        this.intrus = intrus;
	    }
	}

	public void setGardien(int gardien) {
	    if (gardien < NB_GARDIEN_MIN) {
	        this.gardien = NB_GARDIEN_MIN;
	    } else if (gardien > NB_GARDIEN_MAX) {
	        this.gardien = NB_GARDIEN_MAX;
	    } else {
	        this.gardien = gardien;
	    }
	}

	public void setVision(int vision) {
	    if (vision < NB_VISION_MIN) {
	        this.vision = NB_VISION_MIN;
	    } else if (vision > NB_VISION_MAX) {
	        this.vision = NB_VISION_MAX;
	    } else {
	        this.vision = vision;
	    }
	}

	public int getCases_lacs_max() {
		return cases_lacs_max;
	}

	public void setCases_lacs_max(int cases_lacs_max) {
		this.cases_lacs_max = cases_lacs_max;
	}

	public int getCases_lacs_min() {
		return cases_lacs_min;
	}

	public void setCases_lacs_min(int cases_lacs_min) {
		this.cases_lacs_min = cases_lacs_min;
	}

	public int getCases_arbres_min() {
		return cases_arbres_min;
	}

	public void setCases_arbres_min(int cases_arbres_min) {
		this.cases_arbres_min = cases_arbres_min;
	}

	public int getCases_arbres_max() {
		return cases_arbres_max;
	}

	public void setCases_arbres_max(int cases_arbres_max) {
		this.cases_arbres_max = cases_arbres_max;
	}

	public int getCases_roches_min() {
		return cases_roches_min;
	}

	public void setCases_roches_min(int cases_roches_min) {
		this.cases_roches_min = cases_roches_min;
	}

	public int getCases_roches_max() {
		return cases_roches_max;
	}

	public void setCases_roches_max(int cases_roches_max) {
		this.cases_roches_max = cases_roches_max;
	}

	public int getElements_lacs_max() {
		return elements_lacs_max;
	}

	public void setElements_lacs_max(int elements_lacs_max) {
		this.elements_lacs_max = elements_lacs_max;
	}

	public int getElements_lacs_min() {
		return elements_lacs_min;
	}

	public void setElements_lacs_min(int elements_lacs_min) {
		this.elements_lacs_min = elements_lacs_min;
	}

	public int getElements_arbres_max() {
		return elements_arbres_max;
	}

	public void setElements_arbres_max(int elements_arbres_max) {
		this.elements_arbres_max = elements_arbres_max;
	}

	public int getElements_arbres_min() {
		return elements_arbres_min;
	}

	public void setElements_arbres_min(int elements_arbres_min) {
		this.elements_arbres_min = elements_arbres_min;
	}

	public int getElements_roches_max() {
		return elements_roches_max;
	}

	public void setElements_roches_max(int elements_roches_max) {
		this.elements_roches_max = elements_roches_max;
	}

	public int getElements_roches_min() {
		return elements_roches_min;
	}

	public void setElements_roches_min(int elements_roches_min) {
		this.elements_roches_min = elements_roches_min;
	}

	public Boolean getApparitionIntrus() {
		return apparitionIntrus;
	}

	public void setApparitionIntrus(Boolean apparitionIntrus) {
		this.apparitionIntrus = apparitionIntrus;
	}

	public Boolean getCommunicationGardien() {
		return communicationGardien;
	}

	public void setCommunicationGardien(Boolean communicationGardien) {
		this.communicationGardien = communicationGardien;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public int getLargeur() {
		return largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public int getIntrus() {
		return intrus;
	}

	public int getGardien() {
		return gardien;
	}

	public int getVision() {
		return vision;
	}

	public int getBlock_size() {
		return block_size;
	}

	public int getWindow_width() {
		return block_size*largeur;
	}

	public int getWindow_height() {
		return block_size*hauteur;
	}
}