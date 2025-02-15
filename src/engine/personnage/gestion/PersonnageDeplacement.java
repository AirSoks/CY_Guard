package engine.personnage.gestion;

import java.util.List;

import engine.map.Case;
import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
import engine.personnage.Gardien;
import engine.personnage.Personnage;

public class PersonnageDeplacement {
	
	public static boolean deplacerPersonnage(Grille grille, Personnage personnage, Direction direction) {
		Coordonnee nouvellePosition = direction.getCoordonnee(personnage.getCoordonnee());
		if (isCoordonneeValide(grille, nouvellePosition)) {
			personnage.setCoordonnee(nouvellePosition);
			return true;
		}
		return false;
	}
	
	private static Boolean isCoordonneeValide(Grille grille, Coordonnee coordonnee) {
		Case c = grille.getCase(coordonnee);
		if (c != null && !c.getObstacle().isBloqueDeplacement()) {
			return true;
		}
		return false;
	}

	public static void contactPersonnage(PersonnageManager personnageManager, Coordonnee coordonnee) { //
		List<Personnage> personnages = personnageManager.getPersonnages(coordonnee);
		int nbPersonnageInitial = personnages.size();

    	if (nbPersonnageInitial >= 2) {
    		for (Personnage personnage : personnages) {
    			if (personnage instanceof Gardien) {
    				personnages.remove(personnage);
    			}
    		}
    		if (nbPersonnageInitial != personnages.size()) {
    			for (Personnage personnage : personnages) {
    				personnageManager.retirerPersonnage(personnage);
    			}
    		}
    	}
	}
}