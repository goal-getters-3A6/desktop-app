package edu.esprit.services;

import edu.esprit.entities.Client;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ClientService implements IClientService<Client> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouterClient(Client p) {

        String req = "INSERT INTO `client`(`nom`, `prenom`) VALUES (?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(2,p.getPrenom());
            ps.setString(1,p.getNom());
            ps.executeUpdate();
            System.out.println("Client added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifierClient(Client p) {

    }

    @Override
    public void supprimerClient(int id) {

    }

    @Override
    public Client getClientById(int id) {
        return null;
    }

    @Override
    public Set<Client> getAllClients() {
        Set<Client> Clients = new HashSet<>();

        String req = "Select * from client";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt(1);
                String nom = res.getString("nom");
                String prenom = res.getString(3);
                Client p = new Client(id,nom,prenom);
                Clients.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return Clients;
    }
}
