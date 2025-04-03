package gui.dessin;

import java.awt.Graphics;

public interface Dessiner {
	
	String getNom();
	
    void paint(Graphics g);

    void setActive(Boolean etat);
    
    void setPerformance(Boolean etat);
}