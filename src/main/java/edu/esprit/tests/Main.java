package edu.esprit.tests;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.utils.DataSource;

public class Main {
    public static void main(String[] args) {
        ServiceReclamation sr =new ServiceReclamation();
        User us1 =new User (1,"mayssa","hakimi");
        Reclamation rec =new Reclamation("uu","ff","odd17","tt","bb",0,us1);
        Reclamation rec1 =new Reclamation(7,"uu3","ff","odd17","tt","bb",0,us1);
        Reclamation rec2 =new Reclamation(8,"hhhhb","ff","odd17","tt","bb",0,us1);

//sr.ajouter(rec2);
//sr.modifier(rec1);
//sr.supprimer(rec1.getIdRec());
        //System.out.println(sr.getAll());
        Reclamation reclamation = sr.getOneById(rec2.getIdRec());

        // Vérification si l'abonnement a été trouvé
        if (reclamation != null) {
            // Affichage des informations sur labonnement
            System.out.println("abonnement trouvé : " + reclamation);
        } else {
            System.out.println("Aucun abonnement trouvé avec l'ID spécifié.");
        }
    }

    }

