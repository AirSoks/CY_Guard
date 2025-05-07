package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

import engine.map.Grid;
import engine.map.GridService;

@SuppressWarnings("serial")
public class MainGUI extends JFrame implements Runnable{

	private GridService gridService;
	
	/**
	 * L'interface d'affichage de la simulation
	 */
	private GameDisplay dashboard;
	
	public MainGUI(String title) {
		super(title);
		init();
	}

	private void init() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		GridService.initialize(new Grid(20,20));
		this.gridService = GridService.getInstance();
		
		dashboard = new GameDisplay(gridService);
		contentPane.add(dashboard, BorderLayout.CENTER);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		dashboard.setPreferredSize(new Dimension(400,400));
		pack();
		setVisible(true);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			dashboard.repaint();
		}
	}

}
