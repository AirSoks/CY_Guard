package engine.personnage.deplacement;

import engine.personnage.Personnage;

/**
 * Cette interface défini le déplacement des personnages
 * 
 * @author GLP_19
 * @see StrategieDeplacement
 * @see Personnage
 */
public interface Deplacement {
	
	/**
	 * Déplace le personnage
	 * 
	 * @param personnage Le personnage à déplacer
	 */
	void deplacer(Personnage personnage);
	
}