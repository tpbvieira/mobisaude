package co.salutary.mobisaude.model;

import java.util.Date;

public class AvaliacaoMedia {

    private Integer idES;
    private Float rating;
    private Date date;
    private Integer count;

    public Integer getIdES() {
        return idES;
    }

    public void setIdES(Integer idES) {
        this.idES = idES;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
