package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

public class ActionButton implements ActionListener {
	
    private MainGUI mainFrame;

    public ActionButton(MainGUI parentFrame) {
        this.mainFrame = parentFrame; 
    }
    
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
            case "Débutant": setNumberFileds(10,10,2,1,5); break;
            case "Intermédiaire": setNumberFileds(20,20,5,2,5); break;
            case "Difficile": setNumberFileds(32,32,10,3,5); break;
            case "Extraterestre": setNumberFileds(60,60,15,5,8); break;
            case "Personnalisé": break;
        }
    }
	
	private Boolean isSelected(ActionEvent e) {
        JCheckBoxMenuItem sourceItem;
		sourceItem = (JCheckBoxMenuItem) e.getSource();
    	if (sourceItem != null) {
        	return sourceItem.isSelected();
    	}
    	throw new ClassCastException("La source de l'événement n'est pas un JCheckBoxMenuItem");
	}

	private void start() {
		mainFrame.setActive(true);
	}

	private void pause() {
		mainFrame.setActive(false);
	}

	private void restart() {
		mainFrame.getManager().initPersonnages();
	}
	
	private void rebuild() {
		mainFrame.getMapBuilder().build();
		mainFrame.getManager().initPersonnages();
	}

	private void setDeplacement(Boolean etat) {
		String deplacementNom = PaintStrategy.DEPLACEMENT;
		mainFrame.getPaintStrategy().setDessinActif(deplacementNom, etat);
	}

	private void setVision(Boolean etat) {
		String visionNom = PaintStrategy.VISION;
		mainFrame.getPaintStrategy().setDessinActif(visionNom, etat);
	}

	private void setPerformanceGrille(Boolean etat) {
		String gtilleNom = PaintStrategy.GRILLE;
		mainFrame.getPaintStrategy().setPerformanceActif(gtilleNom, etat);
	}

	private void setPerformancePersonnage(Boolean etat) {
		String personnagesNom = PaintStrategy.PERSONNAGES;
		mainFrame.getPaintStrategy().setPerformanceActif(personnagesNom, etat);
	}
	
	private void showOptions() {
		OptionsPanel.initInstance(mainFrame, this);
    	OptionsPanel dialog = OptionsPanel.getInstance();
    	dialog.resetLocation(mainFrame);
        dialog.setVisible(true);
    }
	
	private void setNumberFileds(int largeur, int hauteur, int intrus, int gardien, int vision) {
		OptionsPanel optionPanel = OptionsPanel.getInstance();
		optionPanel.setTextLargeur(largeur);
		optionPanel.setTextHauteur(hauteur);
		optionPanel.setTextIntrus(intrus);
		optionPanel.setTextGardien(gardien);
		optionPanel.setTextVision(vision);
	}
}