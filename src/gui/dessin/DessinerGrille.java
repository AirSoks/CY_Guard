package gui.dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import config.Settings;
import engine.map.Case;
import engine.map.Grille;
import engine.map.obstacle.Arbre;
import engine.map.obstacle.Lac;
import engine.map.obstacle.Obstacle;
import engine.map.obstacle.Roche;
import engine.utilitaire.SimulationUtility;

/**
 * La classe DessinerGrille implémente l'interface Dessiner et DPerformanceElement.
 * Elle est responsable du dessin de la grille de jeu et de ses obstacles.
 */
public class DessinerGrille implements Dessiner, DessinPerformance {

    private Settings settings;
    private Grille grille;
    private boolean performanceMode = false;
    
    private Map<String, Image> image = new HashMap<>();
    
    /**
     * Constructeur de la classe DessinerGrille
     *
     * @param grille La grille du jeu
     */
    public DessinerGrille(Grille grille, Settings settings) {
        this.grille = grille;
        this.settings = settings;
    }

    /**
     * Obtient l'image correspondante à un chemin donné.
     * 
     * @param path Le chemin de l'image
     * @return L'image correspondante
     */
    public Image getImage(String path) {
        if (!image.containsKey(path)) {
            image.put(path, SimulationUtility.readImage(path));
        }
        return image.get(path);
    }
    
    /**
     * Dessine la grille de jeu et ses obstacles.
     *
     * @param g L'objet Graphics utilisé pour dessiner
     */
    @Override
    public void paint(Graphics g) {
    	
        int blockSize = settings.getBlock_size();
        Case[][] cases = grille.getGrille();
        int nbLigne = grille.getNbLigne();
        int nbColonne = grille.getNbColonne();

        for (int line = 0; line < nbLigne; line++) {
            for (int col = 0; col < nbColonne; col++) {
                Case c = cases[line][col];
                Obstacle obstacle = c.getObstacle();

            	if (!performanceMode) {
                    if (obstacle instanceof Arbre) {
                    	Image tile = getImage("src/images/tiles/arbre.png");
                    	g.drawImage(tile, col*blockSize, line*blockSize, blockSize, blockSize, null);
                    } else if (obstacle instanceof Lac) {
                    	Image tile = getLacTile(line, col);
                    	g.drawImage(tile, col*blockSize, line*blockSize, blockSize, blockSize, null);
                    } else if (obstacle instanceof Roche) {
                    	Image tile = getImage("src/images/tiles/roche.png");
                    	g.drawImage(tile, col*blockSize, line*blockSize, blockSize, blockSize, null);
                    } else {
                    	Image tile = getImage("src/images/tiles/plaine.png");
                    	g.drawImage(tile, col*blockSize, line*blockSize, blockSize, blockSize, null);
                    }
            	} 
            	else {
            		if (obstacle instanceof Arbre) {
                        g.setColor(new Color(43, 139, 27));
                    } else if (obstacle instanceof Lac) {
                        g.setColor(Color.blue);
                    } else if (obstacle instanceof Roche) {
                        g.setColor(Color.gray);
                    } else {
                        g.setColor(Color.green);
                    }

                    g.fillRect(col * blockSize, line * blockSize, blockSize, blockSize);
                }
            }
        }
    }

    /**
     * Obtient l'image correspondante d'une tuile de lac en fonction de ses
     * coordonnées et des tuiles adjacentes.
     * 
     * @param line La ligne de la tuile
     * @param col La colonne de la tuile
     * @return L'image correspondante de la tuile de lac
     */
    public Image getLacTile(int line, int col) {
        Case[][] cases = grille.getGrille();
        int nbLigne = grille.getNbLigne();
        int nbColonne = grille.getNbColonne();

        boolean leftLac = (col > 0) && (cases[line][col-1].getObstacle() instanceof Lac);
        boolean topLac = (line > 0) && (cases[line-1][col].getObstacle() instanceof Lac);
        boolean rightLac = (col < nbColonne-1) && (cases[line][col+1].getObstacle() instanceof Lac);
        boolean bottomLac = (line < nbLigne-1) && (cases[line+1][col].getObstacle() instanceof Lac);
        
        if (leftLac && topLac && rightLac && bottomLac) {
        	return getImage("src/images/tiles/lac/l0.png");
        } else if (!leftLac && topLac && rightLac && bottomLac) {
            return getImage("src/images/tiles/lac/l1.png");
        } else if (leftLac && !topLac && rightLac && bottomLac) {
            return getImage("src/images/tiles/lac/l2.png");
        } else if (leftLac && topLac && !rightLac && bottomLac) {
            return getImage("src/images/tiles/lac/l3.png");
        } else if (leftLac && topLac && rightLac && !bottomLac) {
            return getImage("src/images/tiles/lac/l4.png");
        }  else if (!leftLac && !topLac && rightLac && bottomLac) {
            return getImage("src/images/tiles/lac/l5.png");
        } else if (leftLac && !topLac && !rightLac && bottomLac) {
            return getImage("src/images/tiles/lac/l6.png");
        } else if (leftLac && topLac && !rightLac && !bottomLac) {
            return getImage("src/images/tiles/lac/l7.png");
        } else if (!leftLac && topLac && rightLac && !bottomLac) {
            return getImage("src/images/tiles/lac/l8.png");
        } else if (!leftLac && !topLac && rightLac && !bottomLac) {
            return getImage("src/images/tiles/lac/l9.png");
        } else if (!leftLac && !topLac && !rightLac && bottomLac) {
            return getImage("src/images/tiles/lac/l10.png");
        } else if (leftLac && !topLac && !rightLac && !bottomLac) {
            return getImage("src/images/tiles/lac/l11.png");
        } else if (!leftLac && topLac && !rightLac && !bottomLac) {
            return getImage("src/images/tiles/lac/l12.png");
        } else if (!leftLac && topLac && !rightLac && bottomLac) {
            return getImage("src/images/tiles/lac/l14.png");
        } else if (leftLac && !topLac && rightLac && !bottomLac) {
            return getImage("src/images/tiles/lac/l15.png");
        } else {
            return getImage("src/images/tiles/lac/l13.png");
        }
    }
    
    /**
     * Obtient le nom de l'élément à dessiner.
     *
     * @return Le nom de l'élément
     */
    @Override
	public String getNom() {
		return "GRILLE";
	}

    /**
     * Active ou désactive le mode performance.
     *
     * @param etat L'état d'activation
     */
	@Override
	public void setPerformance(Boolean etat) {
		this.performanceMode = etat;
	}
}