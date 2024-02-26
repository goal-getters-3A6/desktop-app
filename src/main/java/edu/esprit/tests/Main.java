package edu.esprit.tests;


import edu.esprit.entities.AvisP;
import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesPlat;
import edu.esprit.services.ServicesAvisPlat;
import edu.esprit.utils.DataSource;

import java.sql.SQLException;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws SQLException {
        //ajout plat
       // Plat plataj = new Plat(10,"salade8", 10.99f, "verte ", "lactose", true,"fghguh",105);
        ServicesPlat servicesPlat = new ServicesPlat();
        //servicesPlat.ajouter(plataj);
        //modif//////////////////////////////
       /* int newPlatId = 12;
        Plat modifiedPlat = new Plat(newPlatId, "SMOOTHIE", 15.99f, "novdesc", "lactose intolerance", false, "fgfdf",105);
        servicesPlat.modifier(modifiedPlat);*/
        //supp/////////////////////////////////
        /* int PlatId = 4 ;
        servicesPlat.supprimer(PlatId);*/
        //aff/////////////////////////////////
       /* int IDPAFF = 12;
        Plat plat = servicesPlat.getOneById(IDPAFF);*/

        ///aff tous//////////////////////////////////////////
       /* Set<Plat> plats = servicesPlat.getAll();
        System.out.println("List of Plat objects:");
        for (Plat plat : plats) {
            System.out.println(plat);
        }*/
        ServicesAvisPlat servicesAvisPlat = new ServicesAvisPlat();
        //ajoutez avis///////////////////////////////////////////
       AvisP avis = new AvisP();

        /*avis.setCommAP("tres bien!");
        avis.setStar(5);
        avis.setFav(true);
        avis.setPlat(plataj);
        avis.setIduap(1);
        servicesAvisPlat.ajouter(avis);*/
        //modifier avis///////////////////////////////
        AvisP avismod = new AvisP();
        avismod.setIdAP(32);
        avismod.setCommAP("modifi");
        avismod.setStar(4);
        avismod.setFav(true);

        servicesAvisPlat.modifier(avismod);

        ////supp avis///////////////////////////////
        /*int PlatId = 12 ;
        servicesAvisPlat.supprimer(PlatId);*/

        //aff/////////////////////////////////
        /*int idav= 13; // Replace with the ID of the review you want to retrieve
        AvisP avisp = servicesAvisPlat.getOneById(idav);
        System.out.println(avisp);*/
        ///aff tous//////////////////////////////////////////
       /* Set<AvisP> tous = servicesAvisPlat.getAll();
        for (AvisP avisp :tous) {
            System.out.println(avisp);
        }*/


        // Specify the idPlat for which you want to retrieve comments
       /*int idPlat = 11; // Example: Change 5 to the actual idPlat you want to query

        // Call the getAllCommentsForPlat method to retrieve comments for the specified idPlat
        Set<AvisP> commentsForPlat = servicesAvisPlat.getAllP(idPlat);

        // Print the retrieved comments
        System.out.println("Comments for Plat with ID " + idPlat + ":");
        for (AvisP avis : commentsForPlat) {
            System.out.println("ID: " + avis.getIdAP());
            System.out.println("Comment: " + avis.getCommAP());
            System.out.println("Star: " + avis.getStar());
            System.out.println("Favorite: " + avis.isFav());
            System.out.println("-----------------------------------------");
        }
*/

    }

    }

