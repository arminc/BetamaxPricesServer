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
	String tmpDataOne = HtmlResponseParser.getPricesTable(responseData);
	assertNotNull(tmpDataOne);
	writeStringToFile("SMS_STEP2.html", tmpDataOne);
	// Step 4
	String tmpDataThree = HtmlResponseParser.removeUnwantedTrsFromSmsTableAndAddRootTag(tmpDataOne);
	assertNotNull(tmpDataThree);
	writeStringToFile("SMS_STEP4.html", tmpDataThree);
	// Step 5
	String tmpDataFour = HtmlResponseParser.removeUnwantedChars(tmpDataThree);
	assertNotNull(tmpDataFour);
	writeStringToFile("SMS_STEP5.xml", tmpDataFour);
	// Step 6
	Document document = DocumentParser.parseTableDataToXmlDocument(tmpDataFour);
	assertNotNull(document);
	writeDocumentToFile("SMS_STEP6.xml", document);
	// Step 7
	HashMap<String, String> pricesMap = DocumentParser.getPricesFromTableData(tmpDataFour);
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
	String tmpDataOne = HtmlResponseParser.getPricesTable(responseData);
	assertNotNull(tmpDataOne);
	writeStringToFile("CALL_STEP2.html", tmpDataOne);
	// Step 4
	String tmpDataThree = HtmlResponseParser.removeUnwantedTrsFromCallTableAndAddRootTag(tmpDataOne);
	assertNotNull(tmpDataThree);
	writeStringToFile("CALL_STEP4.html", tmpDataThree);
	// Step 5
	String tmpDataFour = HtmlResponseParser.removeUnwantedChars(tmpDataThree);
	assertNotNull(tmpDataFour);
	writeStringToFile("CALL_STEP5.xml", tmpDataFour);
	// Step 6
	Document document = DocumentParser.parseTableDataToXmlDocument(tmpDataFour);
	assertNotNull(document);
	writeDocumentToFile("CALL_STEP6.xml", document);
	// Step 7
	HashMap<String, String> pricesMap = DocumentParser.getPricesFromTableData(tmpDataFour);
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
	String tmpDataOne = HtmlResponseParser.getRawExchangeSelectData(responseData);
	assertNotNull(tmpDataOne);
	writeStringToFile("EXCHANGE_STEP2.html", tmpDataOne);
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
