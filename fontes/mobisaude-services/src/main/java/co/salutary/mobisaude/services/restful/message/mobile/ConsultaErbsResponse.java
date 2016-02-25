package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do response do servico Consulta ERBs
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "consultaErbsResponse")
public class ConsultaErbsResponse implements MobileResponse {
	/**
	 * Campo erbs
	 */
	private Erb[] erbs;
	/**
	 * Campo erro
	 */
	private String erro;

	/**
	 * Getter de erbs
	 * @return
	 */
	public Erb[] getErbs() {
		return erbs;
	}
	/**
	 * Setter de erbs
	 * @param erbs
	 */
	public void setErbs(Erb[] erbs) {
		this.erbs = erbs.clone();
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
