package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionButton implements ActionListener {
	
    private MainGUI mainFrame;

    public ActionButton(MainGUI parentFrame) {
        this.mainFrame = parentFrame; 
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Start": start(); break;
            case "Pause": pause(); break;
            case "Restart": restart(); break;
            case "Rebuild": rebuild(); break;
        }
    }

	private void start() {
		mainFrame.setActive(true);
	}

	private void pause() {
		mainFrame.setActive(false);
	}

	private void restart() {
		mainFrame.getManager().initPersonnages();
	}
	
	private void rebuild() {
		mainFrame.getMapBuilder().build();
		mainFrame.getManager().initPersonnages();
	}
}