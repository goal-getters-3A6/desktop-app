package edu.esprit.services;
import edu.esprit.entities.AvisP;
import edu.esprit.entities.Plat;
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

        if (checkIfAvisExists(avis.getPlat().getIdP(), avis.getIduap().getId())) {
            System.out.println("You have already added an avis for this plat.");
            return;
        }

        // If no existing avis, proceed to add a new one
        String req = "INSERT INTO avisp (commAP, star, fav, idPlat , iduap ) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, avis.getCommAP());
            ps.setInt(2, avis.getStar());
            ps.setBoolean(3, avis.isFav());
            ps.setInt(4, avis.getPlat().getIdP());
            ps.setInt(5, avis.getIduap().getId());
            ps.executeUpdate();
            System.out.println("Avis added to the plat.");
        }
    }

    // Helper method to check if an avis already exists for the given plat and user
    private boolean checkIfAvisExists(int platId, int userId) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM avisp WHERE idPlat = ? AND iduap = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, platId);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        }
        return false; // Return false in case of an error or no records found
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
    public boolean checkIfAvisExistss(int platId, int userId) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM avisp WHERE idPlat = ? AND iduap = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, platId);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        }
        return false;
    }

    public Set<Plat> getReviewedPlatsByUser(int userId) {
        Set<Plat> reviewedPlats = new HashSet<>();
        String query = "SELECT p.* FROM plat p JOIN avisp a ON p.idP = a.idPlat WHERE ( a.iduap = ? AND a.fav = true);";
        try {
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idPlat = rs.getInt("idP");
                String nomP = rs.getString("nomP");
                float prixP = rs.getFloat("prixP");
                String descP = rs.getString("descP");
                String alergieP = rs.getString("alergieP");
                boolean etatP = rs.getBoolean("etatP");
                String photop = rs.getString("photop");
                int calories = rs.getInt("calories");
                Plat plat = new Plat(idPlat, nomP, prixP, descP, alergieP, etatP, photop, calories);
                reviewedPlats.add(plat);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving reviewed plats: " + e.getMessage());
        }
        return reviewedPlats;
    }



}
