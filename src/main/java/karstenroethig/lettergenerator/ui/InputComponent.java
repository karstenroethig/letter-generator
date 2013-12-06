package karstenroethig.lettergenerator.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.StringUtils;

import karstenroethig.lettergenerator.domain.Letter;

public class InputComponent extends JComponent {

    /** Use serialVersionUID for interoperability. */
    private static final long serialVersionUID = 7929854290458835344L;

    private Map<String, JTextComponent> INPUT_FIELDS = new HashMap<String, JTextComponent>();

    public InputComponent() {

        setLayout( new GridBagLayout() );

        addComp( createSenderPanel(), 0, 0, 1, 1, 1, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL );
        addComp( createAddresseePanel(), 1, 0, 1, 1, 1, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH );
        addComp( createContentPanel(), 0, 1, 2, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH );
        addComp( createOptionsPanel(), 0, 2, 2, 1, 1, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL );

    }

    private JPanel createSenderPanel() {

        JPanel panel = new JPanel();

        panel.setPreferredSize( new Dimension( 300, 130 ) );

        panel.setLayout( new GridBagLayout() );

        panel.setBorder( new CompoundBorder( new TitledBorder( null, "Absender", TitledBorder.LEFT, TitledBorder.TOP ),
                null ) );

        addComp( panel, new JLabel( "Name" ), 0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE );
        addComp( panel, new JLabel( "Straße" ), 0, 1, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.NONE );
        addComp( panel, new JLabel( "PLZ" ), 0, 2, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE );
        addComp( panel, new JLabel( "Ort" ), 0, 3, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE );
        addComp( panel, createTextField( "senderName" ), 1, 0, 1, 1, 1, 0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.HORIZONTAL );
        addComp( panel, createTextField( "senderStreet" ), 1, 1, 1, 1, 1, 0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.HORIZONTAL );
        addComp( panel, createTextField( "senderPostcode" ), 1, 2, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.HORIZONTAL );
        addComp( panel, createTextField( "senderCity" ), 1, 3, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.HORIZONTAL );

        return panel;

    }

    private JPanel createAddresseePanel() {

        JPanel panel = new JPanel();

        panel.setPreferredSize( new Dimension( 250, 130 ) );

        panel.setLayout( new GridBagLayout() );

        panel.setBorder( new CompoundBorder( new TitledBorder( null, "Empfänger", TitledBorder.LEFT, TitledBorder.TOP ),
                null ) );

        addComp( panel, createTextAreaWithScrollPane( "addressee" ), 0, 0, 1, 1 );

        return panel;

    }

    private JPanel createContentPanel() {

        JPanel panel = new JPanel();

        panel.setPreferredSize( new Dimension( 600, 300 ) );

        panel.setLayout( new GridBagLayout() );

        panel.setBorder( new CompoundBorder( new TitledBorder( null, "Schreiben", TitledBorder.LEFT, TitledBorder.TOP ),
                null ) );

        addComp( panel, new JLabel( "Datum" ), 0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.NONE );
        addComp( panel, new JLabel( "Betreff" ), 0, 1, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.NONE );
        addComp( panel, new JLabel( "Text" ), 0, 2, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE );
        addComp( panel, createTextField( "formattedDate" ), 1, 0, 1, 1, 1, 0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.HORIZONTAL );
        addComp( panel, createTextField( "summary" ), 1, 1, 1, 1, 1, 0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.HORIZONTAL );
        addComp( panel, createTextAreaWithScrollPane( "text" ), 1, 2, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
            GridBagConstraints.BOTH );

        return panel;

    }

    private JPanel createOptionsPanel() {

        JPanel panel = new JPanel();

        panel.setLayout( new GridBagLayout() );

        panel.setBorder( new CompoundBorder( new TitledBorder( null, "Optionen", TitledBorder.LEFT, TitledBorder.TOP ),
                null ) );

        addComp( panel, new JLabel( "Ausgabeverzeichnis" ), 0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.NONE );
        addComp( panel, createTextField( "outputDirectory" ), 1, 0, 1, 1, 1, 0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.HORIZONTAL );
        addComp( panel, new JButton( "Wählen" ), 2, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.NONE );

        return panel;

    }

    private JTextField createTextField( String fieldId ) {

        if( fieldId == null ) {
            throw new IllegalArgumentException( "No field ID entered" );
        }

        if( INPUT_FIELDS.containsKey( fieldId ) ) {
            throw new IllegalArgumentException( "Field ID already exists" );
        }

        JTextField textField = new JTextField();

        INPUT_FIELDS.put( fieldId, textField );

        return textField;
    }

    private JScrollPane createTextAreaWithScrollPane( String fieldId ) {

        if( fieldId == null ) {
            throw new IllegalArgumentException( "No field ID entered" );
        }

        if( INPUT_FIELDS.containsKey( fieldId ) ) {
            throw new IllegalArgumentException( "Field ID already exists" );
        }

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane( textArea );

        INPUT_FIELDS.put( fieldId, textArea );

        return scrollPane;
    }

    private void addComp( Component comp, int gridx, int gridy, int gridwidth, int gridheight, double weightx,
        double weighty, int anchor, int fill ) {

        add( comp,
            new GridBagConstraints( gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill,
                new Insets( 4, 4, 4, 4 ), 0, 0 ) );
    }

    private void addComp( JPanel parentPanel, Component comp, int gridx, int gridy, int gridwidth, int gridheight ) {

        parentPanel.add( comp,
            new GridBagConstraints( gridx, gridy, gridwidth, gridheight, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets( 2, 2, 2, 2 ), 0, 0 ) );
    }

    private void addComp( JPanel parentPanel, Component comp, int gridx, int gridy, int gridwidth, int gridheight,
        double weightx, double weighty, int anchor, int fill ) {

        parentPanel.add( comp,
            new GridBagConstraints( gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill,
                new Insets( 2, 2, 2, 2 ), 0, 0 ) );
    }

    public void setLetter( Letter letter ) {

        if( letter == null ) {

            setFieldText( "senderName", StringUtils.EMPTY );
            setFieldText( "senderStreet", StringUtils.EMPTY );
            setFieldText( "senderPostcode", StringUtils.EMPTY );
            setFieldText( "senderCity", StringUtils.EMPTY );
            setFieldText( "addressee", StringUtils.EMPTY );
            setFieldText( "formattedDate", StringUtils.EMPTY );
            setFieldText( "summary", StringUtils.EMPTY );
            setFieldText( "text", StringUtils.EMPTY );

        } else {

            setFieldText( "senderName", letter.getSenderName() );
            setFieldText( "senderStreet", letter.getSenderStreet() );
            setFieldText( "senderPostcode", letter.getSenderPostcode() );
            setFieldText( "senderCity", letter.getSenderCity() );
            setFieldText( "addressee", letter.getAddressee() );
            setFieldText( "formattedDate", letter.getFormattedDate() );
            setFieldText( "summary", letter.getSummary() );
            setFieldText( "text", letter.getText() );
        }
    }

    private void setFieldText( String fieldId, String text ) {

        if( ( fieldId == null ) || ( INPUT_FIELDS.containsKey( fieldId ) == false ) ) {
            return;
        }

        JTextComponent textComponent = INPUT_FIELDS.get( fieldId );

        if( textComponent == null ) {
            return;
        }

        if( text == null ) {
            textComponent.setText( StringUtils.EMPTY );
        } else {
            textComponent.setText( text );
        }
    }

    public Letter readLetter() {

        Letter letter = new Letter();

        letter.setSenderName( readFieldText( "senderName" ) );
        letter.setSenderStreet( readFieldText( "senderStreet" ) );
        letter.setSenderPostcode( readFieldText( "senderPostcode" ) );
        letter.setSenderCity( readFieldText( "senderCity" ) );

        letter.setAddressee( readFieldText( "addressee" ) );

        letter.setFormattedDate( readFieldText( "formattedDate" ) );
        letter.setSummary( readFieldText( "summary" ) );
        letter.setText( readFieldText( "text" ) );

        return letter;
    }

    public String readOutputDirectory() {
        return readFieldText( "outputDirectory" );
    }

    private String readFieldText( String fieldId ) {

        if( ( fieldId == null ) || ( INPUT_FIELDS.containsKey( fieldId ) == false ) ) {
            return null;
        }

        JTextComponent textComponent = INPUT_FIELDS.get( fieldId );

        if( textComponent == null ) {
            return null;
        }

        return textComponent.getText();
    }
}
