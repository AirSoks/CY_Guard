package test;

import gui.MainGUI;

/**
 * Cette classe permet de tester la simulation du gardien de parc
 * Elle démarre le thread d'exécution.
 * 
 * @author GLP_19
 * @see MainGUI
 */

public class TestGame {
	public static void main(String[] args) {
		MainGUI gameMainGUI = new MainGUI("Gardien");
		Thread gameThread = new Thread(gameMainGUI);
		gameThread.start();
	}

}
