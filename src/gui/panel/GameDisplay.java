package gui.panel;

import java.awt.Graphics;

import javax.swing.JPanel;

import config.Settings;
import engine.map.Grille;
import engine.personnage.PersonnageManager;

/**
 * Cette classe est le panneau dans lequel le parc et les personnages sont imprimés
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

    private PaintStrategy paintStrategy;

    public GameDisplay(Grille grille, PersonnageManager personnages, Settings settings) {
        this.paintStrategy = new PaintStrategy(grille, personnages, settings);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintStrategy.paint(g);
    }

	public PaintStrategy getPaintStrategy() {
		return paintStrategy;
	}
}