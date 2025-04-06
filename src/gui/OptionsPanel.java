package gui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class OptionsPanel extends JDialog {
	
	/**
	 * Utilisation d'un singleton
	 */
	private static OptionsPanel instance;

	private JRadioButton debutant, intermediaire, difficile, extratersetre, personalise;
	private JSimpleNumberBox largeur, hauteur, gardien, intrus, vision;
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
    	setLayout(new GridLayout(3,1));
    	
    	JPanel difficulte = createSubOptionPanel("Difficulté");
    	JPanel radiosPanel = new JPanel(new GridLayout(5, 1, 1, 1));
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

    	largeur = new JSimpleNumberBox("Largeur :", this, 5, 100, actionButton);
		labelPanel.add(largeur.getJPanel());
		hauteur = new JSimpleNumberBox("Hauteur : ", this, 5, 100, actionButton);
		labelPanel.add(hauteur.getJPanel());
		gardien = new JSimpleNumberBox("Intrus : ", this, 1, 30, actionButton);
		labelPanel.add(gardien.getJPanel());
		intrus = new JSimpleNumberBox("Gardiens : ", this, 1, 10, actionButton);
		labelPanel.add(intrus.getJPanel());
		vision = new JSimpleNumberBox("Vision : ", this, 1, 10, actionButton);
		labelPanel.add(vision.getJPanel());
    	
    	difficulte.add(labelPanel);
    	add(difficulte);
    	
    	JPanel initialisation = createSubOptionPanel("Initialisation");
    	initialisation.setLayout(new GridLayout(2,0));
    	initialisation.add(createSubOptionPanel("Cases"));
    	initialisation.add(createSubOptionPanel("Elements"));

    	add(initialisation);
    	
    	JPanel autresOption = createSubOptionPanel("Autres Options");
    	autresOption.setLayout(new BoxLayout(autresOption, BoxLayout.Y_AXIS));
    	
    	this.apparitionIntrus = new JCheckBox("Apparition des intrus");
    	this.apparitionIntrus.addActionListener(actionButton);
    	this.communicationGardien = new JCheckBox("Communication entre gardien");
    	this.communicationGardien.addActionListener(actionButton);
    	autresOption.add(apparitionIntrus);
    	autresOption.add(communicationGardien);
    	
    	add(autresOption);
    	personalise.setSelected(true);
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
	
	public void setSelectionCheckBoxPersonnalise() {
		personalise.setSelected(true);
	}
}