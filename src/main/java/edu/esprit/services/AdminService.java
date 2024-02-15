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
            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setString(1, admin.getNom());
            Ps.setString(2, admin.getPrenom());
            Ps.setString(3, admin.getMail());
            Ps.setString(4, admin.getMdp());
            Ps.setBytes(5, admin.getImage());
            Ps.setString(6, "ADMIN"); // Ajout du rôle admin
            Ps.executeUpdate();
            System.out.println("Admin ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'administrateur : " + e.getMessage());
        }
    }

    public void modifierAdmin(Admin admin) {
        String req = "UPDATE user SET nom=?, prenom=?, mdp=?, mail=?, image=? WHERE id=? AND role='ADMIN'";
        try {
            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setString(1, admin.getNom());
            Ps.setString(2, admin.getPrenom());
            Ps.setString(3, admin.getMdp());
            Ps.setString(4, admin.getMail());
            Ps.setBytes(5, admin.getImage());
            Ps.setInt(6, admin.getId());
            Ps.executeUpdate();
            System.out.println("Admin modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'administrateur : " + e.getMessage());
        }
    }

    public void supprimerAdmin(int id) {
        String req = "DELETE FROM user WHERE id=? AND role='ADMIN'";
        try {
            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setInt(1, id);
            Ps.executeUpdate();
            System.out.println("Admin supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'administrateur : " + e.getMessage());
        }
    }

    public Admin getAdminById(int id) {
        String req = "SELECT * FROM user WHERE id=? AND role='ADMIN'";
        try {
            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setInt(1, id);
            ResultSet res = Ps.executeQuery();
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
            PreparedStatement Ps = cnx.prepareStatement(req);
            ResultSet res = Ps.executeQuery();
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
