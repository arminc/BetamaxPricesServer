package nl.coralic.betamax.prices.server.servlets;

import java.io.IOException;
import javax.servlet.http.*;

import nl.coralic.betamax.prices.server.Updater;

@SuppressWarnings("serial")
public class UpdateServlet extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
	Updater.updatePrices();
    }
}
