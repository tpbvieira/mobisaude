package co.salutary.mobisaude.services.model.relatorioranking.facade;

import java.util.List;

import co.salutary.mobisaude.services.model.relatorioranking.RelatorioRanking;
/**
 * Interface implementada pelas fachadas de RelatorioRanking
 *
 */
public interface RelatorioRankingFacade {
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
