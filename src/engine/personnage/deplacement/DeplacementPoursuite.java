package engine.personnage.deplacement;

import engine.map.Case;
import engine.map.Coordonnee;
import engine.map.Grille;
import engine.personnage.Gardien;
import engine.personnage.Intrus;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;

import java.util.*;

/**
 * Cette classe représente le déplacement intelligent d'un personnage utilisant l'algorithme A*
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
        this.deplacementAleatoire = new DeplacementAleatoire(personnages, grille);
    }
    
    public List<Coordonnee> getChemin() {
        return new ArrayList<>(chemin);
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
        
        this.chemin = trouverChemin(arrivee, pas - 1);
        
        if (!chemin.isEmpty()) {
        	personnage.setCoordonnee(chemin.get(0));
        }
    }

	/**
     * Récupère les coordonnées (vide) adjacentes d'une coordonnée
     * 
     * @param coordonnee La coordonnée à traiter
     * @return Une liste de coordonnée adjacente
     */
    private List<Coordonnee> getCoordonneeAdjacentes(Coordonnee coordonnee, int pasActuel) {
		List<Coordonnee> coordonneeAdjacentes = new ArrayList<>();

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0) { continue; }

				Coordonnee coordonneeAdjacente = new Coordonnee(coordonnee.getLigne() + i, coordonnee.getColonne() + j);
				Case caseAdjacente = getGrille().getCase(coordonneeAdjacente);
				if (caseAdjacente != null && !caseAdjacente.getObstacle().isBloqueDeplacement()){
					if (!mapPasCoordonnee.coordonneeIsDejaVu(coordonneeAdjacente, pasActuel)) {
						coordonneeAdjacentes.add(coordonneeAdjacente);
					}
				}
            }
		}
		return coordonneeAdjacentes;
	}
    
    private Intrus getCible(Gardien gardien) {
		// TODO Auto-generated method stub
		return null;
	}
    

	private List<Coordonnee> trouverChemin(Coordonnee arrivee, int pas) {
		// TODO Auto-generated method stub
		return null;
	}
}