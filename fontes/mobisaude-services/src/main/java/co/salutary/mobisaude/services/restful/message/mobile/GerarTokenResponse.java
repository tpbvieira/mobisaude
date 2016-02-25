package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do response do servico Gerar Token
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "gerarTokenResponse")
public class GerarTokenResponse implements MobileResponse {
	/**
	 * Campo token
	 */
	private String token;
	/**
	 * Campo erro
	 */
	private String erro;
	
	/**
	 * Getter de token
	 * @return
	 */
	public String getToken() {
		return token;
	}
	/**
	 * Setter de token
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * Getter de erro
	 * @return
	 */
	public String getErro() {
		return erro;
	}
	/**
	 * Setter de erro
	 * @param erro
	 */
	public void setErro(String erro) {
		this.erro = erro;
	}

}
