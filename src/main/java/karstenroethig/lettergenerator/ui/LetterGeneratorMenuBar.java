package karstenroethig.lettergenerator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class LetterGeneratorMenuBar extends JMenuBar {

	/** Use serialVersionUID for interoperability. */
	private static final long serialVersionUID = -4488135531804338591L;

	public LetterGeneratorMenuBar() {

        // Menü "Datei"
        JMenu menu = new JMenu( "Datei" );
        add( menu );

        // Menüpunkt "Datei" -> "Öffnen"
        JMenuItem menuItemOpen = new JMenuItem( "Öffnen", new ImageIcon( getClass().getResource( "open.gif" ) ) );

        menuItemOpen.addActionListener( new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    System.out.println( "Noch nicht implementiert" );
                }
            } );

        menu.add( menuItemOpen );

        // Menüpunkt "Datei" -> "Speichern"
        JMenuItem menuItemSave = new JMenuItem( "Speichern", new ImageIcon( getClass().getResource( "save.gif" ) ) );

        menuItemSave.addActionListener( new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    System.out.println( "Noch nicht implementiert" );
                }
            } );

        menu.add( menuItemSave );

        // Menüpunkt "Datei" -> "Beenden"
        JMenuItem menuItemExit = new JMenuItem( "Beenden" );

        menuItemExit.addActionListener( new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    System.exit( 0 );
                }
            } );

        menu.add( menuItemExit );
    }
}
