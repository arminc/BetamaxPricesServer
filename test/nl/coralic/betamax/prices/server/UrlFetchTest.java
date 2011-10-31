package nl.coralic.betamax.prices.server;

import static org.junit.Assert.*;
import nl.coralic.betamax.prices.server.UrlFetcher;

import org.junit.Test;

public class UrlFetchTest
{
    @Test
    public void fetchUrl()
    {
	String response = UrlFetcher.fetchSmsRates("webcalldirect.com");
	assertNotNull(response);
	assertNotSame(new String(), response);
    }
    
    @Test
    public void fetchWrongUrl()
    {
	String response = UrlFetcher.fetchSmsRates("fake");
	assertNotNull(response);
	assertEquals("", response);
    }
}
