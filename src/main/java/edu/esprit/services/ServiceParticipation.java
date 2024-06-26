package edu.esprit.services;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;
import edu.esprit.entities.Participation;
import edu.esprit.utils.SessionManagement;
import edu.esprit.entities.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import edu.esprit.services.UserService;
public class ServiceParticipation implements IService<Participation>{

    SessionManagement ss=new SessionManagement();
    String mail=ss.getEmail();
    UserService us=new UserService();
    User u =us.getOneByEmail(mail);


    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Participation p) throws SQLException{

        String req = "INSERT INTO `participation`( `nom_p`,`prenom_p`, `age`,`email` , `idf_event` , `id_User`) VALUES (?,?,?,?,?,?)";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1,p.getNom_p());
        ps.setString(2,p.getPrenom_p());
        ps.setInt(3,p.getAge());
        ps.setString(4,p.getEmail());
        ps.setInt(5,p.getEvent().getId_eve());
        ps.setInt(6,p.getUser().getId());
        ps.executeUpdate();
        System.out.println("participation added !");


    }




    @Override
    public void modifier(Participation p) throws SQLException {
     /*  String req = "UPDATE participation SET nom_p = ?, prenom_p = ?, age = ?, email = ?, idf_event = ? WHERE id_p = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1,p.getNom_p());
        ps.setString(2,p.getPrenom_p());
        ps.setInt(3,p.getAge());
        ps.setString(4,p.getEmail());
        ps.setInt(5,p.getEvent().getId_eve());
        ps.setInt(6, p.getId_p()); // Spécification de l'ID de participation à modifier

        ps.executeUpdate();
        System.out.println("updated !");
*/
    }

    @Override
    public void supprimer(int id_eve) {
        String sql = "delete from participation where id_p = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(sql);
            preparedStatement.setInt(1, id_eve);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Participation getOneById(int id_p) {
        /*String req = "SELECT * FROM participation WHERE id_p = ?";
        Participation participation = null;
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id_p);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ServiceEvenement se = new ServiceEvenement();
                UserService user = new UserService();

                Evenement e = se.getOneById(rs.getInt("idf_event"));
                User us = user.getOneByEmail();
                        participation = new Participation(
                        rs.getString("nom_p"),
                        rs.getString("prenom_p"),
                        rs.getInt("age"),
                        rs.getString("email"),
                        e,
                        us
                );
                participation.setId_p(rs.getInt("id_p"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return participation;*/
        return null;
    }

    @Override
    public List<Participation> getAll() throws  SQLException{
        List<Participation> participations = new ArrayList<>();

        String req = "SELECT * FROM participation";
        PreparedStatement ps = cnx.prepareStatement(req);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ServiceEvenement se = new ServiceEvenement();
            UserService userService = new UserService();

            Evenement e = se.getOneById(rs.getInt("idf_event"));
            User user = userService.getOneById(rs.getInt("id_User"));

            Participation participation = new Participation(
                    rs.getString("nom_p"),
                    rs.getString("prenom_p"),
                    rs.getInt("age"),
                    rs.getString("email"),
                    e,
                    user

            );
            participation.setId_p(rs.getInt("id_p"));
            participations.add(participation);
        }


        return participations;
    }

}