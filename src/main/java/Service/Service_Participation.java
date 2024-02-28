package Service;

import Utils.DataSource;
import entities.Evenement;
import entities.Participation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Service_Participation implements IService<Participation>{
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Participation p) throws SQLException{

        String req = "INSERT INTO `participation`( `nom_p`,`prenom_p`, `age`,`email` , `idf_event`) VALUES (?,?,?,?,?)";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1,p.getNom_p());
        ps.setString(2,p.getPrenom_p());
        ps.setInt(3,p.getAge());
        ps.setString(4,p.getEmail());
        ps.setInt(5,p.getEvent().getId_eve());
        ps.executeUpdate();
        System.out.println("participation added !");


    }




    @Override
    public void modifier(Participation p) throws SQLException {
        String req = "UPDATE participation SET nom_p = ?, prenom_p = ?, age = ?, email = ?, idf_event = ? WHERE id_p = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1,p.getNom_p());
        ps.setString(2,p.getPrenom_p());
        ps.setInt(3,p.getAge());
        ps.setString(4,p.getEmail());
        ps.setInt(5,p.getEvent().getId_eve());
        ps.setInt(6, p.getId_p()); // Spécification de l'ID de participation à modifier

        ps.executeUpdate();
        System.out.println("updated !");

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
        String req = "SELECT * FROM participation WHERE id_p = ?";
        Participation participation = null;
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id_p);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Service_evenement se = new Service_evenement();
                Evenement e = se.getOneById(rs.getInt("idf_event"));
                participation = new Participation(
                        rs.getString("nom_p"),
                        rs.getString("prenom_p"),
                        rs.getInt("age"),
                        rs.getString("email"),
                        e
                );
                participation.setId_p(rs.getInt("id_p"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return participation;
    }

    @Override
    public Set<Participation> getAll() throws  SQLException{
        Set<Participation> participations = new HashSet<>();

        String req = "SELECT * FROM participation";
        PreparedStatement ps = cnx.prepareStatement(req);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Service_evenement se = new Service_evenement();
            Evenement e = se.getOneById(rs.getInt("idf_event"));
            Participation participation = new Participation(
                    rs.getString("nom_p"),
                    rs.getString("prenom_p"),
                    rs.getInt("age"),
                    rs.getString("email"),
                    e

            );
            participation.setId_p(rs.getInt("id_p"));
            participations.add(participation);
        }


        return participations;
    }
}