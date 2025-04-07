package gui.numberField;

import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JNumberBoxDouble extends JPanel{
		
	private JNumberField numberMin, numberMax;
	private int min, max;
	
	public JNumberBoxDouble(String label1, String label2, int min, int max) {
		setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 2));
		this.min = min;
		this.max = max;
		
		this.numberMin = new JNumberField(min, max);
		this.numberMax = new JNumberField(min, max);
		JNumberBoxSimple firstBox = new JNumberBoxSimple(label1, numberMin);
		JNumberBoxSimple secondBox = new JNumberBoxSimple(label2, numberMax);
		
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