package edu.esprit.services;

import edu.esprit.entities.Client;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;
import edu.esprit.utils.HashWithMD5;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientService  {

    private final Connection cnx;

    public ClientService() {
        this.cnx = DataSource.getInstance().getCnx();
    }


    public boolean ajouterClient(Client p) {

            String req = "INSERT INTO user (nom, prenom, mail,mdp,statut,nb_tentative,image,date_naissance,date_inscription, tel, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement Ps = cnx.prepareStatement(req)) {
                Ps.setString(1, p.getNom());
                Ps.setString(2, p.getPrenom());
                Ps.setString(3, p.getMail());
                Ps.setString(4, HashWithMD5.hashWithMD5(p.getMdp() ));
                Ps.setBoolean(5, p.getStatut());
                Ps.setInt(6, p.getNb_tentative());
                Ps.setString(7, p.getImage());
                Ps.setDate(8, new java.sql.Date(p.getDate_naissance().getTime()));
                Ps.setDate(9, new java.sql.Date(Date.valueOf(LocalDate.now()).getTime()));
                Ps.setString(10, p.getTel());
                Ps.setString(11, "CLIENT");
                Ps.executeUpdate();
                System.out.println("Client ajouté avec succès !");
                return true;
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout du client : " + e.getMessage());
                return false;
            }
        }


    public boolean modifierClient(Client p) {
        String req = "UPDATE user SET nom=? , prenom=? , mail=? WHERE id=? AND role='CLIENT'";
        try (PreparedStatement Ps = cnx.prepareStatement(req)) {
            Ps.setString(1,p.getNom());
            Ps.setString(2, p.getPrenom());
            Ps.setString(3, p.getMail());
            Ps.setInt(4, p.getId());
            Ps.executeUpdate();
            System.out.println("Client modifié avec succès !");
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du client : " + e.getMessage());
            return false;
        }

    }


    public boolean supprimerClient(int id) {
        String req = "DELETE FROM user WHERE id=?";
        try {
            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setInt(1, id);
            Ps.executeUpdate();
            System.out.println("Client supprimé avec succès !");
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du client : " + e.getMessage());
            return false;
        }
    }


    public Client getClientById(int id) {
        String req = "SELECT * FROM user WHERE id=?";
        try {
            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setInt(1, id);
            ResultSet res = Ps.executeQuery();
            if (res.next()) {
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                Date dateInscription = res.getDate("date_inscription");
                Date dateNaissance = res.getDate("date_naissance");
                String image = res.getString("image");
                String mail = res.getString("mail");
                String tel = res.getString("tel");
                return new Client(id, nom, prenom, mail,image, dateInscription, dateNaissance, tel);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'obtention du client par ID : " + e.getMessage());
        }
        return null;
    }

    public Client getClientByEmail (String email){
        String req = "SELECT * FROM user WHERE mail=?";
        try {
            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setString(1, email);
            ResultSet res = Ps.executeQuery();
            if (res.next()) {
                int id = res.getInt("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                Date dateInscription = res.getDate("date_inscription");
                Date dateNaissance = res.getDate("date_naissance");
                String image = res.getString("image");
                String tel = res.getString("tel");
                return new Client(id, nom, prenom, email,image, dateInscription, dateNaissance, tel);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'obtention du client par email : " + e.getMessage());
        }
        return null;
    }





    public Set<Client> getAllClients() {

        Set<Client> clients = new HashSet<>();
        String req = "SELECT * FROM user WHERE role='CLIENT'";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int id = res.getInt("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                Date dateInscription = res.getDate("date_inscription");
                Date dateNaissance = res.getDate("date_naissance");
               String image = res.getString("image");
                String mail = res.getString("mail");
                String tel = res.getString("tel");
                Client client = new Client(id, nom, prenom,mail,image, dateInscription, dateNaissance, tel);
                clients.add(client);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des clients : " + e.getMessage());
        }
        return clients;
    }
    public List<Client> getAllClientsList() {
        String req = "SELECT * FROM user WHERE role='CLIENT'";
        List<Client> list = new java.util.ArrayList<>();
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Client u = new Client();
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setMail(rs.getString("mail"));
                u.setMdp(rs.getString("mdp"));
                u.setImage(rs.getString("image"));
                u.setRole(rs.getString("role"));
                u.setTel(rs.getString("tel"));
                u.setDate_naissance(rs.getDate("date_naissance"));
                u.setDate_inscription(rs.getDate("date_inscription"));


                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
        return list;
    }
}
