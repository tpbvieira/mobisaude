package co.salutary.mobisaude.restful.resources; 

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import org.glassfish.jersey.jettison.JettisonConfig;
import org.glassfish.jersey.jettison.JettisonJaxbContext;

import co.salutary.mobisaude.restful.message.mobile.ESMsg;
import co.salutary.mobisaude.restful.message.response.GetESResponse;

@Provider
public class ContextResolverJAXB implements ContextResolver<JAXBContext> {

    private final JAXBContext context;
    private final Set<Class<?>> types;
    private final Class<?>[] cTypes = {ESMsg.class, GetESResponse.class};
 

    public ContextResolverJAXB() throws Exception {
        this.types = new HashSet<Class<?>>(Arrays.asList(cTypes));
        this.context = new JettisonJaxbContext(JettisonConfig.mappedJettison().serializeAsArray("ess","ess").build(), cTypes);
    }
 
    @Override
    public JAXBContext getContext(Class<?> objectType) {
        return (types.contains(objectType)) ? context : null;
    }
}