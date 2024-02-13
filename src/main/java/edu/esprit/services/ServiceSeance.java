package edu.esprit.services;

import edu.esprit.entities.Seance;
import edu.esprit.utils.DataSource;
import java.sql.*;
import java.util.HashSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class ServiceSeance implements IService  <Seance> {
    Connection cnx= DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Seance s) {
        String req=" INSERT INTO `seance` (`nom`,`horaire`,`jourseance`,`numesalle`,`duree`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,s.getNom());
            ps.setTime(2,s.getHoraire());
            ps.setString(3,s.getJour());
            ps.setInt(4,s.getNumsalle());
            ps.setString(5,s.getDuree());
            ps.executeUpdate();
            System.out.println("Seance added !");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void modifier(Seance s) {
        String req = "UPDATE `seance` SET horaire = ?, jourseance = ? WHERE idseance = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setTime(1, s.getHoraire());
            ps.setString(2, s.getJour());
            ps.setInt(3, s.getIdseance());
            ps.executeUpdate();
            System.out.println("seance mise à jour !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `seance` WHERE idseance = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Seance supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Seance getOneById(int id) {
        Seance s = null;
        String req = "SELECT * FROM seance WHERE idseance = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String nom = res.getString("nom");
                Time horaire = res.getTime("horaire");
                String jour = res.getString("jourseance");
                int numSalle = res.getInt("numesalle");
                String duree= res.getString("duree");
                s = new Seance(id, nom,horaire,jour,numSalle,duree);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }

    @Override
    public Set<Seance> getAll() {
        Set<Seance> seances = new HashSet<>();

        String req = "Select * from seance";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt(1);
                String nom = res.getString(2);
                Time horaire = res.getTime(3);
                String jour = res.getString(4);
                int numsalle = res.getInt(5);
                String duree = res.getString(6);

                Seance s = new Seance(id,nom,horaire,jour,numsalle,duree);
                seances.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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

