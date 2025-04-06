package gui.champNombre;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

import javax.swing.JTextField;

import engine.utilitaire.FieldWithNoNumber;

@SuppressWarnings("serial")
public class JNumberField extends JTextField{

	private int nombreMinimal;
	
	private int nombreMaximal;
	
	public JNumberField(int min, int max) {
        super(String.valueOf(min));
        this.nombreMinimal = min;
        this.nombreMaximal = max;

        addKeyListener(new NumberControls());
        addFocusListener(new FocusControls());
        addMouseWheelListener(new WheelControls());
    }
	
	public class NumberControls extends KeyAdapter {
		
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar();

            if (!Character.isDigit(c)) {
                e.consume();
                return;
            }
		}
	}
	
	public class FocusControls extends FocusAdapter {
		
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
            }
            else {
            	setNumber(nombreMinimal);
            }
	    }
	}
	
	public class WheelControls extends MouseAdapter {
		
		public void mouseWheelMoved(MouseWheelEvent e){
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

	public void addNumber(Boolean value) {
		if (value == true) {
			setNumber(getNumber() + 1);
		} else {
			setNumber(getNumber() - 1);
		}
	}
	
	public int getNumber() {
		String field = getText();
		if (field.length() != 0) {
	        try {
	            int value = Integer.parseInt(field);
	            return value;
	        } catch (NumberFormatException ex) {
	        	throw new FieldWithNoNumber(field);
	        }
		}
		return 0;
	}
	
	public void setNumber(int value) {
		setText(String.valueOf(value));
	}
 }