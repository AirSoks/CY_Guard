package gui;
	
import java.awt.Graphics;
import javax.swing.JPanel;

import engine.map.Grille;
import engine.personnage.gestion.PersonnageManager;
	
public class GameDisplay extends JPanel{
		
	private Grille grille;
	private PersonnageManager personnages;
		
	private PaintStrategy paintStrategy = new PaintStrategy();
		
	public GameDisplay(Grille grille, PersonnageManager personnages) {
		this.grille = grille;
		this.personnages = personnages;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
			
		paintStrategy.paint(grille, g);
		paintStrategy.paint(personnages, g);
	}
}