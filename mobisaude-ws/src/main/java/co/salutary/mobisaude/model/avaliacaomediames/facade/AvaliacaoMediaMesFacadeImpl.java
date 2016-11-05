package co.salutary.mobisaude.model.avaliacaomediames.facade;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.salutary.mobisaude.model.avaliacaomediames.AvaliacaoMediaMes;
import co.salutary.mobisaude.model.avaliacaomediames.dao.AvaliacaoMediaMesDao;

@Service("avaliacaoMediaMesFacade")
@Transactional(readOnly = true)
public class AvaliacaoMediaMesFacadeImpl implements AvaliacaoMediaMesFacade {

	@Autowired
	private AvaliacaoMediaMesDao avaliacaoMediaDao;

	@Transactional(readOnly = false)
	public void save(AvaliacaoMediaMes avaliacaoMedia) {
		avaliacaoMediaDao.save(avaliacaoMedia);
	}

	public AvaliacaoMediaMes getByIdESDate(Integer idES, Date date) {
		return avaliacaoMediaDao.getByIdESDate(idES,date);
	}

	public void removeByIdESDate(Integer idES, Date date) {
		avaliacaoMediaDao.removeByIdESDate(idES,date);
	}
	
	public List<AvaliacaoMediaMes> listByIdES(Integer idES) {
    	return avaliacaoMediaDao.listByIdES(idES);
    }
	
	public List<AvaliacaoMediaMes> listByIdESDate(Integer idES, Date date) {
    	return avaliacaoMediaDao.listByIdESDate(idES, date);
    }
	
	public List<AvaliacaoMediaMes> listAvaliacaoBySiglaUF(String siglaUF){
		return avaliacaoMediaDao.listAvaliacaoBySiglaUF(siglaUF);
	}
	
    public List<AvaliacaoMediaMes> listAvaliacaoByIdMunicipio(String idMunicipio){
    	return avaliacaoMediaDao.listAvaliacaoByIdMunicipio(idMunicipio);
    }
    
    public List<AvaliacaoMediaMes> listAvaliacaoByIdTipoES(String idTipoEstabelecimento){
    	return avaliacaoMediaDao.listAvaliacaoByIdTipoES(idTipoEstabelecimento);
    }
	
}