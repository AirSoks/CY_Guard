package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;

import config.GameConfiguration;
import engine.map.Case;
import engine.map.Coordonnee;
import engine.map.Direction;
import engine.map.Grille;
import engine.map.generation.GrilleBuilder;
import engine.personnage.Gardien;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;
import engine.personnage.deplacement.DeplacementCase;
import engine.personnage.deplacement.DeplacementFactory;
import engine.personnage.deplacement.DeplacementManuel;

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
        invisibleTextField.addKeyListener(new KeyControls());
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
				manager.deplacerPersonnages();
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
	
	/**
	 * Classe interne pour gérer les contrôles clavier
	 */
	public class KeyControls extends KeyAdapter{

		public void keyPressed(KeyEvent e) {
		    int keyCode = e.getKeyCode();
		    Gardien gardienActif = manager.getGardienActif();
		    if (gardienActif == null) { 
		    	return; 
		    }
		    if (!(gardienActif.getDeplacement() instanceof DeplacementManuel)) { return; }

		    switch (keyCode) {

		    case KeyEvent.VK_LEFT: // Flèche gauche
	        case KeyEvent.VK_Q:
	        case KeyEvent.VK_A:
	        	gardienActif.setDirection(Direction.GAUCHE);
	            break;

	        case KeyEvent.VK_RIGHT: // Flèche droit
	        case KeyEvent.VK_D:
	        	gardienActif.setDirection(Direction.DROITE);
	            break;

	        case KeyEvent.VK_UP: // Flèche haut
	        case KeyEvent.VK_Z:
	        case KeyEvent.VK_W:
	        	gardienActif.setDirection(Direction.HAUT);
	            break;

	        case KeyEvent.VK_DOWN: // Flèche bas
	        case KeyEvent.VK_S:
	        	gardienActif.setDirection(Direction.BAS);
	            break;
			}
			dashboard.repaint();
		}
	}
	
	public class ClicsControls extends MouseAdapter {

		/**
		 * Grille du simulation
		 */
		private Grille grille;
		
		/**
		 * Gestionnaire des personnages
		 */
		private PersonnageManager manager;
		
		private Personnage personnagePressed;

	    public ClicsControls(Grille grille, PersonnageManager manager) {
	        this.grille = grille;
	        this.manager = manager;
	    }

	    @Override
	    public void mouseClicked(MouseEvent e) {
	        int blockSize = GameConfiguration.BLOCK_SIZE;
	        int colonne = e.getX() / blockSize;
	        int ligne = e.getY() / blockSize;
	        
            Coordonnee coordonnee = new Coordonnee(ligne, colonne);
	        Case c = grille.getCase(coordonnee);
	        if (c == null) { return; }
	        
	        List<Gardien> gardiens = manager.getGardiens(coordonnee);
	        if (gardiens == null || gardiens.isEmpty()) { return; }
	        Gardien gardien = gardiens.get(0);
	        Gardien gardienActif = manager.getGardienActif();
	        
	        System.out.println("Gardien actif : " + gardienActif);
	        System.out.println("Gardien : " + gardien);

	        if (gardienActif != null && gardienActif.equals(gardien)) {
	        	manager.removeGardienActif();
	        } else {
	            manager.setGardienActif(gardien);
	        }
	    }

		@Override
		public void mousePressed(MouseEvent e) {
	        int blockSize = GameConfiguration.BLOCK_SIZE;
	        int colonne = e.getX() / blockSize;
	        int ligne = e.getY() / blockSize;
	        
            Coordonnee coordonnee = new Coordonnee(ligne, colonne);
	        Case c = grille.getCase(coordonnee);
	        if (c == null) { return; }
	        
	        List<Personnage> personnages = manager.getPersonnages(coordonnee);
	        if (personnages == null || personnages.isEmpty()) { return; }
	        personnagePressed = personnages.get(0);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
	        int blockSize = GameConfiguration.BLOCK_SIZE;
	        int colonne = e.getX() / blockSize;
	        int ligne = e.getY() / blockSize;
	        
            Coordonnee coordonnee = new Coordonnee(ligne, colonne);
	        Case c = grille.getCase(coordonnee);
	        
	        if (c == null || personnagePressed == null) {
	        	return;
	        }

	        DeplacementCase deplacementCase = (DeplacementCase) DeplacementFactory.getDeplacement("Case", manager, grille);
	        if (personnagePressed.equals(manager.getGardienActif())) {
	        	manager.setGardienActif(null);
	        }
	        deplacementCase.setCible(coordonnee);
	        personnagePressed.setDeplacement(deplacementCase);
	        
	        personnagePressed = null;
		}
	}
}