package engine.personnage.deplacement;

import engine.map.Case;
import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
import engine.personnage.Gardien;
import engine.personnage.Intrus;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;
import engine.personnage.Vision;

import java.util.*;

/**
 * Cette classe représente le déplacement poursuite d'un gardien
 * 
 * @author GLP_19
 * @see Deplacement
 * @see StrategieDeplacement
 */
public class DeplacementPoursuite extends StrategieDeplacement {
	
	private List<Coordonnee> chemin = new ArrayList<>();
	
	private MapPasCoordonnee mapPasCoordonnee = new MapPasCoordonnee();

	/**
     * Instance d'un déplacement aléatoire
     */
    private DeplacementAleatoire deplacementAleatoire;

    public DeplacementPoursuite(PersonnageManager personnages, Grille grille) {
        super(personnages, grille);
        this.deplacementAleatoire = (DeplacementAleatoire) DeplacementFactory.getDeplacement("Aleatoire", personnages, grille);
    }
    
    public List<Coordonnee> getChemin() {
        return new ArrayList<>(chemin);
    }
    
    /**
     * Déplace le personnage de manière à poursuivre une cible.
     * Si aucune cible n'est trouvée, un déplacement aléatoire est effectué.
     *
     * @param personnage Le personnage à déplacer (doit être un Gardien).
     */
    @Override
    public void deplacer(Personnage personnage) {
    	if (personnage == null || !(personnage instanceof Gardien)) {
        	return;
        }
        Gardien gardien = (Gardien) personnage;
        Intrus cible = getCible(gardien);
        
        if (cible == null) {
            deplacementAleatoire.deplacer(personnage);
            return;
        }
        
        Coordonnee depart = personnage.getCoordonnee();
        Coordonnee arrivee = cible.getCoordonnee();
        
        mapPasCoordonnee.reinitialiserMap();
        mapPasCoordonnee.ajouterCoordonne(0, depart);
        
        int pas = 1;
        boolean cibleTrouvee = false;
        
        while (!cibleTrouvee) {
            List<Coordonnee> coordonneesActuelles = mapPasCoordonnee.getCoordonneesFromPas(pas - 1);
            if (coordonneesActuelles == null) {
            	// Il faut supprimer la cible et prendre la prochaine - à modifier ici pour rester dans cette itération
            	gardien.retirerPremiereCible();
            	break;
            }
            for (Coordonnee coord : coordonneesActuelles) {
                List<Coordonnee> adjacentes = getCoordonneeAdjacentes(coord, pas);
                mapPasCoordonnee.ajouterCoordonnes(pas, adjacentes);
                if (adjacentes.contains(arrivee)) {
                    cibleTrouvee = true;
                    break;
                }
            }
            pas++;
        }
        System.out.println("Nombre de pas : " + pas);
        
        this.chemin = trouverChemin(arrivee, pas - 1);
        
        if (chemin != null && !chemin.isEmpty()) {
        	personnage.setCoordonnee(chemin.get(0));
        }
        return;
    }

	/**
     * Récupère les coordonnées (vide) adjacentes d'une coordonnée
     * 
     * @param coordonnee La coordonnée à traiter
     * @return Une liste de coordonnée adjacente
     */
    private List<Coordonnee> getCoordonneeAdjacentes(Coordonnee coordonnee, int pasActuel) {
        List<Coordonnee> coordonneeAdjacentes = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Coordonnee coordonneeAdjacente = direction.getCoordonnee(coordonnee);
            Case caseAdjacente = getGrille().getCase(coordonneeAdjacente);
            
            if (caseAdjacente != null && !caseAdjacente.getObstacle().isBloqueDeplacement()) {
                if (!mapPasCoordonnee.coordonneeIsDejaVu(coordonneeAdjacente)) {
                    coordonneeAdjacentes.add(coordonneeAdjacente);
                }
            }
        }
        
        return coordonneeAdjacentes;
    }
    
    private Intrus getCible(Gardien gardien) {
		Intrus cible = gardien.getPremiereCible();
		return cible;
	}
    
	private List<Coordonnee> trouverChemin(Coordonnee arrivee, int pas) {
		// TODO Auto-generated method stub
		return null;
	}
}