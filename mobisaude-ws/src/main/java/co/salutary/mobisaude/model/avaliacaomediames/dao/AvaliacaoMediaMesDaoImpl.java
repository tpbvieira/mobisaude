package co.salutary.mobisaude.model.avaliacaomediames.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.salutary.mobisaude.model.avaliacaomediames.AvaliacaoMediaMes;

@Repository("avaliacaoMediaDao")
public class AvaliacaoMediaMesDaoImpl implements AvaliacaoMediaMesDao {

	@PersistenceContext
	private EntityManager em;

	public void save(AvaliacaoMediaMes avaliacaoMedia)  {
		em.merge(avaliacaoMedia);			
	}

	@SuppressWarnings("unchecked")
	public AvaliacaoMediaMes getByIdESDate(Integer idES, Date date) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select s from AvaliacaoMediaMes s ");
		queryString.append("where s.idES = :idES "
				+ "and s.date = :date");
		Query query = em.createQuery(queryString.toString());	

		if (idES != null) {
			query.setParameter("idES", idES);
		}
		if (date != null) {
			query.setParameter("date", date);
		}

		List<AvaliacaoMediaMes> result = query.getResultList();		
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	public void removeByIdESDate(Integer idES, Date date) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("delete from AvaliacaoMediaMes s ");
		queryString.append("where s.idES = :idES "
				+ "and s.date = :date");

		Query query = em.createQuery(queryString.toString());

		if (idES != null) {
			query.setParameter("idES", idES);
		}
		if (date != null) {
			query.setParameter("date", date);
		}

		query.executeUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AvaliacaoMediaMes> listByIdES(Integer idES) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from AvaliacaoMediaMes a ");
		queryStr.append("where a.idES = :idES ");	

		Query query = em.createQuery(queryStr.toString());
		if (idES != null ) {
			query.setParameter("idES", idES);
		}

		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AvaliacaoMediaMes> listByIdESDate(Integer idES, Date date) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("from AvaliacaoMediaMes a ");
		queryStr.append("where a.idES = :idES"
				+ "and s.date = :date");	

		Query query = em.createQuery(queryStr.toString());
		if (idES != null ) {
			query.setParameter("idES", idES);
		}
		if (date != null) {
			query.setParameter("date", date);
		}

		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AvaliacaoMediaMes> listAvaliacaoBySiglaUF(String siglaUF) {

		StringBuffer queryStr = new StringBuffer();
		queryStr.append("select av.rating, count(av.rating)");
		queryStr.append(" from Avaliacao av, EstabelecimentoSaude es");
		queryStr.append(" where av.idES = es.idES");
		queryStr.append(" and es.uf = :siglaUF");
		queryStr.append(" group by av.rating");	

		Query query = em.createQuery(queryStr.toString());
		if (siglaUF != null ) {
			query.setParameter("siglaUF", siglaUF);
		}

		// initiate all rates with 0
		List<AvaliacaoMediaMes> avaliacoes = new ArrayList<>();
		for(int i = 0; i < 6; i++){
			AvaliacaoMediaMes avTmp = new AvaliacaoMediaMes();
			avTmp.setIdES(0);
			avTmp.setRating(0f);
			avTmp.setCount(0);
			avaliacoes.add(avTmp);
		}

		List <Object[]> result = query.getResultList();
		int i = 0;
		for(Object[] row: result){
			if(row.length > 1){
				
				Float rate = (Float)row[0];
				Integer count = ((Long)row[1]).intValue();
				
				AvaliacaoMediaMes avaliacao = avaliacoes.get(i);
				avaliacao.setRating(rate);
				avaliacao.setCount(count);
			}
			i++;
		}

		return avaliacoes;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AvaliacaoMediaMes> listAvaliacaoByIdMunicipio(String idMunicipio) {

		StringBuffer queryStr = new StringBuffer();
		queryStr.append("select av.rating, count(av.rating)");
		queryStr.append(" from Avaliacao av");
		queryStr.append(" join EstabelecimentoSaude es on av.idES = es.idES");
		queryStr.append(" where es.idMunicipio = :idMunicipio");
		queryStr.append(" group by av.rating");	

		Query query = em.createQuery(queryStr.toString());
		if (idMunicipio != null ) {
			query.setParameter("idMunicipio", idMunicipio);
		}

		// initiate all rates with 0
		List<AvaliacaoMediaMes> avaliacoes = new ArrayList<>();
		for(int i = 0; i < 8; i++){
//			avaliacoes.add(new AvaliacaoMediaMes(i,0));
		}

		List <Object[]> result = query.getResultList();
		for(Object[] row: result){
			if(row.length > 1){
				AvaliacaoMediaMes av = avaliacoes.get((Integer)row[0]);
				av.setRating(Float.valueOf((Integer)row[1]));
			}
		}

		return avaliacoes;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AvaliacaoMediaMes> listAvaliacaoByIdTipoES(String idTipoEstabelecimento) {

		StringBuffer queryStr = new StringBuffer();
		queryStr.append("select av.rating, count(av.rating)");
		queryStr.append(" from Avaliacao av");
		queryStr.append(" join EstabelecimentoSaude es on av.idES = es.idES");
		queryStr.append(" where es.uf = :siglaUF");
		queryStr.append(" group by av.rating");	

		//		Query query = em.createQuery(queryStr.toString());
		//		if (siglaUF != null ) {
		//			query.setParameter("siglaUF", siglaUF);
		//		}

		// initiate all rates with 0
		List<AvaliacaoMediaMes> avaliacoes = new ArrayList<>();
		for(int i = 0; i < 8; i++){
//			avaliacoes.add(new AvaliacaoMediaMes(i,0));
		}
		//		
		//		List <Object[]> result = query.getResultList();
		//		for(Object[] row: result){
		//			if(row.length > 1){
		//				AvaliacaoMediaMes av = avaliacoes.get((Integer)row[0]);
		//				av.setRating(Float.valueOf((Integer)row[1]));
		//			}
		//		}

		return avaliacoes;
	}

}