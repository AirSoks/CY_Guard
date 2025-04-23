package gui.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import engine.utilitaire.chrono.ChronoListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SidePanel extends JPanel{
	
	private MainGUI parent;
	
	private JLabel chronoLabel;
	
	public SidePanel(MainGUI parent) {
        super(new GridBagLayout());
        this.parent = parent;
		init();
	}

	private void init() {
    	GridBagConstraints contrainte = new GridBagConstraints();
    	
		JPanel infoGeneral = new JPanel();
		JPanel infoPerso = new JPanel();
		JPanel infoPersoRelatif = new JPanel();
		JPanel chart = new JPanel();
		
		chronoLabel = new JLabel("Temps : 00:00");
		infoGeneral.add(chronoLabel);
		parent.getChrono().setListener(new ChronoListener() {
		    @Override
		    public void onNewSecond(int seconds) {
		    	String newSecond = String.format("Temps : %02d:%02d", seconds / 60, seconds % 60);
		        chronoLabel.setText(newSecond);
		    }
		});
	    
		infoPerso.add(new JLabel("InfoPerso"));
		infoPersoRelatif.add(new JLabel("InfoPersoRelatif"));
		chart.add(new JLabel("chart"));

	    contrainte.gridy = 0;
	    contrainte.gridx = 0;
	    contrainte.fill = GridBagConstraints.HORIZONTAL;
		add(infoGeneral, contrainte);
		
	    contrainte.gridy = 1;
	    contrainte.gridx = 0;
		add(infoPerso, contrainte);

	    contrainte.gridy = 2;
	    contrainte.gridx = 0;
		add(infoPersoRelatif, contrainte);

	    contrainte.gridy = 3;
	    contrainte.gridx = 0;
		add(chart, contrainte);
	}

}