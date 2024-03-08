package edu.esprit.entities;

public class Admin extends User {


    public Admin(String nom, String prenom, String mdp, String mail, String image) {
        super(nom, prenom,mdp,mail,image);

    }


    public Admin(int id, String nom, String prenom, String mdp, String mail, String image) {
        super(id, nom, prenom,mdp,mail,image);
    }


    @Override
    public String toString() {
        return "Admin{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", mdp='" + getMdp() + '\'' +
                ", mail='" + getMail() + '\'' +
                ", image=" + getImage() +
                '}';
    }
}
