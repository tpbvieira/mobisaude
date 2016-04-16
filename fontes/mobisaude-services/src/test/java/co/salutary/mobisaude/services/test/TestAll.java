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
			getESByIdMunicipioTest(mapper, broker, token, idMunicipio);

			// test 07 - get estabelecimentos de saúde by cidade and tipo estabelecimento
			getESByIdMunicipioIdTipoEstabelecimentoTest(mapper, broker, token, idMunicipio, "10");// Brasilia (530010) and 10

			// test 08 - get estabelecimentos de saúde by cidade and tipo estabelecimento
			String[] tiposES = new String[2];
			tiposES[0] = "10";
			tiposES[1] = "11";
			getESByIdMunicipioIdTiposEstabelecimentoTest(mapper, broker, token, idMunicipio, tiposES);// Brasilia (530010) and [10,11]

			// test 09 - testing user services
			deleteUserTest(mapper, broker, "tpbvieira@gmail.com");
			signupTest(mapper, broker, token);
			updateUserTest(mapper, broker, token);
			signinTest(mapper, broker, token);
			getUserTest(mapper, broker, token);

			// test 10 - testing sugestão
			sugerirTest(mapper, broker, token);
			getSugestaoTest(mapper, broker, token);

			// test 11 - testing sugestão
			avaliarTest(mapper, broker, token, "6684181","tpbvieira@gmail.com", "Título", "Avaliacao de teste", "5");
			avaliarTest(mapper, broker, token, "6684181","a@a.com", "Título", "Avaliacao de teste", "2.5");
			avaliarTest(mapper, broker, token, "6684181","b@b.com", "Título", "Avaliacao de teste", "3");
			getAvaliacaoTest(mapper, broker, token, "6684181","tpbvieira@gmail.com", "Título", "Avaliacao de teste", "5.0");
			getAvaliacaoTest(mapper, broker, token, "6684181","a@a.com", "Título", "Avaliacao de teste", "2.5");
			getAvaliacaoTest(mapper, broker, token, "6684181","b@b.com", "Título", "Avaliacao de teste", "3.0");
			listAvaliacaoByIdESTest(mapper, broker, token, "6684181", 3);
			
			// test 12 - testing sugestão
			listAvaliacaoMediaTest(mapper, broker, token, "6684181", 1);
			getAvaliacaoMediaTest(mapper, broker, token, "6684181", "01/04/2016", "3.5");
			avaliarMediaTest(mapper, broker, token,"6684181","3.8","11/01/2016");			
			avaliarMediaTest(mapper, broker, token,"6684181","2.8","01/02/2016");
			avaliarMediaTest(mapper, broker, token,"6684181","4.8","01/03/2016");
			listAvaliacaoMediaTest(mapper, broker, token, "6684181", 4);

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

	@SuppressWarnings("unused")
	private void listESTest(ObjectMapper mapper, ServiceBroker broker, String token) 
			throws IOException, JsonParseException, JsonMappingException {

		ESRequest getESRequest = mapper.readValue(testProperties.getProperty("getESByIdMunicipio").replaceAll("<token>", token), ESRequest.class);
		ESResponse getESResponse = broker.listES(getESRequest);

		if (getESResponse == null || !getESResponse.getErro().startsWith("0|")) {//Success
			logger.error(getESResponse);
			fail("Error = getESByIdMunicipioTest.");			
		}
		if (getESResponse.getEstabelecimentoSaude().length != 274800) {//ES's Number
			logger.error(getESResponse);
			fail("Quantidade (" + getESResponse.getEstabelecimentoSaude().length + ") inválida de estabelecimentos de saúde para a cidade selecionada.");			
		}

	}

	private void getESByIdMunicipioTest(ObjectMapper mapper, ServiceBroker broker, String token, String idMunicipio) 
			throws IOException, JsonParseException, JsonMappingException {

		idMunicipio = idMunicipio.substring(0, idMunicipio.length()-1);

		ESRequest getESRequest = mapper.readValue(testProperties.getProperty("getESByIdMunicipio").replaceAll("<token>", token).replaceAll("<idMunicipio>",idMunicipio), ESRequest.class);
		ESResponse getESResponse = broker.getESByIdMunicipio(getESRequest);

		if (getESResponse == null || !getESResponse.getErro().startsWith("0|")) {//Success
			logger.error(getESResponse);
			fail("Error = getESByIdMunicipioTest.");			
		}
		if (getESResponse.getEstabelecimentoSaude().length != 2736) {//Brasilia's Number
			logger.error(getESResponse);
			fail("Quantidade (" + getESResponse.getEstabelecimentoSaude().length + ") inválida de estabelecimentos de saúde para a cidade selecionada.");			
		}

	}

	private void getESByIdMunicipioIdTipoEstabelecimentoTest(ObjectMapper mapper, ServiceBroker broker, String token, String idMunicipio, String idTipoEstabelecimento) 
			throws IOException, JsonParseException, JsonMappingException {

		//parsing to make it compatible to ibge data
		idMunicipio = idMunicipio.substring(0, idMunicipio.length()-1);

		ESRequest getESRequest = mapper.readValue(testProperties.
				getProperty("getESByIdMunicipioIdTipoEstabelecimento").
				replaceAll("<token>", token).
				replaceAll("<idMunicipio>",idMunicipio).
				replaceAll("<idTipoEstabelecimentoSaude>", idTipoEstabelecimento), ESRequest.class);
		ESResponse getESResponse = broker.getESByIdMunicipioIdTipoEstabelecimento(getESRequest);

		if (getESResponse == null || !getESResponse.getErro().startsWith("0|")) {//Success
			logger.error(getESResponse);
			fail("Error = getESByIdMunicipioTest.");			
		}
		if (getESResponse.getEstabelecimentoSaude().length != 799) {//Brasilia and 10
			logger.error(getESResponse);
			fail("Quantidade (" + getESResponse.getEstabelecimentoSaude().length + ") inválida de estabelecimentos de saúde para a cidade selecionada.");			
		}

	}

	private void getESByIdMunicipioIdTiposEstabelecimentoTest(ObjectMapper mapper, ServiceBroker broker, String token, String idMunicipio, String[] idTiposEstabelecimento) 
			throws IOException, JsonParseException, JsonMappingException {

		//parsing to make it compatible to ibge data
		idMunicipio = idMunicipio.substring(0, idMunicipio.length()-1);

		ESRequest getESRequest = mapper.readValue(testProperties.
				getProperty("getESByIdMunicipioIdTipoEstabelecimento").
				replaceAll("<token>", token).
				replaceAll("<idMunicipio>",idMunicipio), ESRequest.class);		
		getESRequest.setIdTiposEstabelecimentoSaude(idTiposEstabelecimento);
		ESResponse getESResponse = broker.getESByIdMunicipioIdTipoEstabelecimento(getESRequest);

		if (getESResponse == null || !getESResponse.getErro().startsWith("0|")) {//Success
			logger.error(getESResponse);
			fail("Error = getESByIdMunicipioIdTiposEstabelecimentoTest.");			
		}
		if (getESResponse.getEstabelecimentoSaude().length != 2547) {//Brasilia and [10,11]
			logger.error(getESResponse);
			fail("getESByIdMunicipioIdTiposEstabelecimentoTest: Quantidade (" + getESResponse.getEstabelecimentoSaude().length + ") inválida de estabelecimentos de saúde para a cidade selecionada.");			
		}

	}

	private void signupTest(ObjectMapper mapper, ServiceBroker broker, String token){

		try{
			UserRequest userRequest = new UserRequest();
			userRequest.setToken(token);
			userRequest.setEmail("tpbvieira@gmail.com");
			userRequest.setPassword("111111");
			userRequest.setName("Thiago P B Vieira");
			userRequest.setPhone("6183133714");
			userRequest.setContactable(true);

			UserResponse userResponse = broker.signup(userRequest);

			if (userResponse == null || !userResponse.getErro().startsWith("0|")) {//Success
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
			User user = new User ("tpbvieira@gmail.com","222222","Thiago","06183133714",false);
			UserRequest userRequest = new UserRequest();
			userRequest.setToken(token);
			userRequest.setEmail(user.getEmail());
			userRequest.setPassword(user.getPassword());
			userRequest.setName(user.getName());
			userRequest.setPhone(user.getPhone());
			userRequest.setContactable(user.isContactable());
			UserResponse userResponse = broker.updateUser(userRequest);

			if (userResponse == null || !userResponse.getErro().startsWith("0|")) {//Success
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
			User user = new User ("tpbvieira@gmail.com","222222","Thiago","06183133714",false);
			UserRequest userRequest = new UserRequest();
			userRequest.setToken(token);
			userRequest.setEmail(user.getEmail());

			UserResponse userResponse = broker.getUser(userRequest);			
			if (userResponse == null || !userResponse.getErro().startsWith("0|")) {//Success
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
			User user = new User ("tpbvieira@gmail.com","222222","Thiago","06183133714",false);
			UserRequest userRequest = new UserRequest();
			userRequest.setToken(token);
			userRequest.setEmail(user.getEmail());
			userRequest.setPassword(user.getPassword());

			UserResponse userResponse = broker.signin(userRequest);			
			if (userResponse == null || !userResponse.getErro().startsWith("0|")) {//Success
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
			sugestaoRequest.setIdEstabelecimentoSaude("6684181");
			sugestaoRequest.setEmail("tpbvieira@gmail.com");
			sugestaoRequest.setSugestao("Sugestão de teste");

			SugestaoResponse sugestaoResponse = broker.sugerir(sugestaoRequest);

			if (sugestaoResponse == null || !sugestaoResponse.getErro().startsWith("0|")) {//Success
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
			sugestaoRequest.setIdEstabelecimentoSaude("6684181");
			sugestaoRequest.setEmail("tpbvieira@gmail.com");

			SugestaoResponse sugestaoResponse = broker.getSugestao(sugestaoRequest);			
			if (sugestaoResponse == null || !sugestaoResponse.getErro().startsWith("0|")) {//Success
				logger.error(sugestaoResponse);
				fail("getSugestaoTestError");			
			}			

			if (!sugestaoResponse.getEmail().equals("tpbvieira@gmail.com")) {
				logger.error("Email errado");
				fail("Email errado");			
			}

			if (!sugestaoResponse.getIdEstabelecimentoSaude().equals("6684181")) {
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

	private void avaliarTest(ObjectMapper mapper, ServiceBroker broker, String token, String idEstabelecimentoSaude, String email, String titulo, String avaliacao, String rating){
		try{
			AvaliacaoRequest avalicaoRequest = new AvaliacaoRequest();
			avalicaoRequest.setToken(token);
			avalicaoRequest.setIdEstabelecimentoSaude(idEstabelecimentoSaude);
			avalicaoRequest.setEmail(email);
			avalicaoRequest.setTitulo(titulo);
			avalicaoRequest.setAvaliacao(avaliacao);
			avalicaoRequest.setRating(rating);

			AvaliacaoResponse avaliacaoResponse = broker.avaliar(avalicaoRequest);

			if (avaliacaoResponse == null || !avaliacaoResponse.getErro().startsWith("0|")) {//Success
				logger.error(avaliacaoResponse);
				fail("avaliarTestError");			
			}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail(e.getMessage());			
		}

	}

	private void getAvaliacaoTest(ObjectMapper mapper, ServiceBroker broker, String token, String idEstabelecimentoSaude, String email, String titulo, String avaliacao, String rating){

		try{			
			AvaliacaoRequest avaliacaoRequest = new AvaliacaoRequest();
			avaliacaoRequest.setToken(token);
			avaliacaoRequest.setIdEstabelecimentoSaude(idEstabelecimentoSaude);
			avaliacaoRequest.setEmail(email);

			AvaliacaoResponse avaliacaoResponse = broker.getAvaliacao(avaliacaoRequest);			
			if (avaliacaoResponse == null || !avaliacaoResponse.getErro().startsWith("0|")) {//Success
				logger.error(avaliacaoResponse);
				fail("getAvaliacaoTestError");			
			}			
			if (!avaliacaoResponse.getIdEstabelecimentoSaude().equals(idEstabelecimentoSaude)) {
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
	             
	private void listAvaliacaoByIdESTest(ObjectMapper mapper, ServiceBroker broker, String token, String idEstabelecimentoSaude, int num){

		try{			
			AvaliacaoRequest avaliacaoRequest = new AvaliacaoRequest();
			avaliacaoRequest.setToken(token);
			avaliacaoRequest.setIdEstabelecimentoSaude(idEstabelecimentoSaude);

			AvaliacaoResponse avaliacaoResponse = broker.listAvaliacaoByIdES(avaliacaoRequest);			

			if (avaliacaoResponse == null || !avaliacaoResponse.getErro().startsWith("0|")) {//Success
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

	private void avaliarMediaTest(ObjectMapper mapper, ServiceBroker broker, String token, String idEstabelecimentoSaude, String rating, String date){
		try{
			AvaliacaoMediaRequest avalicaoMediaRequest = new AvaliacaoMediaRequest();
			avalicaoMediaRequest.setToken(token);
			avalicaoMediaRequest.setIdEstabelecimentoSaude(idEstabelecimentoSaude);
			avalicaoMediaRequest.setRating(rating);
			avalicaoMediaRequest.setDate(date);

			AvaliacaoMediaResponse avaliacaoMediaResponse = broker.avaliarMedia(avalicaoMediaRequest);

			if (avaliacaoMediaResponse == null || !avaliacaoMediaResponse.getErro().startsWith("0|")) {//Success
				logger.error(avaliacaoMediaResponse);
				fail("avaliarTestError");			
			}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail(e.getMessage());			
		}

	}

	private void getAvaliacaoMediaTest(ObjectMapper mapper, ServiceBroker broker, String token, String idEstabelecimentoSaude, String date, String rating){

		try{			
			AvaliacaoMediaRequest avaliacaoMediaRequest = new AvaliacaoMediaRequest();
			avaliacaoMediaRequest.setToken(token);
			avaliacaoMediaRequest.setIdEstabelecimentoSaude(idEstabelecimentoSaude);
			avaliacaoMediaRequest.setDate(date);

			AvaliacaoMediaResponse avaliacaoMediaResponse = broker.getAvaliacaoMedia(avaliacaoMediaRequest);			
			if (avaliacaoMediaResponse == null || !avaliacaoMediaResponse.getErro().startsWith("0|")) {//Success
				logger.error(avaliacaoMediaResponse);
				fail("getAvaliacaoTestError");			
			}			

			if (!avaliacaoMediaResponse.getIdEstabelecimentoSaude().equals(idEstabelecimentoSaude)) {
				logger.error("Estabelecimento Errado");
				fail("Estabelecimento Errado");			
			}

			if (!avaliacaoMediaResponse.getRating().equals(rating)) {
				logger.error("MeanRating errado = " + avaliacaoMediaResponse.getRating());
				fail("MeanRating errado = " + avaliacaoMediaResponse.getRating());
			}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail(e.getMessage());			
		}

	}

	private void listAvaliacaoMediaTest(ObjectMapper mapper, ServiceBroker broker, String token, String idEstabelecimentoSaude, int num){

		try{			
			AvaliacaoMediaRequest avaliacaoMediaRequest = new AvaliacaoMediaRequest();
			avaliacaoMediaRequest.setToken(token);
			avaliacaoMediaRequest.setIdEstabelecimentoSaude(idEstabelecimentoSaude);

			AvaliacaoMediaResponse avaliacaoMediaResponse = broker.listAvaliacaoMedia(avaliacaoMediaRequest);			

			if (avaliacaoMediaResponse == null || !avaliacaoMediaResponse.getErro().startsWith("0|")) {//Success
				logger.error(avaliacaoMediaResponse);
				fail("listAvaliacaoMediaTestError");			
			}else
				if (avaliacaoMediaResponse.getAvaliacoesMedia() == null) {
					logger.error("listAvaliacaoMediaTest: Avaliacoes == null");
					fail("listAvaliacaoMediaTest: Avaliacoes == null");
				}else
					if (avaliacaoMediaResponse.getAvaliacoesMedia().size() != num) {
						logger.error("listAvaliacaoMediaTest: invalid size " + avaliacaoMediaResponse.getAvaliacoesMedia().size());
						fail("listAvaliacaoMediaTest: invalid size " + avaliacaoMediaResponse.getAvaliacoesMedia().size());
					}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail(e.getMessage());			
		}

	}

}