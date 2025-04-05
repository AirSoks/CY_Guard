package gui.dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

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
    
    private boolean dessiner = true;
    private boolean performanceMode = false;
    
    private Map<String, Image> image = new HashMap<>();
    
    public DessinerGrille(Grille grille) {
        this.grille = grille;
    }

    public Image getImage(String path) {
        if (!image.containsKey(path)) {
            image.put(path, SimulationUtility.readImage(path));
        }
        return image.get(path);
    }
    @Override
    public void paint(Graphics g) {
    	
    	if (!dessiner) return;
    	
        int blockSize = GameConfiguration.BLOCK_SIZE;
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
                    	Image tile = getLacTile(line,col);
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
                        g.setColor(Color.yellow);
                    }

                    g.fillRect(col * blockSize, line * blockSize, blockSize, blockSize);
                }
            }
        }
    }

    public Image getLacTile(int line, int col) {
        Case[][] cases = grille.getGrille();
        int nbLigne = grille.getNbLigne();
        int nbColonne = grille.getNbColonne();

        boolean leftLac = (col>0)&&(cases[line][col-1].getObstacle() instanceof Lac);
        boolean topLac = (line>0)&&(cases[line-1][col].getObstacle() instanceof Lac);
        boolean rightLac = (col<nbColonne-1)&&(cases[line][col+1].getObstacle() instanceof Lac);
        boolean bottomLac = (line<nbLigne-1)&&(cases[line+1][col].getObstacle() instanceof Lac);
        
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
    
    @Override
	public String getNom() {
		return "GRILLE";
	}

	@Override
	public void setActive(Boolean etat) {
		this.dessiner = etat;
	}

	@Override
	public void setPerformance(Boolean etat) {
		this.performanceMode = etat;
	}
}