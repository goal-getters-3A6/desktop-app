package edu.esprit.entities;

import java.sql.Date;
import java.util.Objects;

public class Abonnement {

    private int idA;
    private float montantAb;
    private String codePromoAb;
    private String  typeAb;
    private Date dateExpirationAb;

    public Abonnement(){
    }


    public Abonnement(int idA, float montantAb, String codePromoAb, String typeAb, Date dateExpirationAb) {
        this.idA = idA;
        this.montantAb = montantAb;
        this.codePromoAb = codePromoAb;
        this.typeAb = typeAb;
        this.dateExpirationAb = dateExpirationAb;
    }

    public Abonnement(float montantAb, String codePromoAb, String typeAb, Date dateExpirationAb) {
        this.montantAb = montantAb;
        this.codePromoAb = codePromoAb;
        this.typeAb = typeAb;
        this.dateExpirationAb = dateExpirationAb;
    }

    public int getIdA() {
        return idA;
    }

    public float getMontantAb() {
        return montantAb;
    }

    public String getCodePromoAb() {
        return codePromoAb;
    }

    public String getTypeAb() {
        return typeAb;
    }

    public Date getDateExpirationAb() {
        return  dateExpirationAb;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public void setMontantAb(float montantAb) {
        this.montantAb = montantAb;
    }

    public void setCodePromoAb(String codePromoAb) {
        this.codePromoAb = codePromoAb;
    }

    public void setTypeAb(String typeAb) {
        this.typeAb = typeAb;
    }

    public void setDateExpirationAb(Date dateExpirationAb) {
        this.dateExpirationAb = dateExpirationAb;
    }

    @Override
    public String toString() {
        return "Abonnement{" +
                "montantAb=" + montantAb +
                ", codePromoAb='" + codePromoAb + '\'' +
                ", typeAb='" + typeAb + '\'' +
                ", dateExpirationAb=" + dateExpirationAb +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Abonnement that = (Abonnement) o;
        return idA == that.idA && Float.compare(montantAb, that.montantAb) == 0 && Objects.equals(codePromoAb, that.codePromoAb) && Objects.equals(typeAb, that.typeAb) && Objects.equals(dateExpirationAb, that.dateExpirationAb);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idA, montantAb);
    }
}
