package co.salutary.mobisaude.services.model.configuracao.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.services.model.configuracao.Configuracao;
import co.salutary.mobisaude.services.model.configuracao.dao.ConfiguracaoDao;

/**
 * Implementacao de Facade para Configuracao
 *
 */
@Service("configuracaoFacade")
@Transactional(readOnly = true)
public class ConfiguracaoFacadeImpl implements ConfiguracaoFacade {
	/**
	 * DAO
	 */
	@Autowired
	private ConfiguracaoDao configuracaoDao;
    /**
     * Buscar uma configuracao no banco
     * @param configuracao
     * @return
     */
	public Configuracao getConfiguracao(String chave) {
		return configuracaoDao.getConfiguracao(chave);
	}
}
