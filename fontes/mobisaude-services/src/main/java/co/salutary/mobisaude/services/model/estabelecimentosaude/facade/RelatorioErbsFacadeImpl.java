package co.salutary.mobisaude.services.model.estabelecimentosaude.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.services.model.estabelecimentosaude.RelatorioErbs;
import co.salutary.mobisaude.services.model.estabelecimentosaude.dao.RelatorioErbsDao;

/**
 * Implementacao de Facade para RelatorioErbs
 *
 */
@Service("relatorioErbsFacade")
@Transactional(readOnly = true)
public class RelatorioErbsFacadeImpl implements RelatorioErbsFacade {
	/**
	 * DAO
	 */
	@Autowired
	private RelatorioErbsDao relatorioErbsDao;
	/**
	 * Listar todos registros
	 * @return
	 */
	public List<RelatorioErbs> list() {
		return relatorioErbsDao.list();
	}
    /**
     * Listar os registros por uf, codMunicipioIbte e uma operadora
     * @param uf
     * @param codMunicipio
     * @param operadora
     * @return
     */
    public List<RelatorioErbs> listByUfMunicipioOperadora(String uf, String codMunicipio, String operadora) {
    	return relatorioErbsDao.listByUfMunicipioOperadora(uf, codMunicipio, operadora);
    }
    /**
     * Listar os registros por uf, codMunicipioIbte e uma lista de operadoras
     * @param uf
     * @param codMunicipio
     * @param operadoras
     * @return
     */
    public List<RelatorioErbs> listByUfMunicipioOperadoras(String uf, String codMunicipio, String[] operadoras) {
    	return relatorioErbsDao.listByUfMunicipioOperadoras(uf, codMunicipio, operadoras);
    }
}
