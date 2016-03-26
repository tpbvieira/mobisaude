package co.salutary.mobisaude.model.tipogestao.facade;

import java.util.List;

import co.salutary.mobisaude.model.tipogestao.TipoGestao;

public interface TipoGestaoFacade {

	public TipoGestao getById(int idTipoGestao);

	public List<TipoGestao> list();

}