package nl.coralic.betamax.prices.server;

import java.util.HashMap;

public class Prices
{
    private static HashMap<String, String> smsPrices = new HashMap<String, String>();
    private static HashMap<String, String> callPrices = new HashMap<String, String>();
    private static HashMap<String, String> exchangeRates = new HashMap<String, String>();
    
    public static HashMap<String, String> getSmsPrices()
    {
        return smsPrices;
    }
    public static void setSmsPrices(HashMap<String, String> smsPrices)
    {
        Prices.smsPrices = smsPrices;
    }
    public static HashMap<String, String> getCallPrices()
    {
        return callPrices;
    }
    public static void setCallPrices(HashMap<String, String> callPrices)
    {
        Prices.callPrices = callPrices;
    }
    public static HashMap<String, String> getExchangeRates()
    {
        return exchangeRates;
    }
    public static void setExchangeRates(HashMap<String, String> exchangeRates)
    {
        Prices.exchangeRates = exchangeRates;
    }    
}
