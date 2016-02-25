package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para conter uma linha de Ranking V2.
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "rankingV2")
public class RankingV2 implements Cloneable {
	/**
	 * Campo uf
	 */
	private String uf;
	/**
	 * Campo codMunicipioIbge
	 */
	private String codMunicipioIbge;
	/**
	 * Campo municipio
	 */
	private String municipio;
	/**
	 * Campo prestadora
	 */
	private String prestadora;
	/**
	 * Campo nomeFantasia
	 */
	private String nomeFantasia;
	/**
	 * Campo qtdTecnologia2g
	 */
	private String qtdTecnologia2g;
	/**
	 * Campo qtdTecnologia3g
	 */
	private String qtdTecnologia3g;
	/**
	 * Campo qtdTecnologia4g
	 */
	private String qtdTecnologia4g;
	/**
	 * Campo conexaoVoz
	 */
	private String conexaoVoz;
	/**
	 * Campo desconexaoVoz
	 */
	private String desconexaoVoz;
	/**
	 * Campo conexaoDados
	 */
	private String conexaoDados;
	/**
	 * Campo desconexaoDados
	 */
	private String desconexaoDados;
	/**
	 * Campo rankingVoz
	 */
	private String rankingVoz;
	/**
	 * Campo rankingDados
	 */
	private String rankingDados;
	/**
	 * Campo conexaoDados2g
	 */
	private String conexaoDados2g;
	/**
	 * Campo desconexaoDados2g
	 */
	private String desconexaoDados2g;
	/**
	 * Campo conexaoDados3g
	 */
	private String conexaoDados3g;
	/**
	 * Campo desconexaoDados3g
	 */
	private String desconexaoDados3g;
	/**
	 * Campo conexaoDados4g
	 */
	private String conexaoDados4g;
	/**
	 * Campo desconexaoDados4g
	 */
	private String desconexaoDados4g;
	/**
	 * Campo rankingDados2g
	 */
	private String rankingDados2g;
	/**
	 * Campo rankingDados3g
	 */
	private String rankingDados3g;
	/**
	 * Campo rankingDados4g
	 */
	private String rankingDados4g;
	/**
	 * Campo indiceVoz
	 */
	private String indiceVoz;
	/**
	 * Campo indiceDados
	 */
	private String indiceDados;
	/**
	 * Campo indiceDados2g
	 */
	private String indiceDados2g;
	/**
	 * Campo indiceDados3g
	 */
	private String indiceDados3g;
	/**
	 * Campo indiceDados4g
	 */
	private String indiceDados4g;
	/**
	 * Campo disponibilidade
	 */
	private String disponibilidade;
	
	/**
	 * Getter de uf
	 * @return
	 */
	public String getUf() {
		return uf;
	}
	/**
	 * Setter de uf
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}
	/**
	 * Getter de codMunicipioIbge
	 * @return
	 */
	public String getCodMunicipioIbge() {
		return codMunicipioIbge;
	}
	/**
	 * Setter de codMunicipioIbge
	 * @param codMunicipioIbge
	 */
	public void setCodMunicipioIbge(String codMunicipioIbge) {
		this.codMunicipioIbge = codMunicipioIbge;
	}
	/**
	 * Getter de municipio
	 * @return
	 */
	public String getMunicipio() {
		return municipio;
	}
	/**
	 * Setter de municipio
	 * @param municipio
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	/**
	 * Getter de prestadora
	 */
	public String getPrestadora() {
		return prestadora;
	}
	/**
	 * Setter de prestadora
	 * @param prestadora
	 */
	public void setPrestadora(String prestadora) {
		this.prestadora = prestadora;
	}
	/**
	 * Getter de nomeFantasia
	 * @return
	 */
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	/**
	 * Setter de nomeFantasia
	 * @param nomeFantasia
	 */
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	/**
	 * Getter de qtdTecnologia2g
	 * @return
	 */
	public String getQtdTecnologia2g() {
		return qtdTecnologia2g;
	}
	/**
	 * Setter de qtdTecnologia2g
	 * @param qtdTecnologia2g
	 */
	public void setQtdTecnologia2g(String qtdTecnologia2g) {
		this.qtdTecnologia2g = qtdTecnologia2g;
	}
	/**
	 * Getter de qtdTecnologia3g
	 * @return
	 */
	public String getQtdTecnologia3g() {
		return qtdTecnologia3g;
	}
	/**
	 * Setter de qtdTecnologia3g
	 * @param qtdTecnologia3g
	 */
	public void setQtdTecnologia3g(String qtdTecnologia3g) {
		this.qtdTecnologia3g = qtdTecnologia3g;
	}
	/**
	 * Getter de qtdTecnologia4g
	 * @return
	 */
	public String getQtdTecnologia4g() {
		return qtdTecnologia4g;
	}
	/**
	 * Setter de qtdTecnologia4g
	 * @param qtdTecnologia4g
	 */
	public void setQtdTecnologia4g(String qtdTecnologia4g) {
		this.qtdTecnologia4g = qtdTecnologia4g;
	}
	/**
	 * Getter de conexaoVoz
	 */
	public String getConexaoVoz() {
		return conexaoVoz;
	}
	/**
	 * Setter de conexaoVoz
	 * @param conexaoVoz
	 */
	public void setConexaoVoz(String conexaoVoz) {
		this.conexaoVoz = conexaoVoz;
	}
	/**
	 * Getter de desconexaoVoz
	 * @return
	 */
	public String getDesconexaoVoz() {
		return desconexaoVoz;
	}
	/**
	 * Setter de desconexaoVoz
	 * @param desconexaoVoz
	 */
	public void setDesconexaoVoz(String desconexaoVoz) {
		this.desconexaoVoz = desconexaoVoz;
	}
	/**
	 * Getter de conexaoDados
	 * @return
	 */
	public String getConexaoDados() {
		return conexaoDados;
	}
	/**
	 * Setter de conexaoDados
	 * @param conexaoDados
	 */
	public void setConexaoDados(String conexaoDados) {
		this.conexaoDados = conexaoDados;
	}
	/**
	 * Getter de desconexaoDados
	 * @return
	 */
	public String getDesconexaoDados() {
		return desconexaoDados;
	}
	/**
	 * Setter de desconexaoDados
	 * @param desconexaoDados
	 */
	public void setDesconexaoDados(String desconexaoDados) {
		this.desconexaoDados = desconexaoDados;
	}
	/**
	 * Getter de rankingVoz
	 * @return
	 */
	public String getRankingVoz() {
		return rankingVoz;
	}
	/**
	 * Setter de rankingVoz
	 * @param rankingVoz
	 */
	public void setRankingVoz(String rankingVoz) {
		this.rankingVoz = rankingVoz;
	}
	/**
	 * Getter de rankingDados
	 * @return
	 */
	public String getRankingDados() {
		return rankingDados;
	}
	/**
	 * Setter de rankingDados
	 * @param rankingDados
	 */
	public void setRankingDados(String rankingDados) {
		this.rankingDados = rankingDados;
	}

	/**
	 * @return the conexaoDados2g
	 */
	public String getConexaoDados2g() {
		return conexaoDados2g;
	}
	/**
	 * @param conexaoDados2g the conexaoDados2g to set
	 */
	public void setConexaoDados2g(String conexaoDados2g) {
		this.conexaoDados2g = conexaoDados2g;
	}
	/**
	 * @return the desconexaoDados2g
	 */
	public String getDesconexaoDados2g() {
		return desconexaoDados2g;
	}
	/**
	 * @param desconexaoDados2g the desconexaoDados2g to set
	 */
	public void setDesconexaoDados2g(String desconexaoDados2g) {
		this.desconexaoDados2g = desconexaoDados2g;
	}
	/**
	 * @return the conexaoDados3g
	 */
	public String getConexaoDados3g() {
		return conexaoDados3g;
	}
	/**
	 * @param conexaoDados3g the conexaoDados3g to set
	 */
	public void setConexaoDados3g(String conexaoDados3g) {
		this.conexaoDados3g = conexaoDados3g;
	}
	/**
	 * @return the desconexaoDados3g
	 */
	public String getDesconexaoDados3g() {
		return desconexaoDados3g;
	}
	/**
	 * @param desconexaoDados3g the desconexaoDados3g to set
	 */
	public void setDesconexaoDados3g(String desconexaoDados3g) {
		this.desconexaoDados3g = desconexaoDados3g;
	}
	/**
	 * @return the conexaoDados4g
	 */
	public String getConexaoDados4g() {
		return conexaoDados4g;
	}
	/**
	 * @param conexaoDados4g the conexaoDados4g to set
	 */
	public void setConexaoDados4g(String conexaoDados4g) {
		this.conexaoDados4g = conexaoDados4g;
	}
	/**
	 * @return the desconexaoDados4g
	 */
	public String getDesconexaoDados4g() {
		return desconexaoDados4g;
	}
	/**
	 * @param desconexaoDados4g the desconexaoDados4g to set
	 */
	public void setDesconexaoDados4g(String desconexaoDados4g) {
		this.desconexaoDados4g = desconexaoDados4g;
	}
	/**
	 * @return the rankingDados2g
	 */
	public String getRankingDados2g() {
		return rankingDados2g;
	}
	/**
	 * @param rankingDados2g the rankingDados2g to set
	 */
	public void setRankingDados2g(String rankingDados2g) {
		this.rankingDados2g = rankingDados2g;
	}
	/**
	 * @return the rankingDados3g
	 */
	public String getRankingDados3g() {
		return rankingDados3g;
	}
	/**
	 * @param rankingDados3g the rankingDados3g to set
	 */
	public void setRankingDados3g(String rankingDados3g) {
		this.rankingDados3g = rankingDados3g;
	}
	/**
	 * @return the rankingDados4g
	 */
	public String getRankingDados4g() {
		return rankingDados4g;
	}
	/**
	 * @param rankingDados4g the rankingDados4g to set
	 */
	public void setRankingDados4g(String rankingDados4g) {
		this.rankingDados4g = rankingDados4g;
	}
	/**
	 * @return the indiceVoz
	 */
	public String getIndiceVoz() {
		return indiceVoz;
	}
	/**
	 * @param indiceVoz the indiceVoz to set
	 */
	public void setIndiceVoz(String indiceVoz) {
		this.indiceVoz = indiceVoz;
	}
	/**
	 * @return the indiceDados
	 */
	public String getIndiceDados() {
		return indiceDados;
	}
	/**
	 * @param indiceDados the indiceDados to set
	 */
	public void setIndiceDados(String indiceDados) {
		this.indiceDados = indiceDados;
	}
	/**
	 * @return the indiceDados2g
	 */
	public String getIndiceDados2g() {
		return indiceDados2g;
	}
	/**
	 * @param indiceDados2g the indiceDados2g to set
	 */
	public void setIndiceDados2g(String indiceDados2g) {
		this.indiceDados2g = indiceDados2g;
	}
	/**
	 * @return the indiceDados3g
	 */
	public String getIndiceDados3g() {
		return indiceDados3g;
	}
	/**
	 * @param indiceDados3g the indiceDados3g to set
	 */
	public void setIndiceDados3g(String indiceDados3g) {
		this.indiceDados3g = indiceDados3g;
	}
	/**
	 * @return the indiceDados4g
	 */
	public String getIndiceDados4g() {
		return indiceDados4g;
	}
	/**
	 * @param indiceDados4g the indiceDados4g to set
	 */
	public void setIndiceDados4g(String indiceDados4g) {
		this.indiceDados4g = indiceDados4g;
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
	 * Implementacao de clone
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    RankingV2 cloned = (RankingV2)super.clone();
	    
	    return cloned;
	}
}
