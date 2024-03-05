package edu.esprit.services;
import edu.esprit.entities.AvisP;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServicesAvisPlat implements IService<AvisP>{
    private Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(AvisP avis) throws SQLException {
        String req = "INSERT INTO avisp (commAP, star, fav, idPlat , iduap ) VALUES (?, ?, ?, ?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, avis.getCommAP());
            ps.setInt(2, avis.getStar());
            ps.setBoolean(3, avis.isFav());
            ps.setInt(4, avis.getPlat().getIdP());
            ps.setInt(5, avis.getIduap().getId());
            ps.executeUpdate();
            System.out.println("avis ajoute au Plat");

    }



    @Override
    public void supprimer(int idap) throws SQLException{
        String req = "DELETE FROM avisp WHERE idAP = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idap);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("avis supprime !");
            } else {
                System.out.println("id incorrect");
            }

    }


    @Override
    public void modifier(AvisP avis) throws SQLException{
        String req = "UPDATE avisp SET commAP = ?, star = ?, fav = ? WHERE idAP = ?";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, avis.getCommAP());
            ps.setInt(2, avis.getStar());
            ps.setBoolean(3, avis.isFav());
            ps.setInt(4, avis.getIdAP());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("avis modifie");
            } else {
                System.out.println("ressayez");
            }

    }
    @Override
    public AvisP getOneById(int id) {
        AvisP avis = null;
        String req = "SELECT * FROM avisp WHERE idAP = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idAP = rs.getInt("idAP");
                String commAP = rs.getString("commAP");
                int star = rs.getInt("star");
                boolean fav = rs.getBoolean("fav");

                avis = new AvisP(idAP, commAP, star, fav);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving review: " + e.getMessage());
        }
        return avis;
    }

    @Override
    public List<AvisP> getAll() {
        List<AvisP> avisList = new ArrayList<>();
        String req = "SELECT * FROM avisp";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idAP = rs.getInt("idAP");
                String commAP = rs.getString("commAP");
                int star = rs.getInt("star");
                boolean fav = rs.getBoolean("fav");

                AvisP avis = new AvisP(idAP, commAP, star, fav);
                avisList.add(avis);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving reviews: " + e.getMessage());
        }
        return avisList;
    }
    public void deleteByPlatId(int platId) throws SQLException {
        String query = "DELETE FROM avisp WHERE idPlat = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, platId);
            statement.executeUpdate();
        }
    }


    public Set<AvisP> getAllP(int ID) {
        Set<AvisP> avisList = new HashSet<>();
        String req = "SELECT * FROM avisp WHERE idPlat = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idAP = rs.getInt("idAP");
                String commAP = rs.getString("commAP");
                int star = rs.getInt("star");
                boolean fav = rs.getBoolean("fav");

                AvisP avis = new AvisP(idAP, commAP, star, fav);
                avisList.add(avis);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving reviews: " + e.getMessage());
        }
        return avisList;
    }
    public Set<AvisP> getAllU(int ID) {
        Set<AvisP> avisList = new HashSet<>();
        String req = "SELECT * FROM avisp WHERE iduap = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idAP = rs.getInt("idAP");
                String commAP = rs.getString("commAP");
                int star = rs.getInt("star");
                boolean fav = rs.getBoolean("fav");

                AvisP avis = new AvisP(idAP, commAP, star, fav);
                avisList.add(avis);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving reviews: " + e.getMessage());
        }
        return avisList;
    }
    public double calculateAverageStarRating(int platId) throws SQLException {
        String query = "SELECT AVG(star) AS averageStarRating FROM avisp WHERE idPlat = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, platId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("averageStarRating");
            }
        }
        return 0; // Return 0 if no records found or an error occurred
    }


}
