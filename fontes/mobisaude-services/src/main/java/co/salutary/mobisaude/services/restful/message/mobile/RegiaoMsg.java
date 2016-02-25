package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para conter mensagens sobre regiao
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "regiao")
public class RegiaoMsg implements Cloneable {
	/** 
	 * Campo id
	 */
	private String id;
	/**
	 * Campo descricao
	 */
	private String nome;

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
	public String getNome() {
		return nome;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Implementacao de clone
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    RegiaoMsg cloned = (RegiaoMsg)super.clone();
	    
	    return cloned;
	}
}