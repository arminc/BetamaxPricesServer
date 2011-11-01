package nl.coralic.betamax.prices.server.servlets;

import java.io.IOException;
import javax.servlet.http.*;

import nl.coralic.betamax.prices.server.Prices;

@SuppressWarnings("serial")
public class GetPriceServlet extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
	resp.setContentType("text/plain");
	String country = req.getParameter("country");
	if (country == null)
	{
	    resp.getWriter().println("error");
	    return;
	}
	String price = Prices.getSmsPrices().get(country);
	if(price == null)
	{
	    resp.getWriter().println("error");
	    return; 
	}
	resp.getWriter().println(price);
    }
}
