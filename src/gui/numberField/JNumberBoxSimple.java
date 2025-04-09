package gui.numberField;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JNumberBoxSimple extends JPanel {
    
    private JNumberField numberSelect;

    public JNumberBoxSimple(String label, JNumberField numberSelect) {
    	setLayout(new GridBagLayout());
	    GridBagConstraints contrainte = new GridBagConstraints();
        JLabel jLabel = new JLabel(label);
        
        contrainte.gridy = 0;
        contrainte.gridx = 0;
        contrainte.anchor = GridBagConstraints.WEST;
        contrainte.weightx = 1;
        contrainte.fill = GridBagConstraints.HORIZONTAL;
        this.add(jLabel, contrainte);

        contrainte.gridy = 0;
        contrainte.gridx = 1;
        contrainte.anchor = GridBagConstraints.EAST;
        contrainte.weightx = 1;
        contrainte.fill = GridBagConstraints.NONE;
        this.numberSelect = numberSelect;
        this.add(numberSelect, contrainte);
    }

    public JNumberField getJNumberSelect() {
        return numberSelect;
    }

    public void setNumberSelect(JNumberField numberSelect) {
        this.numberSelect = numberSelect;
    }
}