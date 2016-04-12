package co.salutary.mobisaude.model.sugestao.facade;

import co.salutary.mobisaude.model.sugestao.Sugestao;

public interface SugestaoFacade {

	public void save(Sugestao sugestao);

	public Sugestao getSugestao(Integer idEstabelecimentoSaude, String email);

	public void removeSugestao(Integer idEstabelecimentoSaude, String email);
	
}