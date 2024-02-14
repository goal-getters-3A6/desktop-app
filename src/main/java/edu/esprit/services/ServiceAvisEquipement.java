package edu.esprit.services;

import edu.esprit.entities.AvisEquipement;
import edu.esprit.entities.Equipement;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceAvisEquipement implements IService<AvisEquipement> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(AvisEquipement p) {
        String req = "INSERT INTO `avisequipement`( `commAEq` ,`idEq` ) VALUES (?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,p.getCommAEq());
            ps.setInt(2,p.getEquipement().getIdEq());
            ps.executeUpdate();
            System.out.println("Avis ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(AvisEquipement p) {
        String req = " update avisequipement set commAEq=? , idEq=?  where idAEq=" + p.getIdAEq();
        try {
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, p.getCommAEq());
        ps.setInt(2, p.getEquipement().getIdEq());

        ps.executeUpdate();
        System.out.println("Avis modifié !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    }

    @Override
    public void supprimer(int idAEq) {
        try {
            String req = "DELETE FROM avisequipement WHERE idAEq = " + idAEq;

            PreparedStatement ps  = cnx.prepareStatement(req);
            Statement ste  = cnx.createStatement();
            ste.executeUpdate(req);
            System.out.println("Avis supprimé !");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public AvisEquipement getOneById(int id) {
        AvisEquipement e = null;
        String req = "SELECT * FROM avisequipement " +
                "INNER JOIN equipement ON avisequipement.idEq = equipement.idEq " +
                "WHERE AvisEquipement.idAEq = " + id;
        try {
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

                e = new AvisEquipement(idAEq,commAEq, eq);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return e;
    }

    @Override
    public Set<AvisEquipement> getAll() {
        Set<AvisEquipement> avisequipement = new HashSet<>();

        String req = "SELECT avisequipement.idAEq, avisequipement.commAEq, equipement.idEq, equipement.nomEq, equipement.descEq, equipement.docEq, equipement.imageEq, equipement.categEq, equipement.noteEq " +
                "FROM avisequipement " +
                "INNER JOIN equipement ON avisequipement.idEq = equipement.idEq";
        try {
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return avisequipement;
    }
}