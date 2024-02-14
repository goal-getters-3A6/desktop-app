package edu.esprit.services;

import edu.esprit.entities.Equipement;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceEquipement implements IService<Equipement>{

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Equipement p) {
        String req = "INSERT INTO `equipement`(`nomEq`, `descEq`, `docEq`, `imageEq`, `categEq`, `noteEq`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,p.getNomEq());
            ps.setString(2,p.getDescEq());
            ps.setString(3,p.getDocEq());
            ps.setString(4,p.getImageEq());
            ps.setString(5,p.getCategEq());
            ps.setInt(6,p.getNoteEq());
            ps.executeUpdate();
            System.out.println("Equipement ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Equipement eq) {
        try {
            String req = " update equipement set nomEq=? , descEq=? , docEq=? , imageEq=?, categEq=? ,noteEq=?  where idEq=" + eq.getIdEq() ;
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,eq.getNomEq());
            ps.setString(2,eq.getDescEq());
            ps.setString(3,eq.getDocEq());
            ps.setString(4,eq.getImageEq());
            ps.setString(5,eq.getCategEq());
            ps.setInt(6,eq.getNoteEq());
            ps.executeUpdate();
            System.out.println("Equipement modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int idEq) {
        try {
           String req = "DELETE FROM equipement WHERE idEq = " + idEq;

            PreparedStatement ps  = cnx.prepareStatement(req);
            Statement ste  = cnx.createStatement();
            ste.executeUpdate(req);
            System.out.println("Equipement supprimé !");

        } catch (SQLException e) {
           System.err.println(e.getMessage());
       }
    }

    @Override
    public Equipement getOneById(int id) {
        Equipement e = null;
        String req = "select * from equipement where idEq = " + id;
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
               int idEq = res.getInt(1);
                String nomEq = res.getString("nomEq");
                String descEq = res.getString("descEq");
                String docEq = res.getString("docEq");
                String imageEq = res.getString("imageEq");
                String categEq = res.getString("categEq");
                int noteEq = res.getInt("noteEq");

                e = new Equipement(idEq,  nomEq,  descEq,  docEq,  imageEq,  categEq,  noteEq);


            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return e;
    }

    @Override
    public Set<Equipement> getAll() {
        Set<Equipement> equipement = new HashSet<>();

        String req = "Select * from equipement";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int idEq = res.getInt(1);
                String nomEq = res.getString("nomEq");
                String descEq = res.getString("descEq");
                String docEq = res.getString("docEq");
                String imageEq = res.getString("imageEq");
                String categEq = res.getString("categEq");
                int noteEq = res.getInt("noteEq");

                Equipement eq = new Equipement(idEq,  nomEq,  descEq,  docEq,  imageEq,  categEq,  noteEq);
                equipement.add(eq);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return equipement;
    }

}
