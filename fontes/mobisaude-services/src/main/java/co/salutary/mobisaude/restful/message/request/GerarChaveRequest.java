package co.salutary.mobisaude.restful.message.request;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do request do servico Gerar Chave
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "gerarChaveRequest")
public class GerarChaveRequest implements IMobileRequest {
	/**
	 * Validar o objeto
	 */
	@Override
	public boolean validate() {
		// Validar campos
			
		return true;
	}
}
