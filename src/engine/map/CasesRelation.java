package engine.map;

import java.util.List;

public class CasesRelation {
	
	private Case caseDebut;
	private Case caseFin;
	private Grille grille;
	
    public CasesRelation(Case caseDebut, Case caseFin, Grille grille) {
        this.caseDebut = caseDebut;
        this.caseFin = caseFin;
        this.grille = grille;
    }
    
    public boolean isCasesAccessible() {
    	
    	return false;
    }
    
    public List<Case> getCheminCases(){
    	
    	return null;
    }
    
    public int getDistance() {
    	
    	return 0;
    }
	
}