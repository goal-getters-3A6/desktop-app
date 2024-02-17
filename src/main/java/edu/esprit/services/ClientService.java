package edu.esprit.services;

import edu.esprit.entities.Client;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ClientService  {

    private final Connection cnx;

    public ClientService() {
        this.cnx = DataSource.getInstance().getCnx();
    }

    private Client client;

    public void ajouterClient(Client p) {

            String req = "INSERT INTO user (nom, prenom, mail,mdp,statut,nb_tentative,image,date_naissance,date_inscription, tel, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement Ps = cnx.prepareStatement(req)) {
                Ps.setString(1, p.getNom());
                Ps.setString(2, p.getPrenom());
                Ps.setString(3, p.getMail());
                Ps.setString(4, p.getMdp());
                Ps.setBoolean(5, p.getStatut());
                Ps.setInt(6, p.getNb_tentative());
                Ps.setBytes(7, p.getImage());
                Ps.setDate(8, new java.sql.Date(p.getDate_naissance().getTime()));
                Ps.setDate(9, new java.sql.Date(LocalDate.now().toEpochDay()));
                Ps.setString(10, p.getTel());
                Ps.setString(11, "CLIENT");
                Ps.executeUpdate();
                System.out.println("Client ajouté avec succès !");
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout du client : " + e.getMessage());
            }
        }


    public void modifierClient(Client p) {
        String req = "UPDATE user SET nom=?, prenom=?, mail=?, mdp=?, statut=?, nb_tentative=?, image=?, date_naissance=?, tel=? WHERE id=? AND role='CLIENT'";
        try (PreparedStatement Ps = cnx.prepareStatement(req)) {
            Ps.setString(1, client.getNom());
            Ps.setString(2, client.getPrenom());
            Ps.setString(3, client.getMail());
            Ps.setString(4, client.getMdp());
            Ps.setBoolean(5, client.getStatut());
            Ps.setInt(6, client.getNb_tentative());
            Ps.setBytes(7, client.getImage());
            Ps.setDate(8, new java.sql.Date(client.getDate_naissance().getTime()));
            Ps.setString(9, client.getTel());
            Ps.setInt(10, client.getId());
            Ps.executeUpdate();
            System.out.println("Client modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du client : " + e.getMessage());
        }

    }


    public void supprimerClient(int id) {
        String req = "DELETE FROM user WHERE id=?";
        try {
            PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setInt(1, id);
            Ps.executeUpdate();
            System.out.println("Client supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du client : " + e.getMessage());
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
