package co.salutary.mobisaude.services.model.estabelecimentosaude.dao;

import java.util.List;

import co.salutary.mobisaude.services.model.estabelecimentosaude.RelatorioErbs;

/**
 * Interface implementada pelos DAOs de RelatorioErbs 
 *
 */
public interface RelatorioErbsDao {
	/**
	 * Listar todos registros
	 * @return
	 */
    public List<RelatorioErbs> list();
    /**
     * Listar os registros por uf, codMunicipioIbte e uma operadora
     * @param uf
     * @param codMunicipio
     * @param operadora
     * @return
     */
    public List<RelatorioErbs> listByUfMunicipioOperadora(String uf, String codMunicipio, String operadora);
    /**
     * Listar os registros por uf, codMunicipioIbte e uma lista de operadoras
     * @param uf
     * @param codMunicipio
     * @param operadoras
     * @return
     */
    public List<RelatorioErbs> listByUfMunicipioOperadoras(String uf, String codMunicipio, String[] operadoras);
}
