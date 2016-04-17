package co.salutary.mobisaude.model.avaliacaomediames;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@IdClass(AvaliacaoMediaMesId.class)
@Table(name = "\"tb_avaliacao_media\"", schema = "public")
public class AvaliacaoMediaMes implements java.io.Serializable {

	private static final long serialVersionUID = 5949891975169349167L;
	private int idES;
	private float rating; 
	private Date date;

	public AvaliacaoMediaMes() {
	}

	public AvaliacaoMediaMes(int idES, float rating) {		
		this.idES = idES;
		this.rating = rating;
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		calendar.clear();
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		this.date = calendar.getTime();
	}
	
	public AvaliacaoMediaMes(int idES, float rating, Date date) {		
		this.idES = idES;
		this.rating = rating;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		calendar.clear();
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		this.date = calendar.getTime();
	}

	@Id
	@Column(name = "\"nu_id_cnes\"")
	public Integer getIdES() {
		return this.idES;
	}

	public void setIdES(Integer idES) {
		this.idES = idES;
	}

	@Column(name = "\"nu_rating\"")
	public float getRating() {
		return this.rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	@Id
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"dh_date\"", nullable = false, length = 29)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}