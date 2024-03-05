package edu.esprit.entities;

import java.sql.Date;
import java.util.Objects;

public class Abonnement {

    private int idA;
    private float montantAb;
    private String codePromoAb;
    private String  typeAb;
    private Date dateExpirationAb;
    private User utilisateur;

    public Abonnement(){
    }


    public Abonnement(int idA, float montantAb, String codePromoAb, String typeAb, Date dateExpirationAb,User utilisateur) {
        this.idA = idA;
        this.montantAb = montantAb;
        this.codePromoAb = codePromoAb;
        this.typeAb = typeAb;
        this.dateExpirationAb = dateExpirationAb;
        this.utilisateur = utilisateur;
    }

    public Abonnement(float montantAb, String codePromoAb, String typeAb, Date dateExpirationAb,User utilisateur) {
        this.montantAb = montantAb;
        this.codePromoAb = codePromoAb;
        this.typeAb = typeAb;
        this.dateExpirationAb = dateExpirationAb;
        this.utilisateur = utilisateur;
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
    public User getUtilisateur() {
        return utilisateur;
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
    public void setUtilisateur(User utilisateur) {
        this.utilisateur = utilisateur;
    }


    @Override
    public String toString() {
        String result ="Abonnemnt{" +
                "montantAb=" + montantAb +
                ", codePromoAb='" + codePromoAb + '\'' +
                ", typeAb='" + typeAb + '\'' +
                ", dateExpirationAb=" + dateExpirationAb +
                ", utilisateur=[" ;
        // Concaténer les commentaires des avis
        result += utilisateur.getNom() + ", ";

        result += "]}";

        return result;
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
