package gui.champNombre;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JPanel;

import gui.event.ActionButton;

@SuppressWarnings("serial")
public class JNumberBoxDouble extends JPanel{
		
	private JNumberField numberMin, numberMax;
	private int min, max;
	
	public JNumberBoxDouble(String label1, String label2, int min, int max, ActionButton actionButton) {
		setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 0));
		this.min = min;
		this.max = max;
		
		this.numberMin = new JNumberField(min, max);
		this.numberMax = new JNumberField(min, max);
		JNumberBoxSimple firstBox = new JNumberBoxSimple(label1, numberMin, actionButton);
		firstBox.setLayout(new GridLayout());
		JNumberBoxSimple secondBox = new JNumberBoxSimple(label2, numberMax, actionButton);
		secondBox.setLayout(new GridLayout());
		
		this.add(firstBox);
        this.add(secondBox);
        
        numberMin.addFocusListener(new FocusControlsMin());
        numberMax.addFocusListener(new FocusControlsMax());
        
        initBox();
	}

	private void initBox() {
		numberMin.setNumber(min);
		numberMax.setNumber(max);
	}
	
	public class FocusControlsMin extends FocusAdapter {
		
		public void focusLost(FocusEvent e) {
			
	        int minActual = numberMin.getNumber();
	        int maxActual = numberMax.getNumber();
			if (minActual > maxActual) {
				numberMin.setNumber(maxActual);
            }
	    }
	}
	public class FocusControlsMax extends FocusAdapter {
		
		public void focusLost(FocusEvent e) {
			
	        int minActual = numberMin.getNumber();
	        int maxActual = numberMax.getNumber();
			if (maxActual < minActual) {
				numberMax.setNumber(minActual);
            }
	    }
	}	
}