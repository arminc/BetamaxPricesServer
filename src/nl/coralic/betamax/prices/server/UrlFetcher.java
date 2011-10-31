package nl.coralic.betamax.prices.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlFetcher
{
    public static String fetchSmsRates(String provider)
    {
	String url = "http://" + provider + "/en/sms-rates.html";
	return fetchData(url);
    }
    
    public static String fetchCallingRates(String provider)
    {
	String url = "http://" + provider + "/en/calling-rates.html";
	return fetchData(url);
    }
    
    private static String fetchData(String providerUrl)
    {
	StringBuffer responseData = new StringBuffer();
	try
	{
	    URL url = new URL(providerUrl);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
	    String line;
	    while ((line = reader.readLine()) != null)
	    {
		responseData.append(line);
	    }
	    reader.close();
	}
	catch (MalformedURLException e)
	{
	    e.printStackTrace();
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
	return responseData.toString();
    }
}
