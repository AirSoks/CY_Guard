package engine.map;

/**
 * Test
 */
public class Coordonnee {
	private int ligne;
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
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Coordonnee coordonnee = (Coordonnee) obj;
	    return ligne == coordonnee.ligne && colonne == coordonnee.colonne;
	}
}
