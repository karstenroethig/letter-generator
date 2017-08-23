package karstenroethig.lettergenerator.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.io.IOUtils;

import karstenroethig.lettergenerator.domain.Letter;

public class LetterGeneratorModel
{
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat( "dd.MM.yyyy" );

	public static Letter loadDefaultLetter()
	{
		Properties props = new Properties();
		FileInputStream in = null;

		try
		{
			in = new FileInputStream( "config.properties" );

			props.load( in );
		}
		catch ( IOException ex )
		{
			// Nothing do do
		}
		finally
		{
			IOUtils.closeQuietly( in );
		}

		Letter letter = new Letter();

		letter.setSenderName( props.getProperty( "senderName" ) );
		letter.setSenderStreet( props.getProperty( "senderStreet" ) );
		letter.setSenderPostcode( props.getProperty( "senderPostcode" ) );
		letter.setSenderCity( props.getProperty( "senderCity" ) );

		letter.setFormattedDate( DATE_FORMAT.format( new Date() ) );

		StringBuffer text = new StringBuffer();

		text.append( "Sehr geehrte Damen und Herren,\n" );
		text.append( "\n" );
		text.append( "...\n" );
		text.append( "\n" );
		text.append( "Mit freundlichen Grüßen\n" );
		text.append( "\n" );

		if ( letter.getSenderName() != null )
		{
			text.append( letter.getSenderName() );
		}
		else
		{
			text.append( "..." );
		}

		letter.setText( text.toString() );

		return letter;
	}

	public static String loadDefaultOutputDirectory()
	{
		Properties props = new Properties();
		FileInputStream in = null;

		try
		{
			in = new FileInputStream( "config.properties" );

			props.load( in );
		}
		catch ( IOException ex )
		{
			// Nothing do do
		}
		finally
		{
			IOUtils.closeQuietly( in );
		}

		return props.getProperty( "outputDirectory" );
	}

	public static void generateLetter( Letter letter, String pathToDir ) throws JRException
	{
		/*
		 * Save config
		 */
		Properties props = new Properties();

		props.setProperty( "senderName", letter.getSenderName() );
		props.setProperty( "senderStreet", letter.getSenderStreet() );
		props.setProperty( "senderPostcode", letter.getSenderPostcode() );
		props.setProperty( "senderCity", letter.getSenderCity() );

		props.setProperty( "outputDirectory", pathToDir );

		FileOutputStream out = null;

		try {
			out = new FileOutputStream( "config.properties" );

			props.store( out, null );
		}
		catch ( IOException ex )
		{
			// Nothing to do
		}
		finally
		{
			IOUtils.closeQuietly( out );
		}

		/*
		 * Filename
		 */
		String filename = createFilename( pathToDir, letter );

		/*
		 * Save letter as xml file
		 */
		try
		{
			exportLetterToXmlFile( letter, pathToDir, filename + ".xml" );
		}
		catch ( JAXBException ex )
		{
			// Nothing to do
		}

		/*
		 * Create letter
		 */
		Map<String, Object> params = new HashMap<String, Object>();

		params.put( "senderName", letter.getSenderName() );
		params.put( "senderStreet", letter.getSenderStreet() );
		params.put( "senderPostcode", letter.getSenderPostcode() );
		params.put( "senderCity", letter.getSenderCity() );

		params.put( "addressee", letter.getAddressee() );

		params.put( "formattedDate", letter.getFormattedDate() );

		params.put( "summary", letter.getSummary() );
		params.put( "text", letter.getText() );

		JasperReport jasperReport = JasperCompileManager.compileReport( "reports/letter.xml" );
		JasperPrint jasperPrint = JasperFillManager.fillReport( jasperReport, params, new JREmptyDataSource() );

		JasperExportManager.exportReportToPdfFile( jasperPrint, pathToDir + File.separator + filename + ".pdf" );
	}

	public static void exportLetterToXmlFile( Letter letter, String pathToDir, String filename ) throws JAXBException
	{
		exportLetterToXmlFile( letter, new File( pathToDir, filename ) );
	}

	public static void exportLetterToXmlFile( Letter letter, File xmlFileOrDir ) throws JAXBException
	{
		File xmlFile;

		if ( xmlFileOrDir.isDirectory() )
		{
			xmlFile = new File( xmlFileOrDir, createFilename( xmlFileOrDir.getAbsolutePath(), letter ) + ".xml" );
		}
		else
		{
			xmlFile = xmlFileOrDir;
		}

		JAXBContext context = JAXBContext.newInstance( Letter.class );
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );

		marshaller.marshal( letter, xmlFile );
	}

	public static Letter importLetterFromXmlFile( File xmlFile ) throws JAXBException
	{
		JAXBContext jaxbDbContext = JAXBContext.newInstance( Letter.class );
		Unmarshaller dbUnmarshaller = jaxbDbContext.createUnmarshaller();

		return ( Letter )dbUnmarshaller.unmarshal( xmlFile );
	}

	private static String createFilename( String pathToDir, Letter letter )
	{
		StringBuffer filename = new StringBuffer();

		String summary = letter.getSummary();

		for ( int i = 0; i < summary.length(); i++ )
		{
			char ch = summary.charAt( i );

			if ( ( ( ch >= 'a' ) && ( ch <= 'z' ) ) || ( ( ch >= 'A' ) && ( ch <= 'Z' ) ) ||
					( ( ch >= '0' ) && ( ch <= '9' ) ) || ( ch == '-' ) )
			{
				filename.append( ch );
			}
			else
			{
				filename.append( "_" );
			}
		}

		return findNoneExistingFilename( pathToDir, filename.toString(), new String[] {"pdf", "xml" } );
	}

	private static String findNoneExistingFilename( String pathToDir, String filename, String[] fileEndings )
	{
		File directory = new File( pathToDir );

		boolean filesExists = false;

		for ( String fileEnding : fileEndings )
		{
			File file = new File( directory, filename + "." + fileEnding );

			if ( file.exists() )
			{
				filesExists = true;

				break;
			}
		}

		if ( filesExists == false )
		{
			return filename;
		}

		int count = 0;

		do
		{
			count++;
			filesExists = false;

			for ( String fileEnding : fileEndings )
			{
				File file = new File( directory, filename + "(" + count + ")." + fileEnding );

				if ( file.exists() )
				{
					filesExists = true;

					break;
				}
			}

		}
		while ( filesExists );

		return filename + "(" + count + ")";
	}
}
