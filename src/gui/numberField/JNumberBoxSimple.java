package gui.numberField;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JNumberBoxSimple extends JPanel {
    
    private JNumberField numberSelect;

    public JNumberBoxSimple(String label, JNumberField numberSelect) {
    	setLayout(new GridLayout(1,1));

        JLabel jLabel = new JLabel(label);
        this.numberSelect = numberSelect;
        this.add(jLabel);
        this.add(numberSelect);
    }

    public JNumberField getJNumberSelect() {
        return numberSelect;
    }

    public void setNumberSelect(JNumberField numberSelect) {
        this.numberSelect = numberSelect;
    }
}