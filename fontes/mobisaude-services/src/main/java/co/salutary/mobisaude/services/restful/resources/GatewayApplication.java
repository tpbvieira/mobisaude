package co.salutary.mobisaude.services.restful.resources;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.jersey.jettison.JettisonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *	Classe Application do Gateway 
 *
 */
@ApplicationPath("/")
public class GatewayApplication extends Application {
	/**
	 * Logger
	 */
	private static final Log logger = LogFactory.getLog(GatewayApplication.class);
	/**
	 * getSingletons
	 */
    @Override
    public Set<Object> getSingletons() {
        final Set<Object> instances = new HashSet<Object>();
        instances.add(new JettisonFeature());
        try { 
			instances.add(new JaxbContextResolver());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
        return instances;
    }
	
    /**
     * getClasses
     */
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        // register root resource
        classes.add(ServicesResource.class);
        new ResourceConfig().register(new JettisonFeature()).
        	register(JaxbContextResolver.class);

        return classes;
    }
}
