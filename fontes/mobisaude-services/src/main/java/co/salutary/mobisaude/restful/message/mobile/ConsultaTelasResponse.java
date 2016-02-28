package co.salutary.mobisaude.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do response do servico consulta telas 
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "consultaTelasResponse")
public class ConsultaTelasResponse implements MobileResponse {
	/**
	 * Campo erro
	 */
	private String erro;
	/**
	 * Campo disponibilidade
	 */
	private String disponibilidade;
	/**
	 * Campo voz
	 */
	private String voz;
	/**
	 * Campo dados
	 */
	private String dados;
	/**
	 * Campo dados2g
	 */
	private String dados2g;
	/**
	 * Campo dados3g
	 */
	private String dados3g;
	/**
	 * Campo dados4g
	 */
	private String dados4g;
	/**
	 * Campo dadosGlobal
	 */
	private String dadosGlobal;
	
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
	/**
	 * @return the disponibilidade
	 */
	public String getDisponibilidade() {
		return disponibilidade;
	}
	/**
	 * @param disponibilidade the disponibilidade to set
	 */
	public void setDisponibilidade(String disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
	/**
	 * @return the voz
	 */
	public String getVoz() {
		return voz;
	}
	/**
	 * @param voz the voz to set
	 */
	public void setVoz(String voz) {
		this.voz = voz;
	}
	/**
	 * @return the dados
	 */
	public String getDados() {
		return dados;
	}
	/**
	 * @param dados the dados to set
	 */
	public void setDados(String dados) {
		this.dados = dados;
	}
	/**
	 * @return the dados2g
	 */
	public String getDados2g() {
		return dados2g;
	}
	/**
	 * @param dados2g the dados2g to set
	 */
	public void setDados2g(String dados2g) {
		this.dados2g = dados2g;
	}
	/**
	 * @return the dados3g
	 */
	public String getDados3g() {
		return dados3g;
	}
	/**
	 * @param dados3g the dados3g to set
	 */
	public void setDados3g(String dados3g) {
		this.dados3g = dados3g;
	}
	/**
	 * @return the dados4g
	 */
	public String getDados4g() {
		return dados4g;
	}
	/**
	 * @param dados4g the dados4g to set
	 */
	public void setDados4g(String dados4g) {
		this.dados4g = dados4g;
	}
	/**
	 * @return the dadosGlobal
	 */
	public String getDadosGlobal() {
		return dadosGlobal;
	}
	/**
	 * @param dadosGlobal the dadosGlobal to set
	 */
	public void setDadosGlobal(String dadosGlobal) {
		this.dadosGlobal = dadosGlobal;
	}

}
