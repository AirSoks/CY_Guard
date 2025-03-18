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
        if (!(personnage instanceof Gardien gardien)) {
            return;
        }

        rechercherEtAjouterCible(gardien);

        Intrus cible = gardien.getPremiereCible();

        if (cible == null || !intrusExiste(cible.getCoordonnee()) || !isCoordonneeValide(cible.getCoordonnee())) {
            if (cible != null) {
                gardien.retirerPremiereCible();
            }
            
            cible = trouverNouvelleCibleValide(gardien);
            
            if (cible == null) {
                deplacementAleatoire.deplacer(gardien);
                cheminCalcule = new ArrayList<>();
                return;
            }
        }
        
        deplacerVersCible(gardien, gardien.getCoordonnee(), cible.getCoordonnee());
    }

    private Intrus trouverNouvelleCibleValide(Gardien gardien) {
        Intrus cible;
        do {
            cible = gardien.getPremiereCible();
            if (cible == null) {
                return null;
            }
            if (!intrusExiste(cible.getCoordonnee()) || !isCoordonneeValide(cible.getCoordonnee())) {
                gardien.retirerPremiereCible();
            } else {
                return cible;
            }
        } while (true);
    }

    /**
     * Recherche et ajoute des cibles (Intrus) dans le champ de vision du gardien.
     *
     * @param gardien Le gardien pour lequel on recherche des cibles.
     */
    private void rechercherEtAjouterCible(Gardien gardien) {
        for (Personnage p : getPersonnage().getPersonnages()) {
            if (p instanceof Intrus) {
                Coordonnee coordIntrus = p.getCoordonnee();
                int distance = distanceManhattan(gardien.getCoordonnee(), coordIntrus);
                
                if (distance <= GameConfiguration.NB_CASES_VISION && !gardien.getCibles().contains(p)) {
                    gardien.ajouterCible((Intrus) p);
                }
            }
        }
    }
    
    private boolean intrusExiste(Coordonnee coordIntrus) {
        for (Personnage p : getPersonnage().getPersonnages()) {
            if (p instanceof Intrus && p.getCoordonnee().equals(coordIntrus)) {
                return true;
            }
        }
        return false;
    }
    
	/**
	 * Calcule et renvoie le chemin actuel du gardien vers sa cible.
	 *
	 * @param gardien Le gardien dont on veut calculer le chemin
	 * @return Une liste de coordonnées représentant le chemin, ou une liste vide si aucun chemin n'est trouvé
	 */
	public List<Coordonnee> calculerCheminActuel(Gardien gardien) {
	    Intrus cible = gardien.getPremiereCible();
	
	    if (cible == null || !intrusExiste(cible.getCoordonnee()) || !isCoordonneeValide(cible.getCoordonnee())) {
	        return new ArrayList<>();
	    }
	
	    List<Direction> directions = aStar(gardien.getCoordonnee(), cible.getCoordonnee());
	    return convertirDirectionsEnCoordonnees(gardien.getCoordonnee(), directions);
	}
    
    /**
     * Déplace le gardien vers la cible spécifiée en utilisant l'algorithme A*.
     *
     * @param gardien Le gardien à déplacer.
     * @param depart La coordonnée de départ du gardien.
     * @param objectif La coordonnée de la cible.
     */
    private void deplacerVersCible(Gardien gardien, Coordonnee depart, Coordonnee arrive) {
        List<Direction> directions = aStar(depart, arrive);
        
        if (!directions.isEmpty()) {
            Direction direction = directions.get(0);
            updateAnimation(gardien, direction);
            
            Coordonnee nouvellePosition = direction.getCoordonnee(depart);
            if (isCoordonneeValide(nouvellePosition)) {
                gardien.setCoordonnee(nouvellePosition);
                
                this.cheminCalcule = calculerCheminActuel(gardien);
            }
            contactPersonnage(nouvellePosition);
        } else {
            gardien.retirerPremiereCible();
            deplacementAleatoire.deplacer(gardien);
            this.cheminCalcule = new ArrayList<>();
        }
    }
    
    /**
     * Implémente l'algorithme A* pour trouver le chemin optimal entre deux points.
     *
     * @param depart La coordonnée de départ.
     * @param arrive La coordonnée d'arrivée.
     * @return Une liste de directions représentant le chemin optimal.
     */
    private List<Direction> aStar(Coordonnee depart, Coordonnee arrive) {
        PriorityQueue<Noeud> noeudsAVisiter = new PriorityQueue<>(Comparator.comparingInt(n -> n.coutTotal));
        Map<Coordonnee, Noeud> noeudsDejaVisites = new HashMap<>();

        Noeud noeudDepart = new Noeud(depart, null, 0, distanceManhattan(depart, arrive));
        noeudsAVisiter.add(noeudDepart);
        noeudsDejaVisites.put(depart, noeudDepart);

        while (!noeudsAVisiter.isEmpty()) {
            Noeud courant = noeudsAVisiter.poll();

            if (courant.coord.equals(arrive)) {
                return construireChemin(courant);
            }

            for (Direction direction : Direction.values()) {
                Coordonnee voisinCoord = direction.getCoordonnee(courant.coord);
                if (!isCoordonneeValide(voisinCoord)) continue;
                int nouveauCout = courant.coutActuel + 1;

                Noeud noeudVoisin = noeudsDejaVisites.get(voisinCoord);
                if (noeudVoisin == null) {
                    noeudVoisin = new Noeud(voisinCoord, courant, nouveauCout, nouveauCout + distanceManhattan(voisinCoord, arrive));
                    noeudsDejaVisites.put(voisinCoord, noeudVoisin);
                    noeudsAVisiter.add(noeudVoisin);
                    
                } else if (nouveauCout < noeudVoisin.coutActuel) {
                    noeudVoisin.parent = courant;
                    noeudVoisin.coutActuel = nouveauCout;
                    noeudVoisin.coutTotal = nouveauCout + distanceManhattan(voisinCoord, arrive);
                    noeudsAVisiter.add(noeudVoisin);
                }
            }
        }
        return Collections.emptyList();
    }

    private int distanceManhattan(Coordonnee a, Coordonnee b) {
        return Math.abs(a.getLigne() - b.getLigne()) + Math.abs(a.getColonne() - b.getColonne());
    }

    private List<Direction> construireChemin(Noeud noeudFinal) {
        List<Direction> chemin = new LinkedList<>();
        while (noeudFinal.parent != null) {
            chemin.add(0, trouverDirection(noeudFinal.parent.coord, noeudFinal.coord));
            noeudFinal = noeudFinal.parent;
        }
        return chemin;
    }

    private Direction trouverDirection(Coordonnee depart, Coordonnee arrivee) {
        for (Direction direction : Direction.values()) {
            if (direction.getCoordonnee(depart).equals(arrivee)) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Impossible de trouver la direction de " + depart + " à " + arrivee);
    }
    
    private List<Coordonnee> convertirDirectionsEnCoordonnees(Coordonnee depart, List<Direction> directions) {
        List<Coordonnee> chemin = new ArrayList<>();
        Coordonnee current = depart;
        chemin.add(current); // Ajouter la position initiale
        
        for (Direction dir : directions) {
            current = dir.getCoordonnee(current);
            chemin.add(current);
        }
        
        return chemin;
    }
    
    /**
     * Classe interne représentant un nœud dans l'algorithme A*.
     */
    private static class Noeud {

		Coordonnee coord;
        Noeud parent;
        int coutActuel;
        int coutTotal;

        Noeud(Coordonnee coord, Noeud parent, int coutActuel, int coutTotal) {
            this.coord = coord;
            this.parent = parent;
            this.coutActuel = coutActuel;
            this.coutTotal = coutTotal;
        }

        @Override
		public int hashCode() {
			return Objects.hash(coord);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Noeud other = (Noeud) obj;
			return Objects.equals(coord, other.coord);
		}
    }
}