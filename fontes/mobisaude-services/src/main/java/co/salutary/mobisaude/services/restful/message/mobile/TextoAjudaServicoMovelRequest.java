package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do request do servico Texto Ajuda Servico Movel
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "textoAjudaServicoMovelRequest")
public class TextoAjudaServicoMovelRequest implements MobileRequest {
	/**
	 * Validar o objeto
	 */
	@Override
	public boolean validar() {
		// Validar campos
			
		return true;
	}
}
