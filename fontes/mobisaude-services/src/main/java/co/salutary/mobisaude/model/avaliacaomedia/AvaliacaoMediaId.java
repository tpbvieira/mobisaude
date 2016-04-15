package co.salutary.mobisaude.model.avaliacaomedia;

import java.util.Date;

public class AvaliacaoMediaId implements java.io.Serializable {

	private static final long serialVersionUID = 5949891975169549167L;

	private int idEstabelecimentoSaude; 
	private Date date;
	public int getIdEstabelecimentoSaude() {
		return idEstabelecimentoSaude;
	}
	public void setIdEstabelecimentoSaude(int idEstabelecimentoSaude) {
		this.idEstabelecimentoSaude = idEstabelecimentoSaude;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
