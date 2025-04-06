package gui.champNombre;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

import gui.panel.OptionsPanel;

@SuppressWarnings("serial")
public class JNumberFieldRelative extends JNumberField{

	public JNumberFieldRelative(int min, int max) {
		super(min, max);
		addKeyListener(new NumberControls());
        addMouseWheelListener(new WheelControls());
	}
	public class NumberControls extends KeyAdapter {
		
		public void keyTyped(KeyEvent e) {
			OptionsPanel.getInstance().setSelectionToCheckBoxPersonnalise();
		}
	}
	public class WheelControls extends MouseAdapter {
		
		public void mouseWheelMoved(MouseWheelEvent e){
			OptionsPanel.getInstance().setSelectionToCheckBoxPersonnalise();
		}
	}
	
}