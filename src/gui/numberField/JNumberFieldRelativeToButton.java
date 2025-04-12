package gui.numberField;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

import javax.swing.JToggleButton;

@SuppressWarnings("serial")
public class JNumberFieldRelativeToButton extends JNumberField{
	
	private JToggleButton buttonRelative;

	public JNumberFieldRelativeToButton(int min, int max, JToggleButton button) {
		super(min, max);
		addKeyListener(new NumberControls());
        addMouseWheelListener(new WheelControls());
        this.buttonRelative = button;
	}
	public class NumberControls extends KeyAdapter {
		
		public void keyTyped(KeyEvent e) {
			if (buttonRelative != null) {
				buttonRelative.setSelected(true);
			}
		}
	}
	public class WheelControls extends MouseAdapter {
		
		public void mouseWheelMoved(MouseWheelEvent e){
			if (buttonRelative != null) {
				buttonRelative.setSelected(true);
			}
		}
	}
}