package gui;

import java.awt.Color;
import java.awt.Graphics;

import config.GameConfiguration;
import engine.map.Case;
import engine.map.Coordonnee;
import engine.map.Grille;
import engine.map.obstacle.Arbre;
import engine.map.obstacle.Lac;
import engine.map.obstacle.Obstacle;
import engine.map.obstacle.Roche;
import engine.personnage.Gardien;

public class PaintStrategy {
	
	public void paint(Grille grille, Graphics graphics) {
		
		int blocksize = GameConfiguration.BLOCK_SIZE;
		Case[][] cases = grille.getGrille();
		int nbLigne = grille.getNbLigne();
		int nbColonne = grille.getNbColonne();
		
		for (int line = 0; line < nbLigne; line++) {
			for (int col = 0; col < nbColonne; col++) {
				Case cell = cases[line][col];
				Obstacle obstacle = cell.getObstacle();
				
				
				 if (obstacle instanceof Arbre) {
	                    graphics.setColor(new Color(43, 139, 27));
				 } else if (obstacle instanceof Lac) {
	                    graphics.setColor(Color.blue);
				 } else if (obstacle instanceof Roche) {
	                    graphics.setColor(Color.gray);
				 } else {
	                    graphics.setColor(Color.yellow);
				 }

				 graphics.fillRect(col * blocksize, line * blocksize, blocksize, blocksize);
			}
		}
		
	}
	
	public void paint(Gardien gardien, Graphics graphics) {
		int blocksize = GameConfiguration.BLOCK_SIZE;
		Coordonnee coordonnee = gardien.getCoordonnee();
		int x = coordonnee.getLigne() * GameConfiguration.BLOCK_SIZE;;
		int y = coordonnee.getColonne() * GameConfiguration.BLOCK_SIZE;;
		
		graphics.setColor(Color.pink);
		graphics.fillRect(y, x, blocksize,blocksize);
	
	}
	
}
