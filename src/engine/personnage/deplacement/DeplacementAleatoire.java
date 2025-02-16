package engine.personnage.deplacement;

import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;

public class DeplacementAleatoire extends StrategieDeplacement {

    public DeplacementAleatoire(PersonnageManager manager, Grille grille) {
        super(manager, grille);
    }
    
    @Override
    public void deplacer(Personnage personnage) {
        if (personnage == null) {
            return;
        }
        
        Direction[] directions = Direction.values();
        int randomDirection = getValeurAleatoire(directions.length);
        setDirection(directions[randomDirection]);
        
        Direction direction = getDirection();
        
        Coordonnee nouvellePosition = direction.getCoordonnee(personnage.getCoordonnee());
        if (isCoordonneeValide(nouvellePosition)) {
            personnage.setCoordonnee(nouvellePosition);
        }
        
        contactPersonnage(nouvellePosition);
        setDirection(null);
    }
    
    private static int getValeurAleatoire(int value) {
        return (int) (Math.random() * value);
    }
}