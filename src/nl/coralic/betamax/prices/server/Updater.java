package nl.coralic.betamax.prices.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import nl.coralic.betamax.prices.server.datastore.Database;
import nl.coralic.betamax.prices.server.datastore.PriceEntity;

public class Updater
{
    private static List<String> providers = Arrays.asList("dialnow.com");

    public static void updateSmsPrices()
    {
	for (String provider : providers)
	{
	    update(provider);
	}
    }

    private static void update(String provider)
    {
	String responseData = UrlFetcher.fetchSmsRates(provider);
	HashMap<String, String> smsPrices = getSmsPricesFromResponse(responseData);
	//TODO: extract the exchange rates from the same response data and save these as well
	//Database.savePrices(provider, prices);
    }

    private static HashMap<String, String> getSmsPricesFromResponse(String responseData)
    {
	String pricesTableData = HtmlResponseParser.parseResponseToSmsPricesTable(responseData);
	return DocumentParser.getPricesFromTableData(pricesTableData);
    }
}
