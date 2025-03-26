package engine.personnage.deplacement;

import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
import engine.personnage.Gardien;
import engine.personnage.Intrus;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;

import java.util.*;

import config.GameConfiguration;

/**
 * Cette classe représente le déplacement intelligent d'un personnage utilisant l'algorithme A*
 * 
 * @author GLP_19
 * @see Deplacement
 * @see StrategieDeplacement
 */
public class DeplacementIntelligent extends StrategieDeplacement {
	
	private List<Coordonnee> cheminCalcule = new ArrayList<>();

    public List<Coordonnee> getCheminCalcule() {
        return new ArrayList<>(cheminCalcule);
    }
    
	/**
     * Instance d'un déplacement aléatoire
     */
    private final DeplacementAleatoire deplacementAleatoire;

    public DeplacementIntelligent(PersonnageManager personnages, Grille grille) {
        super(personnages, grille);
        this.deplacementAleatoire = new DeplacementAleatoire(personnages, grille);
    }
    
    /**
     * Déplace le personnage de manière intelligente vers une cible si elle existe.
     * Si aucune cible n'est trouvée, un déplacement aléatoire est effectué.
     *
     * @param personnage Le personnage à déplacer (doit être un Gardien).
     */
    @Override
    public void deplacer(Personnage personnage) {
    	
    	if (personnage == null || !(personnage instanceof Gardien)) {
    		
        }
    }
}