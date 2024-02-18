package edu.esprit.services;

import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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


    public boolean register(String email, String password) {
        String req = "INSERT INTO user (mail, mdp, role) VALUES (?, ?, ?)";
        try {
            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setString(1, email);
            Ps.setString(2, password);
            Ps.setString(3, "CLIENT");
            Ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'inscription : " + e.getMessage());
        }
        return false;
    }
}
