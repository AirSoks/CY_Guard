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
import engine.personnage.Intrus;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;

/**
 * Classe responsable de la gestion de l'affichage des élements de jeu
 * 
 * @author GLP_19
 */
public class PaintStrategy {

	/**
	 * Dessine la grille avec les obstacles
	 * 
	 * @param grille La grille de jeu contenant les obstacles
	 * @param graphics L'objet Graphics utilisé pour le rendu graphique 
	 */
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

	/**
	 * Dessine les personnages sur la grille
	 * 
	 * @param personnages La liste de personnages
	 * @param graphics L'objet Graphics utilisé pour le rendu graphique 
	 */
	public void paint(PersonnageManager personnages, Graphics graphics) {
		int blocksize = GameConfiguration.BLOCK_SIZE;

		for (Personnage personnage : personnages.getPersonnages()) {
			Coordonnee coordonnee = personnage.getCoordonnee();
	        int x = coordonnee.getColonne() * blocksize;
	        int y = coordonnee.getLigne() * blocksize;

	        if (personnage instanceof Gardien) {
	            graphics.setColor(Color.pink);
	        } else if (personnage instanceof Intrus) {
	            graphics.setColor(Color.red);
	        }

	        graphics.fillRect(x, y, blocksize, blocksize);
	    }
	}
}
