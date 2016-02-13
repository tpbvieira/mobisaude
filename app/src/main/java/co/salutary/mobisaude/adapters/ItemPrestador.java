package co.salutary.mobisaude.adapters;

import java.util.Locale;

import android.content.Context;
import android.graphics.drawable.Drawable;
import co.salutary.mobisaude.R;
/**************************************
 * Projeto Anatel
 * 
 * @author Digitrack
 * 
 * @param Email alexlova@gmail.com
 * @param Data 10/06/2014
 **************************************/
public class ItemPrestador {

	private String nomeFantasia;
	private String prestadora;

	private Drawable icon2g;
	private Drawable icon3g;
	private Drawable icon4g;

	private int qtdTecnologia2g;
	private int qtdTecnologia3g;
	private int qtdTecnologia4g;

	private Double conexaoDados;
	private Double disponibilidade;

	private Double conexaoDados2g;
	private Double conexaoDados3g;
	private Double conexaoDados4g;

	private Double conexaoVoz;
	private Double desconexaoDados;

	private Double desconexaoDados2g;
	private Double desconexaoDados3g;
	private Double desconexaoDados4g;

	private String month;

	private Double desconexaoVoz;

	public Drawable getLogo(Context context) {
		if(nomeFantasia != null){
			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("claro")) 
				return context.getResources().getDrawable(R.drawable.logo_claro);
			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("oi")) 
				return context.getResources().getDrawable(R.drawable.logo_oi);
			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("tim")) 
				return context.getResources().getDrawable(R.drawable.logo_tim);
			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("vivo")) 
				return context.getResources().getDrawable(R.drawable.logo_vivo);
			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("nextel")) 
				return context.getResources().getDrawable(R.drawable.logo_nextel);
			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("sercomtel")) 
				return context.getResources().getDrawable(R.drawable.logo_sercomtel);
			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("ctbc")) 
				return context.getResources().getDrawable(R.drawable.logo_ctbc);
			return null;
		}
		else {
			return null;
		}
	}	
	public void setMonth(String month) {
		this.month = month;
	}	
	public String getMonth() {
		return month;
	}	
	public void setConexaoDados2g(Double conexaoDados2g) {
		this.conexaoDados2g = conexaoDados2g;
	}
	public void setConexaoDados3g(Double conexaoDados3g) {
		this.conexaoDados3g = conexaoDados3g;
	}
	public void setConexaoDados4g(Double conexaoDados4g) {
		this.conexaoDados4g = conexaoDados4g;
	}
	public void setDisponibilidade(Double disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
	public void setDesconexaoDados2g(Double desconexaoDados2g) {
		this.desconexaoDados2g = desconexaoDados2g;
	}
	public void setDesconexaoDados3g(Double desconexaoDados3g) {
		this.desconexaoDados3g = desconexaoDados3g;
	}
	public void setDesconexaoDados4g(Double desconexaoDados4g) {
		this.desconexaoDados4g = desconexaoDados4g;
	}
	public Double getConexaoDados2g() {
		return conexaoDados2g;
	}
	public Double getDisponibilidade() {
		return disponibilidade;
	}
	public Double getConexaoDados3g() {
		return conexaoDados3g;
	}
	public Double getConexaoDados4g() {
		return conexaoDados4g;
	}
	public Double getDesconexaoDados2g() {
		return desconexaoDados2g;
	}
	public Double getDesconexaoDados3g() {
		return desconexaoDados3g;
	}
	public Double getDesconexaoDados4g() {
		return desconexaoDados4g;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public String getPrestadora() {
		return prestadora;
	}
	public void setPrestadora(String prestadora) {
		this.prestadora = prestadora;
	}
	public Drawable getIcon2g() {
		return icon2g;
	}
	public void setIcon2g(Drawable icon2g) {
		this.icon2g = icon2g;
	}
	public Drawable getIcon3g() {
		return icon3g;
	}
	public void setIcon3g(Drawable icon3g) {
		this.icon3g = icon3g;
	}
	public Drawable getIcon4g() {
		return icon4g;
	}
	public void setIcon4g(Drawable icon4g) {
		this.icon4g = icon4g;
	}
	public int getQtdTecnologia2g() {
		return qtdTecnologia2g;
	}
	public void setQtdTecnologia2g(int qtdTecnologia2g) {
		this.qtdTecnologia2g = qtdTecnologia2g;
	}
	public int getQtdTecnologia3g() {
		return qtdTecnologia3g;
	}
	public void setQtdTecnologia3g(int qtdTecnologia3g) {
		this.qtdTecnologia3g = qtdTecnologia3g;
	}
	public int getQtdTecnologia4g() {
		return qtdTecnologia4g;
	}
	public void setQtdTecnologia4g(int qtdTecnologia4g) {
		this.qtdTecnologia4g = qtdTecnologia4g;
	}
	public Double getConexaoDados() {
		return conexaoDados;
	}
	public void setConexaoDados(Double conexaoDados) {
		this.conexaoDados = conexaoDados;
	}
	public Double getConexaoVoz() {
		return conexaoVoz;
	}
	public void setConexaoVoz(Double conexaoVoz) {
		this.conexaoVoz = conexaoVoz;
	}
	public Double getDesconexaoDados() {
		return desconexaoDados;
	}
	public void setDesconexaoDados(Double desconexaoDados) {
		this.desconexaoDados = desconexaoDados;
	}
	public Double getDesconexaoVoz() {
		return desconexaoVoz;
	}
	public void setDesconexaoVoz(Double desconexaoVoz) {
		this.desconexaoVoz = desconexaoVoz;
	}
}