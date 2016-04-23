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
import co.salutary.mobisaude.restful.message.mobile.EsDTO;
import co.salutary.mobisaude.restful.message.request.AvaliacaoMediaRequest;
import co.salutary.mobisaude.restful.message.request.AvaliacaoRequest;
import co.salutary.mobisaude.restful.message.request.ConsultaDominiosRequest;
import co.salutary.mobisaude.restful.message.request.ESRequest;
import co.salutary.mobisaude.restful.message.request.GeocodeRequest;
import co.salutary.mobisaude.restful.message.request.GerarTokenRequest;
import co.salutary.mobisaude.restful.message.request.SugestaoRequest;
import co.salutary.mobisaude.restful.message.request.UserRequest;
import co.salutary.mobisaude.restful.message.response.AvaliacaoMediaResponse;
import co.salutary.mobisaude.restful.message.response.AvaliacaoResponse;
import co.salutary.mobisaude.restful.message.response.ConsultaDominiosResponse;
import co.salutary.mobisaude.restful.message.response.ESResponse;
import co.salutary.mobisaude.restful.message.response.GeocodeResponse;
import co.salutary.mobisaude.restful.message.response.GerarTokenResponse;
import co.salutary.mobisaude.restful.message.response.SugestaoResponse;
import co.salutary.mobisaude.restful.message.response.UserResponse;
import co.salutary.mobisaude.restful.resources.ServiceBroker;
import co.salutary.mobisaude.util.CryptographyUtil;
import junit.framework.TestCase;

public class TestAll extends TestCase {

	private static final Log logger = LogFactory.getLog(TestAll.class);
	private Properties testProperties = new Properties();
	private SimpleDateFormat sdfToken = new SimpleDateFormat("ddMMyyyy");
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

			// gerar chave e token
			String chave = gerarChaveTest();
			String token = gerarTokenTest(mapper, broker, chave);
			System.out.println(token);

			// Consulta Dominios
			consultaDominioTest(mapper, broker, token);

			// geocode
			GeocodeResponse geocodeResponse = getGeocodeResponseTest(mapper, broker, token);
			String idMunicipio = geocodeResponse.getCodMunicipioIbge();

			// estabelecimentos de saúde
			// listESTest(mapper, broker, token, 274800);
			getESByIdESTest(mapper, broker, token, "6684181", "AMIGO");// AMIGO (6684181)
			getESByIdMunicipioTest(mapper, broker, token, idMunicipio);
			getESByIdMunicipioIdTipoESTest(mapper, broker, token, idMunicipio, "10");// Brasilia (530010) and "CLINICA/CENTRO DE ESPECIALIDADE" (10)
			String[] tiposES = new String[2];
			tiposES[0] = "10";
			tiposES[1] = "11";
			getESByIdMunicipioIdTiposESTest(mapper, broker, token, idMunicipio, tiposES);// Brasilia (530010) and [10,11]

			// user
			deleteUserTest(mapper, broker, "tpbvieira@gmail.com");
			signupTest(mapper, broker, token);
			updateUserTest(mapper, broker, token);
			signinTest(mapper, broker, token);
			getUserTest(mapper, broker, token);

			// sugestao
			sugerirTest(mapper, broker, token);
			getSugestaoTest(mapper, broker, token);

			// avaliacao
			avaliarTest(mapper, broker, token, "6684181","tpbvieira@gmail.com", "Título", "Avaliacao de teste", "5");
			avaliarTest(mapper, broker, token, "6684181","a@a.com", "Título", "Avaliacao de teste", "2.5");
			avaliarTest(mapper, broker, token, "6684181","b@b.com", "Título", "Avaliacao de teste", "3");
			getAvaliacaoByIdESEmailTest(mapper, broker, token, "6684181","tpbvieira@gmail.com", "Título", "Avaliacao de teste", "5.0");
			getAvaliacaoByIdESEmailTest(mapper, broker, token, "6684181","a@a.com", "Título", "Avaliacao de teste", "2.5");
			getAvaliacaoByIdESEmailTest(mapper, broker, token, "6684181","b@b.com", "Título", "Avaliacao de teste", "3.0");
			listAvaliacaoByIdESTest(mapper, broker, token, "6684181", 3);

			// avaliacao media
			listAvalicaoMediaMesByIdESTest(mapper, broker, token, "6684181", 1);
			getAvaliacaoMediaByIdESTest(mapper, broker, token, "6684181", "3.5");
			getAvaliacaoMediaByIdESDateTest(mapper, broker, token, "6684181", "01/04/2016", "3.5");
			avaliarMediaTest(mapper, broker, token,"6684181","3.8","11/01/2016");			
			avaliarMediaTest(mapper, broker, token,"6684181","2.8","01/02/2016");
			avaliarMediaTest(mapper, broker, token,"6684181","4.8","01/03/2016");
			listAvalicaoMediaMesByIdESTest(mapper, broker, token, "6684181", 4);

		} catch (Exception e){
			logger.error(e);	
			e.printStackTrace();
			fail("Exceção: " + e.getMessage());
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
		if (consultaDominiosResponse.getErro() != null) {
			fail("error message is not null.");
		}
		if(consultaDominiosResponse.getTiposSistemaOperacional().length != 3){
			fail("Número inesperado de tipos de sistema operacional.");
		}
		if(consultaDominiosResponse.getTiposES().length != 36){
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
		String dataStr = sdfToken.format(new Date());

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
		if(gerarTokenResponse.getErro() != null){
			fail("gerarTokenResonse message not successful.");
		}

		String token = gerarTokenResponse.getToken();
		return token;
	}

	private GeocodeResponse getGeocodeResponseTest(ObjectMapper mapper, ServiceBroker broker,
			String token) throws IOException, JsonParseException, JsonMappingException {

		GeocodeRequest geocodeRequest = mapper.readValue(testProperties.getProperty("geocode").replaceAll("<token>", token), GeocodeRequest.class);
		GeocodeResponse geocodeResponse = broker.geocode(geocodeRequest); 

		if (geocodeResponse == null || geocodeResponse.getErro() != null) {
			fail("Retorno do serviço geocode não OK.");
		}
		if (!geocodeResponse.getMunicipio().equals("Brasília")) {
			fail("Cidade inválida." + geocodeResponse.getMunicipio());
		}

		return geocodeResponse;
	}

	@SuppressWarnings("unused")
	private void listESTest(ObjectMapper mapper, ServiceBroker broker, String token, int numES) 
			throws IOException, JsonParseException, JsonMappingException {

		ESRequest getESRequest = mapper.readValue(testProperties.getProperty("getESByIdMunicipio").replaceAll("<token>", token), ESRequest.class);
		ESResponse getESResponse = broker.listES(getESRequest);

		if (getESResponse == null || getESResponse.getErro() != null) {
			logger.error(getESResponse);
			fail("Error = getESByIdMunicipioTest.");			
		}
		if (getESResponse.getEstabelecimentosSaude().length != numES) {
			logger.error(getESResponse);
			fail("Quantidade (" + getESResponse.getEstabelecimentosSaude().length + ") inválida de estabelecimentos de saúde para a cidade selecionada.");			
		}

	}

	private void getESByIdESTest(ObjectMapper mapper, ServiceBroker broker, String token, String idES, String nomeFantasia) 
			throws IOException, JsonParseException, JsonMappingException {

		ESRequest esRequest = new ESRequest();
		esRequest.setToken(token);
		esRequest.setIdES(idES);

		ESResponse esResponse = broker.getESByIdES(esRequest);

		if (esResponse == null || esResponse.getErro() != null) {
			logger.error(esResponse);
			fail("getESByIdESTestError = invalid response");			
		}
		EsDTO[] ess = esResponse.getEstabelecimentosSaude();
		if (ess == null || !ess[0].getNomeFantasia().equals(nomeFantasia)) {//Brasilia's Number
			logger.error(esResponse);
			fail("getESByIdESTestError = invalid nomeFantasia");			
		}

	}

	private void getESByIdMunicipioTest(ObjectMapper mapper, ServiceBroker broker, String token, String idMunicipio) 
			throws IOException, JsonParseException, JsonMappingException {

		idMunicipio = idMunicipio.substring(0, idMunicipio.length()-1);

		ESRequest getESRequest = mapper.readValue(testProperties.getProperty("getESByIdMunicipio").replaceAll("<token>", token).replaceAll("<idMunicipio>",idMunicipio), ESRequest.class);
		ESResponse getESResponse = broker.getESByIdMunicipio(getESRequest);

		if (getESResponse == null || getESResponse.getErro() != null) {
			logger.error(getESResponse);
			fail("Error = getESByIdMunicipioTest.");			
		}
		if (getESResponse.getEstabelecimentosSaude().length != 2736) {//Brasilia's Number
			logger.error(getESResponse);
			fail("Quantidade (" + getESResponse.getEstabelecimentosSaude().length + ") inválida de estabelecimentos de saúde para a cidade selecionada.");			
		}

	}

	private void getESByIdMunicipioIdTipoESTest(ObjectMapper mapper, ServiceBroker broker, String token, String idMunicipio, String idTipoEstabelecimento) 
			throws IOException, JsonParseException, JsonMappingException {

		//parsing to make it compatible to ibge data
		idMunicipio = idMunicipio.substring(0, idMunicipio.length()-1);

		ESRequest getESRequest = mapper.readValue(testProperties.
				getProperty("getESByIdMunicipioIdTipoES").
				replaceAll("<token>", token).
				replaceAll("<idMunicipio>",idMunicipio).
				replaceAll("<idTipoES>", idTipoEstabelecimento), ESRequest.class);
		ESResponse getESResponse = broker.getESByIdMunicipioIdTipoES(getESRequest);

		if (getESResponse == null || getESResponse.getErro() != null) {
			logger.error(getESResponse);
			fail("Error = getESByIdMunicipioTest.");			
		}
		if (getESResponse.getEstabelecimentosSaude().length != 799) {//Brasilia and 10
			logger.error(getESResponse);
			fail("Quantidade (" + getESResponse.getEstabelecimentosSaude().length + ") inválida de estabelecimentos de saúde para a cidade selecionada.");			
		}

	}

	private void getESByIdMunicipioIdTiposESTest(ObjectMapper mapper, ServiceBroker broker, String token, String idMunicipio, String[] idTiposES) 
			throws IOException, JsonParseException, JsonMappingException {

		//parsing to make it compatible to ibge data
		idMunicipio = idMunicipio.substring(0, idMunicipio.length()-1);

		ESRequest getESRequest = mapper.readValue(testProperties.
				getProperty("getESByIdMunicipioIdTipoES").
				replaceAll("<token>", token).
				replaceAll("<idMunicipio>",idMunicipio), ESRequest.class);		
		getESRequest.setIdTiposES(idTiposES);
		ESResponse getESResponse = broker.getESByIdMunicipioIdTipoES(getESRequest);

		if (getESResponse == null || getESResponse.getErro() != null) {
			logger.error(getESResponse);
			fail("Error = getESByIdMunicipioIdTiposESTest.");			
		}
		if (getESResponse.getEstabelecimentosSaude().length != 2547) {//Brasilia and [10,11]
			logger.error(getESResponse);
			fail("getESByIdMunicipioIdTiposESTest: Quantidade (" + getESResponse.getEstabelecimentosSaude().length + ") inválida de estabelecimentos de saúde para a cidade selecionada.");			
		}

	}

	private void signupTest(ObjectMapper mapper, ServiceBroker broker, String token){

		try{
			UserRequest userRequest = new UserRequest();
			userRequest.setToken(token);
			userRequest.setEmail("tpbvieira@gmail.com");
			userRequest.setPassword("qqqqq");
			userRequest.setName("Thiago P B Vieira");
			userRequest.setPhone("6183133714");
			userRequest.setContactable(true);

			UserResponse userResponse = broker.signup(userRequest);

			if (userResponse == null || userResponse.getErro() != null) {
				logger.error(userResponse);
				fail("SignupError");			
			}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail("Exceção: " + e.getMessage());
		}

	}

	private void updateUserTest(ObjectMapper mapper, ServiceBroker broker, String token){

		try{
			User user = new User ("tpbvieira@gmail.com","aaaaa","Thiago","06183133714",false);
			UserRequest userRequest = new UserRequest();
			userRequest.setToken(token);
			userRequest.setEmail(user.getEmail());
			userRequest.setPassword(user.getPassword());
			userRequest.setName(user.getName());
			userRequest.setPhone(user.getPhone());
			userRequest.setContactable(user.isContactable());
			UserResponse userResponse = broker.updateUser(userRequest);

			if (userResponse == null || userResponse.getErro() != null) {
				logger.error(userResponse);
				fail("UpdateUser");			
			}

			User newUser = new User(
					userResponse.getEmail(),
					userResponse.getPassword(),
					userResponse.getName(),
					userResponse.getPhone(),
					userResponse.isContactable());
			if (!newUser.equals(user)) {
				logger.error("Erro ao alterar usuário");
				fail("Erro ao alterar usuário");			
			}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail("Exceção: " + e.getMessage());
		}

	}

	private void getUserTest(ObjectMapper mapper, ServiceBroker broker, String token){

		try{
			User user = new User ("tpbvieira@gmail.com","aaaaa","Thiago","06183133714",false);
			UserRequest userRequest = new UserRequest();
			userRequest.setToken(token);
			userRequest.setEmail(user.getEmail());

			UserResponse userResponse = broker.getUser(userRequest);			
			if (userResponse == null || userResponse.getErro() != null) {
				logger.error(userResponse);
				fail("SignupError");			
			}			

			User newUser = new User(userResponse.getEmail(),
					userResponse.getPassword(),
					userResponse.getName(),
					userResponse.getPhone(),
					userResponse.isContactable());
			if (!newUser.equals(user)) {
				logger.error("Erro ao recuperar usuário");
				fail("Erro ao recuperar usuário");			
			}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail("Exceção: " + e.getMessage());
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

	private void signinTest(ObjectMapper mapper, ServiceBroker broker, String token){

		try{
			User user = new User ("tpbvieira@gmail.com","aaaaa","Thiago","06183133714",false);
			UserRequest userRequest = new UserRequest();
			userRequest.setToken(token);
			userRequest.setEmail(user.getEmail());
			userRequest.setPassword(user.getPassword());

			UserResponse userResponse = broker.signin(userRequest);			
			if (userResponse == null || userResponse.getErro() != null) {
				logger.error(userResponse);
				fail("SigninError");
			}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail("Exceção: " + e.getMessage());
		}

	}

	private void sugerirTest(ObjectMapper mapper, ServiceBroker broker, String token){
		try{
			SugestaoRequest sugestaoRequest = new SugestaoRequest();
			sugestaoRequest.setToken(token);
			sugestaoRequest.setIdES("6684181");
			sugestaoRequest.setEmail("tpbvieira@gmail.com");
			sugestaoRequest.setSugestao("Sugestão de teste");

			SugestaoResponse sugestaoResponse = broker.sugerir(sugestaoRequest);

			if (sugestaoResponse == null || sugestaoResponse.getErro() != null) {
				logger.error(sugestaoResponse);
				fail("sugerirTestError");			
			}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail(e.getMessage());			
		}

	}


	private void getSugestaoTest(ObjectMapper mapper, ServiceBroker broker, String token){

		try{			
			SugestaoRequest sugestaoRequest = new SugestaoRequest();
			sugestaoRequest.setToken(token);
			sugestaoRequest.setIdES("6684181");
			sugestaoRequest.setEmail("tpbvieira@gmail.com");

			SugestaoResponse sugestaoResponse = broker.getSugestao(sugestaoRequest);			
			if (sugestaoResponse == null || sugestaoResponse.getErro() != null) {
				logger.error(sugestaoResponse);
				fail("getSugestaoTestError");			
			}			

			if (!sugestaoResponse.getEmail().equals("tpbvieira@gmail.com")) {
				logger.error("Email errado");
				fail("Email errado");			
			}

			if (!sugestaoResponse.getIdES().equals("6684181")) {
				logger.error("Estabelecimento Errado");
				fail("Estabelecimento Errado");			
			}

			if (!sugestaoResponse.getSugestao().equals("Sugestão de teste")) {
				logger.error("Sugestao erradao");
				fail("Sugestao erradao");
			}



		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail(e.getMessage());			
		}

	}

	private void avaliarTest(ObjectMapper mapper, ServiceBroker broker, String token, String idES, String email, String titulo, String avaliacao, String rating){
		try{
			AvaliacaoRequest avalicaoRequest = new AvaliacaoRequest();
			avalicaoRequest.setToken(token);
			avalicaoRequest.setIdES(idES);
			avalicaoRequest.setEmail(email);
			avalicaoRequest.setTitulo(titulo);
			avalicaoRequest.setAvaliacao(avaliacao);
			avalicaoRequest.setRating(rating);

			AvaliacaoResponse avaliacaoResponse = broker.avaliar(avalicaoRequest);

			if (avaliacaoResponse == null || avaliacaoResponse.getErro() != null) {
				logger.error(avaliacaoResponse);
				fail("avaliarTestError");			
			}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail(e.getMessage());			
		}

	}

	private void getAvaliacaoByIdESEmailTest(ObjectMapper mapper, ServiceBroker broker, String token, String idES, String email, String titulo, String avaliacao, String rating){

		try{			
			AvaliacaoRequest avaliacaoRequest = new AvaliacaoRequest();
			avaliacaoRequest.setToken(token);
			avaliacaoRequest.setIdES(idES);
			avaliacaoRequest.setEmail(email);

			AvaliacaoResponse avaliacaoResponse = broker.getAvaliacaoByIdESEmail(avaliacaoRequest);			
			if (avaliacaoResponse == null || avaliacaoResponse.getErro() != null) {
				logger.error(avaliacaoResponse);
				fail("getAvaliacaoTestError");			
			}			
			if (!avaliacaoResponse.getIdES().equals(idES)) {
				logger.error("Estabelecimento Errado");
				fail("Estabelecimento Errado");			
			}
			if (!avaliacaoResponse.getEmail().equals(email)) {
				logger.error("Email errado");
				fail("Email errado");			
			}
			if (!avaliacaoResponse.getTitulo().equals(titulo)) {
				logger.error("Título Errado");
				fail("Título Errado");
			}
			if (!avaliacaoResponse.getAvaliacao().equals(avaliacao)) {
				logger.error("Avaliacao errada");
				fail("Avaliacao errada");
			}
			if (!avaliacaoResponse.getRating().equals(rating)) {
				logger.error("Rating errado = " + avaliacaoResponse.getRating());
				fail("Rating errado = " + avaliacaoResponse.getRating());
			}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail(e.getMessage());			
		}

	}

	private void listAvaliacaoByIdESTest(ObjectMapper mapper, ServiceBroker broker, String token, String idES, int num){

		try{			
			AvaliacaoRequest avaliacaoRequest = new AvaliacaoRequest();
			avaliacaoRequest.setToken(token);
			avaliacaoRequest.setIdES(idES);

			AvaliacaoResponse avaliacaoResponse = broker.listAvaliacaoByIdES(avaliacaoRequest);			

			if (avaliacaoResponse == null || avaliacaoResponse.getErro() != null) {
				logger.error(avaliacaoResponse);
				fail("listAvaliacaoTestError");			
			}else
				if (avaliacaoResponse.getAvaliacoes() == null) {
					logger.error("listAvaliacaoTest: Avaliacoes == null");
					fail("listAvaliacaoTest: Avaliacoes == null");
				}else
					if (avaliacaoResponse.getAvaliacoes().size() != num) {
						logger.error("listAvaliacaoTest: invalid size " + avaliacaoResponse.getAvaliacoes().size());
						fail("listAvaliacaoTest: invalid size " + avaliacaoResponse.getAvaliacoes().size());
					}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail(e.getMessage());			
		}

	}

	private void avaliarMediaTest(ObjectMapper mapper, ServiceBroker broker, String token, String idES, String rating, String date){
		try{
			AvaliacaoMediaRequest avalicaoMediaRequest = new AvaliacaoMediaRequest();
			avalicaoMediaRequest.setToken(token);
			avalicaoMediaRequest.setIdES(idES);
			avalicaoMediaRequest.setRating(rating);
			avalicaoMediaRequest.setDate(date);

			AvaliacaoMediaResponse avaliacaoMediaMesResponse = broker.avaliarMedia(avalicaoMediaRequest);

			if (avaliacaoMediaMesResponse == null || avaliacaoMediaMesResponse.getErro() != null) {
				logger.error(avaliacaoMediaMesResponse);
				fail("avaliarTestError");			
			}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail(e.getMessage());			
		}

	}

	private void getAvaliacaoMediaByIdESTest(ObjectMapper mapper, ServiceBroker broker, String token, String idES, String targetRating){

		try{			
			AvaliacaoMediaRequest avaliacaoMediaMesRequest = new AvaliacaoMediaRequest();
			avaliacaoMediaMesRequest.setToken(token);
			avaliacaoMediaMesRequest.setIdES(idES);

			AvaliacaoMediaResponse avaliacaoMediaMesResponse = broker.getAvaliacaoMediaByIdES(avaliacaoMediaMesRequest);			
			if (avaliacaoMediaMesResponse == null || avaliacaoMediaMesResponse.getErro() != null) {
				logger.error(avaliacaoMediaMesResponse);
				fail("getAvaliacaoTestError");			
			}			

			if (!avaliacaoMediaMesResponse.getIdES().equals(idES)) {
				logger.error("Estabelecimento Errado");
				fail("Estabelecimento Errado");			
			}

			if (!avaliacaoMediaMesResponse.getRating().equals(targetRating)) {
				logger.error("MeanRating errado = " + avaliacaoMediaMesResponse.getRating());
				fail("MeanRating errado = " + avaliacaoMediaMesResponse.getRating());
			}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail(e.getMessage());			
		}

	}

	private void getAvaliacaoMediaByIdESDateTest(ObjectMapper mapper, ServiceBroker broker, String token, String idES, String date, String rating){

		try{			
			AvaliacaoMediaRequest avaliacaoMediaMesRequest = new AvaliacaoMediaRequest();
			avaliacaoMediaMesRequest.setToken(token);
			avaliacaoMediaMesRequest.setIdES(idES);
			avaliacaoMediaMesRequest.setDate(date);

			AvaliacaoMediaResponse avaliacaoMediaMesResponse = broker.getAvaliacaoMediaByIdESDate(avaliacaoMediaMesRequest);			
			if (avaliacaoMediaMesResponse == null || avaliacaoMediaMesResponse.getErro() != null) {
				logger.error(avaliacaoMediaMesResponse);
				fail("getAvaliacaoTestError");			
			}			

			if (!avaliacaoMediaMesResponse.getIdES().equals(idES)) {
				logger.error("Estabelecimento Errado");
				fail("Estabelecimento Errado");			
			}

			if (!avaliacaoMediaMesResponse.getRating().equals(rating)) {
				logger.error("MeanMonthRating errado = " + avaliacaoMediaMesResponse.getRating());
				fail("MeanMonthRating errado = " + avaliacaoMediaMesResponse.getRating());
			}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail(e.getMessage());			
		}

	}

	private void listAvalicaoMediaMesByIdESTest(ObjectMapper mapper, ServiceBroker broker, String token, String idES, int num){

		try{			
			AvaliacaoMediaRequest avaliacaoMediaMesRequest = new AvaliacaoMediaRequest();
			avaliacaoMediaMesRequest.setToken(token);
			avaliacaoMediaMesRequest.setIdES(idES);

			AvaliacaoMediaResponse avaliacaoMediaMesResponse = broker.listAvalicaoMediaMesByIdES(avaliacaoMediaMesRequest);			

			if (avaliacaoMediaMesResponse == null || avaliacaoMediaMesResponse.getErro() != null) {
				logger.error(avaliacaoMediaMesResponse);
				fail("listAvaliacaoMediaMesTestError");			
			}else
				if (avaliacaoMediaMesResponse.getAvaliacoesMediaMes() == null) {
					logger.error("listAvaliacaoMediaMesTest: Avaliacoes == null");
					fail("listAvaliacaoMediaMesTest: Avaliacoes == null");
				}else
					if (avaliacaoMediaMesResponse.getAvaliacoesMediaMes().size() != num) {
						logger.error("listAvaliacaoMediaMesTest: invalid size " + avaliacaoMediaMesResponse.getAvaliacoesMediaMes().size());
						fail("listAvaliacaoMediaMesTest: invalid size " + avaliacaoMediaMesResponse.getAvaliacoesMediaMes().size());
					}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail(e.getMessage());			
		}

	}

}