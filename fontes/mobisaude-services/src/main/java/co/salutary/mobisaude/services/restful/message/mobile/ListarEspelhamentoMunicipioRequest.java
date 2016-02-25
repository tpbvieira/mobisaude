package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do request do servico listar espelhamento de municipios 
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "listarEspelhamentoMunicipioRequest")
public class ListarEspelhamentoMunicipioRequest implements MobileRequest {
	/**
	 * Campo token
	 */
	private String token;
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
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
