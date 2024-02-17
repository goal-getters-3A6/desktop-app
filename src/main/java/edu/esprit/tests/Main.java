package edu.esprit.tests;

import edu.esprit.entities.Client;
import edu.esprit.entities.Reservation;
import edu.esprit.entities.Seance;
import edu.esprit.entities.User;
import edu.esprit.services.ClientService;
import edu.esprit.services.ServiceReservation;
import edu.esprit.services.ServiceSeance;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws SQLException {

        ServiceSeance ss=new ServiceSeance();
        ClientService clientService = new ClientService();
        ServiceReservation sr=new ServiceReservation();
        User u=new User();
        User user_recupere=clientService.getClientById(2);

     //   System.out.println("****************user a ajouter***************");
      //  System.out.println(user_recupere);
        //User user_id_add = clientService.getClientById(user_recupere.getId());

       // System.out.println(clientService.getClientById(2));
        Seance s1=new Seance("Boxe", Time.valueOf("12:00:00"),"Lundi",1,"1:30h","../../../../resources/imgs/boxe.jpg");
        Seance s2=new Seance("BodyPump", Time.valueOf("20:00:00"),"Mercredi",3,"1:00h","../../../resources/imgs/bodypump.jpg");
        Seance s3=new Seance("crossfit", Time.valueOf("11:30:00"),"Dimanche",2,"45min","../../../resources/imgs/crossfit.jpg");
      //  Reservation r1= new Reservation(s2,"bechikh","emna",15,67,1.64f, "FEMME",user_recupere);
        //  Reservation r2= new Reservation(s2,"khoufi","anwer",22,69,1.75f, "HOMME",user_recupere);
      //  Reservation r3= new Reservation(s3,"bennaser","Selim",22,69,1.75f, "HOMME",Client);


        //ajout dune seance

        try {
            ss.ajouter(s3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("******seances********");
        System.out.println(ss.getAll());
        System.out.println("******************************************************");

        //modifier une seance

        Seance SeanceAMettreAJour = ss.getOneById(20);
        SeanceAMettreAJour.setDuree("60min");
        SeanceAMettreAJour.setHoraire(Time.valueOf("13:30:00"));
        try {
            ss.modifier(SeanceAMettreAJour);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("la seance de "+ SeanceAMettreAJour.getNom()+" apres modification");
        try {
            System.out.println(ss.getOneById(20));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("*****************************************************");


        //supprimer une seance
        System.out.println("liste des reservation avant suppression"+sr.getAll());
        try {
            ss.supprimer(14);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("-----------apres suppression seance-------------------");
        System.out.println(ss.getAll());
        System.out.println("-----------apres suppression reservation -------------------");
        System.out.println(sr.getAll());
        System.out.println("***************************************************");


        //getonebyid (seance)
        int idSeance = 20;
        Seance seanceTrouvee = null;
        try {
            seanceTrouvee = ss.getOneById(idSeance);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (seanceTrouvee != null) {
            System.out.println("Seance trouvée : " + seanceTrouvee);
        } else {
            System.out.println("Aucune personne trouvée pour l'identifiant " + idSeance);
        }
        System.out.println("***********************************************");


         //getall (Seance)
       /* System.out.println(ss.getAll());
        System.out.println("**********************************************");*/

        //ajouter une reservation

        //1ere methode
        Seance seance=ss.getOneById(20);
        Reservation r1= new Reservation(seance,"jabeur","ons",5,69,1.74f, "FEMME",user_recupere);
        Reservation r4= new Reservation(seance,"khoufi","anwer",22,69,1.75f, "HOMME",user_recupere);
        try {
            sr.ajouter(r1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // System.out.println("**********liste des reservations***************");

        //2eme methode
        /* sr.ajouterReservation(r4,s3);
        //sr.ajouterReservation(r2,s1);
         System.out.println(sr.getAll());
        System.out.println("**********************************************");*/


        //supprimer reservation
       int idResasupprimer=sr.getIdReservationByNom(r1.getNom());
        System.out.println("l id a supprimer: "+idResasupprimer);
        try {
            sr.supprimer((idResasupprimer));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("-----------apres suppression-------------------");
        System.out.println(sr.getAll());

       // System.out.println("**********************************************");

        //modifier une reservation

        //1eere methode
        System.out.println("avant modification: "+sr.getOneById(62));
        Reservation ReservationAMettreAJour = sr.getOneById(62);
        ReservationAMettreAJour.setNom("jabeur");
        ReservationAMettreAJour.setPoids(87.5f);
        ReservationAMettreAJour.setAge(23);
        try {
            sr.modifier(ReservationAMettreAJour);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(sr.getOneById(62));
        System.out.println("*****************************************************");

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
