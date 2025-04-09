package gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

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
	    add(createOtherOptionsPanel(), contrainte);
	    

	    contrainte.gridy = 3;
	    contrainte.gridx = 0;
	    add(createButtonPanel(), contrainte);
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
    	
    	JPanel perameterPanel = new JPanel(new GridLayout(5, 0, 0, 4));
    	perameterPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
    	
    	largeur = new JNumberBoxSimple("Largeur : ", new JNumberFieldRelative(5, 100, personalise));
    	perameterPanel.add(largeur);
		hauteur = new JNumberBoxSimple("Hauteur : ", new JNumberFieldRelative(5, 100, personalise));
		perameterPanel.add(hauteur);
		intrus = new JNumberBoxSimple("Intrus : ", new JNumberFieldRelative(1, 30, personalise));
		perameterPanel.add(intrus);
		gardien = new JNumberBoxSimple("Gardiens : ", new JNumberFieldRelative(1, 10, personalise));
		perameterPanel.add(gardien);
		vision = new JNumberBoxSimple("Vision : ",  new JNumberFieldRelative(1, 10, personalise));
		perameterPanel.add(vision);

    	difficulte.add(perameterPanel);
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
    private JPanel createOtherOptionsPanel() {
    	JPanel autresOption = createSubOptionPanel("Autres Options");
    	autresOption.setLayout(new GridBagLayout());
	    GridBagConstraints contrainte = new GridBagConstraints();
	    
    	JPanel checkBoxPanel = new JPanel();
    	checkBoxPanel.setLayout(new GridLayout(2,0));
    	checkBoxPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
    	this.apparitionIntrus = new JCheckBox("Apparition des intrus");
    	this.communicationGardien = new JCheckBox("Communication entre gardien");
    	checkBoxPanel.add(apparitionIntrus);
    	checkBoxPanel.add(communicationGardien);

	    contrainte.gridy = 0;
	    contrainte.gridy = 0;
        contrainte.weightx = 1;
        contrainte.fill = GridBagConstraints.HORIZONTAL;
        autresOption.add(checkBoxPanel, contrainte);
        
        
        JPanel speedPanel = new JPanel();
    	String[] vitesse = {"Rapide", "Normal", "Lent"};
        JComboBox<String> comboBox = new JComboBox<>(vitesse);
        comboBox.setPreferredSize(new Dimension(20, 20));
        
        speedPanel.setLayout(new GridLayout(0,2));
        speedPanel.add(new JLabel("Vitesse du jeux : "));
        speedPanel.setBorder(BorderFactory.createEmptyBorder(2, 9, 0, 8));
        speedPanel.add(comboBox);
        
	    contrainte.gridy = 0;
	    contrainte.gridy = 1;
	    autresOption.add(speedPanel, contrainte);
        
	    
        JPanel zoomPanel = new JPanel();
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
        slider.setPreferredSize(new Dimension(100, 20));
        slider.setMajorTickSpacing(1);
        slider.setSnapToTicks(true);

        zoomPanel.setLayout(new GridLayout(0,2));
        zoomPanel.add(new JLabel("Zoom de la carte : "));
        zoomPanel.setBorder(BorderFactory.createEmptyBorder(2, 9, 0, 0));
        zoomPanel.add(slider);
        
	    contrainte.gridy = 0;
	    contrainte.gridy = 2;
	    autresOption.add(zoomPanel, contrainte);
    	return autresOption;
    }
    
    /**
	 * Crée le panneau pour les boutons.
	 *
	 * @return Le panneau pour les boutons.
	 */
    private JPanel createButtonPanel() {
    	JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 10, 0));
    	buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	
        JButton btnConfirmer = new JButton("Confirmer");
        JButton btnAnnuler = new JButton("Annuler");
        
        buttonPanel.add(btnConfirmer);
        buttonPanel.add(btnAnnuler);
        return buttonPanel;
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
			this.largeur.getJNumberSelect().setNumber(value);
		}
	}

	/**
	 * Définit la valeur du champ de hauteur.
	 *
	 * @param value La valeur à définir.
	 */
	public void setNumberHauteur(int value) {
		if (value >= hauteur.getJNumberSelect().getNombreMinimal() && value <= hauteur.getJNumberSelect().getNombreMaximal()) {
			this.hauteur.getJNumberSelect().setNumber(value);
		}
	}

	/**
	 * Définit la valeur du champ d'intrus.
	 *
	 * @param value La valeur à définir.
	 */
	public void setNumberIntrus(int value) {
		if (value >= intrus.getJNumberSelect().getNombreMinimal() && value <= intrus.getJNumberSelect().getNombreMaximal()) {
			this.intrus.getJNumberSelect().setNumber(value);
		}
	}

	/**
	 * Définit la valeur du champ de gardien.
	 *
	 * @param value La valeur à définir.
	 */
	public void setNumberGardien(int value) {
		if (value >= gardien.getJNumberSelect().getNombreMinimal() && value <= gardien.getJNumberSelect().getNombreMaximal()) {
			this.gardien.getJNumberSelect().setNumber(value);
		}
	}

	/**
	 * Définit la valeur du champ de vision.
	 *
	 * @param value La valeur à définir.
	 */
	public void setNumberVision(int value) {
		if (value >= vision.getJNumberSelect().getNombreMinimal() && value <= vision.getJNumberSelect().getNombreMaximal()) {
			this.vision.getJNumberSelect().setNumber(value);
		}
	}
}