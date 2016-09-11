package co.salutary.mobisaude.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para conter um tipo de sistema operacional
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "tipoSistemaOperacional")
public class TipoSistemaOperacionalDTO implements Cloneable {
	/** 
	 * Campo id
	 */
	private String id;
	/**
	 * Campo descricao
	 */
	private String descricao;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Implementacao de clone
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    TipoSistemaOperacionalDTO cloned = (TipoSistemaOperacionalDTO)super.clone();
	    
	    return cloned;
	}
}
