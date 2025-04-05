package gui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
	
	private ActionButton actionButton;

    public MenuBar(ActionButton actionButton) {
    	this.actionButton = actionButton;

        // Ajouter les JMenu cliquables avec le même ActionListener
    	addMenuCliquable("Start");
    	addMenuCliquable("Pause");
    	addMenuCliquable("Restart");
        addMenuCliquable("Rebuild");

        addOptionsMenu();
        addAffichageMenu();
    }

    /**
     * Crée un JMenu cliquable et lui associe un MouseListener
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
     * Ajoute le menu "Option" à la barre de menu.
     */
    private void addOptionsMenu() {
        JMenu fileMenu = new JMenu("Options");
        add(fileMenu);
    }

    /**
     * Ajoute le menu "Aide" à la barre de menu.
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
     * Ajoute un JMenuItem au menu spécifié et lui associe un gestionnaire d'actions.
     *
     * @param menu Le menu auquel ajouter le JMenuItem.
     * @param itemName Le nom de l'élément à ajouter au menu.
     */
    private void addMenuItem(JMenu menu, String itemName) {
        JMenuItem item = new JMenuItem(itemName);
        item.addActionListener(actionButton);
        menu.add(item);
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
