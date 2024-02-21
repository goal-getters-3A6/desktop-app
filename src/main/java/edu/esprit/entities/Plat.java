package edu.esprit.entities;
import java.util.Objects;
public class Plat {

    private int idP;
    private String nomP;
    private Float prixP;
    private String descP;
    private String alergieP;
    private Boolean etatP;
    private String photop;
    private int calories;

    public Plat(int idP, String nomP){

    }

    public Plat(String nomP, Float prixP,String descP, String alergieP, Boolean etatP , String photop , int calories) {
        this.nomP = nomP;
        this.prixP = prixP;
        this.descP = descP;
        this.alergieP = alergieP;
        this.etatP = etatP;
        this.photop = photop;
        this.calories = calories;
    }
    public Plat(String nomP, Float prixP, String alergieP, Boolean etatP ) {
        this.nomP = nomP;
        this.prixP = prixP;
        this.alergieP = alergieP;
        this.etatP = etatP;

    }


    public Plat( int idP, String nomP, Float prixP,String descP, String alergieP, Boolean etatP , String photop , int calories) {
        this.idP = idP;
        this.nomP = nomP;
        this.prixP = prixP;
        this.descP = descP;
        this.alergieP = alergieP;
        this.etatP = etatP;
        this.photop = photop;
        this.calories = calories;
    }


    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public String getNomP() {
        return nomP;
    }

    public void setNomP(String nomP) {
        this.nomP = nomP;
    }

    public Float getPrixP() {
        return prixP;
    }

    public String getPhotop() {
        return photop;
    }

    public void setPhotop(String photop) {
        this.photop = photop;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setPrixP(Float prixP) {
        this.prixP = prixP;
    }

    public String getDescP() {
        return descP;
    }

    public void setDescP(String descP) {
        this.descP = descP;
    }

    public String getAlergieP() {
        return alergieP;
    }

    public void setAlergieP(String alergieP) {
        this.alergieP = alergieP;
    }

    public Boolean getEtatP() {
        return etatP;
    }

    public void setEtatP(Boolean etatP) {
        this.etatP = etatP;
    }

    @Override
    public String toString() {
        return "Plat{" +
                "idP=" + idP +
                ", nomP='" + nomP + '\'' +
                ", prixP=" + prixP +
                ", descP='" + descP + '\'' +
                ", alergieP='" + alergieP + '\'' +
                ", etatP=" + etatP +
                ", calories=" + calories +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plat plat = (Plat) o;
        return idP == plat.idP;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idP);
    }
}

