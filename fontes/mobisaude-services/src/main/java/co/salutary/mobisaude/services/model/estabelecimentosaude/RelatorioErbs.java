package co.salutary.mobisaude.services.model.estabelecimentosaude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entidade RelatorioErbs 
 *
 */
@Entity
@Table(name = "\"tb_relatorio_erbs\"", schema = "public")
public class RelatorioErbs implements java.io.Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5949891975169349146L;
	/**
	 * Campo idRelatorioErbs
	 */
	private int idRelatorioErbs;
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
	 * Campo tecnologia2g
	 */
	private Boolean tecnologia2g;
	/**
	 * Campo tecnologia3g
	 */
	private Boolean tecnologia3g;
	/**
	 * Campo tecnologia4g
	 */
	private Boolean tecnologia4g;
	/**
	 * Campo latitudeStel
	 */
	private Double latitudeStel;
	/**
	 * Campo longitudeStel
	 */
	private Double longitudeStel;

	/**
	 * Construtor sem parametros
	 */
	public RelatorioErbs() {
	}

	/** 
	 * Getter de idRelatorioErbs
	 * @return
	 */
	@Id
	@Column(name = "\"nu_id_relatorio_erbs\"", unique = true, nullable = false)
	@SequenceGenerator(name = "seqRelatorioErbs", sequenceName = "\"seq_relatorio_erbs\"", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqRelatorioErbs")
	public int getIdRelatorioErbs() {
		return this.idRelatorioErbs;
	}

	/**
	 * Setter de idRelatorioErbs
	 * @param idRelatorioErbs
	 */
	public void setIdRelatorioErbs(int idRelatorioErbs) {
		this.idRelatorioErbs = idRelatorioErbs;
	}

	/**
	 * Getter de codMunicipioIbge
	 * @return
	 */
	@Column(name = "\"no_cod_municipio_ibge\"")
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
	@Id
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
	 * Getter de tecnologia2g
	 * @return
	 */
	@Column(name = "\"in_tecnologia_2g\"")
	public Boolean getTecnologia2g() {
		return this.tecnologia2g;
	}

	/**
	 * Setter de tecnologia2g
	 * @param tecnologia2g
	 */
	public void setTecnologia2g(Boolean tecnologia2g) {
		this.tecnologia2g = tecnologia2g;
	}

	/**
	 * Getter de tecnologia3g
	 * @return
	 */
	@Column(name = "\"in_tecnologia_3g\"")
	public Boolean getTecnologia3g() {
		return this.tecnologia3g;
	}

	/**
	 * Setter de tecnologia3g
	 * @param tecnologia3g
	 */
	public void setTecnologia3g(Boolean tecnologia3g) {
		this.tecnologia3g = tecnologia3g;
	}

	/**
	 * Getter de tecnologia4g
	 * @return
	 */
	@Column(name = "\"in_tecnologia_4g\"")
	public Boolean getTecnologia4g() {
		return this.tecnologia4g;
	}

	/**
	 * Setter de tecnologia4g
	 * @param tecnologia4g
	 */
	public void setTecnologia4g(Boolean tecnologia4g) {
		this.tecnologia4g = tecnologia4g;
	}

	/**
	 * Getter de latitudeStel
	 * @return
	 */
	@Id
	@Column(name = "\"nu_latitude_stel\"")
	public Double getLatitudeStel() {
		return this.latitudeStel;
	}

	/**
	 * Setter de latitudeStel
	 * @param latitudeStel
	 */
	public void setLatitudeStel(Double latitudeStel) {
		this.latitudeStel = latitudeStel;
	}

	/**
	 * Getter de longitudeStel
	 * @return
	 */
	@Id
	@Column(name = "\"nu_longitude_stel\"")
	public Double getLongitudeStel() {
		return this.longitudeStel;
	}

	/**
	 * Setter de longitudeStel
	 * @param longitudeStel
	 */
	public void setLongitudeStel(Double longitudeStel) {
		this.longitudeStel = longitudeStel;
	}
}
