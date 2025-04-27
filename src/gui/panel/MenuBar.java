package gui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import gui.event.ActionButton;

/**
 * Menu de l'interface graphique du simulation.
 * Elle gère les boutons principaux.
 * 
 * @author GLP_19
 * @see MainGUI
 * @see ActionButton
 * @see JMenu
 * @see JMenuBar
 * @see JMenuItem
 */
@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
	
	private ActionButton actionButton;

    public MenuBar(ActionButton actionButton) {
    	this.actionButton = actionButton;

    	addMenuCliquable("Start");
    	addMenuCliquable("Pause");
    	addMenuCliquable("Restart");
        addMenuCliquable("Rebuild");
        addMenuCliquable("Options");
        addAffichageMenu();
    }

    /**
     * Crée un JMenu cliquable.
     */
    private void addMenuCliquable(String text) {
        JMenu menu = new JMenu(text);
        MouseAdapter mouseAdapter = new MouseAdapter() {
        	
        	@Override
            public void mousePressed(MouseEvent e) {
        		int id = ActionEvent.ACTION_PERFORMED;
        		ActionEvent actionEvent = new ActionEvent(menu, id, text);
                actionButton.actionPerformed(actionEvent);
            }
        };
        
		menu.addMouseListener(mouseAdapter);
        add(menu);
    }

    /**
     * Ajoute le menu "Affichage" à la barre de menu.
     */
    private void addAffichageMenu() {
        JMenu affichageMenu = new JMenu("Affichage");
        
        JMenu interactionMenu = new JMenu("Visibilité");
        addCheckMenuItem(interactionMenu, "Déplacement", true);
        addCheckMenuItem(interactionMenu, "Vision", true);
        affichageMenu.add(interactionMenu);

        JMenu performanceMenu = new JMenu("Performance");
        addCheckMenuItem(performanceMenu, "Grille", false);
        addCheckMenuItem(performanceMenu, "Personnage", false);
        affichageMenu.add(performanceMenu);

        add(affichageMenu);
    }
    
    /**
     * Ajoute un JCheckBoxMenuItem au menu spécifié et lui associe un gestionnaire d'actions.
     *
     * @param menu Le menu auquel ajouter le JCheckBoxMenuItem.
     * @param itemName Le nom de l'élément à ajouter au menu.
     */
    private void addCheckMenuItem(JMenu menu, String itemName, Boolean selectionState) {
        JMenuItem item = new JCheckBoxMenuItem(itemName, selectionState);
        item.addActionListener(actionButton);
        menu.add(item);
    }
}
