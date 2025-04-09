package gui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

import gui.panel.MainGUI;
import gui.panel.OptionsPanel;
import gui.panel.PaintStrategy;

/**
 * La classe ActionButton implémente l'interface ActionListener.
 * Elle est responsable de gérer les actions déclenchées par tout les boutons ou menu cliquable.
 */
public class ActionButton implements ActionListener {
	
    private MainGUI mainFrame;

    /**
     * Constructeur de la classe ActionButton
     *
     * @param parentFrame La fenêtre principale de l'application
     */
    public ActionButton(MainGUI parentFrame) {
        this.mainFrame = parentFrame; 
    }
    
    /**
     * Gère les actions déclenchées par les boutons et les cases à cocher.
     *
     * @param e L'événement déclenché par l'utilisateur
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Start": start(); break;
            case "Pause": pause(); break;
            case "Restart": restart(); break;
            case "Rebuild": rebuild(); break;
            case "Déplacement": setDeplacement(isSelected(e)); break;
            case "Vision": setVision(isSelected(e)); break;
            case "Grille": setPerformanceGrille(isSelected(e)); break;
            case "Personnage": setPerformancePersonnage(isSelected(e)); break;
            case "Options": showOptions(); break;
            case "Débutant": setNumberFileds(20,20,2,2,5); break;
            case "Intermédiaire": setNumberFileds(30,30,5,2,5); break;
            case "Difficile": setNumberFileds(35,35,10,3,5); break;
            case "Extraterestre": setNumberFileds(40,40,15,5,8); break;
            case "Personnalisé": break;
        }
    }
	
    /**
     * Vérifie si une case à cocher a été sélectionnée.
     *
     * @param e L'événement déclenché par l'utilisateur
     * @return true si la case à cocher est sélectionnée, false sinon
     */
	private Boolean isSelected(ActionEvent e) {
		JCheckBoxMenuItem sourceItem;
		sourceItem = (JCheckBoxMenuItem) e.getSource();
    	if (sourceItem != null) {
        	return sourceItem.isSelected();
    	}
    	throw new ClassCastException("La source de l'événement n'est pas un JCheckBox");
	}

    /**
     * Démarre l'application.
     */
	private void start() {
		mainFrame.setActive(true);
	}

    /**
     * Met en pause l'application.
     */
	private void pause() {
		mainFrame.setActive(false);
	}

    /**
     * Redémarre l'application.
     */
	private void restart() {
		mainFrame.getManager().initPersonnages();
	}
	
    /**
     * Reconstruit la grille de jeu.
     */
	private void rebuild() {
		mainFrame.getMapBuilder().build();
		mainFrame.getManager().initPersonnages();
	}

    /**
     * Active ou désactive le dessin des déplacements.
     *
     * @param etat L'état d'activation (true pour activer, false pour désactiver)
     */
	private void setDeplacement(Boolean etat) {
		String deplacementNom = PaintStrategy.DEPLACEMENT;
		mainFrame.getPaintStrategy().setDessinActif(deplacementNom, etat);
	}

    /**
     * Active ou désactive le dessin de la vision.
     *
     * @param etat L'état d'activation (true pour activer, false pour désactiver)
     */
	private void setVision(Boolean etat) {
		String visionNom = PaintStrategy.VISION;
		mainFrame.getPaintStrategy().setDessinActif(visionNom, etat);
	}

    /**
     * Active ou désactive le mode performance pour la grille.
     *
     * @param etat L'état d'activation (true pour activer, false pour désactiver)
     */
	private void setPerformanceGrille(Boolean etat) {
		String gtilleNom = PaintStrategy.GRILLE;
		mainFrame.getPaintStrategy().setPerformanceActif(gtilleNom, etat);
	}

    /**
     * Active ou désactive le mode performance pour les personnages.
     *
     * @param etat L'état d'activation (true pour activer, false pour désactiver)
     */
	private void setPerformancePersonnage(Boolean etat) {
		String personnagesNom = PaintStrategy.PERSONNAGES;
		mainFrame.getPaintStrategy().setPerformanceActif(personnagesNom, etat);
	}
	
    /**
     * Affiche le panneau des options.
     */
	private void showOptions() {
		OptionsPanel.initInstance(mainFrame, this);
    	OptionsPanel dialog = OptionsPanel.getInstance();
    	dialog.resetLocation(mainFrame);
        dialog.setVisible(true);
    }
	
    /**
     * Définit les paramètres de la grille de jeu.
     *
     * @param largeur La largeur de la grille
     * @param hauteur La hauteur de la grille
     * @param intrus Le nombre d'intrus
     * @param gardien Le nombre de gardiens
     * @param vision La distance de vision
     */
	private void setNumberFileds(int largeur, int hauteur, int intrus, int gardien, int vision) {
		OptionsPanel optionPanel = OptionsPanel.getInstance();
		optionPanel.setNumberLargeur(largeur);
		optionPanel.setNumberHauteur(hauteur);
		optionPanel.setNumberIntrus(intrus);
		optionPanel.setNumberGardien(gardien);
		optionPanel.setNumberVision(vision);
	}
}