package co.salutary.mobisaude.model.relatorioranking;

import java.io.Serializable;

/**
 * Classe que representa a PK de RelatorioRanking
 *
 */
public class RelatorioRankingPK implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3631257612784403809L;
	/**
	 * Campo codMunicipioIbge
	 */
    protected String codMunicipioIbge;
    /**
     * Campo prestadora
     */
    protected String prestadora;

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
	 * Getter de prestadora
	 * @return
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
	 * Construtor sem parametros
	 */
	public RelatorioRankingPK() {}

	/**
	 * Construtor
	 * @param codMunicipioIbge
	 * @param prestadora
	 */
    public RelatorioRankingPK(String codMunicipioIbge, String prestadora) {
        this.codMunicipioIbge = codMunicipioIbge;
        this.prestadora = prestadora;
    }
    /**
     * Implementacao de equals
     */
    @Override
    public boolean equals(Object obj) {
    	if (!(obj instanceof RelatorioRankingPK)) {
    		return false;
    	}
    	RelatorioRankingPK other = (RelatorioRankingPK)obj;
    	
    	if (this.codMunicipioIbge.equals(other.codMunicipioIbge) && 
    			this.prestadora.equals(other.prestadora)) {
    		return true;
    	}
    	return false;
    }
    /**
     * Implementacao de hashCode
     */
    @Override
    public int hashCode() {
    	int hashCode = 3 * this.codMunicipioIbge.hashCode() + 7 * this.prestadora.hashCode(); 
    	return hashCode;
    }
}