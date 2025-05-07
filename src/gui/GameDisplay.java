package gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import engine.map.GridService;

@SuppressWarnings("serial")
public class GameDisplay extends JPanel {

    private PaintStrategy paintStrategy;
    
    private GridService gridService;

    public GameDisplay(GridService gridService) {
        this.gridService = gridService;
        this.paintStrategy = new PaintStrategy();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        paintStrategy.paint(gridService.getGrid(), g);
    }
}