package gui.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import gui.event.ActionButton;
import gui.numberField.JNumberBoxDouble;
import gui.numberField.JNumberBoxSimple;
import gui.numberField.JNumberFieldRelative;

@SuppressWarnings("serial")
public class OptionsPanel extends JDialog {
	
	/**
	 * Utilisation d'un singleton
	 */
	private static OptionsPanel instance;

	private JRadioButton debutant, intermediaire, difficile, extratersetre, personalise;
	private JNumberBoxSimple largeur, hauteur, gardien, intrus, vision;
	private JNumberBoxDouble casesLacs, casesArbres, casesRoches, elementsLacs, elementsArbres, elementsRoches;
	private JCheckBox apparitionIntrus, communicationGardien;

	/**
	 * Constructeur de la classe OptionsPanel.
	 * Initialise la boîte de dialogue avec le parent spécifié et configure les composants.
	 *
	 * @param parent La fenêtre principale qui est le parent de cette boîte de dialogue.
	 */
    private OptionsPanel(JFrame parent, ActionButton actionButton) {
        super(parent, "Options", true);
        createLayout(actionButton);
        resetLocation(parent);
        setModal(false);
        setResizable(false);
    }
    
    /**
	 * Initialise l'instance de OptionsPanel si elle n'existe pas.
	 *
	 * @param parent La fenêtre principale qui est le parent de cette boîte de dialogue.
	 * @param actionButton Le bouton d'action associé.
	 */
	public static void initInstance(JFrame parent, ActionButton actionButton) {
		if (instance == null) {
	        instance = new OptionsPanel(parent, actionButton);
		}
    }
	
	/**
	 * Retourne l'instance unique de OptionsPanel.
	 *
	 * @return L'instance de OptionsPanel.
	 * @throws IllegalStateException si l'instance n'a pas été initialisée.
	 */
	public static OptionsPanel getInstance() {
		if (instance == null) {
			throw new IllegalStateException("Grille non initialisée");
		}
		return instance;
	}
	
	/**
	 * Réinitialise la position de la boîte de dialogue par rapport à la fenêtre parente.
	 *
	 * @param parent La fenêtre principale qui est le parent de cette boîte de dialogue.
	 */
	public void resetLocation(JFrame parent) {
        pack();
        setLocationRelativeTo(parent);
	}

	/**
	 * Crée la mise en page de la boîte de dialogue en utilisant un GridBagLayout.
	 *
	 * @param actionButton Le bouton d'action associé.
	 */
	private void createLayout(ActionButton actionButton) {
	    setLayout(new GridBagLayout());
	    GridBagConstraints contrainte = new GridBagConstraints();
	    
	    contrainte.gridy = 0;
	    contrainte.gridy = 0;
	    contrainte.fill = GridBagConstraints.HORIZONTAL;
	    add(createDificultyPanel(actionButton), contrainte);

	    contrainte.gridy = 1;
	    contrainte.gridx = 0;
	    add(createInitialisationPanel(), contrainte);

	    contrainte.gridy = 2;
	    contrainte.gridx = 0;
	    add(creatOtherOptions(), contrainte);
	}
    
    /**
	 * Crée le panneau de sélection de la difficulté.
	 *
	 * @param actionButton Le bouton d'action associé.
	 * @return Le panneau de sélection de la difficulté.
	 */
    private JPanel createDificultyPanel(ActionButton actionButton) {
    	JPanel difficulte = createSubOptionPanel("Difficulté");
    	JPanel radiosPanel = new JPanel(new GridLayout(5,0));
    	ButtonGroup buttonGroup = new ButtonGroup();
    	
    	this.debutant = new JRadioButton("Débutant", true);
    	this.debutant.addActionListener(actionButton);
    	buttonGroup.add(debutant);
    	radiosPanel.add(debutant);
    	this.intermediaire = new JRadioButton("Intermédiaire", false);
    	this.intermediaire.addActionListener(actionButton);
    	buttonGroup.add(intermediaire);
    	radiosPanel.add(intermediaire);
    	this.difficile = new JRadioButton("Difficile", false);
    	this.difficile.addActionListener(actionButton);
    	buttonGroup.add(difficile);
    	radiosPanel.add(difficile);
    	this.extratersetre = new JRadioButton("Extraterestre", false);
    	this.extratersetre.addActionListener(actionButton);
    	buttonGroup.add(extratersetre);
    	radiosPanel.add(extratersetre);
    	this.personalise = new JRadioButton("Personnalisé", false);
    	this.personalise.addActionListener(actionButton);
    	buttonGroup.add(personalise);
    	radiosPanel.add(personalise);
    	difficulte.add(radiosPanel);
    	
    	JPanel labelPanel = new JPanel(new GridLayout(5, 0, 0, 4));
    	labelPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
    	
    	largeur = new JNumberBoxSimple("Largeur : ", new JNumberFieldRelative(5, 100, personalise));
		labelPanel.add(largeur);
		hauteur = new JNumberBoxSimple("Hauteur : ", new JNumberFieldRelative(5, 100, personalise));
		labelPanel.add(hauteur);
		gardien = new JNumberBoxSimple("Intrus : ", new JNumberFieldRelative(1, 30, personalise));
		labelPanel.add(gardien);
		intrus = new JNumberBoxSimple("Gardiens : ", new JNumberFieldRelative(1, 10, personalise));
		labelPanel.add(intrus);
		vision = new JNumberBoxSimple("Vision : ",  new JNumberFieldRelative(1, 10, personalise));
		labelPanel.add(vision);
    	difficulte.add(labelPanel);
    	
    	return difficulte;
    }
    
    /**
	 * Crée le panneau d'initialisation.
	 *
	 * @return Le panneau d'initialisation.
	 */
    private JPanel createInitialisationPanel() {
    	
    	JPanel initialisation = createSubOptionPanel("Initialisation");
    	initialisation.setLayout(new GridLayout(2,0));
    	
    	JPanel cases = createSubOptionPanel("Cases");
    	JPanel casesPanel = new JPanel(new GridLayout(3, 1));
    	JPanel casesPanel2 = new JPanel(new GridLayout(3, 0, 0, 8));
    	
    	JLabel labelCasesLacs = new JLabel("Lacs : ");
    	JLabel labelCasesArbres = new JLabel("Arbres : ");
    	JLabel labelCasesRoches = new JLabel("Roches : ");
    	casesPanel2.add(labelCasesLacs);
    	casesPanel2.add(labelCasesArbres);
    	casesPanel2.add(labelCasesRoches);
    	
    	casesLacs = new JNumberBoxDouble("min : ", "max : ", 50, 500);
    	casesArbres = new JNumberBoxDouble("min : ", "max : ", 50, 500);
    	casesRoches = new JNumberBoxDouble("min : ", "max : ", 50, 500);
    	casesPanel.add(casesLacs);
    	casesPanel.add(casesArbres);
    	casesPanel.add(casesRoches);

    	cases.setLayout(new GridBagLayout());
    	GridBagConstraints contrainte1 = new GridBagConstraints();

    	contrainte1.gridx = 0;
    	contrainte1.gridy = 0;
    	contrainte1.weightx = 1;
    	cases.add(casesPanel2, contrainte1);

    	contrainte1.gridx = 1;
    	contrainte1.gridy = 0;
    	contrainte1.weightx = 1;
    	cases.add(casesPanel, contrainte1);

    	
    	JPanel elements = createSubOptionPanel("Elements");
    	JPanel elementsPanel = new JPanel(new GridLayout(3, 1));
    	JPanel elementsPanel2 = new JPanel(new GridLayout(3, 0, 0, 8));
    	
    	elementsLacs = new JNumberBoxDouble("min : ", "max : ", 1, 10);
    	elementsArbres = new JNumberBoxDouble("min : ", "max : ", 1, 10);
    	elementsRoches = new JNumberBoxDouble("min : ", "max : ", 1, 10);
    	elementsPanel.add(elementsLacs);
    	elementsPanel.add(elementsArbres);
    	elementsPanel.add(elementsRoches);
    	
    	JLabel labelElementsLacs = new JLabel("Lacs : ");
    	JLabel labelElementsArbres = new JLabel("Arbres : ");
    	JLabel labelElementsRoches = new JLabel("Roches : ");
    	elementsPanel2.add(labelElementsLacs);
    	elementsPanel2.add(labelElementsArbres);
    	elementsPanel2.add(labelElementsRoches);
    	
    	elements.setLayout(new GridBagLayout());
    	GridBagConstraints contrainte2 = new GridBagConstraints();

    	contrainte2.gridx = 0;
    	contrainte2.gridy = 0;
    	contrainte2.weightx = 1;
    	elements.add(elementsPanel2, contrainte2);

    	contrainte2.gridx = 1;
    	contrainte2.gridy = 0;
    	contrainte2.weightx = 1;
    	elements.add(elementsPanel, contrainte2);
    	
    	initialisation.add(cases);
    	initialisation.add(elements);
    	return initialisation;
    }
    
    /**
	 * Crée le panneau pour les autres options.
	 *
	 * @return Le panneau pour les autres options.
	 */
    private JPanel creatOtherOptions() {
    	JPanel autresOption = createSubOptionPanel("Autres Options");
    	autresOption.setLayout(new GridLayout());
    	
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(2,0));
    	panel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
    	
    	this.apparitionIntrus = new JCheckBox("Apparition des intrus");
    	this.communicationGardien = new JCheckBox("Communication entre gardien");
    	panel.add(apparitionIntrus);
    	panel.add(communicationGardien);
    	
    	autresOption.add(panel);
    	return autresOption;
    }
    
    /**
	 * Crée un panneau avec une bordure et un titre spécifié.
	 *
	 * @param title Le titre du panneau.
	 * @return Le panneau créé.
	 */
    private JPanel createSubOptionPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
		return panel;
    }

    /**
	 * Définit la valeur du champ de largeur.
	 *
	 * @param value La valeur à définir.
	 */
	public void setNumberLargeur(int value) {
		if (value >= largeur.getJNumberSelect().getNombreMinimal() && value <= largeur.getJNumberSelect().getNombreMaximal()) {
			this.largeur.getJNumberSelect().setText(String.valueOf(value));
		}
	}

	/**
	 * Définit la valeur du champ de hauteur.
	 *
	 * @param value La valeur à définir.
	 */
	public void setNumberHauteur(int value) {
		if (value >= hauteur.getJNumberSelect().getNombreMinimal() && value <= hauteur.getJNumberSelect().getNombreMaximal()) {
			this.hauteur.getJNumberSelect().setText(String.valueOf(value));
		}
	}

	/**
	 * Définit la valeur du champ de gardien.
	 *
	 * @param value La valeur à définir.
	 */
	public void setNumberGardien(int value) {
		if (value >= gardien.getJNumberSelect().getNombreMinimal() && value <= gardien.getJNumberSelect().getNombreMaximal()) {
			this.gardien.getJNumberSelect().setText(String.valueOf(value));
		}
	}

	/**
	 * Définit la valeur du champ d'intrus.
	 *
	 * @param value La valeur à définir.
	 */
	public void setNumberIntrus(int value) {
		if (value >= intrus.getJNumberSelect().getNombreMinimal() && value <= intrus.getJNumberSelect().getNombreMaximal()) {
			this.intrus.getJNumberSelect().setText(String.valueOf(value));
		}
	}

	/**
	 * Définit la valeur du champ de vision.
	 *
	 * @param value La valeur à définir.
	 */
	public void setNumberVision(int value) {
		if (value >= vision.getJNumberSelect().getNombreMinimal() && value <= vision.getJNumberSelect().getNombreMaximal()) {
			this.vision.getJNumberSelect().setText(String.valueOf(value));
		}
	}
}