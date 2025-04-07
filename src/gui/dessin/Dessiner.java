package gui.dessin;

import java.awt.Graphics;

/**
 * Interface Dessiner
 * 
 * Cette interface définit les méthodes nécessaires pour dessiner des objets graphiques.
 */
public interface Dessiner {
	
    /**
     * Obtient le nom de l'objet à dessiner.
     *
     * @return Le nom de l'objet.
     */
	String getNom();
	
    /**
     * Dessine l'objet graphique.
     *
     * @param g L'objet Graphics utilisé pour dessiner.
     */
    void paint(Graphics g);
    
}