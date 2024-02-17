package edu.esprit.services;

import edu.esprit.entities.Seance;
import edu.esprit.utils.DataSource;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServiceSeance implements IService  <Seance> {
    Connection cnx= DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Seance s)  throws SQLException{


        String req=" INSERT INTO `seance` (`nom`,`horaire`,`jourseance`,`numesalle`,`duree`,`imageseance`) VALUES (?,?,?,?,?,?)";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,s.getNom());
            ps.setTime(2,s.getHoraire());
            ps.setString(3,s.getJourseance());
            ps.setInt(4,s.getNumesalle());
            ps.setString(5,s.getDuree());
            ps.setString(6,s.getImageseance());
            ps.executeUpdate();
            System.out.println("Seance added !");
    }

    @Override
    public void modifier(Seance s)  throws SQLException {

        String req = "UPDATE `seance` SET nom=?,horaire = ?, jourseance = ? , numesalle=?, duree=?,imageseance=? WHERE idseance = ?";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, s.getNom());
            ps.setTime(2, s.getHoraire());
            ps.setString(3, s.getJourseance());
            ps.setInt(4, s.getNumesalle());
            ps.setString(5, s.getDuree());
            ps.setString(6,s.getImageseance());
            ps.setInt(7,s.getIdseance());


            ps.executeUpdate();
            System.out.println("seance mise à jour !");
    }

    @Override
    public void supprimer(int id)  throws SQLException {
        String req = "DELETE FROM `seance` WHERE idseance = ?";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Seance supprimée !");
    }

    @Override
    public Seance getOneById(int id)  throws SQLException{
        Seance s = null;
        String req = "SELECT * FROM seance WHERE idseance = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String nom = res.getString("nom");
                Time horaire = res.getTime("horaire");
                String jour = res.getString("jourseance");
                int numSalle = res.getInt("numesalle");
                String duree= res.getString("duree");
                String imageseance= res.getString("imageseance");

                s = new Seance(id, nom,horaire,jour,numSalle,duree,imageseance);
            }

        return s;
    }

    @Override
    public List<Seance> getAll()  throws SQLException{
        List <Seance> seances = new ArrayList<>();

        String req = "Select * from seance";

            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt(1);
                String nom = res.getString(2);
                Time horaire = res.getTime(3);
                String jour = res.getString(4);
                int numsalle = res.getInt(5);
                String duree = res.getString(6);
                String imageseance = res.getString(7);

                Seance s = new Seance(id,nom,horaire,jour,numsalle,duree,imageseance);
                seances.add(s);
            }



        return seances;
    }
    public int getIdSeanceByNom(String nomSeance) {
        try {
            String query = "SELECT idseance FROM seance WHERE nom = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, nomSeance);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idseance");
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return -1;
    }
}

