package edu.esprit.services;

import edu.esprit.entities.AvisEquipement;
import edu.esprit.entities.Equipement;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceAvisEquipement implements IService<AvisEquipement> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(AvisEquipement p) throws SQLException {
        String req = "INSERT INTO `avisequipement`( `commAEq` ,`idEq` ) VALUES (?,?)";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,p.getCommAEq());
            ps.setInt(2,p.getEquipement().getIdEq());
            ps.executeUpdate();
            System.out.println("Avis ajouté !");

    }

    @Override
    public void modifier(AvisEquipement p) throws SQLException{
        String req = " update avisequipement set commAEq=? , idEq=?  where idAEq=" + p.getIdAEq();

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, p.getCommAEq());
        ps.setInt(2, p.getEquipement().getIdEq());

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
    public AvisEquipement getOneById(int id) throws SQLException {
        AvisEquipement e = null;
        String req = "SELECT * FROM avisequipement " +
                "INNER JOIN equipement ON avisequipement.idEq = equipement.idEq " +
                "WHERE AvisEquipement.idAEq = " + id;

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

                e = new AvisEquipement(idAEq,commAEq, eq);
            }


        return e;
    }

    @Override
    public List<AvisEquipement> getAll() throws SQLException {
        List<AvisEquipement> avisequipement = new ArrayList<>();

        String req = "SELECT avisequipement.idAEq, avisequipement.commAEq, equipement.idEq, equipement.nomEq, equipement.descEq, equipement.docEq, equipement.imageEq, equipement.categEq, equipement.noteEq " +
                "FROM avisequipement " +
                "INNER JOIN equipement ON avisequipement.idEq = equipement.idEq";

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

                AvisEquipement aeq = new AvisEquipement(idAEq,commAEq, eq);
                avisequipement.add(aeq);
            }



        return avisequipement;
    }
/*
    public List<AvisEquipement> getAllByEquipement(int idEquipement) throws SQLException {
        List<AvisEquipement> avisequipement = new ArrayList<>();
        String req = "SELECT * FROM avisequipement WHERE idEq = ?";

        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, idEquipement);
        ResultSet res = st.executeQuery(req);

        while (res.next()){

            int idAEq = res.getInt("idAEq");
            String commAEq = res.getString("commAEq");

            AvisEquipement aeq = new AvisEquipement(idAEq,commAEq);
            avisequipement.add(aeq);
        }

        return avisequipement;

    }*/
public List<AvisEquipement> getAllByEquipement(int idEquipement) throws SQLException {
    List<AvisEquipement> avisequipement = new ArrayList<>();
    String req = "SELECT * FROM avisequipement WHERE idEq = ?";

    try (PreparedStatement st = cnx.prepareStatement(req)) {
        st.setInt(1, idEquipement);

        try (ResultSet res = st.executeQuery()) {
            while (res.next()) {
                int idAEq = res.getInt("idAEq");
                String commAEq = res.getString("commAEq");

                AvisEquipement aeq = new AvisEquipement(idAEq, commAEq);
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
