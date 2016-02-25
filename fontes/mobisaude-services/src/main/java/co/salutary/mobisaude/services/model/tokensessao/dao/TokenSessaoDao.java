package co.salutary.mobisaude.services.model.tokensessao.dao;

import co.salutary.mobisaude.services.model.tokensessao.TokenSessao;
/**
 * Interface implementada pelos DAOs de TokenSessao
 *
 */
public interface TokenSessaoDao {
	/**
	 * Salvar um objeto
	 * @param tokenSessao
	 */
    public void save(TokenSessao tokenSessao);
    /**
     * Buscar um tokenSessao no banco
     * @param tokenSessao
     * @return
     */
    public TokenSessao getToken(String tokenSessao);
    /**
     * Remover um tokenSessao do banco
     * @param token
     */
    public void removeToken(String token);
}
