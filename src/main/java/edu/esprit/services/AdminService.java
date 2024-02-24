package edu.esprit.services;

import edu.esprit.entities.Admin;
import edu.esprit.utils.HashWithMD5;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminService implements IService<Admin>{
    @Override
    public void ajouter(Admin admin) throws SQLException{
        String req = "INSERT INTO user (nom, prenom, mail, mdp, image, role) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setString(1, admin.getNom());
            Ps.setString(2, admin.getPrenom());
            Ps.setString(3, admin.getMail());
            Ps.setString(4, HashWithMD5.hashWithMD5(admin.getMdp()));
            Ps.setString(5, admin.getImage());
            Ps.setString(6, "ADMIN"); // Ajout du rôle admin
            Ps.executeUpdate();
            System.out.println("Admin ajouté avec succès !");

    }
    @Override
    public void modifier(Admin admin) throws SQLException {
        String req = "UPDATE user SET nom=?, prenom=?, mdp=?, mail=?, image=? WHERE id=? AND role='ADMIN'";

            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setString(1, admin.getNom());
            Ps.setString(2, admin.getPrenom());
            Ps.setString(3,HashWithMD5.hashWithMD5(admin.getMdp()));
            Ps.setString(4, admin.getMail());
            Ps.setString(5, admin.getImage());
            Ps.setInt(6, admin.getId());
            Ps.executeUpdate();
            System.out.println("Admin modifié avec succès !");

    }
    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM user WHERE id=? AND role='ADMIN'";

            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setInt(1, id);
            Ps.executeUpdate();
            System.out.println("Admin supprimé avec succès !");

    }

    @Override
    public Admin getOneById(int id) throws SQLException{
        String req = "SELECT * FROM user WHERE id=? AND role='ADMIN'";

            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setInt(1, id);
            ResultSet res = Ps.executeQuery();
            if (res.next()) {
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String mdp = res.getString("mdp");
                String mail = res.getString("mail");
                String image = res.getString("image");
                return new Admin(id, nom, prenom, mdp, mail, image);
            }
        return null;
    }

    public List<Admin> getAll()  throws SQLException {
        List<Admin> admins = new ArrayList<>();
        String req = "SELECT * FROM user WHERE role='ADMIN'";
            PreparedStatement Ps = cnx.prepareStatement(req);
            ResultSet res = Ps.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String mdp = res.getString("mdp");
                String mail = res.getString("mail");
                String image = res.getString("image");
                Admin admin = new Admin(id, nom, prenom, mdp, mail, image);
                admins.add(admin);
            }
        return admins;
    }



    public Admin getOneByEmail(String email) throws SQLException {
        String req = "SELECT * FROM admin WHERE mail=?";

            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setString(1, email);
            ResultSet res = Ps.executeQuery();
            if (res.next()) {
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String mdp = res.getString("mdp");
                String mail = res.getString("mail");
                String image = res.getString("image");
                return new Admin(nom, prenom,mdp,email, image);
            }

        return null;
    }


}
