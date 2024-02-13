package edu.esprit.entities;

import java.sql.Time;

import java.util.Objects;

public class Seance {
    private int idseance;
    private String nom;
    private Time horaire;
    private  String jour;
    private int numsalle;
    private String duree;
    final private  int nbMax=10;
    public Seance()
    {

    }
    public Seance(int idseance,String nom,Time horaire,String jour,int numsalle,String duree )
    {
        this.idseance=idseance;
        this.nom=nom;
        this.horaire=horaire;
        this.jour=jour;
        this.numsalle=numsalle;
        this.duree=duree;

    }
    public Seance(String nom,Time horaire,String jour,int numsalle,String duree )
    {
        this.nom=nom;
        this.horaire=horaire;
        this.jour=jour;
        this.numsalle=numsalle;
        this.duree=duree;

    }

    public int getIdseance() {
        return idseance;
    }

    public String getNom() {
        return nom;
    }

    public Time getHoraire() {
        return horaire;
    }

    public String getJour() {
        return jour;
    }

    public int getNumsalle() {
        return numsalle;
    }

    public String getDuree() {
        return duree;
    }

    public int getNbMax() {
        return nbMax;
    }

    public void setIdseance(int idseance) {
        this.idseance = idseance;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setHoraire(Time horaire) {
        this.horaire = horaire;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public void setNumsalle(int numsalle) {
        this.numsalle = numsalle;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return "Seance{" +
                "idseance=" + idseance +
                ", nom='" + nom + '\'' +
                ", horaire=" + horaire +
                ", jour='" + jour + '\'' +
                ", numsalle=" + numsalle +
                ", duree='" + duree + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seance seance = (Seance) o;
        return idseance == seance.idseance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idseance);
    }
}
