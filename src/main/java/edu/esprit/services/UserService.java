package edu.esprit.services;

import edu.esprit.entities.Client;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class UserService {
    private final Connection cnx;

    public UserService() {
        this.cnx = DataSource.getInstance().getCnx();
    }

    public boolean login(String email, String password) {
        String req = "SELECT * FROM user WHERE mail=?";
        try {
            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setString(1, email);
            ResultSet Rs = Ps.executeQuery();
            if (Rs.next()) {
                if (Rs.getString("mdp").equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
        }
        return false;
    }


    public List<User> findAll() {
        String req = "SELECT * FROM user";
        List<User> list = null;
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setMail(rs.getString("mail"));
                u.setMdp(rs.getString("mdp"));
                u.setImage(rs.getBytes("image"));
                u.setRole(rs.getString("role"));
                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
        return list;
    }
}
