package co.salutary.mobisaude.model.tokensessao.facade;

import co.salutary.mobisaude.model.tokensessao.TokenSessao;

public interface TokenSessaoFacade {

	public void save(TokenSessao tokenSessao);

	public TokenSessao getToken(String token);

	public void removeToken(String token);
	
}