package co.salutary.mobisaude.services.model.tokensessao.facade;

import co.salutary.mobisaude.services.model.tokensessao.TokenSessao;
/**
 * Interface implementada pelas fachadas de TokenSessao
 *
 */
public interface TokenSessaoFacade {
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
	public TokenSessao getToken(String token);
    /**
     * Remover um tokenSessao do banco
     * @param token
     */
	public void removeToken(String token);
}
