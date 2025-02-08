package gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import engine.map.Grille;

public class GameDisplay extends JPanel{
	
	private Grille grille;
	
	private PaintStrategy paintStrategy = new PaintStrategy();
	
	public GameDisplay(Grille grille) {
		this.grille = grille;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		paintStrategy.paint(grille, g);
	}
}
