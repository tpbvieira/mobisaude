package co.salutary.mobisaude.services.restful.resources; 

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import org.glassfish.jersey.jettison.JettisonConfig;
import org.glassfish.jersey.jettison.JettisonJaxbContext;

import co.salutary.mobisaude.services.restful.message.mobile.ConsultaErbsResponse;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaRankingResponse;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaRankingV1Response;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaRankingV2Response;
import co.salutary.mobisaude.services.restful.message.mobile.Erb;
import co.salutary.mobisaude.services.restful.message.mobile.Ranking;
import co.salutary.mobisaude.services.restful.message.mobile.RankingV1;
import co.salutary.mobisaude.services.restful.message.mobile.RankingV2;

/**
 * Provider para o JAXB
 */
@Provider
public class JaxbContextResolver implements ContextResolver<JAXBContext> {
	/**
	 * Context do JAXB
	 */
    private final JAXBContext context;
    /**
     * Classes a serem serializadas
     */
    private final Set<Class<?>> types;
    /**
     * Classes a serem serializadas
     */
    private final Class<?>[] cTypes = {Erb.class, ConsultaErbsResponse.class, Ranking.class, ConsultaRankingResponse.class, RankingV1.class, ConsultaRankingV1Response.class, RankingV2.class, ConsultaRankingV2Response.class};
 
    /**
     * Construtor
     * @throws Exception
     */
    public JaxbContextResolver() throws Exception {
        this.types = new HashSet<Class<?>>(Arrays.asList(cTypes));
        this.context = new JettisonJaxbContext(JettisonConfig.mappedJettison().serializeAsArray("erbs","rankings", "rankings").build(), cTypes);
    }
 
    /**
     * getContext
     */
    @Override
    public JAXBContext getContext(Class<?> objectType) {
        return (types.contains(objectType)) ? context : null;
    }
}