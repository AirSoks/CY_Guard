package gui.affichage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import config.GameConfiguration;
import engine.map.Case;
import engine.map.Grille;
import engine.map.obstacle.Arbre;
import engine.map.obstacle.Lac;
import engine.map.obstacle.Obstacle;
import engine.map.obstacle.Roche;
import engine.utilitaire.SimulationUtility;

public class DessinerGrille implements Dessiner {
    private Grille grille;
    private boolean enabled = true;

    public DessinerGrille(Grille grille) {
        this.grille = grille;
    }

    @Override
    public void paint(Graphics g) {
        if (!enabled) return;

        int blockSize = GameConfiguration.BLOCK_SIZE;
        Case[][] cases = grille.getGrille();
        int nbLigne = grille.getNbLigne();
        int nbColonne = grille.getNbColonne();

        for (int line = 0; line < nbLigne; line++) {
            for (int col = 0; col < nbColonne; col++) {
                Case cell = cases[line][col];
                Obstacle obstacle = cell.getObstacle();
                int x = col * blockSize;
                int y = line * blockSize;
                if (obstacle instanceof Arbre) {
                	Image tile = SimulationUtility.readImage("src/images/tiles/arbre.png");
                	g.drawImage(tile, line*blockSize, col*blockSize, blockSize, blockSize, null);
                } else if (obstacle instanceof Lac) {
                	Image tile = SimulationUtility.readImage("src/images/tiles/arbre.png");
                	g.drawImage(tile, line*blockSize, col*blockSize, blockSize, blockSize, null);
                } else if (obstacle instanceof Roche) {
                	Image tile = SimulationUtility.readImage("src/images/tiles/arbre.png");
                	g.drawImage(tile, line*blockSize, col*blockSize, blockSize, blockSize, null);
                } else {
                	Image tile = SimulationUtility.readImage("src/images/tiles/plaine.png");
                	g.drawImage(tile, line*blockSize, col*blockSize, blockSize, blockSize, null);
                }
            }
        }
    }

    @Override
    public void activer() {
        this.enabled = true;
    }

    @Override
    public void desactiver() {
        this.enabled = false;
    }

    @Override
    public boolean isActiver() {
        return this.enabled;
    }
}