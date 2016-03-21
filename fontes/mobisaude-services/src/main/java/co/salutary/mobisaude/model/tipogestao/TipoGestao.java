package co.salutary.mobisaude.model.tipogestao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidade TipoGestao
 */
@Entity
@Table(name = "\"tb_tipo_gestao\"", schema = "public")
public class TipoGestao implements java.io.Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5949891975169349167L;
	/**
	 * Campo idTipoGestao
	 */
	private short idTipoGestao;
	/**
	 * Campo nome
	 */
	private String nome;

	/**
	 * Construtor sem parametros
	 */
	public TipoGestao() {
	}

	/**
	 * Construtor
	 * @param idTipoGestao
	 */
	public TipoGestao(short idTipoGestao) {
		this.idTipoGestao = idTipoGestao;
	}

	/**
	 * Getter de idTipoGestao
	 * @return
	 */
	@Id
	@Column(name = "\"nu_id_tipo_gestao\"", unique = true, nullable = false)
	public short getIdTipoGestao() {
		return this.idTipoGestao;
	}

	/**
	 * Setter de idTipoGestao
	 * @param idTipoGestao
	 */
	public void setIdTipoGestao(short idTipoGestao) {
		this.idTipoGestao = idTipoGestao;
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