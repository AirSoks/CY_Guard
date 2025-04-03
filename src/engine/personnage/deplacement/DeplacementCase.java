package engine.personnage.deplacement;

import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
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
public class DeplacementCase extends StrategieDeplacement {

	private List<Coordonnee> chemin = new ArrayList<>();
	
	private MapPasCoordonnee mapPasCoordonnee = new MapPasCoordonnee();
	
	private Coordonnee cible;

    public DeplacementCase(PersonnageManager personnages, Grille grille) {
        super(personnages, grille);
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
        if (personnage == null) {
            return;
        }
        if (isCibleAccessible(personnage)) {
        	try {
                trouverChemin();
                deplacerVersCible(personnage);
                return;
            } catch (CheminNonViable e) {
            	getPersonnage().setDefautDeplacement(personnage);
            	personnage.deplacer();
            }
        }
        else {
        	getPersonnage().setDefautDeplacement(personnage);
        	personnage.setDirection(null);
        	personnage.deplacer();
        }
    }
    
    private boolean isCibleAccessible(Personnage personnage){
    	if (cible == null || cible.equals(personnage.getCoordonnee())) {
    		return false;
    	}
	    Coordonnee depart = personnage.getCoordonnee();
	    
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
	            if (adjacentes.contains(cible)) {
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
    
    private void trouverChemin() throws CheminNonViable{
        if (cible == null) { return; }

        this.chemin = new ArrayList<>();
        chemin.add(cible);

        Coordonnee coordActuel = cible;

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

	private void deplacerVersCible(Personnage personnage) {
		if (chemin != null && !chemin.isEmpty()) {
			Coordonnee prochainPas = chemin.get(0);
			Coordonnee pasActuel = personnage.getCoordonnee();
			if (pasActuel == null || prochainPas == null || pasActuel.equals(prochainPas)) { return; }
			
			try {
				Direction direction = Direction.getDirectionEntreCoordonnees(pasActuel, prochainPas);
				personnage.setCoordonnee(chemin.get(0));
				updateAnimation(personnage, direction);
		        contactPersonnage(chemin.get(0));
			} catch (Exception e) {
                return;
            }
		}
	}

	public void setCible(Coordonnee cible) {
		this.cible = cible;
	}
}