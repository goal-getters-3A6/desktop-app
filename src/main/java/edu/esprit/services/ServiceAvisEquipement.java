package edu.esprit.services;

import edu.esprit.entities.AvisEquipement;
import edu.esprit.entities.Equipement;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceAvisEquipement implements IService<AvisEquipement> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(AvisEquipement p) throws SQLException {
        String req = "INSERT INTO `avisequipement`( `commAEq` ,`idEq` , `idUs` ) VALUES (?,?,?)";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,p.getCommAEq());
            ps.setInt(2,p.getEquipement().getIdEq());
        ps.setInt(3,p.getUser().getId());
            ps.executeUpdate();
            System.out.println("Avis ajouté !");

    }

    @Override
    public void modifier(AvisEquipement p) throws SQLException{
        String req = " update avisequipement set commAEq=?   where idAEq=" + p.getIdAEq();

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, p.getCommAEq());

        ps.executeUpdate();
        System.out.println("Avis modifié !");

    }

    @Override
    public void supprimer(int idAEq)  throws SQLException{

            String req = "DELETE FROM avisequipement WHERE idAEq = " + idAEq;

            PreparedStatement ps  = cnx.prepareStatement(req);
            Statement ste  = cnx.createStatement();
            ste.executeUpdate(req);
            System.out.println("Avis supprimé !");


    }

    @Override
    public AvisEquipement getOneById(int id1) throws SQLException {
        AvisEquipement e = null;
        String req = "SELECT * FROM avisequipement " +
                "INNER JOIN equipement ON avisequipement.idEq = equipement.idEq " +
                "INNER JOIN user ON avisequipement.idUs = user.id " +
                "WHERE avisequipement.idAEq = " + id1;

            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int idAEq = res.getInt(1);
                String commAEq = res.getString("commAEq");

                int idEq= res.getInt("idEq");
                String nomEq = res.getString("nomEq");
                String descEq = res.getString("descEq");
                String docEq = res.getString("docEq");
                String imageEq = res.getString("imageEq");
                String categEq = res.getString("categEq");
                int noteEq = res.getInt("noteEq");

                Equipement eq = new Equipement(idEq, nomEq, descEq, docEq, imageEq, categEq, noteEq );


                int id = res.getInt("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                byte[] image = res.getBytes("image");
                String mail = res.getString("mail");
                User user = new User();

                e = new AvisEquipement(idAEq,commAEq, eq ,user);
            }


        return e;
    }

    @Override
    public List<AvisEquipement> getAll() throws SQLException {
        List<AvisEquipement> avisequipement = new ArrayList<>();

        String req = "SELECT * FROM avisequipement " +
                "INNER JOIN equipement ON avisequipement.idEq = equipement.idEq " +
                "INNER JOIN user ON avisequipement.idUs = user.id";


        Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int idAEq = res.getInt(1);
                String commAEq = res.getString("commAEq");

                int idEq= res.getInt("idEq");
                String nomEq = res.getString("nomEq");
                String descEq = res.getString("descEq");
                String docEq = res.getString("docEq");
                String imageEq = res.getString("imageEq");
                String categEq = res.getString("categEq");
                int noteEq = res.getInt("noteEq");

                        Equipement eq = new Equipement(idEq, nomEq, descEq, docEq, imageEq, categEq, noteEq);

                        User user = new User();
                user.setId(res.getInt("id"));
                user.setNom(res.getString("nom"));
                user.setPrenom(res.getString("prenom"));
                user.setMail(res.getString("mail"));
                user.setMdp(res.getString("mdp"));
                user.setImage(res.getString("image"));
                user.setRole(res.getString("role"));


                AvisEquipement aeq = new AvisEquipement(idAEq,commAEq, eq,user);
                avisequipement.add(aeq);
            }



        return avisequipement;
    }

public List<AvisEquipement> getAllByEquipement(int idEquipement ) throws SQLException {
    List<AvisEquipement> avisequipement = new ArrayList<>();
    String req = "SELECT avisequipement.idAEq ,avisequipement.commAEq , user.nom AS user_nom ,user.prenom AS user_prenom FROM avisequipement INNER JOIN user ON avisequipement.idUs = user.id WHERE avisequipement.idEq = ?";

    try (PreparedStatement st = cnx.prepareStatement(req)) {
        st.setInt(1, idEquipement);

        try (ResultSet res = st.executeQuery()) {
            while (res.next()) {
                int idAEq = res.getInt("idAEq");


                String commAEq = res.getString("commAEq");
                String userNom = res.getString("user_nom");
                String userPrenom = res.getString("user_prenom");
                User user = new User(userNom , userPrenom);


                AvisEquipement aeq = new AvisEquipement( idAEq,commAEq ,user);
                avisequipement.add(aeq);
            }
        }
    }

    return avisequipement;
}

    public List<AvisEquipement> getAllIds(int idEquipement ) throws SQLException {
        List<AvisEquipement> avisequipement = new ArrayList<>();
        String req = "SELECT user.id AS user_idU FROM avisequipement INNER JOIN user ON avisequipement.idUs = user.id WHERE avisequipement.idEq = ?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, idEquipement);

            try (ResultSet res = st.executeQuery()) {
                while (res.next()) {
                    int idAEq = res.getInt("idAEq");
                    int id = res.getInt("user_idU");

                    User user = new User(id);


                    AvisEquipement aeq = new AvisEquipement( idAEq ,user);
                    avisequipement.add(aeq);
                }
            }
        }

        return avisequipement;
    }






    public void incrementLike(int idAEq) throws SQLException {
        AvisEquipement avisEquipement = getOneById(idAEq);
        avisEquipement.setLike(true);
        avisEquipement.setDislike(false);
        modifier(avisEquipement);
    }

    public void incrementDislike(int idAEq) throws SQLException {
        AvisEquipement avisEquipement = getOneById(idAEq);
        avisEquipement.setLike(false);
        avisEquipement.setDislike(true);
        modifier(avisEquipement);
    }

    public void decrementLike(int idAEq) throws SQLException {
        AvisEquipement avisEquipement = getOneById(idAEq);
        avisEquipement.setLike(false);
        modifier(avisEquipement);
    }

    public void decrementDislike(int idAEq) throws SQLException {
        AvisEquipement avisEquipement = getOneById(idAEq);
        avisEquipement.setDislike(false);
        modifier(avisEquipement);
    }



}
