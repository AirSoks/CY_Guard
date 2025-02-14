package engine.personnage.gestion;

import engine.map.Case;
import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
import engine.personnage.Personnage;

public class PersonnageDeplacement {
	
	public boolean deplacerPersonnage(Grille grille, Personnage personnage, Direction direction) {
		Coordonnee nouvellePosition = direction.getCoordonnee(personnage.getCoordonnee());
		if (isCoordonneeValide(grille, nouvellePosition)) {
			personnage.setCoordonnee(nouvellePosition);
			return true;
		}
		return false;
	}
	
	private Boolean isCoordonneeValide(Grille grille, Coordonnee coordonnee) {
		Case c = grille.getCase(coordonnee);
		if (c != null && !c.getObstacle().isBloqueDeplacement()) {
			return true;
		}
		return false;
	}
}