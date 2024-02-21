package edu.esprit.entities;

import java.util.Arrays;

public class Admin extends User {


    public Admin(String nom, String prenom, String mdp, String mail, byte[] image) {
        super(nom, prenom,mdp,mail,image);

    }


    @Override
    public boolean isAdmin() {
        return true; // Un administrateur est toujours un administrateur
    }

    public Admin(int id, String nom, String prenom, String mdp, String mail, byte[] image) {
        super(id, nom, prenom,mdp,mail,image);
    }

    // Getters and setters pour les attributs supplémentaires


    @Override
    public String toString() {
        return "Admin{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", mdp='" + getMdp() + '\'' +
                ", mail='" + getMail() + '\'' +
                ", image=" + Arrays.toString(getImage()) +
                '}';
    }
}
