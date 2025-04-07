package gui.numberField;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

import javax.swing.JTextField;

import engine.utilitaire.FieldWithNoNumber;

/**
 * La classe permet uniquement la saisie de nombres entiers.
 * Les nombres entiers sont définis entre une valeur min et max.
 */
@SuppressWarnings("serial")
public class JNumberField extends JTextField {

	private int nombreMinimal;
	private int nombreMaximal;
	private static final int MAX_LENGTH = 9;

	/**
	 * Constructeur de la classe JNumberField.
	 *
	 * @param min La valeur minimale autorisée
	 * @param max La valeur maximale autorisée
	 */
	public JNumberField(int min, int max) {
		super(String.valueOf(min));
		this.nombreMinimal = min;
		this.nombreMaximal = max;

		addKeyListener(new NumberControls());
		addFocusListener(new FocusControls());
		addMouseWheelListener(new WheelControls());
	}

	/**
	 * Classe interne pour gérer les événements de saisie clavier.
	 */
	public class NumberControls extends KeyAdapter {

		@Override
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar();

			if (!Character.isDigit(c)) {
				e.consume();
			}
		}
	}

	/**
	 * Classe interne pour gérer les événements de perte de focus.
	 */
	public class FocusControls extends FocusAdapter {

		@Override
		public void focusLost(FocusEvent e) {
			String field = getText();
			if (field.length() != 0) {
				int value = getNumber();
				if (value < nombreMinimal) {
					setNumber(nombreMinimal);
				}
				if (value > nombreMaximal) {
					setNumber(nombreMaximal);
				}
			} else {
				setNumber(nombreMinimal);
			}
		}
	}

	/**
	 * Classe interne pour gérer les événements de molette de souris.
	 */
	public class WheelControls extends MouseAdapter {

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			int rotation = e.getWheelRotation();

			int value = getNumber();

			if (rotation == -1 && value < nombreMaximal) {
				addNumber(true);
				requestFocusInWindow();
			} else if (rotation == 1 && value > nombreMinimal) {
				addNumber(false);
				requestFocusInWindow();
			}
		}
	}

	public int getNombreMinimal() {
		return nombreMinimal;
	}

	public void setNombreMinimal(int nombreMinimal) {
		this.nombreMinimal = nombreMinimal;
	}

	public int getNombreMaximal() {
		return nombreMaximal;
	}

	public void setNombreMaximal(int nombreMaximal) {
		this.nombreMaximal = nombreMaximal;
	}

	/**
	 * Modifie la valeur du champ de nombre en fonction de l'argument passé.
	 *
	 * @param value true pour incrémenter, false pour décrémenter
	 */
	public void addNumber(Boolean value) {
		if (value) {
			setNumber(getNumber() + 1);
		} else {
			setNumber(getNumber() - 1);
		}
	}

	/**
	 * Obtient la valeur numérique du champ de texte.
	 *
	 * @return La valeur numérique du champ de texte
	 */
	public int getNumber() {
		String field = getText();
		if (field.length() != 0) {
			if (field.length() >= MAX_LENGTH) {
				setNumber(nombreMaximal);
				return nombreMaximal;
			} else {
				try {
					int value = Integer.parseInt(field);
					return value;
				} catch (NumberFormatException ex) {
					throw new FieldWithNoNumber(field);
				}
			}
		}
		setNumber(nombreMinimal);
		return nombreMinimal;
	}

	/**
	 * Définit la valeur numérique du champ de texte.
	 *
	 * @param value La valeur numérique à définir
	 */
	public void setNumber(int value) {
		setText(String.valueOf(value));
	}
}