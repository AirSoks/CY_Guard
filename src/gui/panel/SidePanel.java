package gui.panel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SidePanel extends JPanel{
	
	public SidePanel() {
        super(new GridBagLayout());
    	GridBagConstraints contrainte = new GridBagConstraints();
		init();
	}

	private void init() {
		setPreferredSize(new Dimension(250,0));
	}
}
