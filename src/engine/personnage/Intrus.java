package engine.personnage;

import config.GameConfiguration;
import engine.map.Coordonnee;

/**
 * Intrus est une classe représentant un personnage intrus
 * 
 * @author GLP_19
 * @see Personnage
 */
public class Intrus extends Personnage {

	public Intrus(Coordonnee coordonnee) {
		super(coordonnee);
		setVitesse(GameConfiguration.VITESSE_INTRUS);
	}
}