package edu.esprit.services;

import edu.esprit.entities.Client;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ClientService implements IClientService<Client> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouterClient(Client client) {
        String req = "INSERT INTO user (nom, prenom, date_inscription, date_naissance, tel, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setDate(3, new java.sql.Date(client.getDate_inscription().getTime()));
            ps.setDate(4, new java.sql.Date(client.getDate_naissance().getTime()));
            ps.setInt(5, client.getTel());
            ps.setString(6, "CLIENT"); // Ajout du rôle client
            ps.executeUpdate();
            System.out.println("Client ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du client : " + e.getMessage());
        }
    }

    @Override
    public void modifierClient(Client client) {
        String req = "UPDATE user SET nom=?, prenom=?, date_inscription=?, date_naissance=?, tel=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setDate(3, new java.sql.Date(client.getDate_inscription().getTime()));
            ps.setDate(4, new java.sql.Date(client.getDate_naissance().getTime()));
            ps.setInt(5, client.getTel());
            ps.setInt(6, client.getId());
            ps.executeUpdate();
            System.out.println("Client modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du client : " + e.getMessage());
        }
    }

    @Override
    public void supprimerClient(int id) {
        String req = "DELETE FROM user WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Client supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du client : " + e.getMessage());
        }
    }

    @Override
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
                int tel = res.getInt("tel");
                return new Client(id, nom, prenom, dateInscription, dateNaissance, tel);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'obtention du client par ID : " + e.getMessage());
        }
        return null;
    }

    @Override
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
                int tel = res.getInt("tel");
                Client client = new Client(id, nom, prenom, dateInscription, dateNaissance, tel);
                clients.add(client);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des clients : " + e.getMessage());
        }
        return clients;
    }
}
