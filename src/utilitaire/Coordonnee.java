package utilitaire;

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
	
	public boolean equals(Coordonnee coordonnee) {
		if (this.ligne == coordonnee.getLigne() && this.colonne == coordonnee.getColonne()) {
			return true;
		}
		return false;
	}
}
