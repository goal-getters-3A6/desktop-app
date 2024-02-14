package edu.esprit.services;

import edu.esprit.entities.Admin;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class AdminService {
    Connection cnx = DataSource.getInstance().getCnx();

    public Admin getAdminById(int id) {
        String req = "SELECT * FROM user WHERE id=? AND role='ADMIN'";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String mdp = res.getString("mdp");
                String mail = res.getString("mail");
                byte[] image = res.getBytes("image");
                return new Admin(id, nom, prenom, mdp, mail, image);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'administrateur par ID : " + e.getMessage());
        }
        return null;
    }
    public Set<Admin> getAllAdmins() {
        Set<Admin> admins = new HashSet<>();
        String req = "SELECT * FROM user WHERE role='ADMIN'";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String mdp = res.getString("mdp");
                String mail = res.getString("mail");
                byte[] image = res.getBytes("image");
                Admin admin = new Admin(id, nom, prenom, mdp, mail, image);
                admins.add(admin);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des administrateurs : " + e.getMessage());
        }
        return admins;
    }
}
