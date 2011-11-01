package nl.coralic.betamax.prices.server;

import static org.junit.Assert.*;
import nl.coralic.betamax.prices.server.parsers.StringManipulations;

import org.junit.Test;

public class StringManipulationsTest
{
    String testPricesData = "<table>first</table><table>/en/smsrates/cheap-sms-afghanistan</table><table>second</table>";
    String testExchangeData = "<select>first</select><select>Australian Dollar</select><select>second</select>";
    
    @Test
    public void getPricesTable()
    {
	String response = StringManipulations.getPricesTable(testPricesData);
	assertEquals("<table>/en/smsrates/cheap-sms-afghanistan", response);
    }
    
    @Test
    public void getExchangeTable()
    {
	String response = StringManipulations.getExchangeTable(testExchangeData);
	assertEquals("<select>Australian Dollar</select>", response);
    }
    
    @Test
    public void removeUnwantedTrTagsFromTable()
    {
	StringBuffer response = StringManipulations.removeUnwantedTrTagsFromTable("<tr>fake></tr><tr>/en/smsrates/ Main-Text</tr>");
	assertEquals("<tr>/en/smsrates/ Main-Text</tr>", response.toString());
    }
    
    @Test
    public void addRootTags()
    {
	StringBuffer data = new StringBuffer("data");
	String response =  StringManipulations.addRootTags(data);
	assertEquals("<root>data</root>", response);
    }
    
    @Test
    public void removeUnwantedChars()
    {
	String response = StringManipulations.removeUnwantedChars("START&nbsp;<font size=1></font>-END");
	assertEquals("START-END", response);
    }
    
    @Test
    public void removeUnwantedTextFromPrice()
    {
	String price = StringManipulations.removeUnwantedTextFromPrice("0.1578;ARS/SMS");
	assertEquals("0.1578", price);
    }
}
