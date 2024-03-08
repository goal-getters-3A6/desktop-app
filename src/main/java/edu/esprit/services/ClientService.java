package edu.esprit.services;

import edu.esprit.entities.Client;
import edu.esprit.utils.DataSource;
import edu.esprit.utils.HashWithMD5;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientService implements IService<Client>{
    Connection cnx= DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Client p) throws SQLException{

        String req = "INSERT INTO user (nom, prenom, mail,mdp,statut,nb_tentative,image,date_naissance,date_inscription, tel, role,poids,taille,sexe) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement Ps = cnx.prepareStatement(req);

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
        Ps.setFloat(12, p.getPoids());
        Ps.setFloat(13, p.getTaille());
        Ps.setString(14, p.getSexe());
        Ps.executeUpdate();
        System.out.println("Client ajouté avec succès !");

    }

    @Override
    public void modifier(Client p) throws SQLException{
        String req = "UPDATE user SET nom=? , prenom=? , mail=? WHERE id=? AND role='CLIENT'";
        PreparedStatement Ps = cnx.prepareStatement(req);
        Ps.setString(1,p.getNom());
        Ps.setString(2, p.getPrenom());
        Ps.setString(3, p.getMail());
        Ps.setInt(4, p.getId());
        Ps.executeUpdate();
        System.out.println("Client modifié avec succès !");

    }

    @Override
    public void supprimer(int id) throws SQLException{
        String req = "DELETE FROM user WHERE id=?";

        PreparedStatement Ps = cnx.prepareStatement(req);
        Ps.setInt(1, id);
        Ps.executeUpdate();
        System.out.println("Client supprimé avec succès !");

    }

    @Override
    public Client getOneById(int id) throws SQLException{
        String req = "SELECT * FROM user WHERE id=?";

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
            Float poids=res.getFloat("poids");
            Float taille=res.getFloat("taille");
            String sexe=res.getString("sexe");
            return new Client(id, nom, prenom, mail,image, dateInscription, dateNaissance, tel,poids,taille,sexe);

        }

        return null;
    }
    public Client getOneByEmail (String email) throws SQLException{
        String req = "SELECT * FROM user WHERE mail=?";

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
            Float poids=res.getFloat("poids");
            Float taille=res.getFloat("taille");
            String sexe=res.getString("sexe");
            Boolean statut=res.getBoolean("statut");
            return new Client(id, nom, prenom, email,image, dateInscription, dateNaissance, tel,statut,poids,taille,sexe);

        }

        return null;
    }




    @Override
    public List<Client> getAll() throws SQLException {

        List<Client> clients = new ArrayList<>();
        String req = "SELECT * FROM user WHERE role='CLIENT'";

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
            Float poids=res.getFloat("poids");
            Float taille=res.getFloat("taille");
            String sexe=res.getString("sexe");

             Client client = new Client(id, nom, prenom, mail,image, dateInscription, dateNaissance, tel,poids,taille,sexe);

            clients.add(client);
        }

        return clients;
    }


}
