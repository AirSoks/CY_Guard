package gui.panel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import engine.personnage.Gardien;
import engine.personnage.Intrus;
import engine.personnage.Personnage;

@SuppressWarnings("serial")
public class SidePanel extends JPanel{
	
	private MainGUI mainFrame;
	
	private JPanel infoGeneral, infoPerso; 
	private JLabel chronoLabel, nbIntrusCapture, nbIntrus, nbGardien;
	private JLabel persoNoms, invocationTime, cibleRepere, intrusCapture;
	
	private XYSeries serie;
	private ChartPanel chartIntrusCapture;
	
	private Personnage personnageClique;
	
	public SidePanel(MainGUI parent) {
        super(new GridBagLayout());
        this.mainFrame = parent;
		init();
	}

	private void init() {
    	GridBagConstraints contrainte = new GridBagConstraints();
    	serie = new XYSeries("Intrus capturés");
    	personnageClique = null;
    	
		infoGeneral = createSubOptionPanel("Informations générales");
		infoGeneral.setLayout(new GridLayout(4,0));
		infoPerso = createSubOptionPanel("Informations du personnage");
		infoPerso.setLayout(new GridLayout(4,0));
		
		chronoLabel = new JLabel("Temps : 00:00");
		nbIntrusCapture = new JLabel("Nombre d'intrus capturé : " + mainFrame.getManager().getNbIntrusCapture());
		nbIntrus = new JLabel("Nombre d'intrus : " + mainFrame.getManager().getIntrus().size());
		nbGardien = new JLabel("Nombre de gardien : " + mainFrame.getManager().getGardiens().size());
		
		infoGeneral.add(chronoLabel);
		infoGeneral.add(nbIntrusCapture);
		infoGeneral.add(nbIntrus);
		infoGeneral.add(nbGardien);
		
		persoNoms = new JLabel("");
		invocationTime = new JLabel("");
		cibleRepere = new JLabel("");
		intrusCapture = new JLabel("");
		
		infoPerso.add(persoNoms);
		infoPerso.add(invocationTime);
		infoPerso.add(cibleRepere);
		infoPerso.add(intrusCapture);
		
		removePersoClique();
		
		chartIntrusCapture = new ChartPanel(getChart());
		chartIntrusCapture.setPreferredSize(new Dimension(0,175));
		
		Timer updateTimer = new Timer(100, new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        int nbCapture = mainFrame.getManager().getNbIntrusCapture();
		        List<Gardien> gardien = mainFrame.getManager().getGardiens();
		        List<Intrus> intrus = mainFrame.getManager().getIntrus();
		        int totalIntrus = intrus.size();
		        int totalGardiens = gardien.size();
		        long seconds = mainFrame.getChrono().getSimulationSecond();
		        
		        chronoLabel.setText(String.format("Temps : %02d:%02d", seconds / 60, seconds % 60));
		        nbIntrusCapture.setText("Nombre d'intrus capturé : " + nbCapture);
		        nbIntrus.setText("Nombre d'intrus total : " + totalIntrus);
		        nbGardien.setText("Nombre de gardien total : " + totalGardiens);
		        
		        if (personnageClique != null) {
			        List<Personnage> personnages = mainFrame.getManager().getPersonnages(personnageClique.getCoordonnee());
		        	if (personnages.contains(personnageClique)) {
			        	updatePersoClique(personnageClique);
		        	}
		        	else {
		        		removePersoClique();
		        	}
		        }
		        updateSerie((int)seconds, nbCapture);
		        chartIntrusCapture = new ChartPanel(getChart());
		    }
		});
		updateTimer.start();

		contrainte.gridx = 0;
		contrainte.fill = GridBagConstraints.HORIZONTAL;
		contrainte.weightx = 1;

		// espace
		contrainte.gridy = 0;
		contrainte.weighty = 1;
		add(Box.createVerticalStrut(0), contrainte);

		// infoGeneral
		contrainte.gridy = 1;
		contrainte.weighty = 0;
		add(infoGeneral, contrainte);

		// espace
		contrainte.gridy = 2;
		contrainte.weighty = 1;
		add(Box.createVerticalStrut(0), contrainte);

		// infoPerso
		contrainte.gridy = 3;
		contrainte.weighty = 0;
		add(infoPerso, contrainte);

		// espace
		contrainte.gridy = 4;
		contrainte.weighty = 1;
		add(Box.createVerticalStrut(0), contrainte);

		// chart
		contrainte.gridy = 5;
		contrainte.weighty = 0;
		add(chartIntrusCapture, contrainte);

		// espace
		contrainte.gridy = 6;
		contrainte.weighty = 1;
		add(Box.createVerticalStrut(0), contrainte);
	}
	
	 /**
	 * Crée un panneau avec une bordure et un titre spécifié.
	 *
	 * @param title Le titre du panneau.
	 * @return Le panneau créé.
	 */
    private JPanel createSubOptionPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
		return panel;
    }
    
    public void removePersoClique() {
    	TitledBorder border = (TitledBorder) infoPerso.getBorder();
    	border.setTitle("Cliquez sur un personnage");
    	persoNoms.setText(null);
		invocationTime.setText(null);
		cibleRepere.setText(null);
		intrusCapture.setText(null);
    }
    
    public void updatePersoClique(Personnage personnage) {
    	personnageClique = personnage;
    	TitledBorder border = (TitledBorder) infoPerso.getBorder();
    	border.setTitle("Informations du personnage");
    	String name = personnage.getName();
    	int tempsInvocation = (int) personnage.getTempsInvocation();
    	int secondesEcoulees = mainFrame.getChrono().getSimulationSecond() - tempsInvocation;

    	persoNoms.setText("Nom : " + name);
    	invocationTime.setText("Temps d'apparition : " + secondesEcoulees + "s");
    	
    	int nbCible = 0;
    	int nbCapture = 0;
    	if (personnage instanceof Gardien) {
    		Gardien gardien = (Gardien) personnage;
	    	nbCible = gardien.getCibles().size();
	    	nbCapture = gardien.getNbIntrusCapture();
			cibleRepere.setText("Cible reperé : " + nbCible);
			intrusCapture.setText("Intrus capturé : "+ nbCapture);
    	}
    	else if (personnage instanceof Intrus) {
    		Intrus intrus = (Intrus) personnage;
	    	nbCible = intrus.getCibles().size();
			cibleRepere.setText("Cible reperé : " + nbCible);
			intrusCapture.setText("");
    	}
    }

	public void updateSerie(int seconds, int intrusCapture) {
		if (serie.getMaxX() != seconds) {
	        serie.add(seconds, intrusCapture);
	    }
    }
	
	public void resetSerie() {
		serie.clear();
	}
	
	public JFreeChart getChart() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(serie);
        
        JFreeChart chart = ChartFactory.createXYLineChart("","Temps (secondes)","Intrus capturés", dataset, PlotOrientation.VERTICAL, true, true, false);
        return chart;
    }
}