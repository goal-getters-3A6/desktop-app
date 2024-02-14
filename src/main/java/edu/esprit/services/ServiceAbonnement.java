package edu.esprit.services;

import edu.esprit.entities.Abonnement;
import edu.esprit.utils.DataSource;

import java.sql.Date;
import java.util.Set;
import java.sql.*;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceAbonnement implements IService<Abonnement> {

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Abonnement ab) {
        String req = "INSERT INTO `abonnement`(`montantAb`, `dateExpirationAb`, `codePromoAb`, `typeAb`) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setFloat(1,ab.getMontantAb());
            ps.setDate(2,ab.getDateExpirationAb());
            ps.setString(3,ab.getCodePromoAb());
            ps.setString(4,ab.getTypeAb());

            ps.executeUpdate();
            System.out.println("Abonnement added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void modifier(Abonnement ab) {
        try {
            String req ="update `abonnement` set montantAb=?,dateExpirationAb=?,codePromoAb=?,typeAb=? where idAb=" +ab.getIdA();
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setFloat(1,ab.getMontantAb());
            ps.setDate(2,ab.getDateExpirationAb());
            ps.setString(3,ab.getCodePromoAb());
            ps.setString(4,ab.getTypeAb());
            ps.executeUpdate();
            System.out.println("abonnements modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {
        try {
            String req3 = "DELETE FROM abonnement WHERE idAb = " + id;

            PreparedStatement ps  = cnx.prepareStatement(req3);
            Statement ste  = cnx.createStatement();
            ste.executeUpdate(req3);
            System.out.println("Equipement supprimé !");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Abonnement getOneById(int id) {

        Abonnement a = null;
        String req = "select * from abonnement where idAb = " + id;
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int idA = res.getInt(1);
                Float montantAb = res.getFloat("montantAb");
                Date dateExpirationAb = res.getDate("dateExpirationAb");
                String codePromoAb = res.getString("codePromoAb");
                String typeAb = res.getString("typeAb");

                 a = new Abonnement(idA, montantAb,codePromoAb, typeAb, dateExpirationAb);


            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return a;
        }
        @Override
    public Set<Abonnement> getAll() {
        Set<Abonnement> abonnements = new HashSet<>();

        String req = "Select * from abonnement";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int idA = res.getInt(1);
                Float montantAb = res.getFloat("montantAb");
               Date dateExpirationAb = res.getDate("dateExpirationAb");
                String codePromoAb = res.getString("codePromoAb");
                String typeAb = res.getString("typeAb");

                Abonnement a = new Abonnement(idA, montantAb,codePromoAb, typeAb, dateExpirationAb);
                abonnements .add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return abonnements;
    }
}