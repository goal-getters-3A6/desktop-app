package edu.esprit.entities;

import java.sql.Time;

import java.util.Objects;

public class Seance {
    private int idseance;
    private String nom;
    private Time horaire;
    private  String jourseance;
    private int numesalle;
    private String duree;
    private String imageseance;
    final private  int nbMax=3;
    private int frequency;
    public Seance()
    {

    }
    public Seance(int idseance,String nom,Time horaire,String jour,int numsalle,String duree,String image)
    {
        this.idseance=idseance;
        this.nom=nom;
        this.horaire=horaire;
        this.jourseance=jour;
        this.numesalle=numsalle;
        this.duree=duree;
        this.imageseance=image;

    }
    public Seance(String nom,Time horaire,String jour,int numsalle,String duree,String image )
    {
        this.nom=nom;
        this.horaire=horaire;
        this.jourseance=jour;
        this.numesalle=numsalle;
        this.duree=duree;
        this.imageseance=image;

    }
    public Seance(String nom, int frequency) {
        this.nom = nom;
        this.frequency = frequency;
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

    public String getJourseance() {
        return jourseance;
    }

    public int getNumesalle() {
        return numesalle;
    }

    public String getDuree() {
        return duree;
    }

    public String getImageseance(){return imageseance;}

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

    public void setJourseance(String jour) {
        this.jourseance = jour;
    }

    public void setNumesalle(int numsalle) {
        this.numesalle = numsalle;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }
    public void setImageseance(String image) {
        this.imageseance = image;
    }


    @Override
    public String toString() {
        return  nom ;
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
