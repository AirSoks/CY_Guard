package gui.numberField;

import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JPanel;

/**
 * Cette classe assure que le min ne dépasse pas le max et que le max n'est pas inférieur au min.
 */
@SuppressWarnings("serial")
public class JNumberBoxDouble extends JPanel{
	
	/**
	 * Champ pour la valeur minimale et maximale
	 */
	private JNumberField numberMin, numberMax;
	
	
	/**
	 * Créer un composant avec 2 champs numérotés et leurs labels
	 * 
	 * @param label1 label du champ minimale
	 * @param label2 label du champ maximale
	 * @param min valeur minimale possible
	 * @param max valeur maximale possible 
	 */
	public JNumberBoxDouble(String label1, String label2, int min, int max) {
		setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 2));
		
		this.numberMin = new JNumberField(min, max);
		this.numberMax = new JNumberField(min, max);
		JNumberBoxSimple firstBox = new JNumberBoxSimple(label1, numberMin);
		JNumberBoxSimple secondBox = new JNumberBoxSimple(label2, numberMax);
		
		this.add(firstBox);
        this.add(secondBox);
        
        numberMin.addFocusListener(new FocusControlsMin());
        numberMax.addFocusListener(new FocusControlsMax());

		numberMin.setNumber(min);
		numberMax.setNumber(max);
	}

	/**
	 * Définit les limites des 2 champs
	 * 
	 * @param cases_lacs_min limite minimal
	 * @param cases_lacs_max limite maximal
	 */
	public void setLimits(int cases_lacs_min, int cases_lacs_max) {
		if (cases_lacs_min <= cases_lacs_max ) {
			numberMin.setLimits(cases_lacs_min, cases_lacs_max);
			numberMax.setLimits(cases_lacs_min, cases_lacs_max);
		}
		else {
			numberMin.setLimits(cases_lacs_min, cases_lacs_min);
			numberMax.setLimits(cases_lacs_min, cases_lacs_min);
		}
		
	}
	
	/**
	 * Définit les valeurs affiches des 2 champs
	 * 
	 * @param cases_lacs_min valeur pour le champ minimale
	 * @param cases_lacs_max valeur pour le champ maximale
	 */
	public void setNumbers(int cases_lacs_min, int cases_lacs_max) {
		numberMin.setNumber(cases_lacs_min);
		numberMax.setNumber(cases_lacs_max);
	}
	
	/**
	 * Affiche les limites autorisées dans chaque champ
	 */
	public void showLimits() {
		numberMin.showLimitsMin();
		numberMax.showLimitsMax();
	}
	
    /**
     * Retourne le champ minimale
     * 
     * @return champ pour la valeur minimale
     */
    public JNumberField getJNumberMin() {
        return numberMin;
    }
    
    /**
     * Retourne le champ maximale
     * 
     * @return champ pour la valeur maximale
     */
    public JNumberField getJNumberMax() {
        return numberMax;
    }
	
	/**
	 * Vérifie que la valeur minimale ne depasse pas le maximum
	 */
	public class FocusControlsMin extends FocusAdapter {
		
		public void focusLost(FocusEvent e) {
			
	        int minActual = numberMin.getNumber();
	        int maxActual = numberMax.getNumber();
			if (minActual > maxActual) {
				numberMin.setNumber(maxActual);
            }
	    }
	}
	
	/**
	 * Vérifie que le maximum ne soit pas inférieur à la valeur minimale.
	 */
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