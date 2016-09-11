package co.salutary.mobisaude.model.tiposistemaoperacional.facade;

import java.util.List;

import co.salutary.mobisaude.model.tiposistemaoperacional.TipoSistemaOperacional;

public interface TipoSistemaOperacionalFacade {

	public TipoSistemaOperacional getById(int idTipoSistemaOperacional);

	public List<TipoSistemaOperacional> list();

}