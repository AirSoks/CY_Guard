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

import gui.champNombre.JNumberBoxDouble;
import gui.champNombre.JNumberFieldRelative;
import gui.event.ActionButton;
import gui.champNombre.JNumberBoxSimple;

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
	 * Constructeur de la classe SearchDialog.
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
    
	public static void initInstance(JFrame parent, ActionButton actionButton) {
		if (instance == null) {
	        instance = new OptionsPanel(parent, actionButton);
		}
    }
	
	public static OptionsPanel getInstance() {
		if (instance == null) {
			throw new IllegalStateException("Grille non initialisée");
		}
		return instance;
	}
	
	public void resetLocation(JFrame parent) {
        pack();
        setLocationRelativeTo(parent);
	}

	/**
	 * Crée la mise en page de la boîte de dialogue en utilisant un GridLayout.
	 */
	private void createLayout(ActionButton actionButton) {
	    setLayout(new GridBagLayout());
	    GridBagConstraints gridContrainte = new GridBagConstraints();
	    
	    gridContrainte.gridy = 0;
	    gridContrainte.gridy = 0;
	    gridContrainte.fill = GridBagConstraints.HORIZONTAL;

	    add(createDificultyPanel(actionButton), gridContrainte);

	    gridContrainte.gridy = 1;
	    gridContrainte.gridx = 0;
	    add(createInitialisationPanel(actionButton), gridContrainte);

	    gridContrainte.gridy = 2;
	    gridContrainte.gridx = 0;
	    add(creatOtherOptions(actionButton), gridContrainte);
	}
    
    private JPanel createDificultyPanel(ActionButton actionButton) {
    	JPanel difficulte = createSubOptionPanel("Difficulté");
    	JPanel radiosPanel = new JPanel(new GridLayout(5, 0, 0, 0));
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
    	
    	JPanel labelPanel = new JPanel(new GridLayout(5, 1, 1, 5));
    	labelPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
    	largeur = new JNumberBoxSimple("Largeur : ", new JNumberFieldRelative(5, 100), actionButton);
		labelPanel.add(largeur);
		hauteur = new JNumberBoxSimple("Hauteur : ", new JNumberFieldRelative(5, 100), actionButton);
		labelPanel.add(hauteur);
		gardien = new JNumberBoxSimple("Intrus : ", new JNumberFieldRelative(1, 30), actionButton);
		labelPanel.add(gardien);
		intrus = new JNumberBoxSimple("Gardiens : ", new JNumberFieldRelative(1, 10), actionButton);
		labelPanel.add(intrus);
		vision = new JNumberBoxSimple("Vision : ",  new JNumberFieldRelative(1, 10), actionButton);
		labelPanel.add(vision);
    	difficulte.add(labelPanel);
    	
    	return difficulte;
    }
    
    private JPanel createInitialisationPanel(ActionButton actionButton) {
    	
    	JPanel initialisation = createSubOptionPanel("Initialisation");
    	initialisation.setLayout(new GridLayout(2,0,1,1));
    	
    	JPanel cases = createSubOptionPanel("Cases");
    	
    	JPanel casesPanel = new JPanel(new GridLayout(3, 1));
    	JPanel casesPanel2 = new JPanel(new GridLayout(3, 1, 1,4));
    	
    	JLabel labelCasesLacs = new JLabel("Lacs : ");
    	JLabel labelCasesArbres = new JLabel("Arbres : ");
    	JLabel labelCasesRoches = new JLabel("Roches : ");
    	casesLacs = new JNumberBoxDouble("min : ", "max : ", 50, 500, actionButton);
    	casesArbres = new JNumberBoxDouble("min : ", "max : ", 50, 500, actionButton);
    	casesRoches = new JNumberBoxDouble("min : ", "max : ", 50, 500, actionButton);
    	
    	casesPanel.add(casesLacs);
    	casesPanel2.add(labelCasesLacs);
    	casesPanel.add(casesArbres);
    	casesPanel2.add(labelCasesArbres);
    	casesPanel.add(casesRoches);
    	casesPanel2.add(labelCasesRoches);

    	cases.setLayout(new GridBagLayout());
    	GridBagConstraints gridContrainte = new GridBagConstraints();

    	gridContrainte.gridx = 0;
    	gridContrainte.gridy = 0;
    	gridContrainte.weightx = 1;
    	cases.add(casesPanel2, gridContrainte);

    	gridContrainte.gridx = 1;
    	gridContrainte.gridy = 0;
    	gridContrainte.weightx = 1;
    	cases.add(casesPanel, gridContrainte);

    	
    	JPanel elements = createSubOptionPanel("Elements");
    	
    	JPanel elementsPanel = new JPanel(new GridLayout(3, 1));
    	JPanel elementsPanel2 = new JPanel(new GridLayout(3, 1, 1,4));
    	
    	JLabel labelElementsLacs = new JLabel("Lacs : ");
    	JLabel labelElementsArbres = new JLabel("Arbres : ");
    	JLabel labelElementsRoches = new JLabel("Roches : ");
    	elementsLacs = new JNumberBoxDouble("min : ", "max : ", 1, 10, actionButton);
    	elementsArbres = new JNumberBoxDouble("min : ", "max : ", 1, 10, actionButton);
    	elementsRoches = new JNumberBoxDouble("min : ", "max : ", 1, 10, actionButton);
    	
    	elementsPanel.add(elementsLacs);
    	elementsPanel2.add(labelElementsLacs);
    	elementsPanel.add(elementsArbres);
    	elementsPanel2.add(labelElementsArbres);
    	elementsPanel.add(elementsRoches);
    	elementsPanel2.add(labelElementsRoches);
    	
    	elements.setLayout(new GridBagLayout());
    	GridBagConstraints gridContrainte2 = new GridBagConstraints();

    	gridContrainte2.gridx = 0;
    	gridContrainte2.gridy = 0;
    	gridContrainte2.weightx = 1;
    	elements.add(elementsPanel2, gridContrainte2);

    	gridContrainte2.gridx = 1;
    	gridContrainte2.gridy = 0;
    	gridContrainte2.weightx = 1;
    	elements.add(elementsPanel, gridContrainte2);
    	
    	initialisation.add(cases);
    	initialisation.add(elements);

    	return initialisation;
    }
    
    private JPanel creatOtherOptions(ActionButton actionButton) {
    	JPanel autresOption = createSubOptionPanel("Autres Options");
    	autresOption.setLayout(new GridLayout(2,0,1,1));
    	
    	this.apparitionIntrus = new JCheckBox("Apparition des intrus");
    	this.apparitionIntrus.addActionListener(actionButton);
    	this.communicationGardien = new JCheckBox("Communication entre gardien");
    	this.communicationGardien.addActionListener(actionButton);
    	autresOption.add(apparitionIntrus);
    	autresOption.add(communicationGardien);
    	
    	return autresOption;
    }
    
    private JPanel createSubOptionPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
		return panel;
    }

	public void setTextLargeur(int value) {
		if (value >= largeur.getJNumberSelect().getNombreMinimal() && value <= largeur.getJNumberSelect().getNombreMaximal()) {
			this.largeur.getJNumberSelect().setText(String.valueOf(value));
		}
	}

	public void setTextHauteur(int value) {
		if (value >= hauteur.getJNumberSelect().getNombreMinimal() && value <= hauteur.getJNumberSelect().getNombreMaximal()) {
			this.hauteur.getJNumberSelect().setText(String.valueOf(value));
		}
	}

	public void setTextGardien(int value) {
		if (value >= gardien.getJNumberSelect().getNombreMinimal() && value <= gardien.getJNumberSelect().getNombreMaximal()) {
			this.gardien.getJNumberSelect().setText(String.valueOf(value));
		}
	}

	public void setTextIntrus(int value) {
		if (value >= intrus.getJNumberSelect().getNombreMinimal() && value <= intrus.getJNumberSelect().getNombreMaximal()) {
			this.intrus.getJNumberSelect().setText(String.valueOf(value));
		}
	}

	public void setTextVision(int value) {
		if (value >= vision.getJNumberSelect().getNombreMinimal() && value <= vision.getJNumberSelect().getNombreMaximal()) {
			this.vision.getJNumberSelect().setText(String.valueOf(value));
		}
	}
	
	public void setSelectionToCheckBoxPersonnalise() {
		personalise.setSelected(true);
	}
}