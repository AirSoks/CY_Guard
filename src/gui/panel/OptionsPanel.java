package gui.panel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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

import config.Settings;
import gui.event.ActionButton;
import gui.numberField.JNumberBoxDouble;
import gui.numberField.JNumberBoxSimple;
import gui.numberField.JNumberFieldRelativeToButton;

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
	private JComboBox<String> speedBox;

	/**
	 * Constructeur de la classe OptionsPanel.
	 * Initialise la boîte de dialogue avec le parent spécifié et configure les composants.
	 *
	 * @param parent La fenêtre principale qui est le parent de cette boîte de dialogue.
	 */
    private OptionsPanel(JFrame parent, ActionButton actionButton, Settings settings) {
        super(parent, "Options", true);
        createLayout(actionButton, settings);
		setLimitsObstacles(30, 30);
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
	public static void initInstance(JFrame parent, ActionButton actionButton, Settings settings) {
		if (instance == null) {
	        instance = new OptionsPanel(parent, actionButton, settings);
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
			throw new IllegalStateException("OptionsPanel non initialisée");
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
	private void createLayout(ActionButton actionButton, Settings settings) {
	    setLayout(new GridBagLayout());
	    GridBagConstraints contrainte = new GridBagConstraints();
	    
	    contrainte.gridy = 0;
	    contrainte.gridx = 0;
	    contrainte.fill = GridBagConstraints.HORIZONTAL;
	    add(createDificultyPanel(actionButton), contrainte);
	    
	    contrainte.gridy = 1;
	    contrainte.gridx = 0;
	    add(createInitialisationPanel(settings), contrainte);
	    
	    contrainte.gridy = 2;
	    contrainte.gridx = 0;
	    add(createOtherOptionsPanel(settings), contrainte);
	    
	    contrainte.gridy = 3;
	    contrainte.gridx = 0;
	    add(createButtonPanel(actionButton, settings), contrainte);
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
    	
    	this.debutant = new JRadioButton("Débutant", false);
    	this.debutant.addActionListener(actionButton);
    	buttonGroup.add(debutant);
    	radiosPanel.add(debutant);
    	this.intermediaire = new JRadioButton("Intermédiaire", true);
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
    	largeur = new JNumberBoxSimple("Largeur : ", new JNumberFieldRelativeToButton(Settings.NB_LARGEUR_MIN, Settings.NB_LARGEUR_MAX, personalise));
		largeur.getJNumberSelect().addFocusListener(new FocusControls());
		largeur.getJNumberSelect().setNumber(30);
    	perameterPanel.add(largeur);
		hauteur = new JNumberBoxSimple("Hauteur : ", new JNumberFieldRelativeToButton(Settings.NB_HAUTEUR_MIN, Settings.NB_HAUTEUR_MAX, personalise));
		hauteur.getJNumberSelect().addFocusListener(new FocusControls());
		perameterPanel.add(hauteur);
		hauteur.getJNumberSelect().setNumber(30);
		intrus = new JNumberBoxSimple("Intrus : ", new JNumberFieldRelativeToButton(Settings.NB_INTRUS_MIN, Settings.NB_INTRUS_MAX, personalise));
		perameterPanel.add(intrus);
		intrus.getJNumberSelect().setNumber(5);
		gardien = new JNumberBoxSimple("Gardiens : ", new JNumberFieldRelativeToButton(Settings.NB_GARDIEN_MIN, Settings.NB_GARDIEN_MAX, personalise));
		perameterPanel.add(gardien);
		gardien.getJNumberSelect().setNumber(2);
		vision = new JNumberBoxSimple("Vision : ",  new JNumberFieldRelativeToButton(Settings.NB_VISION_MIN, Settings.NB_VISION_MAX, personalise));
		perameterPanel.add(vision);
		vision.getJNumberSelect().setNumber(5);

    	difficulte.add(perameterPanel);
    	return difficulte;
    }
    
    /**
	 * Crée le panneau d'initialisation.
	 *
	 * @return Le panneau d'initialisation.
	 */
    private JPanel createInitialisationPanel(Settings settings) {
    	
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
    	
    	casesLacs = new JNumberBoxDouble("min : ", "max : ", 0, 1);
    	casesArbres = new JNumberBoxDouble("min : ", "max : ", 0, 1);
    	casesRoches = new JNumberBoxDouble("min : ", "max : ", 0, 1);
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
    	
    	elementsLacs = new JNumberBoxDouble("min : ", "max : ", 0, 1);
    	elementsArbres = new JNumberBoxDouble("min : ", "max : ", 0, 1);
    	elementsRoches = new JNumberBoxDouble("min : ", "max : ", 0, 1);
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
    private JPanel createOtherOptionsPanel(Settings settings) {
    	JPanel autresOption = createSubOptionPanel("Autres Options");
    	autresOption.setLayout(new GridBagLayout());
	    GridBagConstraints contrainte = new GridBagConstraints();
	    
    	JPanel checkBoxPanel = new JPanel();
    	checkBoxPanel.setLayout(new GridLayout(2,0));
    	checkBoxPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
    	this.apparitionIntrus = new JCheckBox("Apparition des intrus", settings.getApparitionIntrus());
    	this.communicationGardien = new JCheckBox("Communication entre gardien", settings.getCommunicationGardien());
    	checkBoxPanel.add(apparitionIntrus);
    	checkBoxPanel.add(communicationGardien);

	    contrainte.gridy = 0;
	    contrainte.gridy = 0;
        contrainte.weightx = 1;
        contrainte.fill = GridBagConstraints.HORIZONTAL;
        autresOption.add(checkBoxPanel, contrainte);
        
        
        JPanel speedPanel = new JPanel();
    	String[] vitesse = {Settings.SPEED_RAPIDE, Settings.SPEED_NORMAL, Settings.SPEED_LENT};
        this.speedBox = new JComboBox<>(vitesse);
        speedBox.setSelectedItem(Settings.SPEED_NORMAL);
        this.speedBox.setPreferredSize(new Dimension(20, 20));
        
        speedPanel.setLayout(new GridLayout(0,2));
        speedPanel.add(new JLabel("Vitesse du jeux : "));
        speedPanel.setBorder(BorderFactory.createEmptyBorder(2, 9, 0, 8));
        speedPanel.add(speedBox);
        
	    contrainte.gridy = 0;
	    contrainte.gridy = 1;
	    autresOption.add(speedPanel, contrainte);

    	return autresOption;
    }
    
    /**
	 * Crée le panneau pour les boutons.
	 *
	 * @return Le panneau pour les boutons.
	 */
    private JPanel createButtonPanel(ActionButton actionButton, Settings settings) {
    	JPanel buttonPanel = new JPanel(new GridLayout(0, 3, 2, 2));

        JButton confirmer = new JButton("Confirmer");
        JButton annuler = new JButton("Annuler");
        JButton defaut = new JButton("Défaut");
        confirmer.setPreferredSize(new Dimension(40, 30));
        annuler.setPreferredSize(new Dimension(40, 30));
        defaut.setPreferredSize(new Dimension(40, 30));
        confirmer.setMargin(new Insets(0,5,0,5));
        annuler.setMargin(new Insets(0,5,0,5));
        defaut.setMargin(new Insets(0,5,0,5));

        buttonPanel.add(confirmer);
        confirmer.addActionListener(actionButton);
        
        buttonPanel.add(annuler);
        annuler.addActionListener(actionButton);
        
        buttonPanel.add(defaut);
        defaut.addActionListener(actionButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(4, 2, 6, 2));
        
        return buttonPanel;
    }
    
    public void loadSettings(Settings settings) {

        setNumberLargeur(settings.getLargeur());
        setNumberHauteur(settings.getHauteur());
        setNumberIntrus(settings.getIntrus());
        setNumberGardien(settings.getGardien());
        setNumberVision(settings.getVision());
        
        personalise.setSelected(true);
        setLimitsObstacles(settings.getLargeur(), settings.getHauteur());

        casesLacs.setNumbers(settings.getCases_lacs_min(), settings.getCases_lacs_max());
        casesArbres.setNumbers(settings.getCases_arbres_min(), settings.getCases_arbres_max());
        casesRoches.setNumbers(settings.getCases_roches_min(), settings.getCases_roches_max());

        elementsLacs.setNumbers(settings.getElements_lacs_min(), settings.getElements_lacs_max());
        elementsArbres.setNumbers(settings.getElements_arbres_min(), settings.getElements_arbres_max());
        elementsRoches.setNumbers(settings.getElements_roches_min(), settings.getElements_roches_max());

        apparitionIntrus.setSelected(settings.getApparitionIntrus());
        communicationGardien.setSelected(settings.getCommunicationGardien());

        String speed;
        if (settings.getSpeed() == Settings.SPEED_LENT_VALUE) {
            speed = Settings.SPEED_LENT;
        } else if (settings.getSpeed() == Settings.SPEED_NORMAL_VALUE) {
            speed = Settings.SPEED_NORMAL;
        } else {
            speed = Settings.SPEED_RAPIDE;
        }
        speedBox.setSelectedItem(speed);
    }
    
    public void applySettings(Settings settings) {

        settings.setLargeur(largeur.getJNumberSelect().getNumber());
        settings.setHauteur(hauteur.getJNumberSelect().getNumber());
        settings.setIntrus(intrus.getJNumberSelect().getNumber());
        settings.setGardien(gardien.getJNumberSelect().getNumber());
        settings.setVision(vision.getJNumberSelect().getNumber());

        settings.setCases_lacs_min(casesLacs.getJNumberMin().getNumber());
        settings.setCases_lacs_max(casesLacs.getJNumberMax().getNumber());
        
        settings.setCases_arbres_min(casesArbres.getJNumberMin().getNumber());
        settings.setCases_arbres_max(casesArbres.getJNumberMax().getNumber());
        
        settings.setCases_roches_min(casesRoches.getJNumberMin().getNumber());
        settings.setCases_roches_max(casesRoches.getJNumberMax().getNumber());

        settings.setElements_lacs_min(elementsLacs.getJNumberMin().getNumber());
        settings.setElements_lacs_max(elementsLacs.getJNumberMax().getNumber());
        
        settings.setElements_arbres_min(elementsArbres.getJNumberMin().getNumber());
        settings.setElements_arbres_max(elementsArbres.getJNumberMax().getNumber());
        
        settings.setElements_roches_min(elementsRoches.getJNumberMin().getNumber());
        settings.setElements_roches_max(elementsRoches.getJNumberMax().getNumber());

        settings.setApparitionIntrus(apparitionIntrus.isSelected());
        settings.setCommunicationGardien(communicationGardien.isSelected());

        String selectedSpeed = (String) speedBox.getSelectedItem();
        switch(selectedSpeed) {
            case Settings.SPEED_LENT:
                settings.setSpeed(Settings.SPEED_LENT_VALUE);
                break;
            case Settings.SPEED_NORMAL:
                settings.setSpeed(Settings.SPEED_NORMAL_VALUE);
                break;
            case Settings.SPEED_RAPIDE:
                settings.setSpeed(Settings.SPEED_RAPIDE_VALUE);
                break;
            default:
                settings.setSpeed(Settings.SPEED_NORMAL_VALUE);
        }
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
	
	public void setLimitsObstacles(int largeur, int hauteur) {
		int totalCases = largeur*hauteur;

        casesLacs.setLimits(0, totalCases/30);
        casesLacs.setNumbers(totalCases/40, totalCases/30);
        casesArbres.setLimits(0, totalCases/10);
        casesArbres.setNumbers(totalCases/17, totalCases/10);
        casesRoches.setLimits(0, totalCases/20);
        casesRoches.setNumbers(totalCases/70, totalCases/20);

        elementsLacs.setLimits(0, totalCases/300);
        elementsLacs.setNumbers(totalCases/400, totalCases/300);
        elementsArbres.setLimits(0, 1);
        elementsArbres.setNumbers(1, 1);
        elementsRoches.setLimits(0, totalCases/200);
        elementsRoches.setNumbers(totalCases/400, totalCases/200);
	}
	
	
	public class FocusControls extends FocusAdapter {
		
		public void focusLost(FocusEvent e) {
			setLimitsObstacles(largeur.getJNumberSelect().getNumber(), hauteur.getJNumberSelect().getNumber());
		}
	}
}