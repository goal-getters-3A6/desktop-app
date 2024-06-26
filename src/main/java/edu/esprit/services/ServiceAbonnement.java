package edu.esprit.services;

import edu.esprit.entities.Abonnement;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.Date;
import java.util.*;
import java.sql.*;

public class ServiceAbonnement implements IService<Abonnement> {

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Abonnement ab) throws SQLException {
        String req = "INSERT INTO `abonnement`(`montantAb`, `dateExpirationAb`, `codePromoAb`, `typeAb`,`idU`) VALUES (?,?,?,?,?)";

            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setFloat(1,ab.getMontantAb());
            ps.setDate(2,ab.getDateExpirationAb());
            ps.setString(3,ab.getCodePromoAb());
            ps.setString(4,ab.getTypeAb());
            ps.setInt(5,ab.getUtilisateur().getId());

            ps.executeUpdate();
            System.out.println("Abonnement added !");

    }

    @Override
    public void modifier(Abonnement ab)  {

        try {
            String req ="update `abonnement` set montantAb=?,dateExpirationAb=?,codePromoAb=?,typeAb=? where idAb=" +ab.getIdA();

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setFloat(1,ab.getMontantAb());
            ps.setDate(2,ab.getDateExpirationAb());
            ps.setString(3,ab.getCodePromoAb());
            ps.setString(4,ab.getTypeAb());
           // ps.setInt(5,ab.getUtilisateur().getId());
            ps.executeUpdate();
            System.out.println("abonnements modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {
        try {
            String req3 = "DELETE FROM abonnement WHERE idAb = " + id;

            PreparedStatement ps  = cnx.prepareStatement(req3);
            Statement ste  = cnx.createStatement();
            ste.executeUpdate(req3);
            System.out.println("Abonnement supprimé !");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Abonnement getOneById(int id1)  {

        Abonnement a = null;
        String req = "SELECT * FROM abonnement " +
                "INNER JOIN user ON abonnement.idU = user.id " +
                "WHERE abonnement.idAb = " + id1;
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){

                int id = res.getInt("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                byte[] image = res.getBytes("image");
                String mail = res.getString("mail");
                int idA = res.getInt(1);

                Float montantAb = res.getFloat("montantAb");
                Date dateExpirationAb = res.getDate("dateExpirationAb");
                String codePromoAb = res.getString("codePromoAb");
                String typeAb = res.getString("typeAb");
                User e = new User();
                 a = new Abonnement(idA, montantAb,codePromoAb, typeAb, dateExpirationAb,e);


            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return a;
        }
        @Override
    public List<Abonnement> getAll()  {
        List<Abonnement> abonnements = new ArrayList<>();
            String req = "SELECT abonnement.idAb, abonnement.montantAb, abonnement.dateExpirationAb, abonnement.codePromoAb, abonnement.typeAb, user.id, user.nom, user.prenom, user.mdp, user.mail, user.image FROM abonnement INNER JOIN user ON abonnement.idU = user.id ";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                byte[] image = res.getBytes("image");
                String mail = res.getString("mail");

                int idA = res.getInt(1);
                Float montantAb = res.getFloat("montantAb");
               Date dateExpirationAb = res.getDate("dateExpirationAb");
                String codePromoAb = res.getString("codePromoAb");
                String typeAb = res.getString("typeAb");

                User e = new User(id,nom,prenom,mail, Arrays.toString(image));
                Abonnement a = new Abonnement(idA, montantAb,codePromoAb, typeAb, dateExpirationAb,e);
                abonnements.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return abonnements;
    }
   /* public List<Abonnement> getAllByUser(String email)
    {

        List<Abonnement> abonnements = new ArrayList<>();
        String req = "SELECT abonnement.idAb, abonnement.montantAb, abonnement.dateExpirationAb, abonnement.codePromoAb, abonnement.typeAb , user.nom FROM abonnement INNER JOIN user ON abonnement.idU = user.id WHERE user.mail =" +email ;
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {

                String nom = res.getString("nom");

                int idA = res.getInt(1);
                Float montantAb = res.getFloat("montantAb");
                Date dateExpirationAb = res.getDate("dateExpirationAb");
                String codePromoAb = res.getString("codePromoAb");
                String typeAb = res.getString("typeAb");

                User e = new User();
                e.setNom(nom);

                Abonnement a = new Abonnement(idA, montantAb, codePromoAb, typeAb, dateExpirationAb, e);
                abonnements.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return abonnements;
    }*/
   public List<Abonnement> getAllByUser(String email) {

       List<Abonnement> abonnements = new ArrayList<>();
       String req = "SELECT abonnement.idAb, abonnement.montantAb, abonnement.dateExpirationAb, abonnement.codePromoAb, abonnement.typeAb , user.nom FROM abonnement INNER JOIN user ON abonnement.idU = user.id WHERE user.mail = ?";

       try (PreparedStatement st = cnx.prepareStatement(req)) {
           st.setString(1, email);
           ResultSet res = st.executeQuery();

           while (res.next()) {
               String nom = res.getString("nom");
               int idA = res.getInt(1);
               Float montantAb = res.getFloat("montantAb");
               Date dateExpirationAb = res.getDate("dateExpirationAb");
               String codePromoAb = res.getString("codePromoAb");
               String typeAb = res.getString("typeAb");

               User e = new User();
               e.setNom(nom);

               Abonnement a = new Abonnement(idA, montantAb, codePromoAb, typeAb, dateExpirationAb, e);
               abonnements.add(a);
           }
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }

       return abonnements;
   }


}