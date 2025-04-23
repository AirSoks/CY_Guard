package gui.panel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JTextField;

import config.Settings;
import engine.map.generation.GrilleBuilder;
import engine.personnage.PersonnageManager;
import engine.utilitaire.ChronoSimulation;
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
	
	private final static int INVISIBLE_TEXT_FIELD_SIZE = 0;
	private final static int SIDE_PANEL_SIZE = 225;
	
	/**
	 * Settings de la simulation
	 */
	private Settings settings;

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
	
	/**
	 * Temps écoulé de la simulation (en ms)
	 */
	private ChronoSimulation chrono;

	public MainGUI(String title) throws HeadlessException {
		super(title);
		init();
	}

	/**
	 * Initialise l'interface graphique et les élements du jeu
	 */
	private void init() {
		this.settings = new Settings();
		this.chrono = ChronoSimulation.getInstance();
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		mapBuilder = new GrilleBuilder(settings.getHauteur(), settings.getLargeur(), settings);
		mapBuilder.build();

	    PersonnageManager.initInstance(mapBuilder.getGrille(), settings);
	    manager = PersonnageManager.getInstance();
	    manager.initPersonnages();
	    
	    setJMenuBar(new MenuBar(new ActionButton(this, settings)));

        SidePanel sidePanel = new SidePanel(this);
        sidePanel.setPreferredSize(new Dimension(SIDE_PANEL_SIZE,0));
        contentPane.add(sidePanel, BorderLayout.EAST);
        
		dashboard = new GameDisplay(mapBuilder.getGrille(), manager, settings);
		dashboard.addMouseListener(new ClicsControls(mapBuilder.getGrille(), manager, settings, sidePanel));
		dashboard.setLayout(new BorderLayout());
        contentPane.add(dashboard, BorderLayout.CENTER);

        JTextField invisibleTextField = new JTextField();
        invisibleTextField.setPreferredSize(new Dimension(0,INVISIBLE_TEXT_FIELD_SIZE));
        invisibleTextField.addKeyListener(new KeyControls(manager, dashboard));
        contentPane.add(invisibleTextField, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        redimensionner();
        setVisible(true);
        setResizable(false);
    }

	public void redimensionner() {
		dashboard.setPreferredSize(new Dimension(settings.getWindow_width(), settings.getWindow_height()));
		dashboard.revalidate();
	    pack();
	}
	
	/**
	 * Boucle principale du jeu qui met à jour les personnages
	 */
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(settings.getSpeed());
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			if (active) {
	            chrono.start();
	            manager.actionPersonnages();
	        } else {
	            chrono.pause();
	        }
			dashboard.repaint();
		}
	}

	public void setActive(Boolean active) {
		this.active = active;
		if (active) {
            chrono.start();
		}
		else {
	        chrono.pause();
		}
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
	
	public GameDisplay getDashboard() {
		return dashboard;
	}

	public ChronoSimulation getChrono() {
		return chrono;
	}
}