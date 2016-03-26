package co.salutary.mobisaude.model.tiposistemaoperacional.dao;

import java.util.List;

import co.salutary.mobisaude.model.tiposistemaoperacional.TipoSistemaOperacional;

public interface TipoSistemaOperacionalDao {

	public TipoSistemaOperacional getById(int idTipoSistemaOperacional);

	public List<TipoSistemaOperacional> list();

}