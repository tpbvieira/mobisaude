package co.salutary.mobisaude.services.model.tipoestabelecimentosaude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidade TipoEstabelecimentoSaude
 */
@Entity
@Table(name = "\"tb_tipo_estabelecimento_saude\"", schema = "public")
public class TipoEstabelecimentoSaude implements java.io.Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5949891975169349167L;
	/**
	 * Campo idTipoEstabelecimentoSaude
	 */
	private int idTipoEstabelecimentoSaude;
	/**
	 * Campo nome
	 */
	private String nome;

	/**
	 * Construtor sem parametros
	 */
	public TipoEstabelecimentoSaude() {
	}

	/**
	 * Construtor
	 * @param idTipoEstabelecimentoSaude
	 */
	public TipoEstabelecimentoSaude(int idTipoEstabelecimentoSaude) {
		this.idTipoEstabelecimentoSaude = idTipoEstabelecimentoSaude;
	}

	/**
	 * Getter de idTipoEstabelecimentoSaude
	 * @return
	 */
	@Id
	@Column(name = "\"nu_id_tipo_estabelecimento_saude\"", unique = true, nullable = false)
	public int getIdTipoEstabelecimentoSaude() {
		return this.idTipoEstabelecimentoSaude;
	}

	/**
	 * Setter de idTipoEstabelecimentoSaude
	 * @param idTipoEstabelecimentoSaude
	 */
	public void setIdTipoEstabelecimentoSaude(int idTipoEstabelecimentoSaude) {
		this.idTipoEstabelecimentoSaude = idTipoEstabelecimentoSaude;
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
