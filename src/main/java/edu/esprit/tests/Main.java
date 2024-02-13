package edu.esprit.tests;

import edu.esprit.entities.Reservation;
import edu.esprit.entities.Seance;
import edu.esprit.services.ServiceReservation;
import edu.esprit.services.ServiceSeance;

import java.sql.Time;

public class Main {
    public static void main(String[] args) {
        ServiceSeance ss=new ServiceSeance();
        ServiceReservation sr=new ServiceReservation();
        Seance s1=new Seance("Boxe", Time.valueOf("12:00:00"),"Lundi",1,"1:30h");
        Seance s2=new Seance("BodyPump", Time.valueOf("20:00:00"),"Mercredi",3,"1:00h");
        Seance s3=new Seance("TRX", Time.valueOf("10:30:00"),"Samedi",1,"30min");
        Reservation r1= new Reservation(s2,"Hammemi","salma",15,67,1.64f, "FEMME");
        Reservation r2= new Reservation(s2,"bennaser","Selim",22,69,1.75f, "HOMME");
        Reservation r3= new Reservation(s3,"bennaser","Selim",22,69,1.75f, "HOMME");

        //ajout d'une seance
     /*   ss.ajouter(s3);
        System.out.println("******seances********");
       System.out.println(ss.getAll());
        System.out.println("******************************************************");*/

        //modifier une seance
       /* Seance SeanceAMettreAJour = new Seance(9,"TRX",Time.valueOf("19:00:00"),"Samedi",1,"30min" );
        ss.modifier(SeanceAMettreAJour);
        System.out.println("la seace de "+ SeanceAMettreAJour.getNom()+" apres modification");
        System.out.println(ss.getAll());
        System.out.println("*****************************************************");*/


        //supprimer une seance
        /*ss.supprimer(17);
        System.out.println("-----------apres suppression seance-------------------");
        System.out.println(ss.getAll());
        System.out.println("-----------apres suppression reservation -------------------");
        System.out.println(sr.getAll());
        System.out.println("***************************************************");*/


        //getonebyid (seance)
       /* int idSeance = 9;
        Seance seanceTrouvee = ss.getOneById(idSeance);
        if (seanceTrouvee != null) {
            System.out.println("Seance trouvée : " + seanceTrouvee);
        } else {
            System.out.println("Aucune personne trouvée pour l'identifiant " + idSeance);
        }
        System.out.println("***********************************************");*/


         //getall (Seance)
       /* System.out.println(ss.getAll());
        System.out.println("**********************************************");*/

        //ajouter une reservation

        //1ere methode
       /* Seance seance=ss.getOneById(12);
        Reservation R= new Reservation(seance,"fekih","sarra",15,55.5f,15,"femme");
        sr.ajouter(R);
        System.out.println(sr.getAll());*/

        //2eme methode
        /* sr.ajouterReservation(r4,s3);
        //sr.ajouterReservation(r2,s1);
         System.out.println(sr.getAll());
        System.out.println("**********************************************");*/


        //supprimer reservation
   /*     int idResasupprimer=sr.getIdReservationByNom(r2.getNom());
        System.out.println("l id a supprimer: "+idResasupprimer);
        sr.supprimer((idResasupprimer));
        System.out.println("-----------apres suppression-------------------");
        System.out.println(sr.getAll());

        System.out.println("**********************************************");*/

        //modifier une reservation
        //1eere methode
       // System.out.println("avant modification: "+sr.getOneById(33));
        /*Reservation ReservationAMettreAJour = new Reservation(s3,"jandoubi","rania",8,56,1.75f, "femme");
        sr.modifier(ReservationAMettreAJour);
        System.out.println("la reservation de "+ ReservationAMettreAJour.getNom()+ " son poids "+ReservationAMettreAJour.getPoids()+" apres modification");
        System.out.println(sr.getAll());
        System.out.println("*****************************************************");*/
        //2eme methode
      /*  Reservation r4= new Reservation(s3,"jandoubi","maryem",10,40,1.75f, "femme");

        Reservation ReservationAMettreAJour = new Reservation(36,s3,"hmida","safi",88,56,1.75f, "homme");
        System.out.println("id seance "+s3.getIdseance());
        sr.modifier(ReservationAMettreAJour);*/
        //sr.getOneById(35);
        //System.out.println(sr.getAll());




        //recuperer une reservation

       /* System.out.println(sr.getOneById(sr.getIdReservationByNom("jandoubi")));
        System.out.println("********************************************");*/

    }
}
