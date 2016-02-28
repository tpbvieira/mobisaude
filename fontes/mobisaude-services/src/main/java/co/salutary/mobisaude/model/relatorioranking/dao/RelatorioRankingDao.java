package co.salutary.mobisaude.model.relatorioranking.dao;

import java.util.List;

import co.salutary.mobisaude.model.relatorioranking.RelatorioRanking;

/**
 * Interface implementada pelos DAOs de RelatorioRanking
 *
 */
public interface RelatorioRankingDao {
	/**
	 * Listar todos registros
	 * @return
	 */
    public List<RelatorioRanking> list();
    /**
     * Listar os registros por uf e codMunicipioIbge
     * @param uf
     * @param codMunicipio
     * @return
     */
    public List<RelatorioRanking> listByUfMunicipio(String uf, String codMunicipio);
}
