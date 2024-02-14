package edu.esprit.entities;

import java.util.Objects;

public class Reservation {
private int IdReservation;
private Seance seance;
private String nom;
private  String prenom;
private int age;
private float poids;
private float taille;
private String sexe;
private User user;
//private ServiceSeance seanceService;

    public Reservation()
    {
    }

    public Reservation(int idReservation, Seance seance, String nom, String prenom, int age, float poids, float taille, String sexe,User user) {
        IdReservation = idReservation;
        this.seance = seance;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.poids = poids;
        this.taille = taille;
        this.sexe = sexe;
        this.user=user;

    }

    public Reservation(Seance seance, String nom, String prenom, int age, float poids, float taille, String sexe,User user) {
        this.seance = seance;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.poids = poids;
        this.taille = taille;
        this.sexe = sexe;
        this.user=user;
    }

    public int getIdReservation() {
        return IdReservation;
    }

    public Seance getSeance() {
        return seance;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    public float getPoids() {
        return poids;
    }

    public float getTaille() {
        return taille;
    }
    public String getSexe() {
        return sexe;
    }

    public User getUser(){return user; }
    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }

    public void setTaille(float taille) {
        this.taille = taille;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
    public  void setUser(User user){this.user = user; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return IdReservation == that.IdReservation && Objects.equals(seance, that.seance) && Objects.equals(nom, that.nom) && Objects.equals(prenom, that.prenom) && sexe == that.sexe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(IdReservation, seance);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "IdReservation=" + IdReservation +
                ", seance=" + seance +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", poids=" + poids +
                ", taille=" + taille +
                ", sexe=" + sexe +
                ",user="+user+
                '}';
    }
}
