package co.salutary.mobisaude.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do response do servico Gerar Chave
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "gerarChaveResponse")
public class GerarChaveResponse implements MobileResponse {
	/**
	 * Campo chave
	 */
	private String chave;
	/**
	 * Campo erro
	 */
	private String erro;
	
	/**
	 * Getter de chave
	 * @return
	 */
	public String getChave() {
		return chave;
	}
	/**
	 * Setter de chave
	 * @param chave
	 */
	public void setChave(String chave) {
		this.chave = chave;
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
