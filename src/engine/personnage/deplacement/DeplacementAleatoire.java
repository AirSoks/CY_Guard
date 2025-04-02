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

	/**
     * Tableau contenant toutes les directions possibles pour le déplacement aléatoire
     */
    Direction[] directions = Direction.values();

    public DeplacementAleatoire(PersonnageManager personnages, Grille grille) {
        super(personnages, grille);
    }
    
    /**
     * Déplace le personnage dans une direction aléatoire.
     *
     * @param personnage Le personnage à déplacer
     */
    @Override
    public void deplacer(Personnage personnage) {
        if (personnage == null) {
            return;
        }
        
        int randomDirection = getValeurAleatoire(directions.length);
        Direction direction = directions[randomDirection];
        
        updateAnimation(personnage, direction);
        
        Coordonnee nouvellePosition = direction.getCoordonnee(personnage.getCoordonnee());
        if (getGrille().isCoordonneeValide(nouvellePosition, "DEPLACEMENT")) {
            personnage.setCoordonnee(nouvellePosition);
        }
        
        contactPersonnage(nouvellePosition);
    }
    
    private static int getValeurAleatoire(int value) {
        return (int) (Math.random() * value);
    }
}