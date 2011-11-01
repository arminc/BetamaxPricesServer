package nl.coralic.betamax.prices.server.parsers;


public class HtmlResponseParser
{
    public static String parseResponseToPricesTable(String responseData)
    {
	String pricesTable = StringManipulations.getPricesTable(responseData);
	StringBuffer pricesTableBuffer = StringManipulations.removeUnwantedTrTagsFromTable(pricesTable);
	pricesTable =  StringManipulations.addRootTags(pricesTableBuffer);
	pricesTable = StringManipulations.removeUnwantedChars(pricesTable);
	return pricesTable;
    }
    
    public static String parseResponseToExchangeTable(String responseData)
    {
	String exchangeTable = StringManipulations.getExchangeTable(responseData);
	return exchangeTable;
    }    
}
