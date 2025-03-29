package engine.map;

import java.util.Objects;

/**
 * Cette classe représente des coordonnées pour les cases et les personnages dans la grille
 * 
 * @author GLP_19
 * @see Case
 * @see Personnage
 * 
 */
public class Coordonnee {
	
	/**
	 * La coordonnée des abscisses
	 */
	private int ligne;
	
	/**
	 * La coordonnée des ordonnées
	 */
	private int colonne;
	
	public Coordonnee(int ligne, int colonne) {
		this.ligne = ligne;
		this.colonne = colonne;
	}
	
	public int getLigne() {
		return ligne;
	}
	
	public int getColonne() {
		return colonne;
	}
	
	public void setLigne(int ligne) {
		this.ligne = ligne;	
	}
	
	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	@Override
	public int hashCode() {
		return Objects.hash(colonne, ligne);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Coordonnee other = (Coordonnee) obj;
		return colonne == other.colonne && ligne == other.ligne;
	}
	
	@Override
	public String toString() {
	    return "[ligne=" + ligne + ", colonne=" + colonne + "]";
	}
}