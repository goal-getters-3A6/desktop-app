package edu.esprit.entities;

import java.util.Objects;

public class Reclamation {
    private int idRec;
    private String categorieRec;
    private String descriptionRec;
    private String piéceJointeRec;
    private String oddRec;
    private String serviceRec ;
    private  int etatRec;
    private User utilisateur;


    public Reclamation(){

}

    public Reclamation(int idRec, String categorieRec, String descriptionRec, String piéceJointeRec, String oddRec, String serviceRec, int etatRec, User utilisateur) {
        this.idRec = idRec;
        this.categorieRec = categorieRec;
        this.descriptionRec = descriptionRec;
        this.piéceJointeRec = piéceJointeRec;
        this.oddRec = oddRec;
        this.serviceRec = serviceRec;
        this.etatRec = etatRec;
        this.utilisateur = utilisateur;
    }

    public Reclamation(String categorieRec, String descriptionRec, String piéceJointeRec, String oddRec, String serviceRec, int etatRec, User utilisateur) {
        this.categorieRec = categorieRec;
        this.descriptionRec = descriptionRec;
        this.piéceJointeRec = piéceJointeRec;
        this.oddRec = oddRec;
        this.serviceRec = serviceRec;
        this.etatRec = etatRec;
        this.utilisateur = utilisateur;
    }
    public Reclamation(String categorieRec, String descriptionRec, String piéceJointeRec, String oddRec, String serviceRec ,User utilisateur) {
        this.categorieRec = categorieRec;
        this.descriptionRec = descriptionRec;
        this.piéceJointeRec = piéceJointeRec;
        this.oddRec = oddRec;
        this.serviceRec = serviceRec;
        this.utilisateur = utilisateur;


    }

    public int getIdRec() {
        return idRec;
    }

    public String getCategorieRec() {
        return categorieRec;
    }

    public String getDescriptionRec() {
        return descriptionRec;
    }

    public String getPiéceJointeRec() {
        return piéceJointeRec;
    }

    public String getOddRec() {
        return oddRec;
    }

    public String getServiceRec() {
        return serviceRec;
    }

    public int getEtatRec() {
        return etatRec;
    }

    public User getUtilisateur() {
        return utilisateur;
    }

    public void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    public void setCategorieRec(String categorieRec) {
        this.categorieRec = categorieRec;
    }

    public void setDescriptionRec(String descriptionRec) {
        this.descriptionRec = descriptionRec;
    }

    public void setPiéceJointeRec(String piéceJointeRec) {
        this.piéceJointeRec = piéceJointeRec;
    }

    public void setOddRec(String oddRec) {
        this.oddRec = oddRec;
    }

    public void setServiceRec(String serviceRec) {
        this.serviceRec = serviceRec;
    }

    public void setEtatRec(int etatRec) {
        this.etatRec = etatRec;
    }

    public void setUtilisateur(User utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        String result ="Reclamation{" +
                "categorieRec='" + categorieRec + '\'' +
                ", descriptionRec='" + descriptionRec + '\'' +
                ", piéceJointeRec='" + piéceJointeRec + '\'' +
                ", oddRec='" + oddRec + '\'' +
                ", serviceRec='" + serviceRec + '\'' +
                ", etatRec=" + etatRec +
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
        Reclamation that = (Reclamation) o;
        return idRec == that.idRec && etatRec == that.etatRec && Objects.equals(categorieRec, that.categorieRec) && Objects.equals(descriptionRec, that.descriptionRec) && Objects.equals(piéceJointeRec, that.piéceJointeRec) && Objects.equals(oddRec, that.oddRec) && Objects.equals(serviceRec, that.serviceRec) && Objects.equals(utilisateur, that.utilisateur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRec, categorieRec, descriptionRec, piéceJointeRec, oddRec, serviceRec, etatRec, utilisateur);
    }

}
