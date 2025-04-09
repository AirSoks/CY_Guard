package gui.panel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JTextField;

import config.GameConfiguration;
import engine.map.generation.GrilleBuilder;
import engine.personnage.PersonnageManager;
import gui.event.ActionButton;
import gui.event.ClicsControls;
import gui.event.KeyControls;

/**
 * Classe principale de l'interface graphique du simulation
 * Il gère les interactions utilisateur
 * 
 * @author GLP_19
 * @see GameDisplay
 */
@SuppressWarnings("serial")
public class MainGUI extends JFrame implements Runnable{

	/**
	 * Dimension de la fenêtre de simulation
	 */
	private static Dimension preferredSize = new Dimension(GameConfiguration.WINDOW_WIDTH,GameConfiguration.WINDOW_HEIGHT);

	/**
	 * Grille de la simulation
	 */
	private GrilleBuilder mapBuilder;
	
	/**
	 * Gestionnaire des personnages
	 */
	private PersonnageManager manager;
	
	/**
	 * L'interface d'affichage de la simulation
	 */
	private GameDisplay dashboard;
	
	/**
	 * L'êtat de la simulation (true pour activé, false sinon)
	 */
	private Boolean active = false;

	public MainGUI(String title) throws HeadlessException {
		super(title);
		init();
	}

	/**
	 * Initialise l'interface graphique et les élements du jeu
	 */
	private void init() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		mapBuilder = new GrilleBuilder(GameConfiguration.NB_LIGNE, GameConfiguration.NB_COLONNE);
		mapBuilder.build();

	    PersonnageManager.initInstance(mapBuilder.getGrille());
	    this.manager = PersonnageManager.getInstance();
	    manager.initPersonnages();
	    
	    setJMenuBar(new MenuBar(new ActionButton(this)));
        
		dashboard = new GameDisplay(mapBuilder.getGrille(), manager);
		dashboard.addMouseListener(new ClicsControls(mapBuilder.getGrille(), manager));
		dashboard.setPreferredSize(preferredSize);
        contentPane.add(dashboard, BorderLayout.CENTER);

        JTextField invisibleTextField = new JTextField();
        invisibleTextField.addKeyListener(new KeyControls(manager, dashboard));
        contentPane.add(invisibleTextField, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setPreferredSize(preferredSize);
        setResizable(false);
    }

	/**
	 * Boucle principale du jeu qui met à jour les déplacements des personnages
	 */
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(GameConfiguration.GAME_SPEED);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			if (active) {
				manager.actionPersonnages();
			}
			dashboard.repaint();
		}
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public GrilleBuilder getMapBuilder() {
		return mapBuilder;
	}
	
	public PersonnageManager getManager() {
		return manager;
	}
	
	public PaintStrategy getPaintStrategy() {
		return dashboard.getPaintStrategy();
	}
}