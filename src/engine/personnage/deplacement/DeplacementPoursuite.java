package engine.personnage.deplacement;

import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
import engine.personnage.Gardien;
import engine.personnage.Intrus;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;
import engine.utilitaire.CheminNonViable;

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
    
    public void effacerChemin() {
    	chemin.clear();
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

        while (true) {
            Intrus cible = cibleAccessible(gardien);

            if (cible != null) {
                try {
                    trouverChemin(cible);
                    deplacerVersCible(gardien, cible);
                    return;
                } catch (CheminNonViable e) {
                    gardien.retirerPremiereCible();
                }
            } else {
            	effacerChemin();
                deplacementAleatoire.deplacer(gardien);
                return;
            }
        }
    }

    private Intrus cibleAccessible(Gardien gardien) {
        while (gardien.getPremiereCible() != null) {
            Intrus cible = gardien.getPremiereCible();
            if (getPersonnage().getIntrus().contains(cible)) {
                if (isCibleAccessible(gardien, cible)) {
                    return cible;
                }
            }
            gardien.retirerPremiereCible();
        }
        return null;
    }

	private boolean isCibleAccessible(Gardien gardien, Intrus cible){
	    Coordonnee depart = gardien.getCoordonnee();
	    Coordonnee arrivee = cible.getCoordonnee();
	    mapPasCoordonnee.reinitialiserMap();
	    mapPasCoordonnee.ajouterCoordonne(0, depart);

	    int pas = 1;
	    while (true) {
	        List<Coordonnee> coordonneesActuelles = mapPasCoordonnee.getCoordonneesFromPas(pas - 1);
	        if (coordonneesActuelles == null || coordonneesActuelles.isEmpty()) {
	            return false;
	        }
	        for (Coordonnee coord : coordonneesActuelles) {
	            List<Coordonnee> adjacentes = getCoordonneeAdjacentes(coord);
	            mapPasCoordonnee.ajouterCoordonnes(pas, adjacentes);
	            if (adjacentes.contains(arrivee)) {
	                return true;
	            }
	        }
	        pas++;
	    }
	}

	/**
     * Récupère les coordonnées adjacentes d'une coordonnée accessible
     * 
     * @param coordonnee La coordonnée à traiter
     * @return Une liste de coordonnée adjacente
     */
    private List<Coordonnee> getCoordonneeAdjacentes(Coordonnee coordonnee) {
        List<Coordonnee> coordonneeAdjacentes = new ArrayList<>();
        
        for (Direction direction : Direction.values()) {
            Coordonnee coordonneeAdjacente = direction.getCoordonnee(coordonnee);
            if (getGrille().isCoordonneeValide(coordonneeAdjacente, "DEPLACEMENT")) {
                coordonneeAdjacentes.add(coordonneeAdjacente);
            }
        }
        return coordonneeAdjacentes;
    }
    
    private void trouverChemin(Intrus cible) throws CheminNonViable{
        if (cible == null || cible.getCoordonnee() == null) {
            return;
        }

        this.chemin = new ArrayList<>();
        Coordonnee arrivee = cible.getCoordonnee();
        chemin.add(arrivee);

        Coordonnee coordActuel = arrivee;

        int pas = mapPasCoordonnee.getListePas().size() - 1;

        for (int i = pas - 1; i > 0; i--) {
            List<Coordonnee> coordonnees = mapPasCoordonnee.getCoordonneesFromPas(i);
            if (coordonnees == null) {
                throw new CheminNonViable(i);
            }

            boolean coordonneeTrouvee = false;
            for (Coordonnee coord : coordonnees) {
                List<Coordonnee> coordonneeAdjacentes = getCoordonneeAdjacentes(coordActuel);
                if (coordonneeAdjacentes.contains(coord)) {
                    chemin.add(coord);
                    coordActuel = coord;
                    coordonneeTrouvee = true;
                    break;
                }
            }
            if (!coordonneeTrouvee) {
                throw new CheminNonViable(i);
            }
        }
        Collections.reverse(chemin);
    }

	private void deplacerVersCible(Gardien gardien, Intrus cible) {
		if (chemin != null && !chemin.isEmpty()) {
			Coordonnee prochainPas = chemin.get(0);
			Direction direction = Direction.getDirectionEntreCoordonnees(gardien.getCoordonnee(), prochainPas);
			
			gardien.setCoordonnee(chemin.get(0));
	        updateAnimation(gardien, direction);
	        contactPersonnage(chemin.get(0));
		}
	}
}