package gui.dessin;

/**
 * Interface DOptionnelElement.
 * 
 * Cette interface définit une méthode pour activer ou désactiver un dessin.
 */
public interface DOptionnelElement {
	
    /**
     * Active ou désactive le dessin.
     *
     * @param etat L'état d'activation (true pour activer, false pour désactiver)
     */
    void setActive(Boolean etat);
    
}