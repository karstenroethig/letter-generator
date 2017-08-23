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

import net.sf.jasperreports.engine.JRException;

import karstenroethig.lettergenerator.domain.Letter;
import karstenroethig.lettergenerator.model.LetterGeneratorModel;

public class LetterGeneratorFrame extends JFrame
{
	/** Use serialVersionUID for interoperability. */
	private static final long serialVersionUID = 875495102719093700L;

	private InputComponent inputComponent;
	private JLabel statusLabel = null;

	public LetterGeneratorFrame()
	{
		inputComponent = new InputComponent();

		setTitle( "Letter Generator" );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		// busy glasspane is initially invisible
		setGlassPane( new BusyGlass() );

		// add a menubar
		setJMenuBar( new LetterGeneratorMenuBar( inputComponent ) );

		// add a horizontal toolbar
		JToolBar toolbar = new JToolBar();
		add( toolbar, BorderLayout.NORTH );

		JButton button = new JButton();
		button.setIcon( new ImageIcon( getClass().getResource( "run.gif" ) ) );
		button.setToolTipText( "Generierung starten" );
		button.addActionListener( new GenerateLetterActionListener() );
		toolbar.add( button );

		add( inputComponent );

		// add a statusbar
		statusLabel = new JLabel( " " );
		statusLabel.setBorder( new EmptyBorder( 4, 4, 4, 4 ) );
		statusLabel.setHorizontalAlignment( JLabel.LEADING );
		add( statusLabel, BorderLayout.SOUTH );

		pack();

		// Model setzen
		inputComponent.setLetter( LetterGeneratorModel.loadDefaultLetter() );
		inputComponent.setOutputDirectory( LetterGeneratorModel.loadDefaultOutputDirectory() );
	}

	public void setStatusMessage( String message )
	{
		if ( statusLabel != null )
		{
			statusLabel.setText( message );
		}
	}

	public void resetStatusMessage()
	{
		setStatusMessage( " " );
	}

	/**
	 * Make toplevel "busy"
	 *
	 * @param  busy  DOCUMENT ME!
	 */
	public void setFrameBusy( boolean busy )
	{
		getGlassPane().setVisible( busy );

		// Must explicitly disable the menubar because on OSX ist will be
		// in the system menubar and not covered by the glasspane
		getJMenuBar().setEnabled( !busy );
	}

	protected class GenerateLetterActionListener implements ActionListener
	{
		@Override
		public void actionPerformed( ActionEvent e )
		{
			setFrameBusy( true );
			resetStatusMessage();

			Thread th = new Thread()
			{
				@Override
				public void run()
				{
//					try {
//						Thread.sleep( 1000 );
//					}
//					catch ( Exception ex )
//					{
//						// Nothing to do
//					}

					Letter letter = inputComponent.readLetter();
					String outputDirectory = inputComponent.readOutputDirectory();

					// TODO Validierung

					try
					{
						LetterGeneratorModel.generateLetter( letter, outputDirectory );

						setStatusMessage( "Das Anschreiben wurde erfolgreich generiert." );
					}
					catch ( JRException ex )
					{
						setStatusMessage( "Bei der Erstellung des Anschreibens ist ein Fehler aufgetreten." );

						ex.printStackTrace();
					}

					setFrameBusy( false );
				}
			};

			th.start();
		}
	}
}
