package co.salutary.mobisaude.services.model.relatorioranking.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.services.model.relatorioranking.RelatorioRanking;
import co.salutary.mobisaude.services.model.relatorioranking.dao.RelatorioRankingDao;

/**
 * Implementacao de Facade para RelatorioRanking
 *
 */
@Service("relatorioRankingFacade")
@Transactional(readOnly = true)
public class RelatorioRankingFacadeImpl implements RelatorioRankingFacade {
	/**
	 * DAO
	 */
	@Autowired
	private RelatorioRankingDao relatorioRankingDao;
	/**
	 * Listar todos registros
	 * @return
	 */
	public List<RelatorioRanking> list() {
		return relatorioRankingDao.list();
	}
    /**
     * Listar os registros por uf e codMunicipioIbge
     * @param uf
     * @param codMunicipio
     * @return
     */
	public List<RelatorioRanking> listByUfMunicipio(String uf, String codMunicipio) {
		return relatorioRankingDao.listByUfMunicipio(uf, codMunicipio);
	}
}
