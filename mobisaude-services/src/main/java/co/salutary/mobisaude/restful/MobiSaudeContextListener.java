package co.salutary.mobisaude.restful;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.salutary.mobisaude.model.Factory;

public class MobiSaudeContextListener implements ServletContextListener {

	private static final Log logger = LogFactory.getLog(MobiSaudeContextListener.class);
	
	public void contextInitialized(ServletContextEvent event) {		
		logger.info("Configurando ServletContext para as fachadas que utilizam o Spring.");
		Factory.getInstance().setApplicationContext(event.getServletContext());		
		Factory.getInstance().get("estabelecimentoSaudeFacade");
	}

	public void contextDestroyed(ServletContextEvent event) {

	}
}
