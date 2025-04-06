package gui;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class JDoubleNumberBox {
		
	private JSimpleNumberBox numberMin, numberMax;
	private JPanel gridPanel;
	
	public JDoubleNumberBox(String labelMin, String labelMax, OptionsPanel optionsPanel, int firstMin, int firstMax, int LastMin, int LastMax, ActionButton actionButton) {

        gridPanel = new JPanel(new GridLayout(1, 1));
        
		this.numberMin = new JSimpleNumberBox(labelMin, optionsPanel, firstMin, firstMax, actionButton);
		this.numberMax = new JSimpleNumberBox(labelMax, optionsPanel, LastMin, LastMax, actionButton);
	}
	
	
}
