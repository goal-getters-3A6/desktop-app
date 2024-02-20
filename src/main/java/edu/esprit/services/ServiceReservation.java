package edu.esprit.services;

import edu.esprit.entities.Reservation;
import edu.esprit.entities.Seance;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceReservation implements IService <Reservation>{
    Connection cnx= DataSource.getInstance().getCnx();
   /* public void ajouterReservation(Reservation reservation,Seance s) {

              ServiceSeance ss=new ServiceSeance();
              int idseancefk=ss.getIdSeanceByNom(s.getNom());
            String req = "INSERT INTO reservation (ids,nom,prenom,age,poids,taille,sexe ) VALUES (?,?,?,?,?,?,?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1,idseancefk);
                ps.setString(2, reservation.getNom());
                ps.setString(3, reservation.getPrenom());
                ps.setInt(4, reservation.getAge());
                ps.setFloat(5, reservation.getPoids());
                ps.setFloat(6, reservation.getTaille());
                ps.setString(7, reservation.getSexe());
                ps.executeUpdate();
                System.out.println("Réservation ajoutée !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


    }*/
    @Override
    public void ajouter(Reservation r) throws SQLException
    {
       // System.out.println("id user dans ajouter"+r.getUser().getId());
       // System.out.println(r.getSeance().getIdseance());

        String req = "INSERT INTO reservation (ids,nompersonne,prenom,age,poids,taille,sexe,iduser ) VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,r.getSeance().getIdseance());
            ps.setString(2, r.getNompersonne());
            ps.setString(3, r.getPrenom());
            ps.setInt(4, r.getAge());
            ps.setFloat(5, r.getPoids());
            ps.setFloat(6, r.getTaille());
            ps.setString(7, r.getSexe());
            ps.setInt(8, r.getUser().getId());

            ps.executeUpdate();
            System.out.println("Réservation ajoutée !");

    }

    @Override
    public void modifier(Reservation r) throws SQLException {
        //1ere methode
       /* System.out.println(r.getNom());
        int idr =getIdReservationByNom(r.getNom());
        System.out.println("idreservation a modifier"+idr);
        String req = "UPDATE reservation SET  prenom = ? , poids= ? WHERE idreservation = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, r.getPrenom());
            ps.setFloat(2, r.getPoids());
            ps.setInt(3,idr);
            ps.executeUpdate();
            System.out.println("reservation mise à jour !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        //2eme methode
        String req = "UPDATE reservation SET  nompersonne=?, prenom=?, age=?, poids=?, taille=?,sexe=? WHERE idreservation = ?";

            PreparedStatement ps = cnx.prepareStatement(req);
            System.out.println(r.getSeance());
          //  ps.setInt(1, r.getSeance().getIdseance());
            ps.setString(1, r.getNompersonne());
            ps.setString(2, r.getPrenom());
            ps.setInt(3, r.getAge());
            ps.setFloat(4, r.getPoids());
            ps.setFloat(5, r.getTaille());
            ps.setString(6,r.getSexe());
            ps.setInt(7 ,r.getIdReservation());

            ps.executeUpdate();
            System.out.println("reservation mise à jour !");


    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM reservation WHERE idreservation = ?";


            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("supprimer saye");

    }

    @Override
    public Reservation getOneById(int id) throws SQLException {
        Reservation r = null;
        String req = "SELECT * FROM reservation WHERE idreservation = ?";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                int idReservation = res.getInt("idreservation");
                int idSeance = res.getInt("ids");
               // Seance s= getOneById(idSeance).getSeance();
                Seance s= new Seance();
                ServiceSeance sr=new ServiceSeance();
                s=  sr.getOneById((idSeance));
                // System.out.println("la seance requpere est :"+s);

                String nom=res.getString("nompersonne");
                String prenom=res.getString("prenom");
                int age = res.getInt("age");
                float poids = res.getFloat("poids");
                float taille = res.getFloat("taille");
                String sexe=res.getString("sexe");
                int idUser=res.getInt("iduser");
                User u= new User();
                ClientService cs=new ClientService();
                u=cs.getClientById(idUser);
                r = new Reservation(idReservation,s,nom,prenom,age,poids,taille,sexe,u);
            }

        return r;
    }

    @Override
    public List<Reservation> getAll() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String req = "SELECT * FROM reservation";

            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int idReservation = res.getInt("idreservation");
                int idSeance = res.getInt("ids");
                System.out.println("foreign key : "+idSeance);
                Seance s= new Seance();
                ServiceSeance sr=new ServiceSeance();
                s=  sr.getOneById((idSeance));
               // System.out.println("la seance requpere est :"+s);
                String nom = res.getString("nompersonne");
                String prenom=res.getString("prenom");
                int age = res.getInt("age");
                float poids = res.getFloat("poids");
                float taille = res.getFloat("taille");
                String sexe=res.getString("sexe");
                int idUser=res.getInt("iduser");
                User u= new User();
                ClientService cs=new ClientService();
                u=cs.getClientById(idUser);
                Reservation r = new Reservation(idReservation,s,nom,prenom,age,poids,taille,sexe,u);

                  reservations.add(r);
            }

        return reservations;
    }
    public int getIdReservationByNom(String nomReservaation) {
        try {
            String query = "SELECT idreservation FROM reservation WHERE nom = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, nomReservaation);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return rs.getInt("idreservation");
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
