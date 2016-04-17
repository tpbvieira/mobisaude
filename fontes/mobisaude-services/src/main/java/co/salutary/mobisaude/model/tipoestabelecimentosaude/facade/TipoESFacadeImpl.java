package co.salutary.mobisaude.model.tipoestabelecimentosaude.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.tipoestabelecimentosaude.TipoES;
import co.salutary.mobisaude.model.tipoestabelecimentosaude.dao.TipoESDao;

@Service("tipoESFacade")
@Transactional(readOnly = true)
public class TipoESFacadeImpl implements TipoESFacade {

	@Autowired
	private TipoESDao tipoESDao;

	public TipoES getById(int idTiposES) {
		return tipoESDao.getById(idTiposES);
	}

	public List<TipoES> list() {
		return tipoESDao.list();
	}
	
}