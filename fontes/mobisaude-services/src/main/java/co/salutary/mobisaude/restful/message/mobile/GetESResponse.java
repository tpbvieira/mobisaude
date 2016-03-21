package co.salutary.mobisaude.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement
@XmlType(namespace = "mobile", name = "getESResponse")
public class GetESResponse implements MobileResponse {

	private ESMsg[] esMsg;
	private String erro;

	public ESMsg[] getEstabelecimentoSaude() {
		return esMsg;
	}

	public void setEstabelecimentoSaude(ESMsg[] esMsg) {
		this.esMsg = esMsg.clone();
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

}