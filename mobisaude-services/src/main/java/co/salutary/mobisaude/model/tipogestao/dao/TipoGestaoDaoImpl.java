package co.salutary.mobisaude.model.tipogestao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.model.tipogestao.TipoGestao;

@Repository("tipoGestaoDao")
public class TipoGestaoDaoImpl implements TipoGestaoDao {

	@PersistenceContext
	private EntityManager entityMngr;

	public TipoGestao getById(int idTipoGestao) {
		return entityMngr.find(TipoGestao.class, idTipoGestao);
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoGestao> list() {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("select tg from TipoGestao tg ");
		Query query = entityMngr.createQuery(queryStr.toString());	
		return query.getResultList();
	}
}