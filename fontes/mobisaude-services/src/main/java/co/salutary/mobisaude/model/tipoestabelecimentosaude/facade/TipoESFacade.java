package co.salutary.mobisaude.model.tipoestabelecimentosaude.facade;

import java.util.List;

import co.salutary.mobisaude.model.tipoestabelecimentosaude.TipoES;

public interface TipoESFacade {

	public TipoES getById(int idTiposES);
	public List<TipoES> list();
	
}