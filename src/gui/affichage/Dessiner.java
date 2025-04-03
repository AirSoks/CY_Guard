package gui.affichage;

import java.awt.Graphics;

public interface Dessiner {
	
    void paint(Graphics g);

    void activer();
    
    void desactiver();
    
    void activerPerformance();
    
    void desactiverPerformance();
}