package gui.affichage;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import config.GameConfiguration;
import engine.map.Coordonnee;
import engine.personnage.Gardien;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;
import engine.personnage.deplacement.Deplacement;
import engine.personnage.deplacement.DeplacementPoursuite;

public class DessinerDeplacement implements Dessiner {
	
    private PersonnageManager personnageManager;
    private boolean enabled = true;

    public DessinerDeplacement(PersonnageManager personnageManager) {
        this.personnageManager = personnageManager;
    }

    @Override
    public void paint(Graphics g) {
    	if (!enabled) return;
        int blockSize = GameConfiguration.BLOCK_SIZE;

        for (Personnage personnage : personnageManager.getPersonnages()) {
            Deplacement deplacement = personnage.getDeplacement();

            if (deplacement instanceof DeplacementPoursuite && personnage instanceof Gardien) {
            	
            } 
        }
    }

    @Override
    public void enable() {
        this.enabled = true;
    }

    @Override
    public void disable() {
        this.enabled = false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}