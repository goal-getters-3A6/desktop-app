package edu.esprit.services;

import edu.esprit.entities.Client;
import edu.esprit.entities.Seance;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ClientService {
    Connection cnx = DataSource.getInstance().getCnx();
    public void ajouterClient(Client client) {
        String req = "INSERT INTO user (nom, prenom, mail,mdp,statut,nb_tentative,image,date_naissance,date_inscription, tel, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getMail());
            ps.setString(4, client.getMdp());
            ps.setBoolean(5, client.getStatut());
            ps.setInt(6, client.getNb_tentative());
            ps.setBytes(7, client.getImage());
            ps.setDate(8, new java.sql.Date(client.getDate_naissance().getTime()));
            ps.setDate(9, new java.sql.Date(LocalDate.now().toEpochDay()));
            ps.setString(10, client.getTel());
            ps.setString(11, "CLIENT");
            ps.executeUpdate();
            System.out.println("Client ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du client : " + e.getMessage());
        }
    }
    public Client getClientById(int id) {
        String req = "SELECT * FROM user WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                Date dateInscription = res.getDate("date_inscription");
                Date dateNaissance = res.getDate("date_naissance");
                byte[] image = res.getBytes("image");
                String mail = res.getString("mail");
                String tel = res.getString("tel");
                return new Client(id, nom, prenom, mail,image, dateInscription, dateNaissance, tel);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'obtention du client par ID : " + e.getMessage());
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
                byte[] image = res.getBytes("image");
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

}
