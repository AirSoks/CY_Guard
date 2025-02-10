package engine.personnage;

import config.GameConfiguration;
import engine.map.Coordonnee;

public class Intrus extends Personnage {

	public Intrus(Coordonnee coordonnee) {
		super(coordonnee);
		setVitesse(GameConfiguration.VITESSE_INTRUS);
	}
}