package karstenroethig.lettergenerator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import karstenroethig.lettergenerator.domain.Letter;
import karstenroethig.lettergenerator.model.LetterGeneratorModel;

import org.apache.commons.lang3.StringUtils;

public class LetterGeneratorMenuBar extends JMenuBar {

	/** Use serialVersionUID for interoperability. */
	private static final long serialVersionUID = -4488135531804338591L;

    private InputComponent inputComponent;

	public LetterGeneratorMenuBar( InputComponent inputComponent ) {

        this.inputComponent = inputComponent;

        // Menü "Datei"
        JMenu menu = new JMenu( "Datei" );
        add( menu );

        // Menüpunkt "Datei" -> "Öffnen"
        JMenuItem menuItemOpen = new JMenuItem( "Öffnen", new ImageIcon( getClass().getResource( "open.gif" ) ) );

        menuItemOpen.addActionListener( new OpenActionListener() );

        menu.add( menuItemOpen );

        // Menüpunkt "Datei" -> "Speichern"
        JMenuItem menuItemSave = new JMenuItem( "Speichern", new ImageIcon( getClass().getResource( "save.gif" ) ) );

        menuItemSave.addActionListener( new SaveActionListener() );

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

        // Menü "Bearbeiten"
        JMenu menuEdit = new JMenu( "Bearbeiten" );
        add( menuEdit );

        // Menüpunkt "Bearbeiten" -> "Eingaben zurücksetzen"
        JMenuItem menuItemReset = new JMenuItem( "Eingaben zurücksetzen" );

        menuItemReset.addActionListener( new ResetActionListener() );

        menuEdit.add( menuItemReset );
    }

    protected class OpenActionListener implements ActionListener {

        @Override
        public void actionPerformed( ActionEvent event ) {

            String outputDirectory = inputComponent.readOutputDirectory();

            JFileChooser fileChooser = new JFileChooser( StringUtils.isBlank( outputDirectory ) ? "."
                                                                                                : outputDirectory );
            fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );

            int returnVal = fileChooser.showOpenDialog( null );

            if( returnVal == JFileChooser.APPROVE_OPTION ) {

                try {
                    Letter letter = LetterGeneratorModel.importLetterFromXmlFile( fileChooser.getSelectedFile() );

                    inputComponent.setLetter( letter );
                } catch( JAXBException ex ) {
                    JOptionPane.showMessageDialog( null, "Beim Laden der XML-Datei ist ein Fehler aufgetreten." );
                    ex.printStackTrace();
                }
            }
        }
    }

    protected class SaveActionListener implements ActionListener {

        @Override
        public void actionPerformed( ActionEvent event ) {

            String outputDirectory = inputComponent.readOutputDirectory();

            JFileChooser fileChooser = new JFileChooser( StringUtils.isBlank( outputDirectory ) ? "."
                                                                                                : outputDirectory );
            fileChooser.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );

            int returnVal = fileChooser.showSaveDialog( null );

            if( returnVal == JFileChooser.APPROVE_OPTION ) {

                try {
                    LetterGeneratorModel.exportLetterToXmlFile( inputComponent.readLetter(),
                        fileChooser.getSelectedFile() );
                } catch( JAXBException ex ) {
                    JOptionPane.showMessageDialog( null, "Beim Speichern der XML-Datei ist ein Fehler aufgetreten." );
                    ex.printStackTrace();
                }
            }
        }
    }

    protected class ResetActionListener implements ActionListener {

        @Override
        public void actionPerformed( ActionEvent event ) {
            inputComponent.setLetter( null );
            inputComponent.setOutputDirectory( StringUtils.EMPTY );
        }
    }
}
