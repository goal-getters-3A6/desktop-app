package edu.esprit.tests;

import edu.esprit.entities.Personne;
import edu.esprit.services.ServicePersonne;
import edu.esprit.utils.DataSource;

public class Main {
    public static void main(String[] args) {
        ServicePersonne sp = new ServicePersonne();

        System.out.println(sp.getAll());

    }
}
