package edu.esprit.entities;

import java.util.Date;

public class Client extends User {
    private Date date_inscription;
    private Date date_naissance;
    private int tel;

    public Client() {
        super();
    }

    public Client(String nom, String prenom, Date date_inscription, Date date_naissance, int tel) {
        super(nom, prenom);
        this.date_inscription = date_inscription;
        this.date_naissance = date_naissance;
        this.tel = tel;
    }

    public Client(int id, String nom, String prenom, Date date_inscription, Date date_naissance, int tel) {
        super(id, nom, prenom);
        this.date_inscription = date_inscription;
        this.date_naissance = date_naissance;
        this.tel = tel;
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
}
