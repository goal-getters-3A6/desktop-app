package edu.esprit.services;

import edu.esprit.entities.Client;
import edu.esprit.utils.DataSource;
import edu.esprit.utils.HashWithMD5;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientService implements IService<Client>, IOtherServices<Client>{

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
                return new Client(id, nom, prenom, mail,image, dateInscription, dateNaissance, tel);
            }

        return null;
    }
    @Override
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
                return new Client(id, nom, prenom, email,image, dateInscription, dateNaissance, tel);
            }

        return null;
    }




    @Override
    public Set<Client> getAll() throws SQLException {

        Set<Client> clients = new HashSet<>();
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

    @Override
    public List<Client> getAllList() throws SQLException {
        String req = "SELECT * FROM user WHERE role='CLIENT'";
        List<Client> list = new java.util.ArrayList<>();

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
        return list;
    }
}
