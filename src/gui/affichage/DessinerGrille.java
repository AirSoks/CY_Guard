package gui.affichage;

import java.awt.Color;
import java.awt.Graphics;

import config.GameConfiguration;
import engine.map.Case;
import engine.map.Grille;
import engine.map.obstacle.Arbre;
import engine.map.obstacle.Lac;
import engine.map.obstacle.Obstacle;
import engine.map.obstacle.Roche;

public class DessinerGrille implements Dessiner {
    private final Grille grille;
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

                if (obstacle instanceof Arbre) {
                    g.setColor(new Color(43, 139, 27));
                } else if (obstacle instanceof Lac) {
                    g.setColor(Color.blue);
                } else if (obstacle instanceof Roche) {
                    g.setColor(Color.gray);
                } else {
                    g.setColor(Color.yellow);
                }

                g.fillRect(col * blockSize, line * blockSize, blockSize, blockSize);
            }
        }
    }

    @Override
    public void enable() {
        this.enabled = true;
    }

    @Override
    public void disable() {
        this.enabled = false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
