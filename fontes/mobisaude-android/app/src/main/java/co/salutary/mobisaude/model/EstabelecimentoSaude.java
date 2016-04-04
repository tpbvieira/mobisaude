package co.salutary.mobisaude.model;

import com.google.gson.Gson;

import org.json.JSONObject;

public class EstabelecimentoSaude {

    private static final String TAG = EstabelecimentoSaude.class.getSimpleName();

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

    public EstabelecimentoSaude(){}

    public int getIdCnes() {
        return idCnes;
    }

    public void setIdCnes(int idCnes) {
        this.idCnes = idCnes;
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public short getIdTipoEstabelecimentoSaude() {
        return idTipoEstabelecimentoSaude;
    }

    public void setIdTipoEstabelecimentoSaude(short idTipoEstabelecimentoSaude) {
        this.idTipoEstabelecimentoSaude = idTipoEstabelecimentoSaude;
    }

    public short getIdTipoGestao() {
        return idTipoGestao;
    }

    public void setIdTipoGestao(short idTipoGestao) {
        this.idTipoGestao = idTipoGestao;
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

    public String getOrigemCoordenada() {
        return origemCoordenada;
    }

    public void setOrigemCoordenada(String origemCoordenada) {
        this.origemCoordenada = origemCoordenada;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public static EstabelecimentoSaude jsonObjectToEstabelecimentoSaude(JSONObject jsonObject){
        Gson gson = new Gson();
        EstabelecimentoSaude estabelecimentoSaude = gson.fromJson(jsonObject.toString(),EstabelecimentoSaude.class);
        return estabelecimentoSaude;
//        try {
//            EstabelecimentoSaude estabelecimentoSaude = new EstabelecimentoSaude();
//            estabelecimentoSaude.setIdCnes(json.getInt("idCnes"));
//            estabelecimentoSaude.setIdMunicipio(json.getInt("idMunicipio"));
//            estabelecimentoSaude.setIdTipoGestao((short) json.getInt("idTipoGestao"));
//            estabelecimentoSaude.setCnpjMantenedora(json.getString("cnpjMantenedora"));
//            estabelecimentoSaude.setRazaoSocialMantenedora(json.getString("razaoSocialMantenedora"));
//            estabelecimentoSaude.setRazaoSocial(json.getString("razaoSocial"));
//            estabelecimentoSaude.setNomeFantasia(json.getString("nomeFantasia"));
//            estabelecimentoSaude.setNaturezaOrganizacao(json.getString("naturezaOrganizacao"));
//            estabelecimentoSaude.setEsferaAdministrativa(json.getString("esferaAdministrativa"));
//            estabelecimentoSaude.setLogradouro(json.getString("logradouro"));
//            estabelecimentoSaude.setEndereco(json.getString("endereco"));
//            estabelecimentoSaude.setBairro(json.getString("bairro"));
//            estabelecimentoSaude.setCep(json.getString("cep"));
//            estabelecimentoSaude.setOrigemCoordenada(json.getString("origemCoordenada"));
//            estabelecimentoSaude.setLatitude(json.getDouble("latitude"));
//            estabelecimentoSaude.setLongitude(json.getDouble("longitude"));
//            return estabelecimentoSaude;
//        } catch (Exception e) {
//            Log.d(TAG, "UtilError converting from json", e);
//            return null;
//        }
    }
}