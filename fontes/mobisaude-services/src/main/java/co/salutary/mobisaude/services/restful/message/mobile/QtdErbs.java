package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para conter uma quantidade de antenas (ERBs) para uma operadora e tecnologia.
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "qtdErbs")
public class QtdErbs implements Cloneable {
	/** 
	 * Campo prestadora
	 */
	private String prestadora;
	/**
	 * Campo tecnologia
	 */
	private String tecnologia;
	/**
	 * Campo qtdErbs
	 */
	private String qtdErbs;
	/**
	 * Getter de prestadora
	 * @return
	 */
	public String getPrestadora() {
		return prestadora;
	}
	/**
	 * Setter de prestadora
	 * @param prestadora
	 */
	public void setPrestadora(String prestadora) {
		this.prestadora = prestadora;
	}
	/**
	 * Getter de tecnologia
	 * @return
	 */
	public String getTecnologia() {
		return tecnologia;
	}
	/**
	 * Setter de tecnologia
	 * @param tecnologia
	 */
	public void setTecnologia(String tecnologia) {
		this.tecnologia = tecnologia;
	}
	/**
	 * Getter de qtdErbs
	 * @return
	 */
	public String getQtdErbs() {
		return qtdErbs;
	}
	/**
	 * Setter de qtdErbs
	 * @param qtdErbs
	 */
	public void setQtdErbs(String qtdErbs) {
		this.qtdErbs = qtdErbs;
	}
	/**
	 * Implementacao de clone
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    QtdErbs cloned = (QtdErbs)super.clone();
	    
	    return cloned;
	}
}
