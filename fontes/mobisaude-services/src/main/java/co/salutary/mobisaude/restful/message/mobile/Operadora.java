package co.salutary.mobisaude.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para conter uma operadora
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "operadora")
public class Operadora implements Cloneable {
	/** 
	 * Campo id
	 */
	private String id;
	/**
	 * Campo codigo
	 */
	private String codigo;
	/**
	 * Campo nome
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
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Implementacao de clone
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    Operadora cloned = (Operadora)super.clone();
	    
	    return cloned;
	}
}
