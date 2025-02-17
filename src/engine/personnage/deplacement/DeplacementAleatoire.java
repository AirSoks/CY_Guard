package engine.personnage.deplacement;

import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;

/**
 * Cette classe représente le déplacement aléatoire d'un personnage
 * 
 * @author GLP_19
 * @see Deplacement
 * @see StrategieDeplacement
 */
public class DeplacementAleatoire extends StrategieDeplacement {

    public DeplacementAleatoire(PersonnageManager personnages, Grille grille) {
        super(personnages, grille);
    }
    
    @Override
    public void deplacer(Personnage personnage) {
        if (personnage == null) {
            return;
        }
        
        Direction[] directions = Direction.values();
        int randomDirection = getValeurAleatoire(directions.length);
        Direction direction = directions[randomDirection];
        
        Coordonnee nouvellePosition = direction.getCoordonnee(personnage.getCoordonnee());
        if (isCoordonneeValide(nouvellePosition)) {
            personnage.setCoordonnee(nouvellePosition);
        }
        
        contactPersonnage(nouvellePosition);
    }
    
    private static int getValeurAleatoire(int value) {
        return (int) (Math.random() * value);
    }
}