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

import co.salutary.mobisaude.model.Factory;
import co.salutary.mobisaude.model.user.User;
import co.salutary.mobisaude.model.user.facade.UserFacade;
import co.salutary.mobisaude.restful.message.request.ConsultaDominiosRequest;
import co.salutary.mobisaude.restful.message.request.GeocodeRequest;
import co.salutary.mobisaude.restful.message.request.GerarTokenRequest;
import co.salutary.mobisaude.restful.message.request.GetESRequest;
import co.salutary.mobisaude.restful.message.request.UserRequest;
import co.salutary.mobisaude.restful.message.response.ConsultaDominiosResponse;
import co.salutary.mobisaude.restful.message.response.GeocodeResponse;
import co.salutary.mobisaude.restful.message.response.GerarTokenResponse;
import co.salutary.mobisaude.restful.message.response.GetESResponse;
import co.salutary.mobisaude.restful.message.response.UserResponse;
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
	
	@Test
	public void test() {
		try {
			ObjectMapper mapper = new ObjectMapper(); // json object mapper
			ServiceBroker broker = new ServiceBroker(); // get Resource

			// test 01 - gerar chave
			String chave = gerarChaveTest();

			// test 02 - gerar token
			String token = gerarTokenTest(mapper, broker, chave);
			System.out.println(token);
			
			// test 03 - Consulta Dominios
			consultaDominioTest(mapper, broker, token);

			// test 04 - Consulta Telas 
			GeocodeResponse geocodeResponse = getGeocodeResponseTest(mapper, broker, token);
			String idMunicipio = geocodeResponse.getCodMunicipioIbge();
//			String uf = geocodeResponse.getUf();

			// test 05 - list estabelecimentos de saúde
//			listESTest(mapper, broker, token);
			
			// test 06 - get estabelecimentos de saúde by cidade
			getESByMunicipioTest(mapper, broker, token, idMunicipio);
			
			// test 07 - get estabelecimentos de saúde by cidade and tipo estabelecimento
			getESByMunicipioTipoEstabelecimentoTest(mapper, broker, token, idMunicipio, "10");// Brasilia (530010) and 10
			
			// test 08 - get estabelecimentos de saúde by cidade and tipo estabelecimento
			String[] tiposES = new String[2];
			tiposES[0] = "10";
			tiposES[1] = "11";
			getESByMunicipioTiposEstabelecimentoTest(mapper, broker, token, idMunicipio, tiposES);// Brasilia (530010) and [10,11]
			
			// test 09 - testing user services
			deleteUserTest(mapper, broker, "tpbvieira@gmail.com");
			signupTest(mapper, broker, token);
			updateUserTest(mapper, broker, token);
			getUserTest(mapper, broker, token);
			
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
			logger.error(ex);	
			fail("Exceção: " + ex.getMessage());
		}
	}

	@After
	public void tearDown() throws Exception {
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
	
	private void listESTest(ObjectMapper mapper, ServiceBroker broker, String token) 
			throws IOException, JsonParseException, JsonMappingException {
		
		GetESRequest getESRequest = mapper.readValue(testProperties.getProperty("getESByMunicipio").replaceAll("<token>", token), GetESRequest.class);
		GetESResponse getESResponse = broker.listES(getESRequest);
		
		if (getESResponse == null || !getESResponse.getErro().startsWith("0|")) {//Success
			logger.error(getESResponse);
			fail("Error = getESByMunicipioTest.");			
		}
		if (getESResponse.getEstabelecimentoSaude().length != 274800) {//ES's Number
			logger.error(getESResponse);
			fail("Quantidade (" + getESResponse.getEstabelecimentoSaude().length + ") inválida de estabelecimentos de saúde para a cidade selecionada.");			
		}
		
	}
	
	private void getESByMunicipioTest(ObjectMapper mapper, ServiceBroker broker, String token, String idMunicipio) 
			throws IOException, JsonParseException, JsonMappingException {
		
		idMunicipio = idMunicipio.substring(0, idMunicipio.length()-1);
		
		GetESRequest getESRequest = mapper.readValue(
				testProperties.getProperty("getESByMunicipio").replaceAll("<token>", token).replaceAll("<municipio>", 
						idMunicipio), GetESRequest.class);
		GetESResponse getESResponse = broker.getESByMunicipio(getESRequest);
		
		if (getESResponse == null || !getESResponse.getErro().startsWith("0|")) {//Success
			logger.error(getESResponse);
			fail("Error = getESByMunicipioTest.");			
		}
		if (getESResponse.getEstabelecimentoSaude().length != 2736) {//Brasilia's Number
			logger.error(getESResponse);
			fail("Quantidade (" + getESResponse.getEstabelecimentoSaude().length + ") inválida de estabelecimentos de saúde para a cidade selecionada.");			
		}
		
	}
	
	private void getESByMunicipioTipoEstabelecimentoTest(ObjectMapper mapper, ServiceBroker broker, String token, String idMunicipio, String idTipoEstabelecimento) 
			throws IOException, JsonParseException, JsonMappingException {
		
		//parsing to make it compatible to ibge data
		idMunicipio = idMunicipio.substring(0, idMunicipio.length()-1);
		
		GetESRequest getESRequest = mapper.readValue(testProperties.
				getProperty("getESByMunicipioTipoEstabelecimento").
					replaceAll("<token>", token).
					replaceAll("<municipio>",idMunicipio).
					replaceAll("<tipoEstabelecimentoSaude>", idTipoEstabelecimento), GetESRequest.class);
		GetESResponse getESResponse = broker.getESByMunicipioTipoEstabelecimento(getESRequest);
		
		if (getESResponse == null || !getESResponse.getErro().startsWith("0|")) {//Success
			logger.error(getESResponse);
			fail("Error = getESByMunicipioTest.");			
		}
		if (getESResponse.getEstabelecimentoSaude().length != 799) {//Brasilia and 10
			logger.error(getESResponse);
			fail("Quantidade (" + getESResponse.getEstabelecimentoSaude().length + ") inválida de estabelecimentos de saúde para a cidade selecionada.");			
		}
		
	}

	private void getESByMunicipioTiposEstabelecimentoTest(ObjectMapper mapper, ServiceBroker broker, String token, String idMunicipio, String[] idTiposEstabelecimento) 
			throws IOException, JsonParseException, JsonMappingException {
		
		//parsing to make it compatible to ibge data
		idMunicipio = idMunicipio.substring(0, idMunicipio.length()-1);
		
		GetESRequest getESRequest = mapper.readValue(testProperties.
				getProperty("getESByMunicipioTipoEstabelecimento").
					replaceAll("<token>", token).
					replaceAll("<municipio>",idMunicipio), GetESRequest.class);		
		getESRequest.setTiposEstabelecimentoSaude(idTiposEstabelecimento);
		GetESResponse getESResponse = broker.getESByMunicipioTipoEstabelecimento(getESRequest);
		
		if (getESResponse == null || !getESResponse.getErro().startsWith("0|")) {//Success
			logger.error(getESResponse);
			fail("Error = getESByMunicipioTiposEstabelecimentoTest.");			
		}
		if (getESResponse.getEstabelecimentoSaude().length != 2547) {//Brasilia and [10,11]
			logger.error(getESResponse);
			fail("getESByMunicipioTiposEstabelecimentoTest: Quantidade (" + getESResponse.getEstabelecimentoSaude().length + ") inválida de estabelecimentos de saúde para a cidade selecionada.");			
		}
		
	}
	
	private void signupTest(ObjectMapper mapper, ServiceBroker broker, String token){
	
		try{
			UserRequest userRequest = new UserRequest();
			userRequest.setToken(token);
			userRequest.setEmail("tpbvieira@gmail.com");
			userRequest.setPassword("123");
			userRequest.setName("Thiago P B Vieira");
			userRequest.setPhone("6183133714");
			
			UserResponse userResponse = broker.signup(userRequest);
			
			if (userResponse == null || !userResponse.getErro().startsWith("0|")) {//Success
				logger.error(userResponse);
				fail("SignupError");			
			}
			
		}catch(Exception e){
			logger.error(e);
			fail(e.getMessage());
		}

	}
	
	private void updateUserTest(ObjectMapper mapper, ServiceBroker broker, String token){
	
		try{
			User user = new User ("tpbvieira@gmail.com","1234","Thiago","06183133714");
			UserRequest userRequest = new UserRequest();
			userRequest.setToken(token);
			userRequest.setEmail(user.getEmail());
			userRequest.setPassword(user.getPassword());
			userRequest.setName(user.getName());
			userRequest.setPhone(user.getPhone());
			
			UserResponse userResponse = broker.updateUser(userRequest);
			
			if (userResponse == null || !userResponse.getErro().startsWith("0|")) {//Success
				logger.error(userResponse);
				fail("UpdateUser");			
			}
			
			User newUser = new User(userResponse.getEmail(),userResponse.getPassword(),userResponse.getName(),userResponse.getPhone());
			if (!newUser.equals(user)) {
				logger.error("Erro ao alterar usuário");
				fail("Erro ao alterar usuário");			
			}
			
		}catch(Exception e){
			logger.error(e);
			fail(e.getMessage());
		}

	}
	
	private void getUserTest(ObjectMapper mapper, ServiceBroker broker, String token){
	
		try{
			User user = new User ("tpbvieira@gmail.com","1234","Thiago","06183133714");
			UserRequest userRequest = new UserRequest();
			userRequest.setToken(token);
			userRequest.setEmail(user.getEmail());
			
			UserResponse userResponse = broker.getUser(userRequest);			
			if (userResponse == null || !userResponse.getErro().startsWith("0|")) {//Success
				logger.error(userResponse);
				fail("SignupError");			
			}			
			
			User newUser = new User(userResponse.getEmail(),userResponse.getPassword(),userResponse.getName(),userResponse.getPhone());
			if (!newUser.equals(user)) {
				logger.error("Erro ao recuperar usuário");
				fail("Erro ao recuperar usuário");			
			}
			
		}catch(Exception e){
			logger.error(e);
			fail(e.getMessage());
		}

	}
	
	private void deleteUserTest(ObjectMapper mapper, ServiceBroker broker, String email){
		
		try{
			UserFacade userFacade = (UserFacade)Factory.getInstance().get("userFacade");
			userFacade.remove(email);		}catch(Exception e){
			logger.error(e);
			fail(e.getMessage());
		}

	}
	
}