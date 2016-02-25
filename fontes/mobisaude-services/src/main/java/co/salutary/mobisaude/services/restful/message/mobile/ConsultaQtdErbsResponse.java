package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do response do servico Consulta Qtd ERBs
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "consultaQtdErbsResponse")
public class ConsultaQtdErbsResponse implements MobileResponse {
	/**
	 * Campo qtdErbs
	 */
	private QtdErbs[] qtdErbs;
	/**
	 * Campo erro
	 */
	private String erro;

	/**
	 * Getter de qtdErbs
	 * @return
	 */
	public QtdErbs[] getQtdErbs() {
		return qtdErbs;
	}
	/**
	 * Setter de qtdErbs
	 * @param qtdErbs
	 */
	public void setQtdErbs(QtdErbs[] qtdErbs) {
		this.qtdErbs = qtdErbs.clone();
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
