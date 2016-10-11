package co.salutary.mobisaude.model.tokensessao.dao;

import co.salutary.mobisaude.model.tokensessao.TokenSessao;

public interface TokenSessaoDao {

    public void save(TokenSessao tokenSessao);

    public TokenSessao getToken(String tokenSessao);

    public void removeToken(String token);

}