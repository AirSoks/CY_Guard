package gui.affichage;

import java.awt.Graphics;

public interface Dessiner {
	
    void paint(Graphics g);
    
    void activer();
    
    void desactiver();
    
    boolean isActiver();
}