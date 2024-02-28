package entities;

import java.util.Date;
import java.util.Objects;

public class Evenement {


    private int id_eve ;

    private String nom_eve;

    private Date dated_eve;
    private Date datef_eve;
    private int nbr_max;
    private String adresse_eve;
    private  String image_eve;




    public Evenement(int nbr_max,String nom_eve,  String adresse_eve, Date dated_eve, Date datef_eve) {
        this.nbr_max = nbr_max;
        this.nom_eve = nom_eve;
        this.adresse_eve = adresse_eve;
        this.dated_eve = dated_eve;
        this.datef_eve = datef_eve;


    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id_eve=" + id_eve +
                ", nom_eve='" + nom_eve + '\'' +
                ", dated_eve=" + dated_eve +
                ", datef_eve=" + datef_eve +
                ", nbr_max=" + nbr_max +
                ", adresse_eve='" + adresse_eve + '\'' +
                ", image_eve='" + image_eve + '\'' +
                '}';
    }



    public Evenement(int id_eve, String nom_eve, Date dated_eve, Date datef_eve, int nbr_max, String adresse_eve, String image_eve) {
        this.id_eve = id_eve;
        this.nom_eve = nom_eve;
        this.dated_eve = dated_eve;
        this.datef_eve = datef_eve;
        this.nbr_max = nbr_max;
        this.adresse_eve = adresse_eve;
        this.image_eve = image_eve;
    }

    public Evenement(String nom_eve, Date dated_eve, Date datef_eve, int nbr_max, String adresse_eve, String image_eve) {
        this.nom_eve = nom_eve;
        this.dated_eve = dated_eve;
        this.datef_eve = datef_eve;
        this.nbr_max = nbr_max;
        this.adresse_eve = adresse_eve;
        this.image_eve = image_eve;
    }

    public String getImage_eve() {
        return image_eve;
    }

    public void setImage_eve(String image_eve) {
        this.image_eve = image_eve;
    }





    public Evenement() {
    }



    public int getId_eve() {
        return id_eve;
    }

    public void setId_eve(int id_eve) {
        this.id_eve = id_eve;
    }

    public int getNbr_max() {
        return nbr_max;
    }

    public void setNbr_max(int nbr_max) {
        this.nbr_max = nbr_max;
    }

    public String getNom_eve() {
        return nom_eve;
    }

    public void setNom_eve(String nom_eve) {
        this.nom_eve = nom_eve;
    }

    public String getAdresse_eve() {
        return adresse_eve;
    }

    public void setAdresse_eve(String adresse_eve) {
        this.adresse_eve = adresse_eve;
    }

    public Date getDated_eve() {
        return dated_eve;
    }

    public void setDated_eve(Date dated_eve) {
        this.dated_eve = dated_eve;
    }

    public Date getDatef_eve() {
        return datef_eve;
    }

    public void setDatef_eve(Date datef_eve) {
        this.datef_eve = datef_eve;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evenement evenement = (Evenement) o;
        return id_eve == evenement.id_eve && nbr_max == evenement.nbr_max && Objects.equals(nom_eve, evenement.nom_eve) && Objects.equals(dated_eve, evenement.dated_eve) && Objects.equals(datef_eve, evenement.datef_eve) && Objects.equals(adresse_eve, evenement.adresse_eve) && Objects.equals(image_eve, evenement.image_eve);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_eve, nom_eve, dated_eve, datef_eve, nbr_max, adresse_eve, image_eve);
    }
}
