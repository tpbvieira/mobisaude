package co.salutary.mobisaude.model.tiposistemaoperacional.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.tiposistemaoperacional.TipoSistemaOperacional;
import co.salutary.mobisaude.model.tiposistemaoperacional.dao.TipoSistemaOperacionalDao;

@Service("tipoSistemaOperacionalFacade")
@Transactional(readOnly = true)
public class TipoSistemaOperacionalFacadeImpl implements TipoSistemaOperacionalFacade {

	@Autowired
	private TipoSistemaOperacionalDao tipoSistemaOperacionalDao;

	public TipoSistemaOperacional getById(int idTipoSistemaOperacional) {
		return tipoSistemaOperacionalDao.getById(idTipoSistemaOperacional);
	}

	public List<TipoSistemaOperacional> list() {
		return tipoSistemaOperacionalDao.list();
	}

}