package co.salutary.mobisaude.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do request do servico Consulta Patamar
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "consultaPatamarRequest")
public class ConsultaPatamarRequest implements MobileRequest {
	/**
	 * Campo token
	 */
	private String token;

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
	 * Validar o objeto
	 */
	@Override
	public boolean validar() {
		// Validar campos
		return true;
	}
}
