package co.salutary.mobisaude.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do response do servico Geocode
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "geocodeResponse")
public class GeocodeResponse implements MobileResponse {
	/**
	 * Campo codMunicipioIbge
	 */
	private String codMunicipioIbge;
	/**
	 * Campo municipio
	 */
	private String municipio;
	/**
	 * Campo uf
	 */
	private String uf;
	/**
	 * Campo erro
	 */
	private String erro;
	
	/**
	 * Getter de codMunicipioIbge
	 * @return
	 */
	public String getCodMunicipioIbge() {
		return codMunicipioIbge;
	}
	/**
	 * Setter de codMunicipioIbge
	 * @param codMunicipioIbge
	 */
	public void setCodMunicipioIbge(String codMunicipioIbge) {
		this.codMunicipioIbge = codMunicipioIbge;
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
