package co.salutary.mobisaude.services.model.configuracao.facade;

import co.salutary.mobisaude.services.model.configuracao.Configuracao;
/**
 * Interface implementada pelas fachadas de Configuracao
 *
 */
public interface ConfiguracaoFacade {
    /**
     * Buscar uma configuracao no banco
     * @param chave
     * @return
     */
	public Configuracao getConfiguracao(String chave);
}
