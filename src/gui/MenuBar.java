package gui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

    public MenuBar(ActionButton actionButton) {

        // Ajouter les JMenu cliquables avec le même ActionListener
    	addMenuCliquable("Start", actionButton);
        addMenuCliquable("Pause", actionButton);
        addMenuCliquable("Restart", actionButton);
        addMenuCliquable("Rebuild", actionButton);

        addOptionMenu();
        addAideMenu();
    }

    /**
     * Crée un JMenu cliquable et lui associe un MouseListener
     */
    private void addMenuCliquable(String text, ActionButton actionButton) {
        JMenu menu = new JMenu(text);
        
        // Ajouter un MouseListener pour gérer les clics comm un JMenuItem
        menu.addMouseListener(new MouseAdapter() {
            
        	@Override
            public void mousePressed(MouseEvent e) {
                actionButton.actionPerformed(new ActionEvent(menu, ActionEvent.ACTION_PERFORMED, text));
            }
        	
        });
        
        add(menu);
    }

    /**
     * Ajoute le menu "Option" à la barre de menu.
     */
    private void addOptionMenu() {
        JMenu fileMenu = new JMenu("Option");
        add(fileMenu);
    }

    /**
     * Ajoute le menu "Aide" à la barre de menu.
     */
    private void addAideMenu() {
        JMenu fileMenu = new JMenu("Aide");
        add(fileMenu);
    }
}
