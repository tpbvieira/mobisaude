package co.salutary.mobisaude.model.tokensessao.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.tokensessao.TokenSessao;
import co.salutary.mobisaude.model.tokensessao.dao.TokenSessaoDao;

@Service("tokenSessaoFacade")
@Transactional(readOnly = true)
public class TokenSessaoFacadeImpl implements TokenSessaoFacade {

	@Autowired
	private TokenSessaoDao tokenSessaoDao;

	@Transactional(readOnly = false)
	public void save(TokenSessao tokenSessao) {
		tokenSessaoDao.save(tokenSessao);
	}

	public TokenSessao getToken(String token) {
		return tokenSessaoDao.getToken(token);
	}

	public void removeToken(String token) {
		tokenSessaoDao.removeToken(token);
	}
	
}