package nl.coralic.betamax.prices.server.parsers;

public class StringManipulations
{
    public static String getPricesTable(String html)
    {
	String pricesTable = stripDataBeforePriceTable(html);
	pricesTable = stripDataAfterTag(pricesTable, "</table>");
	return pricesTable;
    }

    private static String stripDataBeforePriceTable(String html)
    {
	String rawTableData = new String();
	String[] tablesArray = html.split("<table");
	for (int i = 0; i < tablesArray.length; i++)
	{
	    // checking for both allows this function to work for call and for sms prices, there is always only one in the HTML
	    if (tablesArray[i].contains("/en/smsrates/cheap-sms-afghanistan") || tablesArray[i].contains("/en/callingrates/landline/cheap-calls-afghanistan"))
	    {
		rawTableData = "<table" + tablesArray[i];
	    }
	}
	return rawTableData;
    }

    private static String stripDataAfterTag(String html, String tag)
    {
	int endOfTable = html.indexOf(tag);
	html = html.substring(0, endOfTable);
	return html;
    }

    public static String getExchangeTable(String responseData)
    {
	String exchangeTable = stripDataBeforeExchangeTable(responseData);
	exchangeTable = stripDataAfterTag(exchangeTable, "</select>");
	// this is added because it is lost in "stripDataAfterTag" and it makes the string XML correct
	return exchangeTable + "</select>";
    }

    private static String stripDataBeforeExchangeTable(String responseData)
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

    public static StringBuffer removeUnwantedTrTagsFromTable(String pricesTable)
    {
	StringBuffer rawData = new StringBuffer();
	String[] trArray = pricesTable.split("<tr>");
	for (int i = 0; i < trArray.length; i++)
	{
	    // check if the TR matches smsrates or callingrates if so use it, only one is present depending of the table that is parsed
	    if ((trArray[i].contains("/en/smsrates/") || trArray[i].contains("/en/callingrates/")) && trArray[i].contains("Main-Text"))
	    {
		rawData.append("<tr>");
		rawData.append(trArray[i]);
	    }
	}
	return rawData;
    }

    public static String addRootTags(StringBuffer pricesTable)
    {
	pricesTable.insert(0, "<root>");
	pricesTable.append("</root>");
	return pricesTable.toString();
    }

    public static String removeUnwantedChars(String tableData)
    {
	tableData = tableData.replaceAll("&nbsp;", "");
	tableData = tableData.replaceAll("<font size=1>", "");
	tableData = tableData.replaceAll("</font>", "");
	return tableData;
    }

    public static String removeUnwantedTextFromPrice(String price)
    {
	int end = price.indexOf(";");
	price = price.substring(0, end);
	return price;
    }
}
