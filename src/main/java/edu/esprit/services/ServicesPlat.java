package edu.esprit.services;
import edu.esprit.entities.Plat;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServicesPlat implements IService<Plat>{

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Plat p)throws SQLException {
        String req = "INSERT INTO `plat`(`nomP`, `prixP`, `descP`, `alergieP`, `etatP` , `photop` ,`calories`) VALUES (?, ?, ?, ?, ? , ? , ?)";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, p.getNomP());
            ps.setFloat(2, p.getPrixP());
            ps.setString(3, p.getDescP());
            ps.setString(4, p.getAlergieP());
            ps.setBoolean(5, p.getEtatP());
        ps.setString(6, p.getPhotop());
        ps.setInt(7, p.getCalories());
            ps.executeUpdate();
            System.out.println("Plat ajouté !");

    }
    @Override
    public void modifier(Plat p) throws SQLException{
        String req = "UPDATE plat SET nomP = ?, prixP = ?, descP = ?, alergieP = ?, etatP = ? , photop =? , calories = ? WHERE idP = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, p.getNomP());
            ps.setFloat(2, p.getPrixP());
            ps.setString(3, p.getDescP());
            ps.setString(4, p.getAlergieP());
            ps.setBoolean(5, p.getEtatP());
            ps.setString(6, p.getPhotop());
            ps.setInt(7, p.getCalories());
        ps.setInt(8, p.getIdP());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Plat modifie !");
            } else {
                System.out.println("id incorrect");
            }

    }

    @Override
    public void supprimer(int idP)  throws SQLException {
        String req = "DELETE FROM plat WHERE idP = ?";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idP);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Plat supprime !");
            } else {
                System.out.println("id incorrect");
            }

    }


    @Override
    public Plat getOneById(int idP) throws SQLException{
        Plat plat = null;
        String req = "SELECT * FROM plat WHERE idP = ?";

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
                String photop = rs.getString("photop");
                int calories= rs.getInt("calories");
                plat = new Plat(id, nomP,prixP,descP,alergieP,etatP,photop,calories); // Create a new Plat object with retrieved attributes


                System.out.println("Plat info:");
                System.out.println("ID: " + id);
                System.out.println("Nom: " + nomP);
                System.out.println("Prix: " + prixP);
                System.out.println("Description: " + descP);
                System.out.println("Allergies: " + alergieP);
                System.out.println("Etat: " + etatP);
                System.out.println("Photo: " + photop);
                System.out.println("cals: " + calories);
            } else {
                System.out.println("id incofrect");
            }

        return plat;
    }


    @Override
    public Set<Plat> getAll() throws SQLException{
        Set<Plat> plats = new HashSet<>();

        String req = "SELECT * FROM plat";

            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int idP = res.getInt("idP");
                String nomP = res.getString("nomP");
                Float prixP = res.getFloat("prixP");
                String descP = res.getString("descP");
                String alergieP = res.getString("alergieP");
                Boolean etatP = res.getBoolean("etatP");
                String photop = res.getString("photop");
                int calories = res.getInt("calories");
                Plat p = new Plat(idP, nomP, prixP, descP, alergieP, etatP, photop, calories);
                plats.add(p);
            }

        return plats;
    }
}