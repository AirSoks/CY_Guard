package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JSimpleNumberBox {
    
    private JNumberSelect numberSelect;
    private JPanel gridPanel;
    
    public JSimpleNumberBox(String label, OptionsPanel optionsPanel, int min, int max, ActionButton actionButton) {
        gridPanel = new JPanel(new GridLayout(1, 1));
        
        JLabel jLabel = new JLabel(label);
        this.numberSelect = new JNumberSelect(optionsPanel, min, max);
        this.numberSelect.addActionListener(actionButton);

        gridPanel.add(jLabel);
        gridPanel.add(numberSelect);

        optionsPanel.add(gridPanel);
    }
    
    public JPanel getJPanel() {
    	return gridPanel;
    }

    public JNumberSelect getJNumberSelect() {
        return numberSelect;
    }

    public void setNumberSelect(JNumberSelect numberSelect) {
        this.numberSelect = numberSelect;
    }
}