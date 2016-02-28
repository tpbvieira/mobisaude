package co.salutary.mobisaude.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(namespace = "mobile", name = "estabelecimentosaude")
public class ESMsg implements Cloneable {

	private String municipio;
	private String tipoEstabelecimentoSaude;
	private String tipoGestao;
	private String regiao;
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
	private String uf;
	private String origemCoordenada;
	private String latitude;
	private String longitude;

	
	
	public String getMunicipio() {
		return municipio;
	}



	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}



	public String getTipoEstabelecimentoSaude() {
		return tipoEstabelecimentoSaude;
	}



	public void setTipoEstabelecimentoSaude(String tipoEstabelecimentoSaude) {
		this.tipoEstabelecimentoSaude = tipoEstabelecimentoSaude;
	}



	public String getTipoGestao() {
		return tipoGestao;
	}



	public void setTipoGestao(String tipoGestao) {
		this.tipoGestao = tipoGestao;
	}



	public String getRegiao() {
		return regiao;
	}



	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}



	public String getCnpjMantenedora() {
		return cnpjMantenedora;
	}



	public void setCnpjMantenedora(String cnpjMantenedora) {
		this.cnpjMantenedora = cnpjMantenedora;
	}



	public String getRazaoSocialMantenedora() {
		return razaoSocialMantenedora;
	}



	public void setRazaoSocialMantenedora(String razaoSocialMantenedora) {
		this.razaoSocialMantenedora = razaoSocialMantenedora;
	}



	public String getRazaoSocial() {
		return razaoSocial;
	}



	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}



	public String getNomeFantasia() {
		return nomeFantasia;
	}



	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}



	public String getNaturezaOrganizacao() {
		return naturezaOrganizacao;
	}



	public void setNaturezaOrganizacao(String naturezaOrganizacao) {
		this.naturezaOrganizacao = naturezaOrganizacao;
	}



	public String getEsferaAdministrativa() {
		return esferaAdministrativa;
	}



	public void setEsferaAdministrativa(String esferaAdministrativa) {
		this.esferaAdministrativa = esferaAdministrativa;
	}



	public String getLogradouro() {
		return logradouro;
	}



	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}



	public String getEndereco() {
		return endereco;
	}



	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}



	public String getBairro() {
		return bairro;
	}



	public void setBairro(String bairro) {
		this.bairro = bairro;
	}



	public String getCep() {
		return cep;
	}



	public void setCep(String cep) {
		this.cep = cep;
	}



	public String getUf() {
		return uf;
	}



	public void setUf(String uf) {
		this.uf = uf;
	}



	public String getOrigemCoordenada() {
		return origemCoordenada;
	}



	public void setOrigemCoordenada(String origemCoordenada) {
		this.origemCoordenada = origemCoordenada;
	}



	public String getLatitude() {
		return latitude;
	}



	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}



	public String getLongitude() {
		return longitude;
	}



	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}



	@Override
	protected Object clone() throws CloneNotSupportedException {
		ESMsg cloned = (ESMsg)super.clone();

		return cloned;
	}
}
