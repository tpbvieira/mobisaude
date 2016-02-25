package co.salutary.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *	Context Listener 
 *	Realizar inicializacoes de recursos da aplicacao ao inicializar o contexto.  
 *
 */
public class GatewayContextListener implements ServletContextListener {

	/**
	 * Logger
	 */
	private static final Log logger = LogFactory.getLog(
			GatewayContextListener.class);
	
	/**
	 * Metodo chamado na inicializacao do contexto
	 */
	public void contextInitialized(ServletContextEvent event) {
		
		logger.info("Configurando ServletContext para as fachadas que utilizam o Spring.");
		Factory.getInstance().setApplicationContext(event.getServletContext());
		
		Factory.getInstance().get("relatorioErbsFacade");
	}

	/**
	 * Metodo chamado na destruicao do contexto
	 */
	public void contextDestroyed(ServletContextEvent event) {

	}
}
