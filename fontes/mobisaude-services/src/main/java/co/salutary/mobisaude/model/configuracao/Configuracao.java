package co.salutary.mobisaude.model.configuracao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entidade Configuracao
 */
@Entity
@Table(name = "\"tb_configuracao\"", schema = "public")
public class Configuracao implements java.io.Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5949891975169349167L;
	/**
	 * Campo idConfiguracao
	 */
	private int idConfiguracao;
	/**
	 * Campo chave
	 */
	private String chave;
	/**
	 * Campo valor
	 */
	private String valor;

	/**
	 * Construtor sem parametros
	 */
	public Configuracao() {
	}

	/**
	 * Construtor
	 * @param idConfiguracao
	 * @param valor
	 */
	public Configuracao(int idConfiguracao, String valor) {
		this.idConfiguracao = idConfiguracao;
		this.valor = valor;
	}

	/**
	 * Getter de idConfiguracao
	 * @return
	 */
	@Id
	@Column(name = "\"nu_id_configuracao\"", unique = true, nullable = false)
	@SequenceGenerator(name = "seqConfiguracao", sequenceName = "\"seq_configuracao\"", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqConfiguracao")
	public int getIdConfiguracao() {
		return this.idConfiguracao;
	}

	/**
	 * Setter de idConfiguracao
	 * @param idConfiguracao
	 */
	public void setIdConfiguracao(int idConfiguracao) {
		this.idConfiguracao = idConfiguracao;
	}

	/**
	 * Getter de chave
	 * @return
	 */
	@Column(name = "\"no_chave\"", length = 20)
	public String getChave() {
		return this.chave;
	}

	/**
	 * Setter de chave
	 * @param chave
	 */
	public void setChave(String chave) {
		this.chave = chave;
	}

	/**
	 * Getter de valor
	 * @return
	 */
	@Column(name = "\"tx_valor\"", length = 100)
	public String getValor() {
		return this.valor;
	}

	/**
	 * Setter de valor
	 * @param valor
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

}
