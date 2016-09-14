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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + idES;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AvaliacaoMediaMesId other = (AvaliacaoMediaMesId) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (idES != other.idES)
			return false;
		return true;
	}
	
}
