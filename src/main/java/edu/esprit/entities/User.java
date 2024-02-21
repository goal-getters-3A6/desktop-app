package edu.esprit.entities;

import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

public class User {
    private int id;
    private String nom;
    private String prenom;
    private String mdp;
    private String mail;
    private byte[] image;

    public User(int id, String nom, String prenom, String mail, byte[] image, Date dateInscription, Date dateNaissance, String tel) {
    }

    public User(int id,String nom, String prenom) {
        this.id=id;
        this.nom = nom;
        this.prenom = prenom;
    }
    public  User()
    {}

    public User(String nom, String prenom, String mdp, String mail, byte[] image) {
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
        this.mail = mail;
        this.image = image;
    }

    public User(int id, String nom, String prenom, String mail, byte[] image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public User(int id, String nom, String prenom, String mdp, String mail, byte[] image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
        this.mail = mail;
        this.image = image;
    }

    public User(Object userData) {
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mdp='" + mdp + '\'' +
                ", mail='" + mail + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
