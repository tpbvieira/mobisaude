package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do response do servico listar espelhamento de municipios 
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "listarEspelhamentoMunicipioResponse")
public class ListarEspelhamentoMunicipioResponse implements MobileResponse {
	/**
	 * Campo erro
	 */
	private String erro;
	/**
	 * Campo registros
	 */
	private EspelhamentoMunicipio[] registros;
	
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
	 * @return the registros
	 */
	public EspelhamentoMunicipio[] getRegistros() {
		return registros;
	}
	/**
	 * @param registros the registros to set
	 */
	public void setRegistros(EspelhamentoMunicipio[] registros) {
		this.registros = registros.clone();
	}
}
