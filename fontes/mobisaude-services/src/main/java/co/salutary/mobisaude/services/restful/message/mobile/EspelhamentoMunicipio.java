package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para conter um registro da tabela de Espelhamento de Municipio
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "espelhamentoMunicipio")
public class EspelhamentoMunicipio implements Cloneable {
	/**
	 * Campo codigoOrigem
	 */
	private String codigoOrigem;
	/**
	 * Campo codigoDestino
	 */
	private String codigoDestino;
	/**
	 * Campo ufDestino
	 */
	private String ufDestino;
	/**
	 * Campo municDestino
	 */
	private String municDestino;

	/**
	 * @return the codigoOrigem
	 */
	public String getCodigoOrigem() {
		return codigoOrigem;
	}

	/**
	 * @param codigoOrigem the codigoOrigem to set
	 */
	public void setCodigoOrigem(String codigoOrigem) {
		this.codigoOrigem = codigoOrigem;
	}

	/**
	 * @return the codigoDestino
	 */
	public String getCodigoDestino() {
		return codigoDestino;
	}

	/**
	 * @param codigoDestino the codigoDestino to set
	 */
	public void setCodigoDestino(String codigoDestino) {
		this.codigoDestino = codigoDestino;
	}

	/**
	 * @return the ufDestino
	 */
	public String getUfDestino() {
		return ufDestino;
	}

	/**
	 * @param ufDestino the ufDestino to set
	 */
	public void setUfDestino(String ufDestino) {
		this.ufDestino = ufDestino;
	}

	/**
	 * @return the municDestino
	 */
	public String getMunicDestino() {
		return municDestino;
	}

	/**
	 * @param municDestino the municDestino to set
	 */
	public void setMunicDestino(String municDestino) {
		this.municDestino = municDestino;
	}

	/**
	 * Implementacao de clone
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    EspelhamentoMunicipio cloned = (EspelhamentoMunicipio)super.clone();
	    
	    return cloned;
	}
}
