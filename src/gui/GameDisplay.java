package gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import engine.map.Grille;
import engine.personnage.PersonnageManager;

/**
 * Cette classe est le panneau dans lequel le parc (avec les obstacles) et les personnages (gardien et intrus) sont imprimés
 * 
 * @author GLP_19
 * @see JPanel
 * @see Grille
 * @see PersonnageManager
 * @see PaintStrategy
 * @see MainGUI
 */
@SuppressWarnings("serial")
public class GameDisplay extends JPanel {

    private final PaintStrategy paintStrategy;

    public GameDisplay(Grille grille, PersonnageManager personnages) {
        this.paintStrategy = new PaintStrategy(grille, personnages);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintStrategy.paint(g);
    }
}
