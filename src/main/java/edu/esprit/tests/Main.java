package edu.esprit.tests;

import edu.esprit.entities.Client;
import edu.esprit.entities.Reservation;
import edu.esprit.entities.Seance;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceReservation;
import edu.esprit.services.ServiceSeance;

import java.sql.Time;
import java.util.Date;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        ServiceSeance ss=new ServiceSeance();
        ClientService clientService = new ClientService();
        ServiceReservation sr=new ServiceReservation();
        User u=new User();
        User user_recupere=clientService.getClientById(3);

     //   System.out.println("****************user a ajouter***************");
      //  System.out.println(user_recupere);
        //User user_id_add = clientService.getClientById(user_recupere.getId());

       // System.out.println(clientService.getClientById(2));
        Seance s1=new Seance("Boxe", Time.valueOf("12:00:00"),"Lundi",1,"1:30h");
        Seance s2=new Seance("BodyPump", Time.valueOf("20:00:00"),"Mercredi",3,"1:00h");
        Seance s3=new Seance("YOGA", Time.valueOf("11:30:00"),"Samedi",2,"30" + "45min");
      //  Reservation r1= new Reservation(s2,"bechikh","emna",15,67,1.64f, "FEMME",user_recupere);
        //  Reservation r2= new Reservation(s2,"khoufi","anwer",22,69,1.75f, "HOMME",user_recupere);
      //  Reservation r3= new Reservation(s3,"bennaser","Selim",22,69,1.75f, "HOMME",Client);


        //ajout d'une seance
       /* ss.ajouter(s3);
        System.out.println("******seances********");
       System.out.println(ss.getAll());
        System.out.println("******************************************************");*/

        //modifier une seance
       /* Seance SeanceAMettreAJour = new Seance(9,"TRX",Time.valueOf("21:00:00"),"Lundi",5,"10min" );
        ss.modifier(SeanceAMettreAJour);
        System.out.println("la seance de "+ SeanceAMettreAJour.getNom()+" apres modification");
        System.out.println(ss.getOneById(9));
        System.out.println("*****************************************************");*/


        //supprimer une seance
       /* System.out.println("liste des reservation avant suppression"+sr.getAll());
        ss.supprimer(12);
        System.out.println("-----------apres suppression seance-------------------");
        System.out.println(ss.getAll());
        System.out.println("-----------apres suppression reservation -------------------");
        System.out.println(sr.getAll());
        System.out.println("***************************************************");*/


        //getonebyid (seance)
       /* int idSeance = 20;
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
        Seance seance=ss.getOneById(20);
        Reservation r1= new Reservation(seance,"bechikh","emna",17,77,1.64f, "FEMME",user_recupere);
        Reservation r4= new Reservation(seance,"khoufi","anwer",22,69,1.75f, "HOMME",user_recupere);
        sr.ajouter(r1);
       // System.out.println("**********liste des reservations***************");

        //2eme methode
        /* sr.ajouterReservation(r4,s3);
        //sr.ajouterReservation(r2,s1);
         System.out.println(sr.getAll());
        System.out.println("**********************************************");*/


        //supprimer reservation
       /* int idResasupprimer=sr.getIdReservationByNom(r4.getNom());
        System.out.println("l id a supprimer: "+idResasupprimer);
        sr.supprimer((idResasupprimer));
        System.out.println("-----------apres suppression-------------------");
        System.out.println(sr.getAll());*/

       // System.out.println("**********************************************");

        //modifier une reservation

        //1eere methode
        /*System.out.println("avant modification: "+sr.getOneById(56));
        Reservation ReservationAMettreAJour = new Reservation(56,seance,"jandoubi","rania",8,56,1.75f, "femme",user_recupere);
        sr.modifier(ReservationAMettreAJour);
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

        //System.out.println(sr.getOneById(sr.getIdReservationByNom("jandoubi")));
       // System.out.println(sr.getOneById(56));

      //  System.out.println("********************************************");

    }
}
