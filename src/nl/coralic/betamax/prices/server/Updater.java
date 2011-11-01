package nl.coralic.betamax.prices.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import nl.coralic.betamax.prices.server.http.UrlFetcher;
import nl.coralic.betamax.prices.server.parsers.DocumentParser;
import nl.coralic.betamax.prices.server.parsers.HtmlResponseParser;

public class Updater
{
    private static List<String> providers = Arrays.asList("dialnow.com");

    public static void updatePrices()
    {
	for (String provider : providers)
	{
	    update(provider);
	}
    }

    private static void update(String provider)
    {
	HashMap<String, String> smsPrices = getSmsPricesFromResponse(provider);
	HashMap<String, String> callPrices = getCallPricesFromResponse(provider);
	HashMap<String, String> exchangeRates = getExchangeRatesFromResponse(provider);
	Prices.setSmsPrices(smsPrices);
	Prices.setCallPrices(callPrices);
	Prices.setExchangeRates(exchangeRates);
    }

    private static HashMap<String, String> getSmsPricesFromResponse(String provider)
    {
	String responseData = UrlFetcher.fetchSmsRates(provider);
	String pricesTableData = HtmlResponseParser.parseResponseToPricesTable(responseData);
	return DocumentParser.getPricesFromTableData(pricesTableData);
    }
    
    private static HashMap<String, String> getCallPricesFromResponse(String provider)
    {
	String responseData = UrlFetcher.fetchCallingRates(provider);
	String pricesTableData = HtmlResponseParser.parseResponseToPricesTable(responseData);
	return DocumentParser.getPricesFromTableData(pricesTableData);
    }
    
    private static HashMap<String, String> getExchangeRatesFromResponse(String provider)
    {
	String responseData = UrlFetcher.fetchSmsRates(provider);
	String exchangeRatesTableData = HtmlResponseParser.parseResponseToExchangeTable(responseData);
	return DocumentParser.getExchangeRatesFromTableData(exchangeRatesTableData);
    }
}
