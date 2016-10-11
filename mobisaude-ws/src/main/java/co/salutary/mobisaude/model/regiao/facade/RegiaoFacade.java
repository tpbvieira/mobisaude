package co.salutary.mobisaude.model.regiao.facade;

import java.util.List;

import co.salutary.mobisaude.model.regiao.Regiao;

public interface RegiaoFacade {

	public Regiao getById(int idRegiao);

	public List<Regiao> list();
	
}