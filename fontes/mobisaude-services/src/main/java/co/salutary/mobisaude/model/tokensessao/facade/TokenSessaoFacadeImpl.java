package co.salutary.mobisaude.model.tokensessao.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.tokensessao.TokenSessao;
import co.salutary.mobisaude.model.tokensessao.dao.TokenSessaoDao;

/**
 * Implementacao de Facade para TokenSessao
 *
 */
@Service("tokenSessaoFacade")
@Transactional(readOnly = true)
public class TokenSessaoFacadeImpl implements TokenSessaoFacade {
	/**
	 * DAO
	 */
	@Autowired
	private TokenSessaoDao tokenSessaoDao;
	/**
	 * Salvar um objeto
	 * @param tokenSessao
	 */
	@Transactional(readOnly = false)
	public void save(TokenSessao tokenSessao) {
		tokenSessaoDao.save(tokenSessao);
	}
    /**
     * Buscar um tokenSessao no banco
     * @param tokenSessao
     * @return
     */
	public TokenSessao getToken(String token) {
		return tokenSessaoDao.getToken(token);
	}
    /**
     * Remover um tokenSessao do banco
     * @param token
     */
	public void removeToken(String token) {
		tokenSessaoDao.removeToken(token);
	}
}
