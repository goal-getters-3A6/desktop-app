package edu.esprit.services;

import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.esprit.utils.HashWithMD5.hashWithMD5;

public class UserService implements IService<User> {

    static Connection cnx= DataSource.getInstance().getCnx();

    public Integer checklogin(String email, String password) throws SQLException {
        try {

            Statement st = cnx.createStatement();
            String query = "SELECT `id` FROM `user` WHERE `mail`='" + email + "' AND `mdp`='" + hashWithMD5(password) + "'";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                return rs.getObject(1, Integer.class);
            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void ajouter(User x) throws SQLException {

    }

    @Override
    public void modifier(User x) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public  User getOneById(int id) throws SQLException {
        String req = "SELECT * FROM user WHERE id=?";
        try {
            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setInt(1, id);
            ResultSet Rs = Ps.executeQuery();
            if (Rs.next()) {
                User u = new User();
                u.setId(Rs.getInt("id"));
                u.setNom(Rs.getString("nom"));
                u.setPrenom(Rs.getString("prenom"));
                u.setMail(Rs.getString("mail"));
                u.setMdp(Rs.getString("mdp"));
                u.setImage(Rs.getString("image"));
                u.setRole(Rs.getString("role"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
        return null;
    }
    public static   User getOneByIde(int id) throws SQLException {
        String req = "SELECT * FROM user WHERE id=?";
        try {
            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setInt(1, id);
            ResultSet Rs = Ps.executeQuery();
            if (Rs.next()) {
                User u = new User();
                u.setId(Rs.getInt("id"));
                u.setNom(Rs.getString("nom"));
                u.setPrenom(Rs.getString("prenom"));
                u.setMail(Rs.getString("mail"));
                u.setMdp(Rs.getString("mdp"));
                u.setImage(Rs.getString("image"));
                u.setRole(Rs.getString("role"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
        return null;
    }

    public User getOneByEmail(String email) {
        String req = "SELECT * FROM user WHERE mail=?";
        try {
            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setString(1, email);
            ResultSet Rs = Ps.executeQuery();
            if (Rs.next()) {
                User u = new User();
                u.setId(Rs.getInt("id"));
                u.setNom(Rs.getString("nom"));
                u.setPrenom(Rs.getString("prenom"));
                u.setMail(Rs.getString("mail"));
                u.setMdp(Rs.getString("mdp"));
                u.setImage(Rs.getString("image"));
                u.setRole(Rs.getString("role"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAll() {
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
                u.setImage(rs.getString("image"));
                u.setRole(rs.getString("role"));
                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
        return list;
    }


}