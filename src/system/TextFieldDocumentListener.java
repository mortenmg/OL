package system;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class TextFieldDocumentListener implements DocumentListener {
	private JTextField textField;
	dataType dataType;

	public TextFieldDocumentListener(JTextField textField,dataType dataType){
		this.textField = textField;
		this.dataType = dataType;
	}

	public void insertUpdate(DocumentEvent e) {
		updateLog(e);
	}
	public void removeUpdate(DocumentEvent e) {
		updateLog(e);
	}
	public void changedUpdate(DocumentEvent e) {
		//Plain text components don't fire these events.
		updateLog(e);
	}
	public void updateLog(DocumentEvent e) {
		new TextFieldColorizer(textField,dataType);
	}
}