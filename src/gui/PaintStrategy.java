package gui;

import java.awt.Color;
import java.awt.Graphics;

import engine.map.Cell;
import engine.map.Grid;
import engine.map.ObstacleType;

public class PaintStrategy {

	public void paint(Grid grid, Graphics g) {
		int blockSize = 20;
		Cell[][] cells = grid.getCells();

		for (int width = 0; width < grid.getWidth(); width++) {
			for (int height = 0; height < grid.getHeight(); height++) {
				Cell cell = cells[width][height];
                ObstacleType obstacle = cell.getObstacle();
                
                switch(obstacle) {
	            	case PLAIN:   
	            		g.setColor(new Color(43, 139, 27));
	                    break;
	                case WATER:
	                	g.setColor(Color.blue);
	                    break;
	                case WALL:   
	                	g.setColor(Color.gray);
	                    break;
	                case TREE:
	                	g.setColor(Color.green);
	                    break;
                }

                g.fillRect(height * blockSize, width * blockSize, blockSize, blockSize);
			}
		}
	}
}
