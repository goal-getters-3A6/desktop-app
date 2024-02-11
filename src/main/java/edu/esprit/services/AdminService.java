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

    private final Connection cnx;

    public AdminService() {
        this.cnx = DataSource.getInstance().getCnx();
    }

    public void ajouterAdmin(Admin admin) {
        String req = "INSERT INTO admin (nom, prenom, mdp, mail, statut, nb_tentative, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getPrenom());
            ps.setString(3, admin.getMdp());
            ps.setString(4, admin.getMail());
            ps.setBoolean(5, admin.isStatut());
            ps.setInt(6, admin.getNb_tentative());
            ps.setBytes(7, admin.getImage());
            ps.executeUpdate();
            System.out.println("Admin added successfully!");
        } catch (SQLException e) {
            System.out.println("Error while adding admin: " + e.getMessage());
        }
    }

    public void modifierAdmin(Admin admin) {
        String req = "UPDATE admin SET nom=?, prenom=?, mdp=?, mail=?, statut=?, nb_tentative=?, image=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getPrenom());
            ps.setString(3, admin.getMdp());
            ps.setString(4, admin.getMail());
            ps.setBoolean(5, admin.isStatut());
            ps.setInt(6, admin.getNb_tentative());
            ps.setBytes(7, admin.getImage());
            ps.setInt(8, admin.getId());
            ps.executeUpdate();
            System.out.println("Admin updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error while updating admin: " + e.getMessage());
        }
    }

    public void supprimerAdmin(int id) {
        String req = "DELETE FROM admin WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Admin deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error while deleting admin: " + e.getMessage());
        }
    }

    public Admin getAdminById(int id) {
        String req = "SELECT * FROM admin WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String mdp = res.getString("mdp");
                String mail = res.getString("mail");
                boolean statut = res.getBoolean("statut");
                int nbTentative = res.getInt("nb_tentative");
                byte[] image = res.getBytes("image");
                return new Admin(id, nom, prenom, mdp, mail, statut, nbTentative, image);
            }
        } catch (SQLException e) {
            System.out.println("Error while getting admin by id: " + e.getMessage());
        }
        return null;
    }

    public Set<Admin> getAllAdmins() {
        Set<Admin> admins = new HashSet<>();
        String req = "SELECT * FROM admin";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String mdp = res.getString("mdp");
                String mail = res.getString("mail");
                boolean statut = res.getBoolean("statut");
                int nbTentative = res.getInt("nb_tentative");
                byte[] image = res.getBytes("image");
                Admin admin = new Admin(id, nom, prenom, mdp, mail, statut, nbTentative, image);
                admins.add(admin);
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching admins: " + e.getMessage());
        }
        return admins;
    }
}
