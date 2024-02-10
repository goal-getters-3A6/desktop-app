package edu.esprit.entities;

public class Client extends User{

    public Client (){
        super();
    }

    public Client(String nom, String prenom) {
        super(nom, prenom);
    }

    public Client(int id, String nom, String prenom) {
        super(id, nom, prenom);
    }
}
