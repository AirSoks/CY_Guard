package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	 * Dimension de la fenêtre du simulation
	 */
	private static Dimension preferredSize = new Dimension(GameConfiguration.WINDOW_WIDTH,GameConfiguration.WINDOW_HEIGHT);

	/**
	 * Grille du simulation
	 */
	private Grille grille;
	
	/**
	 * Gestionnaire des personnages
	 */
	private PersonnageManager manager;
	
	/**
	 * L'interface d'affichage du simulation
	 */
	private GameDisplay dashboard;

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

		GrilleBuilder mapBuilder = new GrilleBuilder(GameConfiguration.NB_LIGNE, GameConfiguration.NB_COLONNE);
		mapBuilder.build();
	    this.grille = mapBuilder.getGrille();

	    PersonnageManager.initInstance(grille);
	    this.manager = PersonnageManager.getInstance();
	    
	    manager.ajouterGardien(2);
	    manager.ajouterIntrus(5);

		dashboard = new GameDisplay(this.grille, manager);
		dashboard.addMouseListener(new ClicsControls(grille, manager));
		dashboard.setPreferredSize(preferredSize);
		contentPane.add(dashboard,BorderLayout.CENTER);

		JTextField textField = new JTextField();
        textField.addKeyListener(new KeyControls());
        contentPane.add(textField, BorderLayout.SOUTH);

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
			manager.deplacerPersonnages();
			dashboard.repaint();
		}
	}

	/**
	 * Classe interne pour gérer les contrôles clavier
	 */
	public class KeyControls implements KeyListener{

		@Override
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

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}
	
	public class ClicsControls implements MouseListener {

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
	        manager.setGardienActif(gardien);
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

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}