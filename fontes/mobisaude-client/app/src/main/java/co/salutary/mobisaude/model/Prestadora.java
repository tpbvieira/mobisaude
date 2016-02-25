package co.salutary.mobisaude.model;

import java.util.ArrayList;
import java.util.List;

public class Prestadora {

	String prestadora;
    String dateStart;
	List<Double> listConexao;
	List<Double> listDesconexao;

	public Prestadora(String prestadora){
		this.prestadora = prestadora;
		listConexao = new ArrayList<Double>();		
		listDesconexao = new ArrayList<Double>();
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getDateStart() {
		return dateStart;
	}
	public String getPrestadora() {
		return prestadora;
	}
	public void addConexao(double value){
		listConexao.add(value);		
	}
	public void addDesconexao(double value){
		listDesconexao.add(value);		
	}
	public List<Double> getListConexao() {
		return listConexao;
	}
	public List<Double> getListDesconexao() {
		return listDesconexao;
	}	
}
