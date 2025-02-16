package engine.personnage.deplacement;

import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;

public class DeplacementManuel extends StrategieDeplacement {

	public DeplacementManuel(PersonnageManager manager, Grille grille) {
		super(manager, grille);
	}

	@Override
	public void deplacer(Personnage personnage) {
		Direction direction = getDirection();
		if (direction == null || personnage == null) {
            return;
        }
		Coordonnee nouvellePosition = direction.getCoordonnee(personnage.getCoordonnee());
		if (isCoordonneeValide(nouvellePosition)) {
			personnage.setCoordonnee(nouvellePosition);
		}
		contactPersonnage(nouvellePosition);
		setDirection(null);
	}

}