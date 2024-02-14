package edu.esprit.services;
import edu.esprit.entities.Plat;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServicesPlat implements IService<Plat>{

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Plat p) {
        String req = "INSERT INTO `plat`(`nomP`, `prixP`, `descP`, `alergieP`, `etatP`) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, p.getNomP());
            ps.setFloat(2, p.getPrixP());
            ps.setString(3, p.getDescP());
            ps.setString(4, p.getAlergieP());
            ps.setBoolean(5, p.getEtatP());
            ps.executeUpdate();
            System.out.println("Plat ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void modifier(Plat p) {
        String req = "UPDATE plat SET nomP = ?, prixP = ?, descP = ?, alergieP = ?, etatP = ? WHERE idP = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, p.getNomP());
            ps.setFloat(2, p.getPrixP());
            ps.setString(3, p.getDescP());
            ps.setString(4, p.getAlergieP());
            ps.setBoolean(5, p.getEtatP());
            ps.setInt(6, p.getIdP()); // Assuming idP is the primary key

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Plat modifie !");
            } else {
                System.out.println("id incorrect");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int idP) {
        String req = "DELETE FROM plat WHERE idP = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idP);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Plat supprime !");
            } else {
                System.out.println("id incorrect");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public Plat getOneById(int idP) {
        Plat plat = null;
        String req = "SELECT * FROM plat WHERE idP = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idP");
                String nomP = rs.getString("nomP");
                Float prixP = rs.getFloat("prixP");
                String descP = rs.getString("descP");
                String alergieP = rs.getString("alergieP");
                Boolean etatP = rs.getBoolean("etatP");
                plat = new Plat(id, nomP); // Create a new Plat object with retrieved attributes


                System.out.println("Plat info:");
                System.out.println("ID: " + id);
                System.out.println("Nom: " + nomP);
                System.out.println("Prix: " + prixP);
                System.out.println("Description: " + descP);
                System.out.println("Allergies: " + alergieP);
                System.out.println("Etat: " + etatP);
            } else {
                System.out.println("id incofrect");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return plat;
    }


    @Override
    public Set<Plat> getAll() {
        Set<Plat> plats = new HashSet<>();

        String req = "SELECT * FROM plat";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int idP = res.getInt("idP");
                String nomP = res.getString("nomP");
                Float prixP = res.getFloat("prixP");
                String descP = res.getString("descP");
                String alergieP = res.getString("alergieP");
                Boolean etatP = res.getBoolean("etatP");
                Plat p = new Plat(idP, nomP, prixP, descP, alergieP, etatP);
                plats.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return plats;
    }
}

