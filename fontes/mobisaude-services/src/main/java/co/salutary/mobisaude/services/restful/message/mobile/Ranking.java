package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para conter uma linha de Ranking.
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "ranking")
public class Ranking implements Cloneable {
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
	 * Implementacao de clone
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    Ranking cloned = (Ranking)super.clone();
	    
	    return cloned;
	}
}
