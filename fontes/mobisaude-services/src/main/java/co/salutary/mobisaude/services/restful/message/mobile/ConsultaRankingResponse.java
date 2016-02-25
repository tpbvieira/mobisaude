package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do response do servico Consulta Ranking
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "consultaRankingResponse")
public class ConsultaRankingResponse implements MobileResponse {
	/**
	 * Campo rankings
	 */
	private Ranking[] rankings;
	/**
	 * Campo erro
	 */
	private String erro;

	/**
	 * Getter de rankings
	 * @return
	 */
	public Ranking[] getRankings() {
		return rankings;
	}
	/**
	 * Setter de rankings
	 * @param rankings
	 */
	public void setRankings(Ranking[] rankings) {
		this.rankings = rankings.clone();
	}
	/**
	 * Getter de erro
	 * @return
	 */
	public String getErro() {
		return erro;
	}
	/**
	 * Setter de erro
	 * @param erro
	 */
	public void setErro(String erro) {
		this.erro = erro;
	}

}
