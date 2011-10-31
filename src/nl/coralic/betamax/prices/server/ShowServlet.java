package nl.coralic.betamax.prices.server;

import java.io.IOException;
import java.util.ArrayList;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import nl.coralic.betamax.prices.server.datastore.PMF;
import nl.coralic.betamax.prices.server.datastore.PriceEntity;
import nl.coralic.betamax.prices.server.datastore.ProviderEntity;

@SuppressWarnings("serial")
public class ShowServlet extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
	PersistenceManager pm = PMF.get().getPersistenceManager();
	 ProviderEntity providerEntity = pm.getObjectById(ProviderEntity.class, "dialnow.com");
	 resp.setContentType("text/plain");
	 
	 resp.getWriter().println(providerEntity.getProvider());
	 
	 ArrayList<PriceEntity> prices = providerEntity.getPrices();
	 for(PriceEntity price : prices)
	 {
	     resp.getWriter().println(price.getCountry());
		     resp.getWriter().println(price.getPrice());
	 }
    }
}
