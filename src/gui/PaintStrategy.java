package gui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import engine.map.Grille;
import engine.personnage.PersonnageManager;
import gui.affichage.DessinerGrille;
import gui.affichage.Dessiner;
import gui.affichage.DessinerDeplacement;
import gui.affichage.DessinerPersonnages;

public class PaintStrategy {
	
    private final List<Dessiner> dessins = new ArrayList<>();

    public PaintStrategy(Grille grille, PersonnageManager personnageManager) {
    	dessins.add(new DessinerGrille(grille));
    	dessins.add(new DessinerPersonnages(personnageManager));
        dessins.add(new DessinerDeplacement(personnageManager));
    }

    public void enable(boolean active) {
        for (Dessiner dessin : dessins) {
            dessin.enable();
        }
    }
    
    public void disable(boolean active) {
        for (Dessiner dessin : dessins) {
            dessin.disable();
        }
    }

    public void paint(Graphics g) {
        for (Dessiner dessin : dessins) {
        	dessin.paint(g);
        }
    }
}