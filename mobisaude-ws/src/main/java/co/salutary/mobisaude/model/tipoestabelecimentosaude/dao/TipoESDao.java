package co.salutary.mobisaude.model.tipoestabelecimentosaude.dao;

import java.util.List;

import co.salutary.mobisaude.model.tipoestabelecimentosaude.TipoES;

public interface TipoESDao {

	public TipoES getById(int idTiposES);
	public List<TipoES> list();
	
}