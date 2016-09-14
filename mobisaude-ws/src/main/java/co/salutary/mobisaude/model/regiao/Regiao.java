package co.salutary.mobisaude.model.regiao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidade Regiao
 */
@Entity
@Table(name = "\"tb_regiao\"", schema = "public")
public class Regiao implements java.io.Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5949891975169349167L;
	/**
	 * Campo idRegiao
	 */
	private short idRegiao;
	/**
	 * Campo nome
	 */
	private String nome;

	/**
	 * Construtor sem parametros
	 */
	public Regiao() {
	}

	/**
	 * Construtor
	 * @param idRegiao
	 */
	public Regiao(short idRegiao) {
		this.idRegiao = idRegiao;
	}

	/**
	 * Getter de idRegiao
	 * @return
	 */
	@Id
	@Column(name = "\"nu_id_regiao\"", unique = true, nullable = false)
	public short getIdRegiao() {
		return this.idRegiao;
	}

	/**
	 * Setter de idRegiao
	 * @param idRegiao
	 */
	public void setIdRegiao(short idRegiao) {
		this.idRegiao = idRegiao;
	}

	/**
	 * Getter de nome
	 * @return
	 */
	@Column(name = "\"tx_nome\"")
	public String getNome() {
		return this.nome;
	}

	/**
	 * Setter de nome
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
}