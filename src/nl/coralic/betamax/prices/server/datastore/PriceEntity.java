package nl.coralic.betamax.prices.server.datastore;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class PriceEntity
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    
    @Persistent
    private String country;
    
    @Persistent
    private String price;
    
    public PriceEntity(String country, String price)
    {
	this.country = country;
	this.price = price;
    }

    public Key getKey()
    {
        return key;
    }

    public String getCountry()
    {
        return country;
    }

    public String getPrice()
    {
        return price;
    }
    
}
