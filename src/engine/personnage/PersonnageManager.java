package engine.personnage;

import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;

public class PersonnageManager {
	private Grille grille;
		
	public PersonnageManager(Grille grille) {
		this.grille = grille;
	}
	
	public void deplacerPersonnage(Personnage personnage, Direction direction) {
		Coordonnee nouvellePosition = direction.getCoordonnee(personnage.getCoordonnee());
		if (grille.getCase(nouvellePosition) != null) {
			personnage.setCoordonnee(nouvellePosition);
		}
	}
}