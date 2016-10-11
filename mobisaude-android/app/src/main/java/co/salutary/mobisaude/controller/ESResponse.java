package co.salutary.mobisaude.controller;

public class ESResponse {

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
