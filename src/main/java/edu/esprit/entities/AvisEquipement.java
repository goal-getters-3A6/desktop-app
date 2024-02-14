package edu.esprit.entities;

import java.util.Objects;

public class AvisEquipement {
    private int idAEq ;
    private String commAEq ;
    private Equipement equipement;

    public AvisEquipement(){}

    public AvisEquipement(int idAEq, String commAEq, Equipement equipement) {
        this.idAEq = idAEq;
        this.commAEq = commAEq;
        this.equipement = equipement;
    }

    public AvisEquipement(String commAEq, Equipement equipement) {
        this.commAEq = commAEq;
        this.equipement = equipement;
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
