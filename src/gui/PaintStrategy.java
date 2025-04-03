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
import gui.affichage.DessinerVision;

public class PaintStrategy {
	
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
}