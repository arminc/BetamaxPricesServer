package nl.coralic.betamax.prices.server.parsers;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DocumentParser
{
    public static Document parseTableDataToXmlDocument(String tableData)
    {
	Document doc = null;
	try
	{
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    InputSource is = new InputSource();
	    is.setCharacterStream(new StringReader(tableData));
	    doc = db.parse(is);
	}
	catch (ParserConfigurationException e)
	{
	    e.printStackTrace();
	}
	catch (SAXException e)
	{
	    e.printStackTrace();
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
	return doc;
    }
    
    public static HashMap<String, String> getPricesFromTableData(String pricesTableData)
    {
	HashMap<String, String> prices = new HashMap<String, String>();
	Document doc = parseTableDataToXmlDocument(pricesTableData);
	if(doc == null)
	{
	    return prices;
	}
	
	NodeList nodes = doc.getElementsByTagName("tr");
	for(int i=0; i<nodes.getLength(); i++)
	{
	    Node trNode = nodes.item(i);
	    Node firstTdNode = trNode.getChildNodes().item(0);
	    Node secondTdNode = trNode.getChildNodes().item(1);
	    String country = firstTdNode.getFirstChild().getTextContent();
	    String price = secondTdNode.getFirstChild().getTextContent();
	    prices.put(country, price);
	}
	return prices;
    }
    
    public static HashMap<String, String> getExchangeRatesFromTableData(String exchangeTableData)
    {
	HashMap<String, String> prices = new HashMap<String, String>();
	Document doc = parseTableDataToXmlDocument(exchangeTableData);
	if(doc == null)
	{
	    return prices;
	}
	
	NodeList nodes = doc.getElementsByTagName("option");
	for(int i=0; i<nodes.getLength(); i++)
	{
	    Node node = nodes.item(i);
	    String country = node.getTextContent();
	    String price = node.getAttributes().getNamedItem("value").getTextContent();
	    price = StringManipulations.removeUnwantedTextFromPrice(price);
	    prices.put(country, price);
	}
	return prices;
    }
}
