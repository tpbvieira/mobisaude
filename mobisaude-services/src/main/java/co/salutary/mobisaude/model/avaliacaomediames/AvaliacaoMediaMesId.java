package co.salutary.mobisaude.model.avaliacaomediames;

import java.util.Date;

public class AvaliacaoMediaMesId implements java.io.Serializable {

	private static final long serialVersionUID = 5949891975169549167L;

	private int idES; 
	private Date date;
	public int getIdES() {
		return idES;
	}
	public void setIdES(int idES) {
		this.idES = idES;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
