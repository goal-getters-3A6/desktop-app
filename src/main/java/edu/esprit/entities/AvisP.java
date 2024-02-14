package edu.esprit.entities;

import java.util.Objects;

public class AvisP {
    private int idAP;
    private String commAP;
    private int star;
    private boolean fav;
    private int idPlat;
    private int iduap;

    public AvisP() {
    }

    public AvisP(int idAP, String commAP, int star, boolean fav, int idPlat , int iduap ) {
        this.idAP = idAP;
        this.commAP = commAP;
        this.star = star;
        this.fav = fav;
        this.idPlat = idPlat;
        this.iduap = iduap;
    }
    public AvisP( String commAP, int star, boolean fav, int idPlat , int iduap ) {
        this.commAP = commAP;
        this.star = star;
        this.fav = fav;
        this.idPlat = idPlat;
        this.iduap = iduap;
    }

    // Getters and setters
    public int getIdAP() {
        return idAP;
    }

    public void setIdAP(int idAP) {
        this.idAP = idAP;
    }

    public String getCommAP() {
        return commAP;
    }

    public void setCommAP(String commAP) {
        this.commAP = commAP;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public int getIduap() {
        return iduap;
    }

    public void setIduap(int iduap) {
        this.iduap = iduap;
    }

    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvisP avis = (AvisP) o;
        return idAP == avis.idAP;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAP);
    }

    @Override
    public String toString() {
        return "AvisP{" +
                "idAP=" + idAP +
                ", commAP='" + commAP + '\'' +
                ", star=" + star +
                ", fav=" + fav +
                '}';
    }
}
