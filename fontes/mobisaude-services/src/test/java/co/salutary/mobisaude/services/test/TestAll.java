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

import co.salutary.mobisaude.restful.message.mobile.ConsultaDominiosRequest;
import co.salutary.mobisaude.restful.message.mobile.ConsultaDominiosResponse;
import co.salutary.mobisaude.restful.message.mobile.ConsultaTelasRequest;
import co.salutary.mobisaude.restful.message.mobile.ConsultaTelasResponse;
import co.salutary.mobisaude.restful.message.mobile.GeocodeRequest;
import co.salutary.mobisaude.restful.message.mobile.GeocodeResponse;
import co.salutary.mobisaude.restful.message.mobile.GerarTokenRequest;
import co.salutary.mobisaude.restful.message.mobile.GerarTokenResponse;
import co.salutary.mobisaude.restful.message.mobile.GetESRequest;
import co.salutary.mobisaude.restful.message.mobile.GetESResponse;
import co.salutary.mobisaude.restful.resources.ServiceBroker;
import co.salutary.mobisaude.util.CryptographyUtil;
import junit.framework.TestCase;

public class TestAll extends TestCase {
	
	private static final Log logger = LogFactory.getLog(TestAll.class);
	private Properties testProperties = new Properties();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");	
	private static int[] arrPermutacao = {7,5,3,1,4,6,0,2};
		
	@Before
	public void setUp() throws Exception {
		testProperties.load(this.getClass().getResourceAsStream("/test.properties"));
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test() {
		try {
			// json object mapper
			ObjectMapper mapper = new ObjectMapper();
			// get Resource
			ServiceBroker broker = new ServiceBroker();

			// test 01 - gerar chave
			String chave = gerarChaveTest();

			// test 02 - gerar token
			String token = gerarTokenTest(mapper, broker, chave);
			
			// test 03 - Consulta Dominios
			consultaDominioTest(mapper, broker, token);

			// test 04 - Consulta Telas
			consultaTelasTest(mapper, broker, token);
			
			// test 05 - get geocode and set global variables 
			GeocodeResponse geocodeResponse = getGeocodeResponseTest(mapper, broker, token);
			String codMunicipio = geocodeResponse.getCodMunicipioIbge();
			String uf = geocodeResponse.getUf();
			
			// test 06 - get estabelecimentos de saúde
			getESByMunicipioTest(mapper, broker, token, codMunicipio);
//
//			// 14 - Texto Ajuda
//			TextoAjudaRequest textoAjudaRequest = mapper.readValue(
//					testProperties.getProperty("textoAjuda"),
//					TextoAjudaRequest.class);
//			TextoAjudaResponse textoAjudaResponse = broker.textoAjuda(textoAjudaRequest);
//			if (textoAjudaResponse == null || !textoAjudaResponse.getErro().startsWith("0|")) {
//				fail("Retorno do serviço Texto Ajuda não OK.");
//			}
//
//			// 15 - Texto Ajuda Servico Movel
//			TextoAjudaServicoMovelRequest textoAjudaServicoMovelRequest = mapper.readValue(
//					testProperties.getProperty("textoAjudaServicoMovel"),
//					TextoAjudaServicoMovelRequest.class);
//			TextoAjudaServicoMovelResponse textoAjudaServicoMovelResponse = broker.textoAjudaServicoMovel(textoAjudaServicoMovelRequest);
//			if (textoAjudaServicoMovelResponse == null || !textoAjudaServicoMovelResponse.getErro().startsWith("0|")) {
//				fail("Retorno do serviço Texto Ajuda Servico Movel não OK.");
//			}
//
//			// 16 - Texto Aviso Relatar Problema
//			TextoAvisoRelatarProblemaRequest textoAvisoRelatarProblemaRequest = mapper.readValue(
//					testProperties.getProperty("textoAvisoRelatarProblema"),
//					TextoAvisoRelatarProblemaRequest.class);
//			TextoAvisoRelatarProblemaResponse textoAvisoRelatarProblemaResponse = broker.textoAvisoRelatarProblema(textoAvisoRelatarProblemaRequest);
//			if (textoAvisoRelatarProblemaResponse == null || !textoAvisoRelatarProblemaResponse.getErro().startsWith("0|")) {
//				fail("Retorno do serviço Texto Aviso Relatar Problema não OK.");
//			}
			
		} catch (Exception ex){
			logger.error("Erro gerando hash.",ex);	
			fail("Exceção: " + ex.getMessage());
		}
	}

	private void consultaTelasTest(ObjectMapper mapper, ServiceBroker broker, String token)
			throws IOException, JsonParseException, JsonMappingException {
		ConsultaTelasRequest consultaTelasRequest = mapper.readValue(
				testProperties.getProperty("consultaTelas").replaceAll("<token>", token),
				ConsultaTelasRequest.class);
		ConsultaTelasResponse consultaTelasResponse = broker.consultaTelas(consultaTelasRequest);
		if (consultaTelasResponse == null || !consultaTelasResponse.getErro().startsWith("0|")) {
			fail("Retorno do serviço Consulta Telas não OK.");
		}
	}

	private void consultaDominioTest(ObjectMapper mapper, ServiceBroker broker, String token)
			throws IOException, JsonParseException, JsonMappingException {
		
		ConsultaDominiosRequest consultaDominiosRequest = mapper.readValue(testProperties.getProperty("consultaDominios").replaceAll("<token>", token), ConsultaDominiosRequest.class);
		ConsultaDominiosResponse consultaDominiosResponse = broker.consultaDominios(consultaDominiosRequest);
		
		if (consultaDominiosResponse == null) {
			fail("Retorno inesperado ao consultar tabelas de domínio.");
		}
		if (consultaDominiosResponse.getErro() == null) {
			fail("error message is null.");
		}
		if (!consultaDominiosResponse.getErro().startsWith("0|")) {
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

	private String gerarChaveTest() {
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
	
	private String gerarTokenTest(ObjectMapper mapper, ServiceBroker broker, String chave)
			throws IOException, JsonParseException, JsonMappingException {

		GerarTokenRequest gerarTokenRequest = mapper.readValue(
				testProperties.getProperty("gerarToken").replaceAll("<chave>", chave),
				GerarTokenRequest.class);

		GerarTokenResponse gerarTokenResponse = broker.gerarToken(gerarTokenRequest); 

		if (gerarTokenResponse == null) {
			fail("gerarTokenResonse is null.");
		}
		if(gerarTokenResponse.getErro() == null){
			fail("gerarTokenResonse error message is null.");
		}
		if(!gerarTokenResponse.getErro().contains("0|")){
			fail("gerarTokenResonse message not successful.");
		}

		String token = gerarTokenResponse.getToken();
		return token;
	}
	
	private GeocodeResponse getGeocodeResponseTest(ObjectMapper mapper, ServiceBroker broker,
			String token) throws IOException, JsonParseException, JsonMappingException {
		
		GeocodeRequest geocodeRequest = mapper.readValue(testProperties.getProperty("geocode").replaceAll("<token>", token), GeocodeRequest.class);
		GeocodeResponse geocodeResponse = broker.geocode(geocodeRequest); 

		if (geocodeResponse == null || !geocodeResponse.getErro().startsWith("0|")) {
			fail("Retorno do serviço geocode não OK.");
		}
		if (!geocodeResponse.getMunicipio().equals("Brasília")) {
			fail("Cidade inválida." + geocodeResponse.getMunicipio());
		}
		
		return geocodeResponse;
	}
	
	private void getESByMunicipioTest(ObjectMapper mapper, ServiceBroker broker, String token, String codMunicipio) 
			throws IOException, JsonParseException, JsonMappingException {
		
		GetESRequest getESRequest = mapper.readValue(
				testProperties.getProperty("getESByMunicipio").replaceAll("<token>", token).				
				replaceAll("<municipio>", codMunicipio),
				GetESRequest.class);
		
		GetESResponse getESResponse = broker.getESByMunicipio(getESRequest);
		
		if (getESResponse == null || !getESResponse.getErro().startsWith("0|")) {
			fail("Error = getESByMunicipioTest.");
			logger.error(getESResponse);
		}
	}
}
