package co.salutary.mobisaude.restful.message.request;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do request do servico Gerar Token
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "gerarTokenRequest")
public class GerarTokenRequest implements IMobileRequest {
	/**
	 * Campo chave
	 */
	private String chave;

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
	 * Validar o objeto
	 */
	@Override
	public boolean validar() {
		// Validar campos
		if (chave == null || chave.trim().equals("")) {
			return false;
		} 
			
		return true;
	}
}
