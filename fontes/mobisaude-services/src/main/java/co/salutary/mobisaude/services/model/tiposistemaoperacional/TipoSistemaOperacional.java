package co.salutary.mobisaude.services.model.tiposistemaoperacional;

// Generated 27/10/2013 21:01:16 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidade TipoSistemaOperacional
 */
@Entity
@Table(name = "\"tb_tipo_sistema_operacional\"", schema = "public")
public class TipoSistemaOperacional implements java.io.Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5949891975169349167L;
	/**
	 * Campo idTipoSistemaOperacional
	 */
	private int idTipoSistemaOperacional;
	/**
	 * Campo descricao
	 */
	private String descricao;

	/**
	 * Construtor sem parametros
	 */
	public TipoSistemaOperacional() {
	}

	/**
	 * Construtor
	 * @param idTipoSistemaOperacional
	 */
	public TipoSistemaOperacional(int idTipoSistemaOperacional) {
		this.idTipoSistemaOperacional = idTipoSistemaOperacional;
	}

	/**
	 * Getter de idTipoSistemaOperacional
	 * @return
	 */
	@Id
	@Column(name = "\"nu_id_tipo_sistema_operacional\"", unique = true, nullable = false)
	public int getIdTipoSistemaOperacional() {
		return this.idTipoSistemaOperacional;
	}

	/**
	 * Setter de idTipoSistemaOperacional
	 * @param idTipoSistemaOperacional
	 */
	public void setIdTipoSistemaOperacional(int idTipoSistemaOperacional) {
		this.idTipoSistemaOperacional = idTipoSistemaOperacional;
	}

	/**
	 * Getter de descricao
	 * @return
	 */
	@Column(name = "\"tx_descricao\"")
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Setter de descricao
	 * @param descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
