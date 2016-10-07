package co.salutary.mobisaude.model.tipogestao.dao;

import java.util.List;

import co.salutary.mobisaude.model.tipogestao.TipoGestao;

public interface TipoGestaoDao {

	public TipoGestao getById(int idTipoGestao);

	public List<TipoGestao> list();

}