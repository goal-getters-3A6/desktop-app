package edu.esprit.entities;

import java.util.Date;

public class Client extends User {
    private Date date_inscription;
    private Date date_naissance;

    private boolean statut;
    private int nb_tentative;
    private String tel;



    public Client(String nom, String prenom, String mdp, String mail, String tel,Boolean statut, int nb_tentative,byte[] image, Date date_naissance) {
        super(nom, prenom, mail, mdp,image );
        this.date_naissance = date_naissance;
        this.tel = tel;
        this.statut = statut;
        this.nb_tentative = nb_tentative;
    }

    public Client(int id, String nom, String prenom, String mdp, String mail, String tel,Boolean statut, int nb_tentative,byte[] image, Date date_inscription, Date date_naissance) {
        super(id, nom, prenom, mail, mdp, image);
        this.date_inscription = date_inscription;
        this.date_naissance = date_naissance;
        this.tel = tel;
        this.statut = statut;
        this.nb_tentative = nb_tentative;
    }

    public Client(int id, String nom, String prenom, String mdp, String mail, String tel,Boolean statut, int nb_tentative,byte[] image, Date date_naissance) {
        super(id, nom, prenom, mail, mdp, image);
        this.date_naissance = date_naissance;
        this.tel = tel;
        this.statut = statut;
        this.nb_tentative = nb_tentative;
    }
    public Client(int id, String nom, String prenom,String mail) {
        super(id, nom, prenom, mail);
    }

    public Client (int id , String nom , String prenom , String mail , byte[] image , Date date_inscription , Date date_naissance , String tel){
        super(id,nom,prenom,mail,image);
        this.date_inscription = date_inscription;
        this.date_naissance = date_naissance;
        this.tel = tel;
    }

    public Client(String    ext, String text1, String text2) {
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public boolean getStatut() {
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



    @Override
    public String toString() {
        return "Client{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", date_inscription=" + date_inscription +
                ", date_naissance=" + date_naissance +
                ", statut=" + statut +
                ", nb_tentative=" + nb_tentative +
                ", tel='" + tel + '\'' +
                '}';
    }
}
