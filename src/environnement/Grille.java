package environnement;

import personnage.Personnage;
import personnage.Gardien;
import personnage.Intrus;
import utilitaire.Coordonnee;

import java.util.ArrayList;
import java.util.List;

import environnement.obstacle.Arbre;
import environnement.obstacle.Lac;
import environnement.obstacle.Obstacle;
import environnement.obstacle.Roche;


public class Grille {
	
	private Case[][] grille;
	private int nbLigne;
	private int nbColonne;
	
    private List<Intrus> intrus = new ArrayList<>();
    private List<Gardien> gardiens = new ArrayList<>();
	
	public Grille(int nbLigne, int nbColonne) {
		init(nbLigne, nbColonne);
		for (int lineIndex = 0; lineIndex < nbLigne; lineIndex++) {
			for (int columnIndex = 0; columnIndex < nbColonne; columnIndex++) {
				
				Coordonnee position = new Coordonnee(lineIndex, columnIndex);
				grille[lineIndex][columnIndex] = new Case(position);	
			}
		}
		// ca c'est pour tester les obstacles
		addObstacle(new Coordonnee(1, 1), new Arbre());
        addObstacle(new Coordonnee(1, 2), new Lac());
        addObstacle(new Coordonnee(1, 3), new Roche());
	}
	
	public void addObstacle(Coordonnee position, Obstacle obstacle) {
        Case cell = getCase(position);
        if (cell != null) {
            cell.setObstacle(obstacle);
        }
    }
	
	private void init(int nbLigne, int nbColonne) {
		this.nbLigne = nbLigne;	
		this.nbColonne = nbColonne;
		this.grille = new Case[nbLigne][nbColonne];
	}
	
	public int getNbLigne() {
		return nbLigne;
	}
	
	public int getNbColonne() {
		return nbColonne;
	}

	public void ajouterIntrus(Intrus intrus) {
        this.intrus.add(intrus);
    }
	
	public List<Intrus> getIntrus() {
        return this.intrus;
    }
	
	public void retirerIntrus(Intrus intrus) {
        this.intrus.remove(intrus);
    }
	
	public void ajouterGardien(Gardien gardien) {
        this.gardiens.add(gardien);
    }

    public List<Gardien> getGardiens() {
        return this.gardiens;
    }
	
	public Case getCase(Coordonnee position) {
		int ligne = position.getLigne();
		int colonne = position.getColonne();
		if (ligne >= 0 && ligne < nbLigne && colonne >= 0 && colonne < nbColonne) { 
			return grille[ligne][colonne];
		}
		return null;
	}
	
	public Case[][] getgrille() {
		return this.grille;
	}
	
	public void setgrille(Case[][] grille) {
		this.grille = grille;
	}
	
	public List<Personnage> getPersonnages(Coordonnee coordonnee) {
        List<Personnage> personnages = new ArrayList<>();
        Case caseRecherchee = getCase(coordonnee);
        
        if (caseRecherchee == null) {
        	return null;
        }
        
        for (Intrus intrus : this.intrus) {
            if (intrus.getCase().equals(caseRecherchee)) {
                personnages.add(intrus);
            }
        }
        
        for (Gardien gardien : this.gardiens) {
            if (gardien.getCase().equals(caseRecherchee)) {
                personnages.add(gardien);
            }
        }
        
        return personnages;
    }
}