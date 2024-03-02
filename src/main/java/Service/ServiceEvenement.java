package Service;

import Utils.DataSource;
import controllers.CalendarActivity;
import entities.Evenement;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceEvenement implements IService<Evenement> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Evenement eve ) throws SQLException {
        /*String req = "INSERT INTO `evenement`(`nom_eve`, `date_deve`,`date_feve`, `nbr_max`,`adresse_eve`,`image_eve`) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1,eve.getNom_eve());
        ps.setDate(2, new Date(eve.getDated_eve().getTime())); // Conversion de java.util.Date en java.sql.Date
        ps.setDate(3, new Date(eve.getDatef_eve().getTime()));
        ps.setInt(4,eve.getNbr_max());
        ps.setString(5,eve.getAdresse_eve());
        ps.setString(6, imagePath);
        ps.executeUpdate();
        System.out.println("evenement added !");*/
    }


     public void ajouter(Evenement eve ,String imagePath ) throws SQLException{



        String req = "INSERT INTO `evenement`(`nom_eve`, `date_deve`,`date_feve`, `nbr_max`,`adresse_eve`,`image_eve`) VALUES (?,?,?,?,?,?)";
       PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1,eve.getNom_eve());
       ps.setDate(2, new Date(eve.getDated_eve().getTime())); // Conversion de java.util.Date en java.sql.Date
        ps.setDate(3, new Date(eve.getDatef_eve().getTime()));
        ps.setInt(4,eve.getNbr_max());
        ps.setString(5,eve.getAdresse_eve());
       ps.setString(6, imagePath);
        ps.executeUpdate();
        System.out.println("evenement added !");

   }



    @Override
    public void modifier(Evenement eve) throws SQLException{
        String req = "UPDATE evenement SET nom_eve = ?, date_deve = ?, date_feve = ?, nbr_max = ?, adresse_eve = ?, image_eve = ? WHERE id_eve = ?";
        /*try {
            PreparedStatement preparedStatement = cnx.prepareStatement(req);
            preparedStatement.setString(1, eve.getNom_eve());
            preparedStatement.setDate(2, new Date(eve.getDated_eve().getTime()));
            preparedStatement.setDate(3, new Date(eve.getDatef_eve().getTime()));
            preparedStatement.setInt(4, eve.getNbr_max());
            preparedStatement.setString(5, eve.getAdresse_eve());
            preparedStatement.setString(6, eve.getImage_eve());
            preparedStatement.setInt(7, eve.getId_eve()); // Spécification de l'ID de l'événement à modifier

            preparedStatement.executeUpdate();
            System.out.println("Evenement updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        PreparedStatement preparedStatement = cnx.prepareStatement(req);
        preparedStatement.setString(1, eve.getNom_eve());
        preparedStatement.setDate(2, new Date(eve.getDated_eve().getTime()));
        preparedStatement.setDate(3, new Date(eve.getDatef_eve().getTime()));
        preparedStatement.setInt(4, eve.getNbr_max());
        preparedStatement.setString(5, eve.getAdresse_eve());
        preparedStatement.setString(6, eve.getImage_eve());
        preparedStatement.setInt(7, eve.getId_eve()); // Spécification de l'ID de l'événement à modifier

        preparedStatement.executeUpdate();
        System.out.println("Evenement updated !");
    }

    @Override
    public void supprimer(int id_eve) throws SQLException {
        String sql = "delete from evenement where id_eve = ?";

        PreparedStatement preparedStatement = cnx.prepareStatement(sql);
        preparedStatement.setInt(1, id_eve);
        preparedStatement.executeUpdate();
    }

    @Override
    public Evenement getOneById(int id_eve) {
        String req = "SELECT * FROM evenement WHERE id_eve = ?";
        Evenement evenement = null;
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id_eve);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                evenement = new Evenement(
                        rs.getInt("id_eve"),

                        rs.getString("nom_eve"),

                        rs.getDate("date_deve"),
                        rs.getDate("date_feve"),
                        rs.getInt("nbr_max"),
                        rs.getString("adresse_eve"),
                        rs.getString("image_eve")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return evenement;
    }

    @Override
    public Set<Evenement> getAll() throws SQLException
    {
        Set<Evenement> evenements = new HashSet<>();
        String req = "Select * from evenement";

        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        while (res.next()) {

            int id_eve = res.getInt(1);
            String nom_eve = res.getString(2);
            Date dated_eve = res.getDate(3);
            Date datef_eve = res.getDate(4);
            int nbr_max = res.getInt(5);
            String adresse_eve = res.getString(6);
            String image_eve = res.getString(7);
            Evenement evenement = new Evenement(id_eve, nom_eve, dated_eve, datef_eve, nbr_max, adresse_eve, image_eve);
            evenements.add(evenement);
            // Correction : Créer un objet CalendarActivity pour chaque événement récupéré
            List<CalendarActivity> calendarActivities = new ArrayList<>();

//            CalendarActivity calendarActivity = new CalendarActivity(ZonedDateTime.ofInstant(dated_eve.toInstant(), ZoneId.systemDefault()), nom_eve, id_eve);
//            // Ajouter l'objet CalendarActivity à la liste des activités du calendrier
         //   calendarActivities.add(calendarActivity);

        }
        return evenements;
    }


    public Evenement getEvenementParNom(String nomEvenement) throws SQLException {

        String query = "SELECT * FROM evenement WHERE nom_eve = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, nomEvenement);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id_eve");
                String nom = resultSet.getString("nom_eve");
                Date dated = resultSet.getDate("dated_eve");
                Date datef = resultSet.getDate("datef_eve");
                int nbrMax = resultSet.getInt("nbr_max");
                String adresse = resultSet.getString("adresse_eve");
                String image = resultSet.getString("image_eve");
                return new Evenement(id, nom, dated, datef, nbrMax, adresse, image);
            }
        }
        return null; // Si aucun événement trouvé avec le nom donné
    }


//    public Set<Evenement> rechercher(Evenement evenement) throws SQLException {
//        Set<Evenement> resultats = new HashSet<>();
//
//        // Construisez votre requête SQL pour rechercher dans la base de données en utilisant les valeurs de l'objet Evenement
//        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM evenement WHERE  ");
//
//        // Ajoutez une condition pour l'attribut nom_eve s'il n'est pas null
//        if (evenement.getNom_eve() != null && !evenement.getNom_eve().isEmpty()) {
//            queryBuilder.append("(nom_eve LIKE '%").append(evenement.getNom_eve()).append("%')");
//        }
//
//        // Ajoutez d'autres conditions pour d'autres attributs si nécessaire
//        if (evenement.getDated_eve() != null) {
//            if (quaeryBuilder.length() > "SELECT * FROM evenement WHERE ".length()) {
//                // S'il y a déjà une condition ajoutée, ajoutez un "AND" avant la nouvelle condition
//                queryBuilder.append(" AND ");
//            }
//            queryBuilder.append("(dated_eve = ?)");
//        }
//
//        // Ajoutez des conditions pour les autres attributs de la même manière
//
//        // Exécutez la requête SQL construite
//        try (PreparedStatement statement = cnx.prepareStatement(queryBuilder.toString())) {
//            int parameterIndex = 1;
//
//            // Affectez les valeurs des paramètres pour les attributs non null de l'objet Evenement
//            if (evenement.getDated_eve() != null) {
//                statement.setDate(parameterIndex++, new Date(evenement.getDated_eve().getTime()));
//            }
//
//            // Affectez les valeurs pour les autres attributs de la même manière
//
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                int id_eve = resultSet.getInt(1);
//                String nom_eve = resultSet.getString(2);
//                Date dated_eve = resultSet.getDate(3);
//                Date datef_eve = resultSet.getDate(4);
//                int nbr_max = resultSet.getInt(5);
//                String adresse_eve = resultSet.getString(6);
//                String image_eve = resultSet.getString(7);
//                Evenement resultat = new Evenement(id_eve, nom_eve, dated_eve, datef_eve, nbr_max, adresse_eve, image_eve);
//                resultats.add(resultat);
//            }
//        }
//
//        return resultats;
//    }



    public Set<Evenement> getEvenementsByMonth(int year, int month) throws SQLException {
        Set<Evenement> evenements = new HashSet<>();
        String req = "SELECT * FROM evenement WHERE YEAR(date_deve) = ? AND MONTH(date_deve) = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, year);
            ps.setInt(2, month);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_eve = rs.getInt(1);
                String nom_eve = rs.getString(2);
                Date dated_eve = rs.getDate(3);
                Date datef_eve = rs.getDate(4);
                int nbr_max = rs.getInt(5);
                String adresse_eve = rs.getString(6);
                String image_eve = rs.getString(7);
                Evenement evenement = new Evenement(id_eve, nom_eve, dated_eve, datef_eve, nbr_max, adresse_eve, image_eve);
                evenements.add(evenement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evenements;
    }

}
