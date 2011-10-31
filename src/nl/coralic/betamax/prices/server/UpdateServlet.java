package nl.coralic.betamax.prices.server;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class UpdateServlet extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
	Updater.updateSmsPrices();
    }
}
