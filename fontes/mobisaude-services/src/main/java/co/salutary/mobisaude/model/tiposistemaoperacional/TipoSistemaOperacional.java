package co.salutary.mobisaude.model.tiposistemaoperacional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"tb_tipo_sistema_operacional\"", schema = "public")
public class TipoSistemaOperacional implements java.io.Serializable {

	private static final long serialVersionUID = 5949891975169349167L;
	private int idTipoSistemaOperacional;
	private String descricao;

	public TipoSistemaOperacional() {
	}

	public TipoSistemaOperacional(int idTipoSistemaOperacional) {
		this.idTipoSistemaOperacional = idTipoSistemaOperacional;
	}

	@Id
	@Column(name = "\"nu_id_tipo_sistema_operacional\"", unique = true, nullable = false)
	public int getIdTipoSistemaOperacional() {
		return this.idTipoSistemaOperacional;
	}

	public void setIdTipoSistemaOperacional(int idTipoSistemaOperacional) {
		this.idTipoSistemaOperacional = idTipoSistemaOperacional;
	}

	@Column(name = "\"tx_descricao\"")
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}