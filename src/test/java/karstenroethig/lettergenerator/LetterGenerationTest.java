package karstenroethig.lettergenerator;

import java.util.HashMap;
import java.util.Map;

import karstenroethig.lettergenerator.domain.Letter;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.junit.Test;

public class LetterGenerationTest
{
	@Test
	public void generateTestLetter() throws Exception
	{
		Letter letter = new Letter();

		letter.setSenderName( "Max Mustermann" );
		letter.setSenderStreet( "Musterallee 42" );
		letter.setSenderPostcode( "00815" );
		letter.setSenderCity( "Musterstadt" );

		letter.setAddressee( "Max Mustermann\nMusterstraße 42\n\n00815 Musterdorf" );

		letter.setFormattedDate( "04.06.2012" );

		letter.setSummary( "Hier könnte Ihr Betreff stehen" );
		letter.setText( "Sehr geehrte Damen und Herren,\n\nich habe folgendes Anliegen: ..." );

		JasperReport jasperReport;
		JasperPrint jasperPrint;

		try
		{
			Map<String, Object> params = new HashMap<>();

			params.put( "senderName", letter.getSenderName() );
			params.put( "senderStreet", letter.getSenderStreet() );
			params.put( "senderPostcode", letter.getSenderPostcode() );
			params.put( "senderCity", letter.getSenderCity() );

			params.put( "addressee", letter.getAddressee() );

			params.put( "formattedDate", letter.getFormattedDate() );

			params.put( "summary", letter.getSummary() );
			params.put( "text", letter.getText() );

			jasperReport = JasperCompileManager.compileReport( "reports/letter.xml" );
			jasperPrint = JasperFillManager.fillReport( jasperReport, params, new JREmptyDataSource() );

			JasperExportManager.exportReportToPdfFile( jasperPrint, "target/letter.pdf" );
		}
		catch ( JRException ex )
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
