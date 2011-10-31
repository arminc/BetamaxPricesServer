package nl.coralic.betamax.prices.server.datastore;

import java.util.ArrayList;

import javax.jdo.PersistenceManager;

public class Database
{
    private static PersistenceManager pm = PMF.get().getPersistenceManager();
    
    public static void savePrices(String provider, ArrayList<PriceEntity> prices)
    {
	ProviderEntity providerEntity = new ProviderEntity(provider, prices);
	try
	{
	    pm.makePersistent(providerEntity);
	}
	finally
	{
	    pm.close();
	}
    }
}
