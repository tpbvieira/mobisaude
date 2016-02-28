package co.salutary.mobisaude.restful.resources;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.jersey.jettison.JettisonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *	Defines the components of a JAX-RS application and supplies additional meta-data. 
 *	A JAX-RS application or implementation supplies a concrete subclass of this abstract class.
 *
 */
@ApplicationPath("/")
public class MobiSaudeApplication extends Application {

	private static final Log logger = LogFactory.getLog(MobiSaudeApplication.class);

	@Override
	public Set<Object> getSingletons() {
		final Set<Object> instances = new HashSet<Object>();
		instances.add(new JettisonFeature());
		try { 
			instances.add(new ContextResolverJAXB());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return instances;
	}


	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		
		// register root resource
		classes.add(ServiceBroker.class);
		new ResourceConfig().register(new JettisonFeature()).register(ContextResolverJAXB.class);

		return classes;
	}
}
