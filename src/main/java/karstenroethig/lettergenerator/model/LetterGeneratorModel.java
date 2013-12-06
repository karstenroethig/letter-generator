package karstenroethig.lettergenerator.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import karstenroethig.lettergenerator.domain.Letter;

public class LetterGeneratorModel {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat( "dd.MM.yyyy" );

    public static Letter loadDefaultLetter() {

        Properties props = new Properties();
        FileInputStream in = null;

        try {
            in = new FileInputStream( "config.properties" );

            props.load( in );
        } catch( IOException ex ) {
            // Nothing do do
        } finally {
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

        if( letter.getSenderName() != null ) {
            text.append( letter.getSenderName() );
        } else {
            text.append( "..." );
        }

        letter.setText( text.toString() );

        return letter;
    }

    public static String loadDefaultOutputDirectory() {

        Properties props = new Properties();
        FileInputStream in = null;

        try {
            in = new FileInputStream( "config.properties" );

            props.load( in );
        } catch( IOException ex ) {
            // Nothing do do
        } finally {
        	IOUtils.closeQuietly( in );
        }

        return props.getProperty( "outputDirectory" );
    }

    public static void generateLetter( Letter letter, String pathToDir ) {

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
        } catch( IOException ex ) {
            // Nothing to do
        } finally {
        	IOUtils.closeQuietly( out );
        }
    }
}
