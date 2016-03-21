package co.salutary.mobisaude.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do response do servico Consulta Patamar
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "consultaPatamarResponse")
public class ConsultaPatamarResponse implements MobileResponse {
	/**
	 * Campo patamarMinAcessoVoz
	 */
	private String patamarMinAcessoVoz;
	/**
	 * Campo patamarMaxAcessoVoz
	 */
	private String patamarMaxAcessoVoz;
	/**
	 * Campo patamarMinQuedaVoz
	 */
	private String patamarMinQuedaVoz;
	/**
	 * Campo patamarMaxQuedaVoz
	 */
	private String patamarMaxQuedaVoz;
	/**
	 * Campo patamarMinAcessoDados
	 */
	private String patamarMinAcessoDados;
	/**
	 * Campo patamarMaxAcessoDados
	 */
	private String patamarMaxAcessoDados;
	/**
	 * Campo patamarMinQuedaDados
	 */
	private String patamarMinQuedaDados;
	/**
	 * Campo patamarMaxQuedaDados
	 */
	private String patamarMaxQuedaDados;
	/**
	 * Campo erro
	 */
	private String erro;


	/**
	 * @return the patamarMinAcessoVoz
	 */
	public String getPatamarMinAcessoVoz() {
		return patamarMinAcessoVoz;
	}
	/**
	 * @param patamarMinAcessoVoz the patamarMinAcessoVoz to set
	 */
	public void setPatamarMinAcessoVoz(String patamarMinAcessoVoz) {
		this.patamarMinAcessoVoz = patamarMinAcessoVoz;
	}
	/**
	 * @return the patamarMaxAcessoVoz
	 */
	public String getPatamarMaxAcessoVoz() {
		return patamarMaxAcessoVoz;
	}
	/**
	 * @param patamarMaxAcessoVoz the patamarMaxAcessoVoz to set
	 */
	public void setPatamarMaxAcessoVoz(String patamarMaxAcessoVoz) {
		this.patamarMaxAcessoVoz = patamarMaxAcessoVoz;
	}
	/**
	 * @return the patamarMinQuedaVoz
	 */
	public String getPatamarMinQuedaVoz() {
		return patamarMinQuedaVoz;
	}
	/**
	 * @param patamarMinQuedaVoz the patamarMinQuedaVoz to set
	 */
	public void setPatamarMinQuedaVoz(String patamarMinQuedaVoz) {
		this.patamarMinQuedaVoz = patamarMinQuedaVoz;
	}
	/**
	 * @return the patamarMaxQuedaVoz
	 */
	public String getPatamarMaxQuedaVoz() {
		return patamarMaxQuedaVoz;
	}
	/**
	 * @param patamarMaxQuedaVoz the patamarMaxQuedaVoz to set
	 */
	public void setPatamarMaxQuedaVoz(String patamarMaxQuedaVoz) {
		this.patamarMaxQuedaVoz = patamarMaxQuedaVoz;
	}
	/**
	 * @return the patamarMinAcessoDados
	 */
	public String getPatamarMinAcessoDados() {
		return patamarMinAcessoDados;
	}
	/**
	 * @param patamarMinAcessoDados the patamarMinAcessoDados to set
	 */
	public void setPatamarMinAcessoDados(String patamarMinAcessoDados) {
		this.patamarMinAcessoDados = patamarMinAcessoDados;
	}
	/**
	 * @return the patamarMaxAcessoDados
	 */
	public String getPatamarMaxAcessoDados() {
		return patamarMaxAcessoDados;
	}
	/**
	 * @param patamarMaxAcessoDados the patamarMaxAcessoDados to set
	 */
	public void setPatamarMaxAcessoDados(String patamarMaxAcessoDados) {
		this.patamarMaxAcessoDados = patamarMaxAcessoDados;
	}
	/**
	 * @return the patamarMinQuedaDados
	 */
	public String getPatamarMinQuedaDados() {
		return patamarMinQuedaDados;
	}
	/**
	 * @param patamarMinQuedaDados the patamarMinQuedaDados to set
	 */
	public void setPatamarMinQuedaDados(String patamarMinQuedaDados) {
		this.patamarMinQuedaDados = patamarMinQuedaDados;
	}
	/**
	 * @return the patamarMaxQuedaDados
	 */
	public String getPatamarMaxQuedaDados() {
		return patamarMaxQuedaDados;
	}
	/**
	 * @param patamarMaxQuedaDados the patamarMaxQuedaDados to set
	 */
	public void setPatamarMaxQuedaDados(String patamarMaxQuedaDados) {
		this.patamarMaxQuedaDados = patamarMaxQuedaDados;
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
