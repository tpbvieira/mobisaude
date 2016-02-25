package co.salutary.mobisaude.services.test;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.salutary.mobisaude.services.restful.message.mobile.ConsultaDominiosRequest;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaDominiosResponse;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaErbsRequest;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaErbsResponse;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaTelasRequest;
import co.salutary.mobisaude.services.restful.message.mobile.ConsultaTelasResponse;
import co.salutary.mobisaude.services.restful.message.mobile.GeocodeRequest;
import co.salutary.mobisaude.services.restful.message.mobile.GeocodeResponse;
import co.salutary.mobisaude.services.restful.message.mobile.GerarTokenRequest;
import co.salutary.mobisaude.services.restful.message.mobile.GerarTokenResponse;
import co.salutary.mobisaude.services.restful.resources.ServicesResource;
import co.salutary.util.CryptographyUtil;
import junit.framework.TestCase;

public class TestAll extends TestCase {
	/**
	 * Logger
	 */
	private static final Log logger = LogFactory.getLog(TestAll.class);
	/**
	 * Arquivo de properties
	 */
	private Properties properties = new Properties();
	/**
	 * Formatacao de data
	 */
	private SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");	
	/**
	 * Array para permutacao na geracao da chave
	 */
	private static int[] arrPermutacao = {7,5,3,1,4,6,0,2};
		
	@Before
	public void setUp() throws Exception {
		properties.load(this.getClass().getResourceAsStream("/testes-junit.properties"));
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test() {
		try {
			// json object mapper
			ObjectMapper mapper = new ObjectMapper();

			// gera chave
			String chave = gerarChave();

			// get Resource
			ServicesResource resource = new ServicesResource();

			// test 01 - gerar token
			String token = gerarTokenTest(mapper, chave, resource);
			System.out.println("Token=" + token);
			
			// test 02 - Consulta Dominios
			consultaDominioTest(mapper, resource, token);

			// test 03 - Consulta Telas
			consultaTelasTest(mapper, resource, token);
			
			// test 04 - get geocode and set global variables 
			GeocodeResponse geocodeResponse = getGeocodeResponseTest(mapper, resource, token);
			String codMunicipioIbge = geocodeResponse.getCodMunicipioIbge();
			String uf = geocodeResponse.getUf();
			
			// test 05 - Consulta ERBs
			consultarERBTest(mapper, resource, token, codMunicipioIbge, uf);
//
//			// 14 - Texto Ajuda
//			TextoAjudaRequest textoAjudaRequest = mapper.readValue(
//					properties.getProperty("textoAjuda"),
//					TextoAjudaRequest.class);
//			TextoAjudaResponse textoAjudaResponse = resource.textoAjuda(textoAjudaRequest);
//			if (textoAjudaResponse == null || !textoAjudaResponse.getErro().contains("0|")) {
//				fail("Retorno do serviço Texto Ajuda não OK.");
//			}
//
//			// 15 - Texto Ajuda Servico Movel
//			TextoAjudaServicoMovelRequest textoAjudaServicoMovelRequest = mapper.readValue(
//					properties.getProperty("textoAjudaServicoMovel"),
//					TextoAjudaServicoMovelRequest.class);
//			TextoAjudaServicoMovelResponse textoAjudaServicoMovelResponse = resource.textoAjudaServicoMovel(textoAjudaServicoMovelRequest);
//			if (textoAjudaServicoMovelResponse == null || !textoAjudaServicoMovelResponse.getErro().contains("0|")) {
//				fail("Retorno do serviço Texto Ajuda Servico Movel não OK.");
//			}
//
//			// 16 - Texto Aviso Relatar Problema
//			TextoAvisoRelatarProblemaRequest textoAvisoRelatarProblemaRequest = mapper.readValue(
//					properties.getProperty("textoAvisoRelatarProblema"),
//					TextoAvisoRelatarProblemaRequest.class);
//			TextoAvisoRelatarProblemaResponse textoAvisoRelatarProblemaResponse = resource.textoAvisoRelatarProblema(textoAvisoRelatarProblemaRequest);
//			if (textoAvisoRelatarProblemaResponse == null || !textoAvisoRelatarProblemaResponse.getErro().contains("0|")) {
//				fail("Retorno do serviço Texto Aviso Relatar Problema não OK.");
//			}
			
		} catch (Exception ex){
			logger.error("Erro gerando hash.",ex);	
			fail("Exceção: " + ex.getMessage());
		}
	}

	private void consultaTelasTest(ObjectMapper mapper, ServicesResource resource, String token)
			throws IOException, JsonParseException, JsonMappingException {
		ConsultaTelasRequest consultaTelasRequest = mapper.readValue(
				properties.getProperty("consultaTelas").replaceAll("<token>", token),
				ConsultaTelasRequest.class);
		ConsultaTelasResponse consultaTelasResponse = resource.consultaTelas(consultaTelasRequest);
		if (consultaTelasResponse == null || !consultaTelasResponse.getErro().contains("0|")) {
			fail("Retorno do serviço Consulta Telas não OK.");
		}
	}

	private void consultaDominioTest(ObjectMapper mapper, ServicesResource resource, String token)
			throws IOException, JsonParseException, JsonMappingException {
		ConsultaDominiosRequest consultaDominiosRequest = mapper.readValue(
				properties.getProperty("consultaDominios").replaceAll("<token>", token),
				ConsultaDominiosRequest.class);
		ConsultaDominiosResponse consultaDominiosResponse = resource.consultaDominios(consultaDominiosRequest);
		if (consultaDominiosResponse == null || !consultaDominiosResponse.getErro().contains("0|")) {
			fail("Retorno inesperado ao consultar tabelas de domínio.");
		}
		if(consultaDominiosResponse.getOperadoras().length != 7){
			fail("Número inesperado de operadoras.");
		}
		if(consultaDominiosResponse.getTiposSistemaOperacional().length != 3){
			fail("Número inesperado de tipos de sistema operacional.");
		}
		if(consultaDominiosResponse.getTiposEstabelecimentoSaude().length != 36){
			fail("Número inesperado de tipos de estabelecimentos de saúde.");
		}
		if(consultaDominiosResponse.getTiposGestao().length != 3){
			fail("Número inesperado de tipos de gestão.");
		}
		if(consultaDominiosResponse.getRegiao().length != 5){
			fail("Número inesperado de regiões.");
		}
	}
	
	/**
	 * Metodo que gera uma chave para geracao de token de sessao
	 * @return chave gerada
	 */
	private String gerarChave() {
		StringBuffer sbChaveGerada = new StringBuffer("");
		String dataStr = dateFormat.format(new Date());

		// Permutar os digitos da data atual DDMMAAAA
		for (int i=0;i<dataStr.length();i++) {
			sbChaveGerada.append(dataStr.charAt(arrPermutacao[i]));
		}

		// Fatorar o numero resultado da permutacao
		Integer intChaveGerada = Integer.parseInt(sbChaveGerada.toString());
		List<Integer> fatoresChaveGerada = CryptographyUtil.primeFactors(intChaveGerada);

		// Concatenar os fatores em ordem crescente numa string
		sbChaveGerada.setLength(0);
		for (Integer fator:fatoresChaveGerada) {
			sbChaveGerada.append(fator.toString());
		}

		return sbChaveGerada.toString();
	}
	
	private void consultarERBTest(ObjectMapper mapper, ServicesResource resource, String token,
			String codMunicipioIbge, String uf) throws IOException, JsonParseException, JsonMappingException {
		ConsultaErbsRequest consultaErbsRequest = mapper.readValue(
				properties.getProperty("consultaErbsPorMunicipio").replaceAll("<token>", token).
				replaceAll("<uf>", uf).
				replaceAll("<municipio>", codMunicipioIbge).
				replaceAll("<operadora>", "CLARO"),
				ConsultaErbsRequest.class);
		ConsultaErbsResponse consultaErbsResponse = resource.consultaErbsPorMunicipio(consultaErbsRequest);
		if (consultaErbsResponse == null || !consultaErbsResponse.getErro().contains("0|")) {
			fail("Retorno do serviço Consulta ERBs por Município não OK.");
		}
	}

	private GeocodeResponse getGeocodeResponseTest(ObjectMapper mapper, ServicesResource resource,
			String token) throws IOException, JsonParseException, JsonMappingException {
		GeocodeRequest geocodeRequest = mapper.readValue(
				properties.getProperty("geocode").replaceAll("<token>", token),
				GeocodeRequest.class);

		GeocodeResponse geocodeResponse = resource.geocode(geocodeRequest); 


		if (geocodeResponse == null || !geocodeResponse.getErro().contains("0|")) {
			fail("Retorno do serviço geocode não OK.");
		}
		return geocodeResponse;
	}

	private String gerarTokenTest(ObjectMapper mapper, String chave, ServicesResource resource)
			throws IOException, JsonParseException, JsonMappingException {
		// 1 - Gerar token
		GerarTokenRequest gerarTokenRequest = mapper.readValue(
				properties.getProperty("gerarToken").replaceAll("<chave>", chave),
				GerarTokenRequest.class);

		GerarTokenResponse gerarTokenResponse = resource.gerarToken(gerarTokenRequest); 

		if (gerarTokenResponse == null || !gerarTokenResponse.getErro().contains("0|")) {
			fail("Retorno do serviço gerar token não OK.");
		}

		String token = gerarTokenResponse.getToken();
		return token;
	}
}
