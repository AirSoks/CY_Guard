package gui.dessin;

/**
 * Interface DPerformanceElement.
 * 
 * Cette interface définit une méthode pour activer ou désactiver un mode performance.
 */
public interface DPerformanceElement {
    
	/**
     * Active ou désactive le mode performance.
     *
     * @param etat L'état d'activation (true pour activer, false pour désactiver)
     */
    void setPerformance(Boolean etat);
    
}