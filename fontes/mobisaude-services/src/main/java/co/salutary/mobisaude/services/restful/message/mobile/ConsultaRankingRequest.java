package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do request do servico Consulta Ranking 
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "consultaRankingRequest")
public class ConsultaRankingRequest implements MobileRequest {
	/**
	 * Campo uf
	 */
	private String uf;
	/**
	 * Campo municipio
	 */
	private String municipio;
	/**
	 * Campo operadora
	 */
	private String operadora;
	/**
	 * Campo token
	 */
	private String token;

	/**
	 * Getter de uf
	 * @return
	 */
	public String getUf() {
		return uf;
	}

	/**
	 * Setter de uf
	 * @param uf
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}

	/**
	 * Getter de municipio
	 * @return
	 */
	public String getMunicipio() {
		return municipio;
	}

	/**
	 * Setter de municipio
	 * @param municipio
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	/**
	 * Getter de operadora
	 * @return
	 */
	public String getOperadora() {
		return operadora;
	}

	/**
	 * Setter de operadora
	 * @param operadora
	 */
	public void setOperadora(String operadora) {
		this.operadora = operadora;
	}

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
		if (municipio == null || municipio.trim().equals("")) {
			return false;
		} 
			
		return true;
	}
}
