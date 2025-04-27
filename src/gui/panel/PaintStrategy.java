package gui.panel;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import config.Settings;
import engine.map.Grille;
import engine.personnage.PersonnageManager;
import gui.dessin.DessinOptionnel;
import gui.dessin.DessinPerformance;
import gui.dessin.Dessiner;
import gui.dessin.DessinerDeplacement;
import gui.dessin.DessinerGrille;
import gui.dessin.DessinerPersonnages;
import gui.dessin.DessinerVision;


/**
 * Gère la stratégie d'affichage graphique en combinant plusieurs objets de dessin.
 * 
 * Chaque élément graphique (grille, personnages, déplacements, vision) est
 * représenté par un {@link Dessiner} spécifique pouvant être activé ou désactivé.
 * 
 * @author GLP_19
 * @see Dessiner
 * @see DessinerGrille
 * @see DessinerPersonnages
 * @see DessinerDeplacement
 * @see DessinerVision
 * @see DessinOptionnel
 * @see DessinPerformance
 */
public class PaintStrategy {
	
    public final static String GRILLE = "GRILLE";
    public final static String PERSONNAGES = "PERSONNAGES";
    public final static String DEPLACEMENT = "DEPLACEMENT";
    public final static String VISION = "VISION";
	
    private List<Dessiner> dessins = new ArrayList<>();

    /**
     * Initialise les différentes couches de dessin selon la grille, le gestionnaire de personnages
     * et les paramètres de configuration.
     *
     * @param grille La grille du terrain
     * @param personnageManager Le gestionnaire des personnages
     * @param settings Les paramètres
     */
    public PaintStrategy(Grille grille, PersonnageManager personnageManager, Settings settings) {
    	dessins.add(new DessinerGrille(grille, settings));
    	dessins.add(new DessinerPersonnages(personnageManager, settings));
        dessins.add(new DessinerDeplacement(personnageManager, settings));
        dessins.add(new DessinerVision(personnageManager, settings));
    }

    /**
     * Dessine tous les éléments sur le composant graphique.
     *
     * @param g contexte graphique sur lequel dessiner
     */
    public void paint(Graphics g) {
        for (Dessiner dessin : dessins) {
        	dessin.paint(g);
        }
    }
    
    /**
     * Récupère un élément de dessin par son nom.
     *
     * @param nom le nom de l'élément de dessin
     * @return l'élément {@link Dessiner} correspondant, ou null si non trouvé
     */
    private Dessiner getDessinParNom(String nom) {
        for (Dessiner dessin : dessins) {
            if (dessin.getNom().equals(nom.toUpperCase())) {
                return dessin;
            }
        }
        return null;
    }
    
    /**
     * Active ou désactive un dessin optionnel par son nom.
     *
     * @param nom Le nom du dessin
     * @param etat True pour activer, false pour désactiver
     */
    public void setDessinActif(String nom, boolean etat) {
        Dessiner dessin = getDessinParNom(nom);
        if (dessin != null && dessin instanceof DessinOptionnel) {
        	DessinOptionnel dessinOptionel = (DessinOptionnel) dessin;
        	dessinOptionel.setActive(etat);
        }
    }

    /**
     * Active ou désactive le mode performance pour un dessin spécifique.
     *
     * @param nom Le nom du dessin
     * @param etat True pour activer le mode performance, false pour le désactiver
     */
    public void setPerformanceActif(String nom, boolean etat) {
        Dessiner dessin = getDessinParNom(nom);
        if (dessin != null && dessin instanceof DessinPerformance) {
        	DessinPerformance dessinPerf = (DessinPerformance) dessin;
        	dessinPerf.setPerformance(etat);
        }
    }
}