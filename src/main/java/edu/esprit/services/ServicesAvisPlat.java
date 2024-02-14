package edu.esprit.services;
import edu.esprit.entities.AvisP;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServicesAvisPlat implements IService<AvisP>{
    private Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(AvisP avis)  {
        String req = "INSERT INTO avisp (commAP, star, fav, idPlat , iduap ) VALUES (?, ?, ?, ?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, avis.getCommAP());
            ps.setInt(2, avis.getStar());
            ps.setBoolean(3, avis.isFav());
            ps.setInt(4, avis.getIdPlat());
            ps.setInt(5, avis.getIduap());
            ps.executeUpdate();
            System.out.println("avis ajoute au Plat");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void supprimer(int idap) {
        String req = "DELETE FROM avisp WHERE idAP = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idap);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("avis supprime !");
            } else {
                System.out.println("id incorrect");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void modifier(AvisP avis) {
        String req = "UPDATE avisp SET commAP = ?, star = ?, fav = ? WHERE idAP = ?";
        try {
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
                int idPlat = rs.getInt("idPlat");
                int iduap = rs.getInt("iduap");

                avis = new AvisP(idAP, commAP, star, fav, idPlat , iduap );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving review: " + e.getMessage());
        }
        return avis;
    }

    @Override
    public Set<AvisP> getAll() {
        Set<AvisP> avisList = new HashSet<>();
        String req = "SELECT * FROM avisp";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idAP = rs.getInt("idAP");
                String commAP = rs.getString("commAP");
                int star = rs.getInt("star");
                boolean fav = rs.getBoolean("fav");
                int idPlat = rs.getInt("idPlat");
                int iduap = rs.getInt("iduap");

                AvisP avis = new AvisP(idAP, commAP, star, fav, idPlat , iduap );
                avisList.add(avis);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving reviews: " + e.getMessage());
        }
        return avisList;
    }
}
