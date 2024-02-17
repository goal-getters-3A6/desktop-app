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
}
