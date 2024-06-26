package edu.esprit.services;

import edu.esprit.entities.Client;
import edu.esprit.utils.HashWithMD5;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientService implements IService<Client>{

    @Override
    public void ajouter(Client p) throws SQLException{

            String req = "INSERT INTO user (nom, prenom, mail,mdp,statut,nb_tentative,image,date_naissance,date_inscription, tel, role, poids,taille,sexe) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement Ps = cnx.prepareStatement(req);

                Ps.setString(1, p.getNom());
                Ps.setString(2, p.getPrenom());
                Ps.setString(3, p.getMail());
                Ps.setString(4, HashWithMD5.hashWithMD5(p.getMdp() ));
                Ps.setBoolean(5, p.getStatut());
                Ps.setInt(6, p.getNb_tentative());
                Ps.setString(7, p.getImage());
                Ps.setDate(8, new Date(p.getDate_naissance().getTime()));
                Ps.setDate(9, new Date(Date.valueOf(LocalDate.now()).getTime()));
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
        String req = "UPDATE user SET nom=? , prenom=? , mail=?  , statut=? , nb_tentative=? , image=? , date_naissance=? , tel=? , poids=? , taille=? , sexe=? WHERE id=? AND role='CLIENT'";
      PreparedStatement Ps = cnx.prepareStatement(req);
            Ps.setString(1,p.getNom());
            Ps.setString(2, p.getPrenom());
            Ps.setString(3, p.getMail());
            Ps.setBoolean(4, p.getStatut());
            Ps.setInt(5, p.getNb_tentative());
            Ps.setString(6, p.getImage());
            Ps.setDate(7, new Date(p.getDate_naissance().getTime()));
            Ps.setString(8, p.getTel());
            Ps.setFloat(9, p.getPoids());
            Ps.setFloat(10, p.getTaille());
            Ps.setString(11, p.getSexe());
            Ps.setInt(12, p.getId());

            Ps.executeUpdate();


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
                return new Client(id, nom, prenom, mail,image, dateInscription, dateNaissance, tel);
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
                Date dateNaissance = res.getDate("date_naissance");
                String image = res.getString("image");
                String tel = res.getString("tel");
                String sexe = res.getString("sexe");
                Float poids = res.getFloat("poids");
                Float taille = res.getFloat("taille");
                String mdp = res.getString("mdp");
                Boolean staut = res.getBoolean("statut");
                Integer nb_tentative= res.getInt("nb_tentative");
                Boolean isTfa = res.getBoolean("tfa");
                String tfaSecret = res.getString("tfa_secret");

                return new Client(id,nom,prenom,mdp,email,tel,staut,nb_tentative,image,dateNaissance,poids,taille,sexe,isTfa,tfaSecret);

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
                Client client = new Client(id, nom, prenom,mail,image, dateInscription, dateNaissance, tel);
                clients.add(client);
            }

        return clients;
    }


}
