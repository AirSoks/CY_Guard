package engine.personnage.deplacement;

import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;

/**
 * Cette classe représente le déplacement manuel d'un personnage
 * 
 * @author GLP_19
 * @see Deplacement
 * @see StrategieDeplacement
 */
public class DeplacementManuel extends StrategieDeplacement {

	public DeplacementManuel(PersonnageManager personnages, Grille grille) {
		super(personnages, grille);
	}

	/**
     * Déplace le personnage dans la direction spécifiée.
     *
     * @param personnage Le personnage à déplacer.
     */
	@Override
	public void deplacer(Personnage personnage) {
		Direction direction = personnage.getDirection();
		if (direction == null || personnage == null) {
            return;
        }
		updateAnimation(personnage, direction);
		
		Coordonnee nouvellePosition = direction.getCoordonnee(personnage.getCoordonnee());
		if (getGrille().isCoordonneeValide(nouvellePosition, "DEPLACEMENT")) {
			personnage.setCoordonnee(nouvellePosition);
		}
		updateAnimation(personnage, null);
		contactPersonnage(nouvellePosition);
	}
}