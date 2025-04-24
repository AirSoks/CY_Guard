package engine.personnage;

import java.util.ArrayList;
import java.util.List;

import engine.map.Coordonnee;

/**
 * Intrus est une classe représentant un personnage intrus
 * 
 * @author GLP_19
 * @see Personnage
 */
public class Intrus extends Personnage {
	
	private List<Gardien> cibles = new ArrayList<>();

	public Intrus(Coordonnee coordonnee) {
		super(coordonnee);
	}

	public void ajouterCible(Gardien cible) {
        if (cible != null && !cibles.contains(cible)) {
            this.cibles.add(cible);
        }
    }

    public List<Gardien> getCibles() {
        return new ArrayList<>(cibles);
    }

    public Gardien getCible() {
        return null;
    }
}