package co.salutary.mobisaude.services.restful.message.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	MobiSaude Services
 *	Entidade para dados do response do servico Consulta Ranking V2
 *
 */
@XmlRootElement
@XmlType(namespace = "mobile", name = "consultaRankingV2Response")
public class ConsultaRankingV2Response implements MobileResponse {
	/**
	 * Campo rankings
	 */
	private RankingV2[] rankings;
	/**
	 * Campo erro
	 */
	private String erro;

	/**
	 * Getter de rankings
	 * @return
	 */
	public RankingV2[] getRankings() {
		return rankings;
	}
	/**
	 * Setter de rankings
	 * @param rankings
	 */
	public void setRankings(RankingV2[] rankings) {
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
