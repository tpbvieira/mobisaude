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
import co.salutary.mobisaude.model.avaliacao.Avaliacao;
import co.salutary.mobisaude.model.avaliacao.facade.AvaliacaoFacade;
import co.salutary.mobisaude.model.sugestao.Sugestao;
import co.salutary.mobisaude.model.sugestao.facade.SugestaoFacade;
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
			String idMunicipio = geocodeResponse.getCodMunicipioIbge();// Brasilia (530010)

			// estabelecimentos de saúde
			// listESTest(mapper, broker, token, 274800);
			getESByIdESTest(mapper, broker, token, "6684181", "AMIGO", "AMIGO");
			getESByIdMunicipioTest(mapper, broker, token, idMunicipio, 2736);
			getESByIdMunicipioIdTipoESTest(mapper, broker, token, idMunicipio, "10", 799);//"CLINICA/CENTRO DE ESPECIALIDADE" (10)
			String[] tiposES = new String[2];
			tiposES[0] = "10";
			tiposES[1] = "11";
			getESByIdMunicipioIdTiposESTest(mapper, broker, token, idMunicipio, tiposES, 2547);// Brasilia (530010) and [10,11]

			// cleaning...
			deleteAvaliacoesTest(mapper, broker);
			deleteSugestoesTest(mapper, broker);
			deleteUserTest(mapper, broker, "tpbvieira@gmail.com");
			deleteUserTest(mapper, broker, "a@a.com");
			deleteUserTest(mapper, broker, "b@b.com");
			deleteUserTest(mapper, broker, "c@c.com");
			deleteUserTest(mapper, broker, "d@d.com");
			deleteUserTest(mapper, broker, "e@e.com");
			deleteUserTest(mapper, broker, "f@f.com");
			deleteUserTest(mapper, broker, "g@g.com");
			deleteUserTest(mapper, broker, "h@h.com");
			deleteUserTest(mapper, broker, "I@I.com");
			deleteUserTest(mapper, broker, "j@j.com");
			deleteUserTest(mapper, broker, "k@k.com");
			deleteUserTest(mapper, broker, "l@l.com");
			deleteUserTest(mapper, broker, "m@m.com");
			deleteUserTest(mapper, broker, "n@n.com");
			deleteUserTest(mapper, broker, "o@o.com");
			deleteUserTest(mapper, broker, "p@p.com");
			deleteUserTest(mapper, broker, "q@q.com");
			deleteUserTest(mapper, broker, "r@r.com");
			deleteUserTest(mapper, broker, "s@s.com");
			deleteUserTest(mapper, broker, "t@t.com");
			deleteUserTest(mapper, broker, "u@u.com");
			deleteUserTest(mapper, broker, "v@v.com");
			deleteUserTest(mapper, broker, "w@w.com");
			deleteUserTest(mapper, broker, "x@x.com");
			deleteUserTest(mapper, broker, "y@y.com");
			deleteUserTest(mapper, broker, "z@z.com");

			// user 			
			signupTest(mapper, broker, token, "tpbvieira@gmail.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "a@a.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "b@b.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "c@c.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "d@d.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "e@e.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "f@f.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "g@g.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "h@h.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "I@I.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "j@j.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "k@k.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "l@l.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "m@m.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "n@n.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "o@o.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "p@p.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "q@q.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "r@r.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "s@s.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "t@t.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "u@u.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "v@v.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "w@w.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "x@x.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "y@y.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			signupTest(mapper, broker, token, "z@z.com", "qqqqq", "Thiago P B Vieira", "6183133714", true);
			updateUserTest(mapper, broker, token, "tpbvieira@gmail.com","aaaaa","Thiago","06183133714",false);
			getUserTest(mapper, broker, token, "tpbvieira@gmail.com","aaaaa","Thiago","06183133714",false);
			signinTest(mapper, broker, token, "tpbvieira@gmail.com","aaaaa");			

			// sugestao
			sugerirTest(mapper, broker, token);
			getSugestaoTest(mapper, broker, token);

			// avaliacao
			avaliarTest(mapper, broker, token, "6684181","tpbvieira@gmail.com", "av1", "avaliarTest(mapper, broker, token, 6684181,tpbvieira@gmail.com, Título, Avaliacao de teste, 5);", "5");
			avaliarTest(mapper, broker, token, "6684181","a@a.com", "Av2", "avaliarTest(mapper, broker, token, 6684181,a@a.com, Título, Avaliacao de teste, 2.5);", "2.5");
			avaliarTest(mapper, broker, token, "6684181","b@b.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,b@b.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","c@c.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,c@c.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","d@d.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,d@d.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","e@e.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,e@e.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","f@f.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,f@f.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","g@g.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,g@g.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","h@h.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,h@h.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","I@I.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,I@I.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","j@j.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,j@j.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","k@k.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,k@k.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","l@l.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,l@l.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","m@m.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,m@m.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","n@n.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,n@n.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","o@o.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,o@o.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","p@p.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,p@p.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","q@q.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,q@q.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","r@r.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,r@r.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","s@s.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,s@s.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","t@t.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,t@t.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","u@u.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,u@u.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","v@v.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,v@v.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","w@w.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,w@w.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","x@x.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,x@x.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","y@y.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,y@y.com, Título, Avaliacao de teste, 3);", "3");
			avaliarTest(mapper, broker, token, "6684181","z@z.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,z@z.com, Título, Avaliacao de teste, 3);", "3");
			getAvaliacaoByIdESEmailTest(mapper, broker, token, "6684181","tpbvieira@gmail.com", "av1", "avaliarTest(mapper, broker, token, 6684181,tpbvieira@gmail.com, Título, Avaliacao de teste, 5);", "5.0");
			getAvaliacaoByIdESEmailTest(mapper, broker, token, "6684181","a@a.com", "Av2", "avaliarTest(mapper, broker, token, 6684181,a@a.com, Título, Avaliacao de teste, 2.5);", "2.5");
			getAvaliacaoByIdESEmailTest(mapper, broker, token, "6684181","b@b.com", "Av3", "avaliarTest(mapper, broker, token, 6684181,b@b.com, Título, Avaliacao de teste, 3);", "3.0");
			listAvaliacaoByIdESTest(mapper, broker, token, "6684181", 27);

			// avaliacao media			
			getAvaliacaoMediaByIdESTest(mapper, broker, token, "6684181", "3.0555556");
			getAvaliacaoMediaByIdESDateTest(mapper, broker, token, "6684181", "01/09/2016", "3.0555556");
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

	private void getESByIdESTest(ObjectMapper mapper, ServiceBroker broker, String token, String idES, String nomeFantasia, String nomeTarget) 
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
		if (ess == null || !ess[0].getNomeFantasia().equals(nomeTarget)) {//Brasilia's Number
			logger.error(esResponse);
			fail("getESByIdESTestError = invalid nomeFantasia");			
		}

	}

	private void getESByIdMunicipioTest(ObjectMapper mapper, ServiceBroker broker, String token, String idMunicipio, int numTarget) 
			throws IOException, JsonParseException, JsonMappingException {

		idMunicipio = idMunicipio.substring(0, idMunicipio.length()-1);

		ESRequest getESRequest = mapper.readValue(testProperties.getProperty("getESByIdMunicipio").replaceAll("<token>", token).replaceAll("<idMunicipio>",idMunicipio), ESRequest.class);
		ESResponse getESResponse = broker.getESByIdMunicipio(getESRequest);

		if (getESResponse == null || getESResponse.getErro() != null) {
			logger.error(getESResponse);
			fail("Error = getESByIdMunicipioTest.");			
		}

		if (getESResponse.getEstabelecimentosSaude().length != numTarget) {
			logger.error(getESResponse);
			fail("Quantidade (" + getESResponse.getEstabelecimentosSaude().length + ") inválida de estabelecimentos de saúde para a cidade selecionada.");			
		}

	}

	private void getESByIdMunicipioIdTipoESTest(ObjectMapper mapper, ServiceBroker broker, String token, String idMunicipio, String idTipoEstabelecimento, int numTarget) 
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

		if (getESResponse.getEstabelecimentosSaude().length != numTarget) {
			logger.error(getESResponse);
			fail("Quantidade (" + getESResponse.getEstabelecimentosSaude().length + ") inválida de estabelecimentos de saúde para a cidade selecionada.");			
		}

	}

	private void getESByIdMunicipioIdTiposESTest(ObjectMapper mapper, ServiceBroker broker, String token, String idMunicipio, String[] idTiposES, int numTarget) 
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

		if (getESResponse.getEstabelecimentosSaude().length != numTarget) {//Brasilia and [10,11]
			logger.error(getESResponse);
			fail("getESByIdMunicipioIdTiposESTest: Quantidade (" + getESResponse.getEstabelecimentosSaude().length + ") inválida de estabelecimentos de saúde para a cidade selecionada.");			
		}

	}

	private void signupTest(ObjectMapper mapper, ServiceBroker broker, String token, String email, String pass, String name, String phone, boolean contactable){

		try{
			UserRequest userRequest = new UserRequest();
			userRequest.setToken(token);
			userRequest.setEmail(email);
			userRequest.setPassword(pass);
			userRequest.setName(name);
			userRequest.setPhone(phone);
			userRequest.setContactable(contactable);

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

	private void updateUserTest(ObjectMapper mapper, ServiceBroker broker, String token, String email, String pass, String name, String phone, boolean contactable){

		try{
			UserRequest userRequest = new UserRequest();
			userRequest.setToken(token);
			userRequest.setEmail(email);
			userRequest.setPassword(pass);
			userRequest.setName(name);
			userRequest.setPhone(phone);
			userRequest.setContactable(contactable);
			UserResponse userResponse = broker.updateUser(userRequest);

			if (userResponse == null || userResponse.getErro() != null) {
				logger.error(userResponse);
				fail("UpdateUser");
			}

			User targetUser = new User (email, pass, name, phone, contactable);

			User newUser = new User(
					userResponse.getEmail(),
					userResponse.getPassword(),
					userResponse.getName(),
					userResponse.getPhone(),
					userResponse.isContactable());

			if (!newUser.equals(targetUser)) {
				logger.error("Erro ao alterar usuário");
				fail("Erro ao alterar usuário");			
			}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail("Exceção: " + e.getMessage());
		}

	}

	private void getUserTest(ObjectMapper mapper, ServiceBroker broker, String token, String email, String pass, String name, String phone, boolean contactable){

		try{
			UserRequest userRequest = new UserRequest();
			userRequest.setToken(token);
			userRequest.setEmail(email);

			UserResponse userResponse = broker.getUser(userRequest);

			if (userResponse == null || userResponse.getErro() != null) {
				logger.error(userResponse);
				fail("SignupError");			
			}			

			User targetUser = new User (email, pass, name, phone, contactable);

			User newUser = new User(userResponse.getEmail(),
					userResponse.getPassword(),
					userResponse.getName(),
					userResponse.getPhone(),
					userResponse.isContactable());

			if (!newUser.equals(targetUser)) {
				logger.error("Erro ao recuperar usuário");
				fail("Erro ao recuperar usuário");			
			}

		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			fail("Exceção: " + e.getMessage());
		}

	}

	private void deleteAvaliacoesTest(ObjectMapper mapper, ServiceBroker broker){

		try{
			AvaliacaoFacade avaliacaoFacade = (AvaliacaoFacade)Factory.getInstance().get("avaliacaoFacade");
			List<Avaliacao> avaliacoes = avaliacaoFacade.list();
			for(Avaliacao avaliacao: avaliacoes){
				avaliacaoFacade.removeByIdESEmail(avaliacao.getIdES(), avaliacao.getEmail());	
			}					
		}catch(NullPointerException e){			
			logger.warn(e);
		}catch(Exception e){
			logger.error(e);
			fail(e.getMessage());
		}

	}
	
	private void deleteSugestoesTest(ObjectMapper mapper, ServiceBroker broker){

		try{
			SugestaoFacade sugestaoFacade = (SugestaoFacade)Factory.getInstance().get("sugestaoFacade");
			List<Sugestao> sugestoes = sugestaoFacade.list();
			for(Sugestao sugestao: sugestoes){
				sugestaoFacade.removeSugestao(sugestao.getIdES(), sugestao.getEmail());	
			}
		}catch(NullPointerException e){			
			logger.warn(e);
		}catch(Exception e){
			logger.error(e);
			fail(e.getMessage());
		}

	}

	private void deleteUserTest(ObjectMapper mapper, ServiceBroker broker, String email){

		try{
			UserFacade userFacade = (UserFacade)Factory.getInstance().get("userFacade");
			userFacade.remove(email);		
		}catch(NullPointerException e){			
			logger.warn(e);
		}catch(Exception e){
			logger.error(e);
			fail(e.getMessage());
		}

	}

	private void signinTest(ObjectMapper mapper, ServiceBroker broker, String token, String email, String pass){

		try{			
			UserRequest userRequest = new UserRequest();
			userRequest.setToken(token);
			userRequest.setEmail(email);
			userRequest.setPassword(pass);

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
				logger.error("Sugestao errada");
				fail("Sugestao errada");
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