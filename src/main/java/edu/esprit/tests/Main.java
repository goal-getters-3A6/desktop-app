package edu.esprit.tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import edu.esprit.entities.Abonnement;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceAbonnement;

public class Main {
    public static void main(String[] args) {
        ServiceAbonnement sa = new ServiceAbonnement();
        String str = "2024-03-15";
        LocalDate date = LocalDate.parse(str, DateTimeFormatter.ISO_DATE);
        java.sql.Date sqlDate = java.sql.Date.valueOf( date );
        User us1 =new User (2,"mayssa","hakimi");
        Abonnement AB1 = new Abonnement(3,500,"Go123","ordinaire",sqlDate,us1);
        //sa.ajouter(AB1);
       // Abonnement AB2 = new Abonnement(1,400,"Go123","ordinaire",sqlDate);
       // sa.modifier(AB2);
        //sa.supprimer(AB2.getIdA());
        // System.out.println(sa.getAll());

        //System.out.println(sa.getAllByUser(2));
        /*Abonnement abonnement = sa.getOneById(AB1.getIdA());

        // Vérification si l'abonnement a été trouvé
        if (abonnement != null) {
            // Affichage des informations sur labonnement
            System.out.println("abonnement trouvé : " + abonnement);
        } else {
            System.out.println("Aucun abonnement trouvé avec l'ID spécifié.");
        }*/

    }
}
