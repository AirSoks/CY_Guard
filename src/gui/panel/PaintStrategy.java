package gui.panel;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import engine.map.Grille;
import engine.personnage.PersonnageManager;
import gui.dessin.DessinOptionnel;
import gui.dessin.DessinPerformance;
import gui.dessin.Dessiner;
import gui.dessin.DessinerDeplacement;
import gui.dessin.DessinerGrille;
import gui.dessin.DessinerPersonnages;
import gui.dessin.DessinerVision;

public class PaintStrategy {
	
    public final static String GRILLE = "GRILLE";
    public final static String PERSONNAGES = "PERSONNAGES";
    public final static String DEPLACEMENT = "DEPLACEMENT";
    public final static String VISION = "VISION";
	
    private List<Dessiner> dessins = new ArrayList<>();

    public PaintStrategy(Grille grille, PersonnageManager personnageManager) {
    	dessins.add(new DessinerGrille(grille));
    	dessins.add(new DessinerPersonnages(personnageManager));
        dessins.add(new DessinerDeplacement(personnageManager));
        dessins.add(new DessinerVision(personnageManager));
    }

    public void paint(Graphics g) {
        for (Dessiner dessin : dessins) {
        	dessin.paint(g);
        }
    }
    
    private Dessiner getDessinParNom(String nom) {
        for (Dessiner dessin : dessins) {
            if (dessin.getNom().equals(nom.toUpperCase())) {
                return dessin;
            }
        }
        return null;
    }
    
    public void setDessinActif(String nom, boolean etat) {
        Dessiner dessin = getDessinParNom(nom);
        if (dessin != null && dessin instanceof DessinOptionnel) {
        	DessinOptionnel dessinOptionel = (DessinOptionnel) dessin;
        	dessinOptionel.setActive(etat);
        }
    }

    public void setPerformanceActif(String nom, boolean etat) {
        Dessiner dessin = getDessinParNom(nom);
        if (dessin != null && dessin instanceof DessinPerformance) {
        	DessinPerformance dessinPerf = (DessinPerformance) dessin;
        	dessinPerf.setPerformance(etat);
        }
    }
}