package edu.esprit.tests;

import edu.esprit.entities.AvisEquipement;
import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceAvisEquipement;
import edu.esprit.services.ServiceEquipement;

public class Main {
    public static void main(String[] args) {
        ServiceEquipement sp = new ServiceEquipement();
        ServiceAvisEquipement sa = new ServiceAvisEquipement();
        //System.out.println(sp.getAll());
        Equipement eq = new Equipement("tapis","behia","doc","ff","cat",16);
        Equipement eq1 = new Equipement(6,"tapisss","behia","doc","hiii","ee",10);
       // sp.ajouter(eq);
       // sp.supprimer(eq1.getIdEq());
      //  sp.modifier(eq1);


     /*  Equipement equipement = sp.getOneById(eq1.getIdEq());

        // Vérification si l'équipement a été trouvé
        if (equipement != null) {
            // Affichage des informations sur l'équipement
            System.out.println("Equipement trouvé : " + equipement);
        } else {
            System.out.println("Aucun équipement trouvé avec l'ID spécifié.");
        }
*/
        AvisEquipement av = new AvisEquipement("lala",eq1);
        AvisEquipement av1 = new AvisEquipement(11,"cv",eq1);


      // sa.ajouter(av);
      //  sa.supprimer(av1.getIdAEq());
        //sa.modifier(av1);
      //  System.out.println(sa.getAll());

     /*   AvisEquipement avisEquipement = sa.getOneById(av1.getIdAEq());

        // Vérification si l'équipement a été trouvé
        if (avisEquipement != null) {
            // Affichage des informations sur l'équipement
            System.out.println("Equipement trouvé : " + avisEquipement);
        } else {
            System.out.println("Aucun équipement trouvé avec l'ID spécifié.");
        }

*/

    }
}
