package co.salutary.mobisaude.services.model.configuracao.dao;

import co.salutary.mobisaude.services.model.configuracao.Configuracao;
/**
 * Interface implementada pelos DAOs de TokenSessao
 *
 */
public interface ConfiguracaoDao {
    /**
     * Buscar uma configuracao no banco
     * @param chave
     * @return
     */
    public Configuracao getConfiguracao(String chave);
}
