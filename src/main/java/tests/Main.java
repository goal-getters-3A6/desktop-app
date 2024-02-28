package tests;

import Service.ServiceParticipation;
import Service.ServiceEvenement;
import entities.Evenement;
import entities.Participation;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ServiceEvenement sp = new ServiceEvenement();
        ServiceParticipation ssp = new ServiceParticipation();

        Evenement ev1 = new Evenement("comar",new Date() , new Date(),40,"tunis","" );
        //Evenement ev2 = new Evenement(20, "evenement2", "menzah6", new Date(), new Date());
        Participation p1 = new Participation( "louay", "abidi" , 21 , "louay@gmail.com" ,ev1);
        // Participation p2 = new Participation( "hadil", "abidi" , 21 , "louay@gmail.com",2 );
        // Participation p3 = new Participation( "arrab", "semah" , 21 , "semah@gmail.com",3 );
        //System.out.println(ev1.getId_eve());
     //sp.ajouter(ev1);
        //sp.ajouter(ev2);
        // sp.supprimer(27);
        //System.out.println(sp.getAll());

        //System.out.println(sp.getOneById(3));






        /*ssp.ajouter(p1);*/
        //ssp.ajouter(p3);
        //ssp.supprimer(8);
        /////modofication d'une participation///////
      /*  Participation participationAModifier= new Participation("semaaaaah","oooohhh", 22, "semah.arab@gmail.com",2);
        participationAModifier.setId_p(1);
        ssp.modifier(participationAModifier);*/
    /*System.out.println(ssp.getOneById(11));
        System.out.println(sp.getOneById(1));*/

///////////l'affichage de tous les participations////////////

        //System.out.println(ssp.getAll());




    }

}
