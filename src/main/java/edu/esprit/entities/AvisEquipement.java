package edu.esprit.entities;

import java.util.Objects;

public class AvisEquipement {
    private int idAEq ;
    private String commAEq ;
    private Equipement equipement;
private User user;
    private boolean like;
    private boolean dislike;



    public AvisEquipement(String text, int idEq){}

    public AvisEquipement(int idAEq, String commAEq, Equipement equipement ) {
        this.idAEq = idAEq;
        this.commAEq = commAEq;
        this.equipement = equipement;
    }

    public AvisEquipement(int idAEq, String commAEq, Equipement equipement, User user, boolean like, boolean dislike) {
        this.idAEq = idAEq;
        this.commAEq = commAEq;
        this.equipement = equipement;
        this.user = user;
        this.like = like;
        this.dislike = dislike;
    }

    public AvisEquipement(String commAEq, Equipement equipement, User user, boolean like, boolean dislike) {
        this.commAEq = commAEq;
        this.equipement = equipement;
        this.user = user;
        this.like = like;
        this.dislike = dislike;
    }

    public AvisEquipement(String commAEq) {

        this.commAEq = commAEq;

    }
    public AvisEquipement(String commAEq, Equipement equipement) {
        this.commAEq = commAEq;
        this.equipement = equipement;
    }

    public AvisEquipement(String commAEq, Equipement equipement, boolean like, boolean dislike) {
        // Initialiser les autres propriétés de la classe
        this.like = like;
        this.dislike = dislike;
        this.commAEq = commAEq;
        this.equipement = equipement;
    }
    public AvisEquipement() {

    }

    public AvisEquipement(int idAEq, String commAEq) {
        this.idAEq = idAEq;
        this.commAEq = commAEq;
    }

    public AvisEquipement(int idAEq, String commAEq, Equipement equipement, User user) {
        this.idAEq = idAEq;
        this.commAEq = commAEq;
        this.equipement = equipement;
        this.user = user;

    }

    public AvisEquipement(String commAEq, Equipement equipement, Client user) {
        this.commAEq = commAEq;
        this.equipement = equipement;
        this.user = user;
    }

    public AvisEquipement(int idAEq, String commAEq, User user) {
        this.idAEq = idAEq;
        this.commAEq = commAEq;
        this.user = user;
    }

    public AvisEquipement(String commAEq, User user) {
        this.commAEq = commAEq;
        this.user = user;
    }

    public AvisEquipement(int idAEq, User user) {
        this.idAEq = idAEq;
        this.user = user;
    }


    public int getIdAEq() {
        return idAEq;
    }

    public void setIdAEq(int idAEq) {
        this.idAEq = idAEq;
    }

    public String getCommAEq() {
        return commAEq;
    }

    public void setCommAEq(String commAEq) {
        this.commAEq = commAEq;
    }

    public Equipement getEquipement() {
        return equipement;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
    }
    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isDislike() {
        return dislike;
    }

    public void setDislike(boolean dislike) {
        this.dislike = dislike;
    }

    @Override
    public String toString() {
        return "AvisEquipement{" +
                "commAEq='" + commAEq + '\'' +
                ", equipement=" + equipement +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvisEquipement that = (AvisEquipement) o;
        return idAEq == that.idAEq && Objects.equals(commAEq, that.commAEq) && Objects.equals(equipement, that.equipement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAEq, commAEq, equipement);
    }
}
