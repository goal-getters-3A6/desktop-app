package edu.esprit.entities;

import java.util.Arrays;

public class Admin extends User {
    private String mdp;
    private String mail;
    private boolean statut;
    private int nb_tentative;
    private byte[] image;

    public Admin() {
        super();
        this.setRole("admin"); // Définir le rôle comme "admin" par défaut
    }

    public Admin(String nom, String prenom, String mdp, String mail, boolean statut, int nb_tentative, byte[] image) {
        super(nom, prenom);
        this.mdp = mdp;
        this.mail = mail;
        this.statut = statut;
        this.nb_tentative = nb_tentative;
        this.image = image;
        this.setRole("admin"); // Définir le rôle comme "admin"
    }

    public Admin(int id, String nom, String prenom, String mdp, String mail, boolean statut, int nb_tentative, byte[] image) {
        super(id, nom, prenom);
        this.mdp = mdp;
        this.mail = mail;
        this.statut = statut;
        this.nb_tentative = nb_tentative;
        this.image = image;
        this.setRole("admin"); // Définir le rôle comme "admin"
    }

    // Getters and setters pour les attributs supplémentaires

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

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public int getNb_tentative() {
        return nb_tentative;
    }

    public void setNb_tentative(int nb_tentative) {
        this.nb_tentative = nb_tentative;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", mdp='" + mdp + '\'' +
                ", mail='" + mail + '\'' +
                ", statut=" + statut +
                ", nb_tentative=" + nb_tentative +
                ", image=" + Arrays.toString(image) +
                ", role='" + getRole() + '\'' +
                '}';
    }
}
