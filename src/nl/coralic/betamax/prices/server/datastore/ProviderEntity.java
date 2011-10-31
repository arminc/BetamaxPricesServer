package nl.coralic.betamax.prices.server.datastore;

import java.util.ArrayList;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class ProviderEntity
{    
    @PrimaryKey
    private String provider;
    
    @Persistent
    @Element(dependent = "true")
    ArrayList<PriceEntity> prices;
    
    public ProviderEntity(String provider, ArrayList<PriceEntity> prices)
    {
	this.provider = provider;
	this.prices = prices;
    }

    public String getProvider()
    {
        return provider;
    }

    public ArrayList<PriceEntity> getPrices()
    {
        return prices;
    }
}
