package karstenroethig.lettergenerator.ui;

import javax.swing.UIManager;

public class App {

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel" );
		} catch( Exception ex ) {
			// Nothing to do
		}
		
		LetterGeneratorFrame frame = new LetterGeneratorFrame();
		frame.setVisible( true );
	}
}
