package co.salutary.mobisaude.model.operadora;

// Generated 27/10/2013 21:01:16 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidade Operadora
 */
@Entity
@Table(name = "\"tb_operadora\"", schema = "public")
public class Operadora implements java.io.Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5949891975169349167L;
	/**
	 * Campo idOperadora
	 */
	private int idOperadora;
	/**
	 * Campo codigo
	 */
	private String codigo;
	/**
	 * Campo nome
	 */
	private String nome;

	/**
	 * Construtor sem parametros
	 */
	public Operadora() {
	}

	/**
	 * Construtor
	 * @param idOperadora
	 */
	public Operadora(int idOperadora) {
		this.idOperadora = idOperadora;
	}

	/**
	 * Construtor
	 * @param idOperadora
	 * @param codigo
	 * @param nome
	 */
	public Operadora(int idOperadora, String codigo, String nome) {
		this.idOperadora = idOperadora;
		this.codigo = codigo;
		this.nome = nome;
	}

	/**
	 * Getter de idOperadora
	 * @return
	 */
	@Id
	@Column(name = "\"nu_id_operadora\"", unique = true, nullable = false)
	public int getIdOperadora() {
		return this.idOperadora;
	}

	/**
	 * Setter de idOperadora
	 * @param idOperadora
	 */
	public void setIdOperadora(int idOperadora) {
		this.idOperadora = idOperadora;
	}

	/**
	 * Getter de codigo
	 * @return
	 */
	@Column(name = "\"no_codigo\"", length = 40)
	public String getCodigo() {
		return this.codigo;
	}

	/**
	 * Setter de codigo
	 * @param codigo
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
