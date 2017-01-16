package system;

import java.awt.Color;

import javax.swing.JTextField;

class TextFieldColorizer{
	private JTextField TextField;
	Color good = new Color(198,239,206);
	Color bad = new Color (255,199,206);
	
	public TextFieldColorizer(JTextField TextField, dataType dataType){
		this.TextField = TextField;
		String s = TextField.getText();
		switch (dataType) {
		case decimal:
			if (s.matches("[0-9]+") || s.matches("[0-9]+\\.?[0-9]{1,2}"))
				background(good);
			else{background(bad);}
			break;

		case integer:
			if (s.matches("[0-9]+"))
				background(good);
			else{background(bad);}
			break;


		case userbarcode:
			if (s.matches("[0-9]{3,5}"))
				background(good);
			else{background(bad);}
			break;

		case productbarcode:
			if (s.matches("[0-9]{6,}"))
				background(good);
			else{background(bad);}
			break;

		case sex:
				if (s.matches("[mMfFkK]"))
					background(good);
				else{background(bad);}
				break;
		}
	}
	public void background(Color c){
		TextField.setBackground(c);
	}
}