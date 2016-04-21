package co.salutary.mobisaude.model;


import java.util.Date;

public class Avaliacao {

    private int idAvaliacao;
    private int idES;
    private String email;
    private String avaliacao;
    private String titulo;
    private float rating;
    private Date date;

    public Avaliacao() {
    }

    public Avaliacao(int idES, String email, String titulo, String avaliacao, float rating) {
        this.idES = idES;
        this.email = email;
        this.titulo = titulo;
        this.avaliacao = avaliacao;
        this.rating = rating;
        this.date = new Date();
    }

    public int getIdAvaliacao() {
        return this.idAvaliacao;
    }

    public void setIdAvaliacao(int idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Integer getIdES() {
        return this.idES;
    }

    public void setIdES(Integer idES) {
        this.idES = idES;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAvaliacao() {
        return this.avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public float getRating() {
        return this.rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}