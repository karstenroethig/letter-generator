package karstenroethig.lettergenerator.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import karstenroethig.lettergenerator.domain.Letter;
import karstenroethig.lettergenerator.model.LetterGeneratorModel;

public class LetterGeneratorFrame extends JFrame {

    /** Use serialVersionUID for interoperability. */
    private static final long serialVersionUID = 875495102719093700L;

    private InputComponent inputComponent;

    public LetterGeneratorFrame() {

        setTitle( "Letter Generator" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        // busy glasspane is initially invisible
        setGlassPane( new BusyGlass() );

        // add a menubar
        setJMenuBar( new LetterGeneratorMenuBar() );

        // add a horizontal toolbar
        JToolBar toolbar = new JToolBar();
        add( toolbar, BorderLayout.NORTH );

        JButton button = new JButton();
        button.setIcon( new ImageIcon( getClass().getResource( "run.gif" ) ) );
        button.setToolTipText( "Generierung starten" );
        button.addActionListener( new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    setFrameBusy( true );

                    Thread th = new Thread() {
                            @Override
                            public void run() {

                                try {
                                    Thread.sleep( 1000 );
                                } catch( Exception ex ) {
                                    // Nothing to do
                                }

                                Letter letter = inputComponent.readLetter();

                                // TODO Validierung

                                LetterGeneratorModel.generateLetter( letter, "pathToDir" );

                                setFrameBusy( false );
                            }
                        };

                    th.start();

                }
            } );
        toolbar.add( button );

        // add the content area
        /*
        JLabel label = new JLabel( "I'm content but a little blue." );
        label.setHorizontalAlignment( JLabel.CENTER );
        label.setPreferredSize( new Dimension( 300, 160 ) );
        label.setBackground( new Color( 197, 216, 236 ) );
        label.setOpaque( true ); // labels non-opaque by default
        add( label );
        */

        inputComponent = new InputComponent();

        add( inputComponent );

        // add a statusbar
        JLabel statusLabel = new JLabel( "I show status." );
        statusLabel.setBorder( new EmptyBorder( 4, 4, 4, 4 ) );
        statusLabel.setHorizontalAlignment( JLabel.LEADING );
        add( statusLabel, BorderLayout.SOUTH );

        pack();

        // Model setzen
        inputComponent.setLetter( LetterGeneratorModel.loadDefaultLetter() );
    }

    /**
     * Make toplevel "busy"
     *
     * @param  busy  DOCUMENT ME!
     */
    public void setFrameBusy( boolean busy ) {

        getGlassPane().setVisible( busy );

        // Must explicitly disable the menubar because on OSX ist will be
        // in the system menubar and not covered by the glasspane
        getJMenuBar().setEnabled( !busy );
    }
}
