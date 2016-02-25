package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do request do servico Texto Aviso Relatar Problema
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "textoAvisoRelatarProblemaRequest")
public class TextoAvisoRelatarProblemaRequest implements MobileRequest {
	/**
	 * Validar o objeto
	 */
	@Override
	public boolean validar() {
		// Validar campos
			
		return true;
	}
}
