package edu.esprit.entities;

public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(String nom, String prenom) {
        super(nom, prenom);
    }

    public Admin(int id, String nom, String prenom) {
        super(id, nom, prenom);
    }

    // You can add additional methods specific to Admin if needed

}
