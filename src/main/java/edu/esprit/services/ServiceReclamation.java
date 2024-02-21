package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceReclamation implements IService<Reclamation> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Reclamation rec)throws SQLException {

            String req = "INSERT INTO `reclamation`(`categorieRec`, `descriptionRec`, `piéceJointeRec`, `oddRec`, `serviceRec`, `etatRec`, `idU`) VALUES (?,?,?,?,?,?,?)";

                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1,rec.getCategorieRec());
                ps.setString(2,rec.getDescriptionRec());
                ps.setString(3,rec.getPiéceJointeRec());
                ps.setString(4,rec.getOddRec());
                ps.setString(5,rec.getServiceRec());
                ps.setInt(6,rec.getEtatRec());
                ps.setInt(7,rec.getUtilisateur().getId());
                ps.executeUpdate();
                System.out.println("Reclamation  ajouté !");

        }


    @Override
    public void modifier(Reclamation rec) {
        String req = "UPDATE `reclamation` SET categorieRec=?, descriptionRec =?, piéceJointeRec=?, oddRec=?,serviceRec=?,etatRec=?,idU=? WHERE idRec = " + rec.getIdRec();
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,rec.getCategorieRec());
            ps.setString(2,rec.getDescriptionRec());
            ps.setString(3,rec.getPiéceJointeRec());
            ps.setString(4,rec.getOddRec());
            ps.setString(5,rec.getServiceRec());
            ps.setInt(6,rec.getEtatRec());
            ps.setInt(7,rec.getUtilisateur().getId());

            ps.executeUpdate();
            System.out.println("Reclamation modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM reclamation WHERE idRec = " + id;

            PreparedStatement ps  = cnx.prepareStatement(req);
            Statement ste  = cnx.createStatement();
            ste.executeUpdate(req);
            System.out.println("Reclamation supprimé !");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public Reclamation getOneById(int id1) {
Reclamation recl =null;
String req ="SELECT * FROM reclamation " +
        "INNER JOIN user ON reclamation.idU = user.id " +
                "WHERE reclamation.idRec= " + id1;

        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){

                int id = res.getInt("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                byte[] image = res.getBytes("image");
                String mail = res.getString("mail");

                int idRec = res.getInt(1);
                String categorieRec = res.getString("categorieRec");
                String descriptionRec =res.getString("descriptionRec");
                String piéceJointeRec = res.getString("piéceJointeRec");
                String oddRec =res.getString("oddRec");
                String serviceRec =res.getString("ServiceRec");
                int etatRec = res.getInt("EtatRec");
                User e = new User(id, nom, prenom,mail,image);
                 recl= new Reclamation(idRec,categorieRec,descriptionRec,piéceJointeRec,oddRec,serviceRec,etatRec,e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return recl;
    }

    @Override
    public Set<Reclamation> getAll() throws SQLException {
        Set<Reclamation> reclamations= new HashSet<>();
       String req = "SELECT reclamation.idRec, reclamation.categorieRec, reclamation.descriptionRec, reclamation.piéceJointeRec, reclamation.oddRec, reclamation.serviceRec, reclamation.etatRec, user.id, user.nom, user.prenom, user.mdp, user.mail, user.image FROM reclamation INNER JOIN user ON reclamation.idU = user.id ";

            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                byte[] image = res.getBytes("image");
                String mail = res.getString("mail");

                int idRec = res.getInt(1);
               String categorieRec = res.getString("categorieRec");
                String descriptionRec =res.getString("descriptionRec");
               String piéceJointeRec = res.getString("piéceJointeRec");
               String oddRec =res.getString("oddRec");
               String serviceRec =res.getString("ServiceRec");
                int etatRec = res.getInt("EtatRec");

                User e = new User(id, nom, prenom,mail,image);
                Reclamation recl= new Reclamation(idRec,categorieRec,descriptionRec,piéceJointeRec,oddRec,serviceRec,etatRec,e);
                reclamations.add(recl);
            }

        return reclamations;
    }
    public List<Reclamation> getAllByUser(int id)  {
        List<Reclamation> reclamations = new ArrayList<>();
        String req = "SELECT reclamation.idRec, reclamation.categorieRec, reclamation.descriptionRec, reclamation.piéceJointeRec, reclamation.oddRec, reclamation.serviceRec, reclamation.etatRec,  user.nom FROM reclamation INNER JOIN user ON reclamation.idU = user.id where reclamation.idU ="+id;
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                //int id = res.getInt("id");
                String nom = res.getString("nom");
               // String prenom = res.getString("prenom");
                //byte[] image = res.getBytes("image");
                //String mail = res.getString("mail");

                int idRec = res.getInt(1);
                String categorieRec = res.getString("categorieRec");
                String descriptionRec =res.getString("descriptionRec");
                String piéceJointeRec = res.getString("piéceJointeRec");
                String oddRec =res.getString("oddRec");
                String serviceRec =res.getString("ServiceRec");
                int etatRec = res.getInt("EtatRec");

                User e = new User(nom);
                Reclamation recl= new Reclamation(idRec,categorieRec,descriptionRec,piéceJointeRec,oddRec,serviceRec,etatRec,e);
                reclamations.add(recl);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reclamations;
    }

}
