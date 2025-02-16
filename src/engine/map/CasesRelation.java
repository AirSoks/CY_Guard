package engine.map;

import java.util.List;

/**
 * La relation entres deux cases
 * 
 * @author GLP_19
 */
public class CasesRelation {
	
	/**
	 * La case de départ
	 */
	private Case caseDebut;
	
	/**
	 * La case d'arrivée
	 */
	private Case caseFin;
	
	/**
	 * La grille dans laquelle les cases se trouvent
	 */
	private Grille grille;
	
    public CasesRelation(Case caseDebut, Case caseFin, Grille grille) {
        this.caseDebut = caseDebut;
        this.caseFin = caseFin;
        this.grille = grille;
    }
    
    /**
     * Vérifie si les cases sont accessibles.
     * 
     * @return true si les cases sont accessible, sinon false
     */
    public boolean isCasesAccessible() {
  
    	return false;
    }
    
    /**
     * Retourne la liste des cases formant le chemin entre la case de départ et la case d'arrivée
     * @return La liste des cases représentant le chemin
     */
    public List<Case> getCheminCases(){
    	
    	return null;
    }
    
    /**
     * Retourne la distance entres les deux cases
     * @return La distance entres les deux cases
     */
    public int getDistance() {
    	
    	return 0;
    }
	
}