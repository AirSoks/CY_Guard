package gui.dessin;

/**
 * Interface DessinOptionnel.
 * 
 * Cette interface définit une méthode pour activer ou désactiver un dessin.
 */
public interface DessinOptionnel {
	
    /**
     * Active ou désactive le dessin.
     *
     * @param etat L'état d'activation (true pour activer, false pour désactiver)
     */
    void setActive(Boolean etat);
    
}