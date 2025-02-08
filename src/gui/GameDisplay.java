	package gui;
	
	import java.awt.Graphics;
	
	import javax.swing.JPanel;
	
	import engine.map.Grille;
	import engine.personnage.Gardien;
	
	public class GameDisplay extends JPanel{
		
		private Grille grille;
		private Gardien gardien;
		
		private PaintStrategy paintStrategy = new PaintStrategy();
		
		public GameDisplay(Grille grille, Gardien gardien) {
			this.grille = grille;
			this.gardien = gardien;
		}
	
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			paintStrategy.paint(grille, g);
			paintStrategy.paint(gardien, g);
		}
	}
