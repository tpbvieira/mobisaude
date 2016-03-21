package co.salutary.mobisaude.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do request do servico Texto Ajuda
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "textoAjudaRequest")
public class TextoAjudaRequest implements MobileRequest {
	/**
	 * Validar o objeto
	 */
	@Override
	public boolean validar() {
		// Validar campos
			
		return true;
	}
}
