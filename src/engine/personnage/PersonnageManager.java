package engine.personnage;

import config.GameConfiguration;
import engine.map.Coordonnee;
import engine.map.Case;
import engine.map.Grille;

public class PersonnageManager {
	private Grille grille;
		
	private Gardien gardien;
		
	public PersonnageManager(Grille grille, Gardien gardien) {
		this.grille = grille;
		this.gardien = gardien;
	}
		
	public void moveLeftGardien() {
		Coordonnee position = gardien.getCoordonnee();
		if (position.getColonne() > 0) {
			Case newCase = grille.getCase(position.getLigne(), position.getColonne() - 1);
			if (newCase != null) {
				gardien.setCoordonnee(newCase.getPosition());
			}
		}
	}
		
	public void moveRightGardien() {
		Coordonnee position = gardien.getCoordonnee();
		if (position.getColonne() < GameConfiguration.NB_COLONNE - 1) {
			Case newCase = grille.getCase(position.getLigne(), position.getColonne() + 1);
			if (newCase != null) {
				gardien.setCoordonnee(newCase.getPosition());
			}
		}
	}
		
	public void moveUpGardien() {
		Coordonnee position = gardien.getCoordonnee();
		if (position.getLigne() > 0) {
			Case newCase = grille.getCase(position.getLigne() - 1, position.getColonne());
			if (newCase != null) {
				gardien.setCoordonnee(newCase.getPosition());
			}
		}
	}
	
	public void moveDownGardien() {
		Coordonnee position = gardien.getCoordonnee();
		if (position.getLigne() < GameConfiguration.NB_LIGNE - 1) {
			Case newCase = grille.getCase(position.getLigne() + 1, position.getColonne());
			if (newCase != null) {
				gardien.setCoordonnee(newCase.getPosition());
			}
		}
	}
}
