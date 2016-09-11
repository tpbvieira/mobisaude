package co.salutary.mobisaude.model.tipogestao.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.tipogestao.TipoGestao;
import co.salutary.mobisaude.model.tipogestao.dao.TipoGestaoDao;

@Service("tipoGestaoFacade")
@Transactional(readOnly = true)
public class TipoGestaoFacadeImpl implements TipoGestaoFacade {
	
	@Autowired
	private TipoGestaoDao tipoGestaoDao;

	public TipoGestao getById(int idTipoGestao) {
		return tipoGestaoDao.getById(idTipoGestao);
	}

	public List<TipoGestao> list() {
		return tipoGestaoDao.list();
	}

}