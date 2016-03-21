package co.salutary.mobisaude.model.estabelecimentosaude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "\"tb_estabelecimento_saude\"", schema = "public")
public class EstabelecimentoSaude implements java.io.Serializable {

	private static final long serialVersionUID = 5949891975169349146L;
	private int idCnes;
	private int idMunicipio;
	private short idTipoEstabelecimentoSaude;
	private short idTipoGestao;
	private String cnpjMantenedora;
	private String razaoSocialMantenedora;
	private String razaoSocial;
	private String nomeFantasia;
	private String naturezaOrganizacao;
	private String esferaAdministrativa;
	private String logradouro;
	private String endereco;
	private String bairro;
	private String cep;
	private String origemCoordenada;
	private Double latitude;
	private Double longitude;

	public EstabelecimentoSaude() {
	}

	@Id
	@Column(name = "\"nu_id_cnes\"", unique = true, nullable = false)
	@SequenceGenerator(name = "seqEstabelecimentoSaude", sequenceName = "\"seq_estabelecimento_saude\"", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqEstabelecimentoSaude")
	public int getIdCnes() {
		return this.idCnes;
	}

	public void setIdCnes(int idCnes) {
		this.idCnes = idCnes;
	}

	@Column(name = "\"nu_id_municipio\"")
	public Integer getIdMunicipio() {
		return this.idMunicipio;
	}

	public void setIdMunicipio(Integer codMunicipioIbge) {
		this.idMunicipio = codMunicipioIbge;
	}

	@Column(name = "\"tx_id_cnpj_mantenedora\"")
	public String getCnpjMantenedora() {
		return this.cnpjMantenedora;
	}

	public void setCnpjMantenedora(String cnpjMantenedora) {
		this.cnpjMantenedora = cnpjMantenedora;
	}
	
	@Column(name = "\"tx_razao_social_mantenedora\"")
	public String getRazaoSocialMantenedora() {
		return this.razaoSocialMantenedora;
	}

	public void setRazaoSocialMantenedora(String razaoSocialMantenedora) {
		this.razaoSocialMantenedora = razaoSocialMantenedora;
	}
	
	@Column(name = "\"tx_razao_social\"")
	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@Column(name = "\"tx_nome_fantasia\"")
	public String getNomeFantasia() {
		return this.nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	
	@Column(name = "\"nu_id_tipo_estabelecimento_saude\"")
	public Short getIdTipoEstabelecimentoSaude() {
		return this.idTipoEstabelecimentoSaude;
	}

	public void setIdTipoEstabelecimentoSaude(Short idTipoEstabelecimentoSaude) {
		this.idTipoEstabelecimentoSaude = idTipoEstabelecimentoSaude;
	}

	@Column(name = "\"tx_natureza_organizacao\"")
	public String getNaturezaOrganizacao() {
		return this.naturezaOrganizacao;
	}

	public void setNaturezaOrganizacao(String naturezaOrganizacao) {
		this.naturezaOrganizacao = naturezaOrganizacao;
	}

	@Column(name = "\"tx_esfera_administrativa\"")
	public String getEsferaAdministrativa() {
		return this.esferaAdministrativa;
	}

	public void setEsferaAdministrativa(String esferaAdministrativa) {
		this.esferaAdministrativa = esferaAdministrativa;
	}

	@Column(name = "\"nu_id_tipo_gestao\"")
	public Short getIdTipoGestao() {
		return this.idTipoGestao;
	}

	public void setIdTipoGestao(Short idTipoGestao) {
		this.idTipoGestao = idTipoGestao;
	}
	
	@Column(name = "\"tx_logradouro\"")
	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@Column(name = "\"tx_endereco\"")
	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	@Column(name = "\"tx_bairro\"")
	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}	

	@Column(name = "\"tx_id_cep\"")
	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}	
	
	@Column(name = "\"nu_latitude\"")
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "\"nu_longitude\"")
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	@Column(name = "\"tx_origem_coordenada\"")
	public String getOrigemCoordenada() {
		return this.origemCoordenada;
	}

	public void setOrigemCoordenada(String origemCoordenada) {
		this.origemCoordenada = origemCoordenada;
	}
}
