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
 */
public class GameDisplay extends JPanel{

	private Grille grille;
	private PersonnageManager personnages;

	private PaintStrategy paintStrategy = new PaintStrategy();

	public GameDisplay(Grille grille, PersonnageManager personnages) {
		this.grille = grille;
		this.personnages = personnages;
	}

	/**
	 * Méthode de dessin appelée lors du rafraîchissement de l'interface
	 * 
	 * @param g L'objet Graphics utilisé pour dessiner
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		paintStrategy.paint(grille, g);
		paintStrategy.paint(personnages, g);
	}
}