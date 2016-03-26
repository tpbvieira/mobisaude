package co.salutary.mobisaude.model.tokensessao;
// Generated 27/10/2013 21:01:16 by Hibernate Tools 4.0.0

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
@Table(name = "\"tb_token_sessao\"", schema = "public")
public class TokenSessao implements java.io.Serializable {

	private static final long serialVersionUID = 5949891975169349167L;
	private int idTokenSessao;
	private String token;
	private Date validade;

	public TokenSessao() {
	}

	public TokenSessao(int idTokenSessao, Date validade) {
		this.idTokenSessao = idTokenSessao;
		this.validade = validade;
	}

	public TokenSessao(int idTokenSessao, String token, Date validade) {
		this.idTokenSessao = idTokenSessao;
		this.token = token;
		this.validade = validade;
	}

	@Id
	@Column(name = "\"nu_id_token_sessao\"", unique = true, nullable = false)
	@SequenceGenerator(name = "seqTokenSessao", sequenceName = "\"seq_token_sessao\"", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTokenSessao")
	public int getIdTokenSessao() {
		return this.idTokenSessao;
	}

	public void setIdTokenSessao(int idTokenSessao) {
		this.idTokenSessao = idTokenSessao;
	}

	@Column(name = "\"tx_token\"", length = 100)
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"dh_validade\"", nullable = false, length = 29)
	public Date getValidade() {
		return this.validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
	}

}