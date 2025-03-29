package gui.affichage;
import java.awt.Graphics;
import java.awt.Image;

import config.GameConfiguration;
import engine.map.Coordonnee;
import engine.personnage.Personnage;
import engine.personnage.PersonnageManager;

public class DessinerPersonnages implements Dessiner {
	
    private PersonnageManager personnageManager;
    
    private boolean enabled = true;

    public DessinerPersonnages(PersonnageManager personnageManager) {
        this.personnageManager = personnageManager;
    }

    @Override
    public void paint(Graphics g) {
        if (!enabled) return;

        int blockSize = GameConfiguration.BLOCK_SIZE;

        for (Personnage personnage : personnageManager.getPersonnages()) {
            if (personnage != null) {
                Coordonnee coordonnee = personnage.getCoordonnee();
                int x = coordonnee.getColonne() * blockSize;
                int y = coordonnee.getLigne() * blockSize;

                Image sprite = personnage.getAnimation().getSprite();

                if (sprite != null) {
                    g.drawImage(sprite, x, y, blockSize, blockSize, null);
                }
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
