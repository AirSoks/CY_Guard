package engine.personnage.vision;

import config.GameConfiguration;
import engine.map.Grille;
import engine.personnage.PersonnageManager;

public class VisionFactory {

	private static Vision vision;
	
	public static Vision getVision(PersonnageManager personnageManager, Grille grille) {
		if (vision == null) {
			Vision.initInstance(personnageManager, grille, GameConfiguration.NB_CASES_VISION);
			vision = Vision.getInstance();
	    }
		return vision;
	}
}