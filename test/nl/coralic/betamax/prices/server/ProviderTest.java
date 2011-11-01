package nl.coralic.betamax.prices.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static org.junit.Assert.*;
import nl.coralic.betamax.prices.server.http.UrlFetcher;
import nl.coralic.betamax.prices.server.parsers.DocumentParser;
import nl.coralic.betamax.prices.server.parsers.StringManipulations;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

public class ProviderTest
{
    private static String responseData;
    private static final String testFolder = "tmpTestFolder";
    private static final File folder = new File(testFolder);

    @BeforeClass
    public static void initialize()
    {
	createOrEmptyFolder();
    }

    @Test
    public void getSmsPrices() throws Exception
    {
	// Step 1
	responseData = UrlFetcher.fetchSmsRates("webcalldirect.com");
	assertNotNull(responseData);
	writeStringToFile("SMS_STEP1.html", responseData);
	// Step 2
	String pricesTable = StringManipulations.getPricesTable(responseData);
	assertNotNull(pricesTable);
	writeStringToFile("SMS_STEP2.html", pricesTable);
	// Step 3
	StringBuffer tableWithoutUnwantedTrTags = StringManipulations.removeUnwantedTrTagsFromTable(pricesTable);
	assertNotNull(tableWithoutUnwantedTrTags);
	writeStringToFile("SMS_STEP3.html", tableWithoutUnwantedTrTags.toString());
	// Step 4
	String completeTable = StringManipulations.addRootTags(tableWithoutUnwantedTrTags);
	assertNotNull(completeTable);
	writeStringToFile("SMS_STEP4.xml", completeTable);
	// Step 5
	String tableWithoutUnwantedChars = StringManipulations.removeUnwantedChars(completeTable);
	assertNotNull(tableWithoutUnwantedChars);
	writeStringToFile("SMS_STEP5.xml", tableWithoutUnwantedChars);
	// Step 6
	Document document = DocumentParser.parseTableDataToXmlDocument(tableWithoutUnwantedChars);
	assertNotNull(document);
	writeDocumentToFile("SMS_STEP6.xml", document);
	// Step 6
	HashMap<String, String> pricesMap = DocumentParser.getPricesFromTableData(tableWithoutUnwantedChars);
	assertNotNull(pricesMap);
	writeHasMapToFile("SMS_STEP7.txt", pricesMap);
    }

    @Test
    public void getCallingRates() throws Exception
    {
	// Step 1
	responseData = UrlFetcher.fetchCallingRates("webcalldirect.com");
	assertNotNull(responseData);
	writeStringToFile("CALL_STEP1.html", responseData);
	// Step 2
	String pricesTable = StringManipulations.getPricesTable(responseData);
	assertNotNull(pricesTable);
	writeStringToFile("CALL_STEP2.html", pricesTable);
	// Step 3
	StringBuffer tableWithoutUnwantedTrTags = StringManipulations.removeUnwantedTrTagsFromTable(pricesTable);
	assertNotNull(tableWithoutUnwantedTrTags);
	writeStringToFile("CALL_STEP3.html", tableWithoutUnwantedTrTags.toString());
	// Step 4
	String completeTable = StringManipulations.addRootTags(tableWithoutUnwantedTrTags);
	assertNotNull(completeTable);
	writeStringToFile("SMS_STEP4.xml", completeTable);
	// Step 5
	String tableWithoutUnwantedChars = StringManipulations.removeUnwantedChars(completeTable);
	assertNotNull(tableWithoutUnwantedChars);
	writeStringToFile("CALL_STEP5.xml", tableWithoutUnwantedChars);
	// Step 6
	Document document = DocumentParser.parseTableDataToXmlDocument(tableWithoutUnwantedChars);
	assertNotNull(document);
	writeDocumentToFile("CALL_STEP6.xml", document);
	// Step 7
	HashMap<String, String> pricesMap = DocumentParser.getPricesFromTableData(tableWithoutUnwantedChars);
	assertNotNull(pricesMap);
	writeHasMapToFile("CALL_STEP7.txt", pricesMap);
    }

    @Test
    public void getExchangeRates() throws Exception
    {
	// Step 1
	responseData = UrlFetcher.fetchSmsRates("webcalldirect.com");
	assertNotNull(responseData);
	writeStringToFile("EXCHANGE_STEP1.html", responseData);
	// Step 2
	String exchangeTable = StringManipulations.getExchangeTable(responseData);
	assertNotNull(exchangeTable);
	writeStringToFile("EXCHANGE_STEP2.html", exchangeTable);
	// Step 3
	Document document = DocumentParser.parseTableDataToXmlDocument(exchangeTable);
	assertNotNull(document);
	writeDocumentToFile("EXCHANGE_STEP3.xml", document);
	// Step 4
	HashMap<String, String> exchangeMap = DocumentParser.getExchangeRatesFromTableData(exchangeTable);
	assertNotNull(exchangeMap);
	writeHasMapToFile("EXCHANGE_STEP4.txt", exchangeMap);
    }

    private static void createOrEmptyFolder()
    {
	boolean exists = folder.exists();
	if (exists)
	{
	    deleteFiles();
	}
	else
	{
	    createFolder();
	}
    }

    private static void deleteFiles()
    {
	File[] files = folder.listFiles();
	for (File file : files)
	{
	    boolean deleted = file.delete();
	    if (!deleted)
	    {
		fail("Could not delete " + file.getName());
	    }
	}
    }

    private static void createFolder()
    {
	boolean created = new File(testFolder).mkdir();
	if (!created)
	{
	    fail("Could not create the " + testFolder);
	}
    }

    private static void writeStringToFile(String file, String data) throws IOException
    {
	BufferedWriter out = new BufferedWriter(new FileWriter(testFolder + File.separator + file));
	out.write(data);
	out.close();
    }

    private static void writeDocumentToFile(String file, Document doc) throws TransformerFactoryConfigurationError, TransformerException
    {
	Source source = new DOMSource(doc);
	File fileToWrite = new File(testFolder + File.separator + file);
	Result result = new StreamResult(fileToWrite);
	Transformer xformer = TransformerFactory.newInstance().newTransformer();
	xformer.transform(source, result);
    }

    private void writeHasMapToFile(String file, HashMap<String, String> pricesMap) throws IOException
    {
	BufferedWriter out = new BufferedWriter(new FileWriter(testFolder + File.separator + file));
	Iterator<String> countries = pricesMap.keySet().iterator();
	while (countries.hasNext())
	{
	    String country = countries.next();
	    out.write(country + " " + pricesMap.get(country) + "\n");
	}
	out.close();
    }
}
