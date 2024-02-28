package edu.esprit.services;

import edu.esprit.entities.Client;
import edu.esprit.entities.Reservation;
import edu.esprit.entities.Seance;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;
import edu.esprit.utils.SessionManagement;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class ServiceReservation implements IService <Reservation> {
    SessionManagement ss=new SessionManagement();

    String mail=ss.getEmail();
    UserService us=new UserService();
    User u =us.getOneByEmail(mail);
    Connection cnx = DataSource.getInstance().getCnx();

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
    public void ajouter(Reservation r) throws SQLException {

        String req = "INSERT INTO reservation (ids, nompersonne, prenompersonne, iduser) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, r.getSeance().getIdseance());
        ps.setString(2, r.getUser().getNom());
        ps.setString(3, r.getUser().getPrenom());
        ps.setInt(4, r.getUser().getId());

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
        /*String req = "UPDATE reservation SET  nompersonne=?, prenom=?,  poids=?, taille=?,sexe=? WHERE idreservation = ?";

            PreparedStatement ps = cnx.prepareStatement(req);
            System.out.println(r.getSeance());
          //  ps.setInt(1, r.getSeance().getIdseance());
            ps.setString(1, r.getUser().getPrenom());
            ps.setString(2, r.getUser().getNom());
            ps.setFloat(4, r.getPoids());
            ps.setFloat(5, r.getTaille());
            ps.setString(6,r.getSexe());
            ps.setInt(7 ,r.getIdReservation());

            ps.executeUpdate();
            System.out.println("reservation mise à jour !");*/
        String req = "UPDATE reservation SET nompersonne=?, prenompersonne=? WHERE ids=?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, r.getUser().getNom());
        ps.setString(2, r.getUser().getPrenom());

        ps.setInt(3, r.getSeance().getIdseance());

        ps.executeUpdate();
        System.out.println("Réservation modifiée !");


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
       /* Reservation r = null;
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

        return r;*/
        Reservation r = null;
        String req = "SELECT * FROM reservation WHERE idreservation = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet res = ps.executeQuery();
        if (res.next()) {
            int idReservation = res.getInt("idreservation");
            int idSeance = res.getInt("ids");
            Seance s = null;
            // Obtenez les détails de la séance en fonction de son identifiant
            ServiceSeance sr = new ServiceSeance();
            s = sr.getOneById(idSeance);

            String nom = res.getString("nompersonne");
            String prenom = res.getString("prenom");
            int idUser = res.getInt("iduser");
            // Obtenez les détails de l'utilisateur en fonction de son identifiant
            ClientService cs = new ClientService();
            User u = cs.getOneById(idUser);
            r = new Reservation(idReservation, s, u);
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
            System.out.println("foreign key : " + idSeance);
            Seance s = new Seance();
            ServiceSeance sr = new ServiceSeance();
            s = sr.getOneById((idSeance));
            // System.out.println("la seance requpere est :"+s);
            String nom = res.getString("nompersonne");
            String prenom = res.getString("prenompersonne");
            int idUser = res.getInt("iduser");
            User u = new User();
            ClientService cs = new ClientService();
            u = cs.getOneById(idUser);

            // Création de la réservation avec les informations disponibles
            Reservation r = new Reservation(idReservation, s, u);
            reservations.add(r);
        }

        return reservations;
    }
    /*public List<Reservation> getAllR(int id) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String req = "SELECT * FROM reservation WHERE idreservation = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);

        ResultSet res = ps.executeQuery();
        while (res.next()) {
            int idReservation = res.getInt("iduser");
            int idSeance = res.getInt("ids");
            System.out.println("foreign key : " + idSeance);
            Seance s = new Seance();
            ServiceSeance sr = new ServiceSeance();
            s = sr.getOneById((idSeance));

            String nom = res.getString("nompersonne");
            String prenom = res.getString("prenompersonne");
            int idUser = res.getInt("iduser");
            User u = new User();
            ClientService cs = new ClientService();
            u = cs.getOneById(idUser);

            // Création de la réservation avec les informations disponibles
            Reservation r = new Reservation(idReservation, s, u);
            reservations.add(r);
        }

        return reservations;
    }*/
  /*  public List<Reservation> getAllReservationsForClient(String email) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();

        // Récupérer l'identifiant de l'utilisateur à partir de son adresse e-mail
        UserService userService = new UserService();
        User user = userService.getOneByEmail(email);
        int userId = user.getId();

        // Requête SQL pour récupérer toutes les réservations du client
        String query = "SELECT * FROM reservation WHERE iduser = ?";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idReservation = resultSet.getInt("idreservation");
                int idSeance = resultSet.getInt("ids");

                // Récupérer la séance associée à la réservation
                ServiceSeance seanceService = new ServiceSeance();
                Seance seance = seanceService.getOneById(idSeance);

                // Créer la réservation et l'ajouter à la liste
                Reservation reservation = new Reservation(idReservation, seance, user);
                reservations.add(reservation);
            }
        }

        return reservations;
    }*/
    public List<Reservation> getReservationsByUser(String email) {
        List<Reservation> reservations = new ArrayList<>();
        String req = "SELECT reservation.idreservation, reservation.nompersonne, reservation.prenompersonne, reservation.iduser,reservation.ids " +
                "FROM reservation " +
                "INNER JOIN user ON reservation.iduser = user.id " +
                "WHERE user.mail = '" + email + "'";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int idReservation = res.getInt("idreservation");
                String nomPersonne = res.getString("nompersonne");
                String prenomPersonne = res.getString("prenompersonne");
                int idSeance = res.getInt("ids");
                int idUser = res.getInt("iduser");

                ServiceSeance seanceService = new ServiceSeance();
                Seance seance = seanceService.getOneById(idSeance);
                UserService use = new UserService();
                User u = use.getOneById(idUser);

                Reservation r = new Reservation(idReservation, seance,u);
                reservations.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reservations;
    }




    public int getIdReservationByNom(String nomReservaation) {
        try {
            String query = "SELECT idreservation FROM reservation WHERE nom = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, nomReservaation);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idreservation");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public Map<String, Integer> getReservationFrequencyMap() {
       /* Map<String, Integer> frequencyMap = new HashMap<>();

        // Parcourir la liste de toutes les séances
        try {
            for (Reservation reservation : getAll()) {

                String sex = reservation.getUser().getSexe();

                // Mettre à jour la fréquence du nom de séance dans la carte
                frequencyMap.put(sex, frequencyMap.getOrDefault(sex, 0) + 1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return frequencyMap;*/
        Map<String, Integer> frequencyMap = new HashMap<>();

        // Parcourir la liste de toutes les réservations
        try {
            for (Reservation reservation : getAll()) {
                // Vérifier le rôle de l'utilisateur associé à la réservation
                if (reservation.getUser() instanceof Client) {
                    String sex = ((Client) reservation.getUser()).getSexe();

                    // Mettre à jour la fréquence du sexe dans la carte
                    frequencyMap.put(sex, frequencyMap.getOrDefault(sex, 0) + 1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return frequencyMap;
    }

    public List<Reservation> getAllByNom(String nom) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String req = "SELECT * FROM reservation WHERE nompersonne = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, nom);

        ResultSet res = ps.executeQuery();
        while (res.next()) {
            int idReservation = res.getInt("idreservation");
            int idSeance = res.getInt("ids");
            Seance s = new Seance();
            ServiceSeance sr = new ServiceSeance();
            s = sr.getOneById(idSeance);
            String prenom = res.getString("prenom");
            int idUser = res.getInt("iduser");
            User u = new User();
            ClientService cs = new ClientService();
            u = cs.getOneById(idUser);
            Reservation r = new Reservation(idReservation, s, u);
            reservations.add(r);
        }

        return reservations;
    }

            // Méthode pour récupérer le nombre de réservations pour une séance spécifiée
            public int getNombreReservationsPourSeance(Seance seance) throws SQLException {
                // Déclaration de la connexion

                // Déclaration du compteur
                int count = 0;
                try {
                    // Récupération de la connexion
                    // Requête SQL pour compter le nombre de réservations pour la séance spécifiée
                    String query = "SELECT COUNT(*) FROM reservation WHERE ids = ?";
                    // Création de la requête préparée
                    PreparedStatement statement = cnx.prepareStatement(query);
                    statement.setInt(1, seance.getIdseance()); // Remplacez le paramètre par le bon ID de la séance
                    // Exécution de la requête
                    ResultSet resultSet = statement.executeQuery();
                    // Récupération du résultat
                    if (resultSet.next()) {
                        count = resultSet.getInt(1);
                    }
                } finally {
                    // Fermeture de la connexion
                    if (cnx != null) {
                        cnx.close();
                    }
                }
                return count;
            }
    /*public List<Reservation> getAllByMail(String email) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String req = "SELECT * FROM reservation WHERE iduser IN (SELECT id FROM client WHERE mail = ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.set(rs.getInt("idreservation"));
                // Récupérez d'autres attributs de la réservation si nécessaire
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des réservations : " + e.getMessage());
        }
        return reservations;
    }*/


}


       /* List<Reservation> reservations = new ArrayList<>();
        String req = "SELECT * FROM reservation WHERE nompersonne = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, nom);

        ResultSet res = ps.executeQuery();
        while (res.next()) {
            int idReservation = res.getInt("idreservation");
            int idSeance = res.getInt("ids");
            Seance s= new Seance();
            ServiceSeance sr=new ServiceSeance();
            s=  sr.getOneById(idSeance);
            String prenom = res.getString("prenom");
            float poids = res.getFloat("poids");
            float taille = res.getFloat("taille");
            String sexe = res.getString("sexe");
            int idUser = res.getInt("iduser");

            // Vérifier si l'utilisateur associé est un client
            User u = null;
            ClientService cs = new ClientService();
            u = cs.getOneById(idUser);
            if (u instanceof Client) {
                // Ajouter la réservation seulement si l'utilisateur est un client
                Reservation r = new Reservation(idReservation, s, u);
                reservations.add(r);
            }
        }

        return reservations;
    }
    public Reservation getReservationByNomPersonne(String nomPersonne) throws SQLException {
        String req = "SELECT * FROM reservation WHERE nompersonne = ? LIMIT 1";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, nomPersonne);

        ResultSet res = ps.executeQuery();
        if (res.next()) {
            int idReservation = res.getInt("idreservation");
            int idSeance = res.getInt("ids");
            Seance s = new Seance();
            ServiceSeance sr = new ServiceSeance();
            s = sr.getOneById(idSeance);
            String nom = res.getString("nompersonne");
            String prenom = res.getString("prenom");
            float poids = res.getFloat("poids");
            float taille = res.getFloat("taille");
            String sexe = res.getString("sexe");
            int idUser = res.getInt("iduser");
            User u = new User();
            ClientService cs = new ClientService();
            u = cs.getOneById(idUser);
            return new Reservation(idReservation, s, u);
        }

        return null; // Aucune réservation trouvée pour le nom de personne spécifié
    }

    }*/

