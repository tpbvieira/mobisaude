package co.salutary.mobisaude.restful.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.salutary.mobisaude.model.Factory;
import co.salutary.mobisaude.model.tokensessao.TokenSessao;
import co.salutary.mobisaude.model.tokensessao.facade.TokenSessaoFacade;
import co.salutary.mobisaude.util.Constantes;
import co.salutary.mobisaude.util.CryptographyUtil;


/**
 *	Classe abstrata de resources dos servicos acessados pelo mobisaude
 *
 */
public abstract class AbstractServicesResource implements MobiSaudeResource {

	private static final Log logger = LogFactory.getLog(AbstractServicesResource.class);
	private static int[] arrPermutacao = {7,5,3,1,4,6,0,2};
	private SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");	
	protected Properties properties;
	private static Random random = null;
	
	static {
		long semente = new Date().getTime();
		random = new Random(semente);
	}


	public AbstractServicesResource() {
		InputStream stream = null;
		InputStreamReader isr = null;
		try {
			properties = new Properties();
			stream = this.getClass().getResourceAsStream("strings.properties");
			isr = new InputStreamReader(stream, "UTF-8");
			properties.load(isr);
		} catch (IOException ex) {
			logger.error("Erro carregando properties." + ex.getMessage());
		} finally {
			try {
				isr.close();
				stream.close();
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
	}
	
	/**
	 * Metodo que gera um token de sessao
	 * @param chave
	 * @return token gerado
	 */
	protected String gerarToken(String chave) {
		StringBuffer sbChaveGerada = new StringBuffer("");
		String dataStr = dateFormat.format(new Date());
		
		if (chave == null || chave.trim().equals("")) {
			return null;
		}
		
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

		if (!chave.equals(sbChaveGerada.toString())) {
			return null;
		}
		
		long semente = new Date().getTime();
		StringBuffer sbCodigo = new StringBuffer(chave);
		sbCodigo.append(Long.toString(semente));
		sbCodigo.append(random.nextInt());
		
		byte[] byteCodigo = CryptographyUtil.encrypt(sbCodigo.toString());
		String token = new String(org.apache.commons.codec.binary.Base64.encodeBase64(byteCodigo));

		Calendar validade = GregorianCalendar.getInstance();
		validade.setTime(new Date());
		long validadeToken = Long.valueOf(
				properties.getProperty("co.mobisaude.gerartoken.validadeToken", 
						Constantes.VALIDADE_TOKEN_SESSAO_MILIS));
		validade.setTimeInMillis(validade.getTimeInMillis() + validadeToken);		

		TokenSessaoFacade tokenFacade = (TokenSessaoFacade)Factory.getInstance().get("tokenSessaoFacade");
		TokenSessao tokenSessao = new TokenSessao();
		tokenSessao.setToken(token);
		tokenSessao.setValidade(validade.getTime());
		tokenFacade.save(tokenSessao);
		
		return token;
	}
	
	/**
	 * Metodo que valida um token de sessao
	 * @param token
	 * @return valido ou nao
	 */
	protected final boolean validarToken(String token) {
		TokenSessaoFacade tokenFacade = (TokenSessaoFacade)Factory.getInstance().get("tokenSessaoFacade");
		TokenSessao tokenSessao = tokenFacade.getToken(token);
		if (tokenSessao != null) {
			Calendar agora = GregorianCalendar.getInstance();
			if (agora.getTime().compareTo(tokenSessao.getValidade()) <= 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Metodo que gera uma chave para geracao de token de sessao
	 * @return chave gerada
	 */
	protected String gerarChaveTest() {
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
}