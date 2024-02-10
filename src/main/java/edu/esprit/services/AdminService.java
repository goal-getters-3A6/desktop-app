package edu.esprit.services;

import edu.esprit.entities.Admin;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AdminService implements IAdminService<Admin> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouterAdmin(Admin p) {

        String req = "INSERT INTO `admin`(`nom`, `prenom`) VALUES (?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(2,p.getPrenom());
            ps.setString(1,p.getNom());
            ps.executeUpdate();
            System.out.println("Admin added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifierAdmin(Admin p) {

    }

    @Override
    public void supprimerAdmin(int id) {

    }

    @Override
    public Admin getAdminById(int id) {
        return null;
    }

    @Override
    public Set<Admin> getAllAdmins() {
        Set<Admin> Admins = new HashSet<>();

        String req = "Select * from admin";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt(1);
                String nom = res.getString("nom");
                String prenom = res.getString(3);
                Admin p = new Admin(id,nom,prenom);
                Admins.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return Admins;
    }
}
