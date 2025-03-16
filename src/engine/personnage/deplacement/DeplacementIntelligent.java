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
    private final DeplacementAleatoire deplacementAleatoire;

    public DeplacementIntelligent(PersonnageManager personnages, Grille grille) {
        super(personnages, grille);
        this.deplacementAleatoire = new DeplacementAleatoire(personnages, grille);
    }

    @Override
    public void deplacer(Personnage personnage) {
        if (personnage == null || !(personnage instanceof Gardien)) {
            return;
        }

        Gardien gardien = (Gardien) personnage;

        rechercherEtAjouterCible(gardien);

        Intrus cible = gardien.getPremiereCible();

        if (cible == null) {
            deplacementAleatoire.deplacer(gardien);
            return;
        }

        if (!intrusExiste(cible.getCoordonnee()) || !isCoordonneeValide(cible.getCoordonnee())) {
            gardien.retirerPremiereCible();

            Intrus nouvelleCible = gardien.getPremiereCible();
            if (nouvelleCible != null) {
                deplacerVersCible(gardien, gardien.getCoordonnee(), nouvelleCible.getCoordonnee());
                return;
            }

            deplacementAleatoire.deplacer(gardien);
            return;
        }

        deplacerVersCible(gardien, gardien.getCoordonnee(), cible.getCoordonnee());
    }

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
    
    private void deplacerVersCible(Personnage personnage, Coordonnee depart, Coordonnee objectif) {
        List<Direction> chemin = aStar(depart, objectif);
        if (!chemin.isEmpty()) {
            Direction direction = chemin.get(0);
            updateAnimation(personnage, direction);
            
            Coordonnee nouvellePosition = direction.getCoordonnee(depart);
            if (isCoordonneeValide(nouvellePosition)) {
                personnage.setCoordonnee(nouvellePosition);
            }
            contactPersonnage(nouvellePosition);
        } else {
            if (personnage instanceof Gardien) {
                ((Gardien) personnage).retirerPremiereCible();
            }
            deplacementAleatoire.deplacer(personnage);
        }
    }

    private List<Direction> aStar(Coordonnee depart, Coordonnee objectif) {
        PriorityQueue<Noeud> noeudsAVisiter = new PriorityQueue<>(Comparator.comparingInt(n -> n.coutTotal));
        Map<Coordonnee, Noeud> noeudsDejaVisites = new HashMap<>();

        Noeud noeudDepart = new Noeud(depart, null, 0, distanceManhattan(depart, objectif));
        noeudsAVisiter.add(noeudDepart);
        noeudsDejaVisites.put(depart, noeudDepart);

        while (!noeudsAVisiter.isEmpty()) {
            Noeud courant = noeudsAVisiter.poll();

            if (courant.coord.equals(objectif)) {
                return construireChemin(courant);
            }

            for (Direction direction : Direction.values()) {
                Coordonnee voisinCoord = direction.getCoordonnee(courant.coord);
                if (!isCoordonneeValide(voisinCoord)) {
                	continue;
                }

                int nouveauCout = courant.coutActuel + 1;
                Noeud noeudVoisin = noeudsDejaVisites.getOrDefault(voisinCoord, new Noeud(voisinCoord));
                noeudsDejaVisites.put(voisinCoord, noeudVoisin);

                if (nouveauCout < noeudVoisin.coutActuel) {
                    noeudVoisin.parent = courant;
                    noeudVoisin.coutActuel = nouveauCout;
                    noeudVoisin.coutTotal = noeudVoisin.coutActuel + distanceManhattan(voisinCoord, objectif);
                    if (noeudsAVisiter.contains(noeudVoisin)) {
                        noeudsAVisiter.remove(noeudVoisin);
                    }
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

    private static class Noeud {

		Coordonnee coord;
        Noeud parent;
        int coutActuel;
        int coutTotal;

        Noeud(Coordonnee coord) {
            this(coord, null, Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

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