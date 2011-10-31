package nl.coralic.betamax.prices.server;

public class HtmlResponseParser
{
    public static String parseResponseToSmsPricesTable(String responseData)
    {
	String pricesTable = getPricesTable(responseData);
	pricesTable = removeUnwantedTrsFromSmsTableAndAddRootTag(pricesTable);
	pricesTable = removeUnwantedChars(pricesTable);
	return pricesTable;
    }

    public static String getPricesTable(String responseData)
    {
	String pricesTable = stripDataBeforePriceTable(responseData);
	pricesTable = removeUnwantedDataAfterTag(pricesTable, "</table>");
	return pricesTable;
    }
    
    private static String stripDataBeforePriceTable(String responseData)
    {
	String rawTableData = new String();
	String[] tablesArray = responseData.split("<table");
	for (int i = 0; i < tablesArray.length; i++)
	{
	    //checking for both allows this function to work for call and for sms prices, there is always only one in the HTML
	    if (tablesArray[i].contains("/en/smsrates/cheap-sms-afghanistan") || tablesArray[i].contains("/en/callingrates/landline/cheap-calls-afghanistan"))
	    {
		rawTableData = "<table" + tablesArray[i];
	    }
	}
	return rawTableData;
    }
    
    private static String removeUnwantedDataAfterTag(String rawTableData, String tag)
    {
	int endOfTable = rawTableData.indexOf(tag);
	rawTableData = rawTableData.substring(0,endOfTable);
	return rawTableData;
    }
    
    
    public static String getRawExchangeSelectData(String responseData)
    {
	String rawSelectData = new String();
	String[] selectArray = responseData.split("<select");
	for (int i = 0; i < selectArray.length; i++)
	{
	    if (selectArray[i].contains("Australian Dollar"))
	    {
		rawSelectData = "<select" + selectArray[i];
	    }
	}
	return rawSelectData;
    }
    
    public static String removeUnwantedTrsFromSmsTableAndAddRootTag(String rawTableData)
    {
	StringBuffer rawData = new StringBuffer();
	String[] trArray = rawTableData.split("<tr>");
	rawData.append("<root>");
	for (int i = 0; i < trArray.length; i++)
	{
	    if (trArray[i].contains("/en/smsrates/") && trArray[i].contains("Main-Text"))
	    {
		rawData.append("<tr>");
		rawData.append(trArray[i]);
	    }
	}
	rawData.append("</root>");
	return rawData.toString();
    }
    
    public static String removeUnwantedTrsFromCallTableAndAddRootTag(String rawTableData)
    {
	StringBuffer rawData = new StringBuffer();
	String[] trArray = rawTableData.split("<tr>");
	rawData.append("<root>");
	for (int i = 0; i < trArray.length; i++)
	{
	    if (trArray[i].contains("/en/callingrates/") && trArray[i].contains("Main-Text"))
	    {
		rawData.append("<tr>");
		rawData.append(trArray[i]);
	    }
	}
	rawData.append("</root>");
	return rawData.toString();
    }
    
    public static String removeUnwantedChars(String tableData)
    {
	tableData = tableData.replaceAll("&nbsp;", "");
	tableData = tableData.replaceAll("<font size=1>", "");
	tableData = tableData.replaceAll("</font>", "");
	return tableData;
    }
    
    
}
