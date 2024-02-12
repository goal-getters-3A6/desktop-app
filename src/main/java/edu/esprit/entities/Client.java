package edu.esprit.entities;

import java.util.Date;

public class Client extends User {
    private Date date_inscription;
    private Date date_naissance;
    private int tel;

    public Client() {
        super();
        this.setRole("client"); // Définir le rôle comme "client" par défaut
    }

    public Client(String nom, String prenom, Date date_inscription, Date date_naissance, int tel) {
        super(nom, prenom);
        this.date_inscription = date_inscription;
        this.date_naissance = date_naissance;
        this.tel = tel;
        this.setRole("client"); // Définir le rôle comme "client"
    }

    public Client(int id, String nom, String prenom, Date date_inscription, Date date_naissance, int tel) {
        super(id, nom, prenom);
        this.date_inscription = date_inscription;
        this.date_naissance = date_naissance;
        this.tel = tel;
        this.setRole("client"); // Définir le rôle comme "client"
    }

    public Date getDate_inscription() {
        return date_inscription;
    }

    public void setDate_inscription(Date date_inscription) {
        this.date_inscription = date_inscription;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", date_inscription=" + date_inscription +
                ", date_naissance=" + date_naissance +
                ", tel='" + tel + '\'' +
                ", role='" + getRole() + '\'' +
                '}';
    }
}
