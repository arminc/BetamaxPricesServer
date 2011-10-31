package nl.coralic.betamax.prices.server;

import static org.junit.Assert.*;
import org.junit.Test;

public class HtmlResponseParserTest
{
    String testData = "<table>first</table><table>/en/smsrates/cheap-sms-afghanistan</table><table>second</table>";
    
    @Test
    public void getRawTableData()
    {
	String response = HtmlResponseParser.getPricesTable(testData);
	assertEquals("<table>/en/smsrates/cheap-sms-afghanistan", response);
    }
    
    @Test
    public void removeUnwantedTrsFromTableAddRootTag()
    {
	String response = HtmlResponseParser.removeUnwantedTrsFromSmsTableAndAddRootTag("<tr>fake></tr><tr>/en/smsrates/ Main-Text</tr>");
	assertEquals("<root><tr>/en/smsrates/ Main-Text</tr></root>", response);
    }
    
    @Test
    public void removeUnwantedChars()
    {
	String response = HtmlResponseParser.removeUnwantedChars("START&nbsp;<font size=1></font>-END");
	assertEquals("START-END", response);
    }
}
