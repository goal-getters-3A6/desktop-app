package edu.esprit.tests;


import edu.esprit.entities.AvisP;
import edu.esprit.entities.Plat;
import edu.esprit.services.ServicesPlat;
import edu.esprit.services.ServicesAvisPlat;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        //ajout plat
        //Plat plataj = new Plat("salade8", 10.99f, "verte ", "lactose", true);
        ServicesPlat servicesPlat = new ServicesPlat();
      // servicesPlat.ajouter(plataj);
        //modif//////////////////////////////
        int newPlatId = 12;
        Plat modifiedPlat = new Plat(newPlatId, "novplat", 15.99f, "novdesc", "novall", false, "fgfdf",10);
        servicesPlat.modifier(modifiedPlat);
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
      /* AvisP avis = new AvisP();

        avis.setCommAP("tres bien!");
        avis.setStar(5);
        avis.setFav(true);
        avis.setIdPlat(5);
        avis.setIduap(1);
        servicesAvisPlat.ajouter(avis);*/
        //modifier avis///////////////////////////////
        /*AvisP avismod = new AvisP();
        avismod.setIdAP(12);
        avismod.setCommAP("modifi");
        avismod.setStar(4);
        avismod.setFav(true);

        servicesAvisPlat.modifier(avismod);*/

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



    }

    }

