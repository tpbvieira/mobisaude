package co.salutary.mobisaude.services.model.relatorioranking;

// Generated 20/10/2013 22:21:30 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Entidade RelatorioRanking
 *
 */
@Entity
@IdClass(RelatorioRankingPK.class)
@Table(name = "\"tb_relatorio_ranking\"", schema = "public")
public class RelatorioRanking implements java.io.Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -259018148356554547L;
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
	private Integer qtdTecnologia2g;
	/**
	 * Campo qtdTecnologia3g
	 */
	private Integer qtdTecnologia3g;
	/**
	 * Campo qtdTecnologia4g
	 */
	private Integer qtdTecnologia4g;
	/**
	 * Campo conexaoVoz
	 */
	private Double conexaoVoz;
	/**
	 * Campo desconexaoVoz
	 */
	private Double desconexaoVoz;
	/**
	 * Campo conexaoDados
	 */
	private Double conexaoDados;
	/**
	 * Campo desconexaoDados
	 */
	private Double desconexaoDados;
	/**
	 * Campo rankingVoz
	 */
	private Double rankingVoz;
	/**
	 * Campo rankingDados
	 */
	private Double rankingDados;
	/**
	 * Campo conexaoDados2g
	 */
	private Double conexaoDados2g;
	/**
	 * Campo desconexaoDados2g
	 */
	private Double desconexaoDados2g;
	/**
	 * Campo conexaoDados3g
	 */
	private Double conexaoDados3g;
	/**
	 * Campo desconexaoDados3g
	 */
	private Double desconexaoDados3g;
	/**
	 * Campo conexaoDados4g
	 */
	private Double conexaoDados4g;
	/**
	 * Campo desconexaoDados4g
	 */
	private Double desconexaoDados4g;
	/**
	 * Campo rankingDados2g
	 */
	private Double rankingDados2g;
	/**
	 * Campo rankingDados3g
	 */
	private Double rankingDados3g;
	/**
	 * Campo rankingDados4g
	 */
	private Double rankingDados4g;
	/**
	 * Campo indiceVoz
	 */
	private Double indiceVoz;
	/**
	 * Campo indiceDados
	 */
	private Double indiceDados;
	/**
	 * Campo indiceDados2g
	 */
	private Double indiceDados2g;
	/**
	 * Campo indiceDados3g
	 */
	private Double indiceDados3g;
	/**
	 * Campo indiceDados4g
	 */
	private Double indiceDados4g;
	/**
	 * Campo disponibilidade
	 */
	private Double disponibilidade;
	/**
	 * Construtor sem parametros
	 */
	public RelatorioRanking() {
	}

	/**
	 * Getter de codMunicipioIbge
	 * @return
	 */
	@Id
	@Column(name = "\"no_cod_municipio_ibge\"", unique = true, nullable = false)
	public String getCodMunicipioIbge() {
		return this.codMunicipioIbge;
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
	@Column(name = "\"no_municipio\"")
	public String getMunicipio() {
		return this.municipio;
	}

	/**
	 * Setter de municipio
	 * @param municipio
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	/**
	 * Getter de uf
	 * @return
	 */
	@Column(name = "\"sg_uf\"")
	public String getUf() {
		return this.uf;
	}

	/**
	 * Setter de uf
	 * @param uf
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}

	/**
	 * Getter de prestadora
	 * @return
	 */
	@Id
	@Column(name = "\"no_prestadora\"")
	public String getPrestadora() {
		return this.prestadora;
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
	@Column(name = "\"tx_nome_fantasia\"")
	public String getNomeFantasia() {
		return this.nomeFantasia;
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
	@Column(name = "\"qt_tecnologia_2g\"")
	public Integer getQtdTecnologia2g() {
		return this.qtdTecnologia2g;
	}

	/**
	 * Setter de qtdTecnologia2g
	 * @param qtdTecnologia2g
	 */
	public void setQtdTecnologia2g(Integer qtdTecnologia2g) {
		this.qtdTecnologia2g = qtdTecnologia2g;
	}

	/**
	 * Getter de qtdTecnologia3g
	 * @return
	 */
	@Column(name = "\"qt_tecnologia_3g\"")
	public Integer getQtdTecnologia3g() {
		return this.qtdTecnologia3g;
	}

	/**
	 * Setter de qtdTecnologia3g
	 * @param qtdTecnologia3g
	 */
	public void setQtdTecnologia3g(Integer qtdTecnologia3g) {
		this.qtdTecnologia3g = qtdTecnologia3g;
	}

	/**
	 * Getter de qtdTecnologia4g
	 * @return
	 */
	@Column(name = "\"qt_tecnologia_4g\"")
	public Integer getQtdTecnologia4g() {
		return this.qtdTecnologia4g;
	}

	/**
	 * Setter de qtdTecnologia4g
	 * @param qtdTecnologia4g
	 */
	public void setQtdTecnologia4g(Integer qtdTecnologia4g) {
		this.qtdTecnologia4g = qtdTecnologia4g;
	}

	/**
	 * Getter de conexaoVoz
	 * @return
	 */
	@Column(name = "\"nu_conexao_voz\"")
	public Double getConexaoVoz() {
		return this.conexaoVoz;
	}

	/**
	 * Setter de conexaoVoz
	 * @param conexaoVoz
	 */
	public void setConexaoVoz(Double conexaoVoz) {
		this.conexaoVoz = conexaoVoz;
	}

	/**
	 * Getter de desconexaoVoz
	 * @return
	 */
	@Column(name = "\"nu_desconexao_voz\"")
	public Double getDesconexaoVoz() {
		return this.desconexaoVoz;
	}

	/**
	 * Setter de desconexaoVoz
	 * @param desconexaoVoz
	 */
	public void setDesconexaoVoz(Double desconexaoVoz) {
		this.desconexaoVoz = desconexaoVoz;
	}

	/**
	 * Getter de conexaoDados
	 * @return
	 */
	@Column(name = "\"nu_conexao_dados\"")
	public Double getConexaoDados() {
		return this.conexaoDados;
	}

	/**
	 * Setter de conexaoDados
	 * @param conexaoDados
	 */
	public void setConexaoDados(Double conexaoDados) {
		this.conexaoDados = conexaoDados;
	}

	/**
	 * Getter de desconexaoDados
	 * @return
	 */
	@Column(name = "\"nu_desconexao_dados\"")
	public Double getDesconexaoDados() {
		return this.desconexaoDados;
	}

	/**
	 * Setter de desconexaoDados
	 * @param desconexaoDados
	 */
	public void setDesconexaoDados(Double desconexaoDados) {
		this.desconexaoDados = desconexaoDados;
	}

	/**
	 * Getter de rankingVoz
	 * @return
	 */
	@Column(name = "\"nu_ranking_voz\"")
	public Double getRankingVoz() {
		return this.rankingVoz;
	}

	/**
	 * Setter de rankingVoz
	 * @param rankingVoz
	 */
	public void setRankingVoz(Double rankingVoz) {
		this.rankingVoz = rankingVoz;
	}

	/**
	 * Getter de rankingDados
	 * @return
	 */
	@Column(name = "\"nu_ranking_dados\"")
	public Double getRankingDados() {
		return this.rankingDados;
	}

	/**
	 * Setter de rankingDados
	 * @param rankingDados
	 */
	public void setRankingDados(Double rankingDados) {
		this.rankingDados = rankingDados;
	}

	/**
	 * @return the conexaoDados2g
	 */
	@Column(name = "\"nu_conexao_dados_2g\"")
	public Double getConexaoDados2g() {
		return conexaoDados2g;
	}

	/**
	 * @param conexaoDados2g the conexaoDados2g to set
	 */
	public void setConexaoDados2g(Double conexaoDados2g) {
		this.conexaoDados2g = conexaoDados2g;
	}

	/**
	 * @return the desconexaoDados2g
	 */
	@Column(name = "\"nu_desconexao_dados_2g\"")
	public Double getDesconexaoDados2g() {
		return desconexaoDados2g;
	}

	/**
	 * @param desconexaoDados2g the desconexaoDados2g to set
	 */
	public void setDesconexaoDados2g(Double desconexaoDados2g) {
		this.desconexaoDados2g = desconexaoDados2g;
	}

	/**
	 * @return the conexaoDados3g
	 */
	@Column(name = "\"nu_conexao_dados_3g\"")
	public Double getConexaoDados3g() {
		return conexaoDados3g;
	}

	/**
	 * @param conexaoDados3g the conexaoDados3g to set
	 */
	public void setConexaoDados3g(Double conexaoDados3g) {
		this.conexaoDados3g = conexaoDados3g;
	}

	/**
	 * @return the desconexaoDados3g
	 */
	@Column(name = "\"nu_desconexao_dados_3g\"")
	public Double getDesconexaoDados3g() {
		return desconexaoDados3g;
	}

	/**
	 * @param desconexaoDados3g the desconexaoDados3g to set
	 */
	public void setDesconexaoDados3g(Double desconexaoDados3g) {
		this.desconexaoDados3g = desconexaoDados3g;
	}

	/**
	 * @return the conexaoDados4g
	 */
	@Column(name = "\"nu_conexao_dados_4g\"")
	public Double getConexaoDados4g() {
		return conexaoDados4g;
	}

	/**
	 * @param conexaoDados4g the conexaoDados4g to set
	 */
	public void setConexaoDados4g(Double conexaoDados4g) {
		this.conexaoDados4g = conexaoDados4g;
	}

	/**
	 * @return the desconexaoDados4g
	 */
	@Column(name = "\"nu_desconexao_dados_4g\"")
	public Double getDesconexaoDados4g() {
		return desconexaoDados4g;
	}

	/**
	 * @param desconexaoDados4g the desconexaoDados4g to set
	 */
	public void setDesconexaoDados4g(Double desconexaoDados4g) {
		this.desconexaoDados4g = desconexaoDados4g;
	}

	/**
	 * @return the rankingDados2g
	 */
	@Column(name = "\"nu_ranking_dados_2g\"")
	public Double getRankingDados2g() {
		return rankingDados2g;
	}

	/**
	 * @param rankingDados2g the rankingDados2g to set
	 */
	public void setRankingDados2g(Double rankingDados2g) {
		this.rankingDados2g = rankingDados2g;
	}

	/**
	 * @return the rankingDados3g
	 */
	@Column(name = "\"nu_ranking_dados_3g\"")
	public Double getRankingDados3g() {
		return rankingDados3g;
	}

	/**
	 * @param rankingDados3g the rankingDados3g to set
	 */
	public void setRankingDados3g(Double rankingDados3g) {
		this.rankingDados3g = rankingDados3g;
	}

	/**
	 * @return the rankingDados4g
	 */
	@Column(name = "\"nu_ranking_dados_4g\"")
	public Double getRankingDados4g() {
		return rankingDados4g;
	}

	/**
	 * @param rankingDados4g the rankingDados4g to set
	 */
	public void setRankingDados4g(Double rankingDados4g) {
		this.rankingDados4g = rankingDados4g;
	}

	/**
	 * @return the indiceVoz
	 */
	@Column(name = "\"nu_indice_voz\"")
	public Double getIndiceVoz() {
		return indiceVoz;
	}

	/**
	 * @param indiceVoz the indiceVoz to set
	 */
	public void setIndiceVoz(Double indiceVoz) {
		this.indiceVoz = indiceVoz;
	}

	/**
	 * @return the indiceDados
	 */
	@Column(name = "\"nu_indice_dados\"")
	public Double getIndiceDados() {
		return indiceDados;
	}

	/**
	 * @param indiceDados the indiceDados to set
	 */
	public void setIndiceDados(Double indiceDados) {
		this.indiceDados = indiceDados;
	}

	/**
	 * @return the indiceDados2g
	 */
	@Column(name = "\"nu_indice_dados_2g\"")
	public Double getIndiceDados2g() {
		return indiceDados2g;
	}

	/**
	 * @param indiceDados2g the indiceDados2g to set
	 */
	public void setIndiceDados2g(Double indiceDados2g) {
		this.indiceDados2g = indiceDados2g;
	}

	/**
	 * @return the indiceDados3g
	 */
	@Column(name = "\"nu_indice_dados_3g\"")
	public Double getIndiceDados3g() {
		return indiceDados3g;
	}

	/**
	 * @param indiceDados3g the indiceDados3g to set
	 */
	public void setIndiceDados3g(Double indiceDados3g) {
		this.indiceDados3g = indiceDados3g;
	}

	/**
	 * @return the indiceDados4g
	 */
	@Column(name = "\"nu_indice_dados_4g\"")
	public Double getIndiceDados4g() {
		return indiceDados4g;
	}

	/**
	 * @param indiceDados4g the indiceDados4g to set
	 */
	public void setIndiceDados4g(Double indiceDados4g) {
		this.indiceDados4g = indiceDados4g;
	}

	/**
	 * @return the disponibilidade
	 */
	@Column(name = "\"nu_disponibilidade\"")
	public Double getDisponibilidade() {
		return disponibilidade;
	}

	/**
	 * @param disponibilidade the disponibilidade to set
	 */
	public void setDisponibilidade(Double disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
}
