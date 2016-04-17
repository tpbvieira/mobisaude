package co.salutary.mobisaude.model.sugestao;

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
@Table(name = "\"tb_sugestao\"", schema = "public")
public class Sugestao implements java.io.Serializable {

	private static final long serialVersionUID = 5949891975169349167L;
	private int idSugestao;
	private int idES;
	private String email;
	private String sugestao;
	private Date date;

	public Sugestao() {
	}

	public Sugestao(int idSugestao, int idES, String email, String sugestao, Date date) {
		this.idSugestao = idSugestao;
		this.idES = idES;
		this.email = email;
		this.sugestao = sugestao;
		this.date = date;
	}
	
	public Sugestao(int idES, String email, String sugestao, Date date) {
		this.idES = idES;
		this.email = email;
		this.sugestao = sugestao;
		this.date = date;
	}

	@Id
	@Column(name = "\"nu_id_sugestao\"", unique = true, nullable = false)
	@SequenceGenerator(name = "seqSugestao", sequenceName = "\"seq_sugestao\"", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqSugestao")
	public int getIdSugestao() {
		return this.idSugestao;
	}

	public void setIdSugestao(int idSugestao) {
		this.idSugestao = idSugestao;
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

	@Column(name = "\"tx_sugestao\"", length = 200)
	public String getSugestao() {
		return this.sugestao;
	}

	public void setSugestao(String sugestao) {
		this.sugestao = sugestao;
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