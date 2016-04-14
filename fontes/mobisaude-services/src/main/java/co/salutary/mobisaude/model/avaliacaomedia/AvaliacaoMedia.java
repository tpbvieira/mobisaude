package co.salutary.mobisaude.model.avaliacaomedia;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "\"tb_avaliacao_media\"", schema = "public")
public class AvaliacaoMedia implements java.io.Serializable {

	private static final long serialVersionUID = 5949891975169349167L;
	private int idAvaliacaoMedia;
	private int idEstabelecimentoSaude;
	private float rating; 
	private Date date;

	public AvaliacaoMedia() {
	}

	public AvaliacaoMedia(int idEstabelecimentoSaude, float rating) {		
		this.idEstabelecimentoSaude = idEstabelecimentoSaude;
		this.rating = rating;
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		this.date = calendar.getTime();
	}

	@Id
	@Column(name = "\"nu_id_avaliacao_media\"", unique = true, nullable = false)
	@SequenceGenerator(name = "seqAvaliacao_media", sequenceName = "\"seq_avaliacao_media\"", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAvaliacao_media")
	public int getIdAvaliacaoMedia() {
		return this.idAvaliacaoMedia;
	}

	public void setIdAvaliacaoMedia(int idAvaliacaoMedia) {
		this.idAvaliacaoMedia = idAvaliacaoMedia;
	}

	@Column(name = "\"nu_id_cnes\"")
	public Integer getIdEstabelecimentoSaude() {
		return this.idEstabelecimentoSaude;
	}

	public void setIdEstabelecimentoSaude(Integer idEstabelecimentoSaude) {
		this.idEstabelecimentoSaude = idEstabelecimentoSaude;
	}

	@Column(name = "\"nu_rating\"")
	public float getRating() {
		return this.rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"dh_date\"", nullable = false, length = 29)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}