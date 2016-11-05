package co.salutary.mobisaude.model.avaliacao;

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
@Table(name = "\"tb_avaliacao\"", schema = "public")
public class Avaliacao implements java.io.Serializable {

	private static final long serialVersionUID = 5949891975169349167L;
	private int idAvaliacao;
	private int idES;
	private String email;
	private String avaliacao;
	private String titulo;
	private float rating;
	private Date date;

	public Avaliacao() {
	}

	public Avaliacao(int idES, String email, String titulo, String avaliacao, float rating) {		
		this.idES = idES;
		this.email = email;
		this.titulo = titulo;
		this.avaliacao = avaliacao;
		this.rating = rating;
		this.date = new Date();
	}
	

	@Id
	@Column(name = "\"nu_id_avaliacao\"", unique = true, nullable = false)
	@SequenceGenerator(name = "seqAvaliacao", sequenceName = "\"seq_avaliacao\"", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAvaliacao")
	public int getIdAvaliacao() {
		return this.idAvaliacao;
	}

	public void setIdAvaliacao(int idAvaliacao) {
		this.idAvaliacao = idAvaliacao;
	}

	@Column(name = "\"nu_id_cnes\"")
	public Integer getIdES() {
		return this.idES;
	}

	public void setIdES(Integer idES) {
		this.idES = idES;
	}

	@Column(name = "\"tx_email\"", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "\"tx_titulo\"", length = 100)
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	@Column(name = "\"tx_avaliacao\"", length = 200)
	public String getAvaliacao() {
		return this.avaliacao;
	}

	public void setAvaliacao(String avaliacao) {
		this.avaliacao = avaliacao;
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