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
        JCheckBoxMenuItem sourceItem;
        switch (command) {
            case "Start": start(); break;
            case "Pause": pause(); break;
            case "Restart": restart(); break;
            case "Rebuild": rebuild(); break;
            case "Déplacement": 
            	sourceItem = (JCheckBoxMenuItem) e.getSource();
            	if (sourceItem != null) {
                	boolean selected = sourceItem.isSelected();
                	setDeplacement(selected);
            	} break;
            case "Vision":
            	sourceItem = (JCheckBoxMenuItem) e.getSource();
            	if (sourceItem != null) {
                	boolean selected = sourceItem.isSelected();
                	setVision(selected);
            	} break;
            case "Grille":
	        	sourceItem = (JCheckBoxMenuItem) e.getSource();
	        	if (sourceItem != null) {
	            	boolean selected = sourceItem.isSelected();
	            	setPerformanceGrille(selected);
	        	} break;
            case "Personnage": 
            	sourceItem = (JCheckBoxMenuItem) e.getSource();
            	if (sourceItem != null) {
                	boolean selected = sourceItem.isSelected();
                	setPerformancePersonnage(selected);
            	} break;
        }
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
}