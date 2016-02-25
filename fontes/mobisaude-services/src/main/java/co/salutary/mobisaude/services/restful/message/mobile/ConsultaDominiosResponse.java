package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do response do servico consulta dominios 
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "consultaDominiosResponse")
public class ConsultaDominiosResponse implements MobileResponse {
	
	private String erro;
	private Operadora[] operadoras;
	private TipoSistemaOperacional[] tipoSistemaOperacional;
	private TipoEstabelecimentoSaudeMsg[] tipoEstabelecimentoSaude;
	private TipoGestaoMsg[] tipoGestaoMsg;
	private RegiaoMsg[] regiaoMsg;
	
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
	 * @return the operadoras
	 */
	public Operadora[] getOperadoras() {
		return operadoras;
	}
	/**
	 * @param operadoras the operadoras to set
	 */
	public void setOperadoras(Operadora[] operadoras) {
		this.operadoras = operadoras.clone();
	}
	/**
	 * @return the tipoSistemaOperacional
	 */
	public TipoSistemaOperacional[] getTiposSistemaOperacional() {
		return tipoSistemaOperacional;
	}
	/**
	 * @param tipoSistemaOperacional the tipoSistemaOperacional to set
	 */
	public void setTiposSistemaOperacional(TipoSistemaOperacional[] tipoSistemaOperacional) {
		this.tipoSistemaOperacional = tipoSistemaOperacional.clone();
	}
	/**
	 * @return the tipoEstabelecimentoSaude
	 */
	public TipoEstabelecimentoSaudeMsg[] getTiposEstabelecimentoSaude() {
		return tipoEstabelecimentoSaude;
	}
	/**
	 * @param tipoEstabelecimentoSaude the tipoEstabelecimentoSaude to set
	 */
	public void setTiposEstabelecimentoSaude(TipoEstabelecimentoSaudeMsg[] tipoEstabelecimentoSaude) {
		this.tipoEstabelecimentoSaude = tipoEstabelecimentoSaude.clone();
	}
	/**
	 * @return the tipoGestaoMsg
	 */
	public TipoGestaoMsg[] getTiposGestao() {
		return tipoGestaoMsg;
	}
	/**
	 * @param tipoGestaoMsg to set
	 */
	public void setTipoGestao(TipoGestaoMsg[] tipoGestao) {
		this.tipoGestaoMsg = tipoGestao.clone();
	}
	/**
	 * @return the regiaoMsg
	 */
	public RegiaoMsg[] getRegiao() {
		return regiaoMsg;
	}
	/**
	 * @param regiaoMsg to set
	 */
	public void setRegiao(RegiaoMsg[] regiao) {
		this.regiaoMsg = regiao.clone();
	}
}