package edu.esprit.services;

import edu.esprit.entities.Admin;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class AdminService {

    private final Connection cnx;

    public AdminService() {
        this.cnx = DataSource.getInstance().getCnx();
    }

    public void ajouterAdmin(Admin admin) {
        String req = "INSERT INTO user (nom, prenom, mail, mdp, image, role) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getPrenom());
            ps.setString(3, admin.getMail());
            ps.setString(4, admin.getMdp());
            ps.setBytes(5, admin.getImage());
            ps.setString(6, "ADMIN"); // Ajout du rôle admin
            ps.executeUpdate();
            System.out.println("Admin ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'administrateur : " + e.getMessage());
        }
    }

    public void modifierAdmin(Admin admin) {
        String req = "UPDATE user SET nom=?, prenom=?, mdp=?, mail=?, image=? WHERE id=? AND role='ADMIN'";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getPrenom());
            ps.setString(3, admin.getMdp());
            ps.setString(4, admin.getMail());
            ps.setBytes(5, admin.getImage());
            ps.setInt(6, admin.getId());
            ps.executeUpdate();
            System.out.println("Admin modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'administrateur : " + e.getMessage());
        }
    }

    public void supprimerAdmin(int id) {
        String req = "DELETE FROM user WHERE id=? AND role='ADMIN'";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Admin supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'administrateur : " + e.getMessage());
        }
    }

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
