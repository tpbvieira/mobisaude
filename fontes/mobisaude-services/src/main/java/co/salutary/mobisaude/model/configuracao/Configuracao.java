package co.salutary.mobisaude.model.configuracao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "\"tb_configuracao\"", schema = "public")
public class Configuracao implements java.io.Serializable {

	private static final long serialVersionUID = 5949891975169349167L;
	private int idConfiguracao;
	private String chave;
	private String valor;

	public Configuracao() {
	}

	public Configuracao(int idConfiguracao, String valor) {
		this.idConfiguracao = idConfiguracao;
		this.valor = valor;
	}

	@Id
	@Column(name = "\"nu_id_configuracao\"", unique = true, nullable = false)
	@SequenceGenerator(name = "seqConfiguracao", sequenceName = "\"seq_configuracao\"", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqConfiguracao")
	public int getIdConfiguracao() {
		return this.idConfiguracao;
	}

	public void setIdConfiguracao(int idConfiguracao) {
		this.idConfiguracao = idConfiguracao;
	}

	@Column(name = "\"no_chave\"", length = 20)
	public String getChave() {
		return this.chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	@Column(name = "\"tx_valor\"", length = 100)
	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}