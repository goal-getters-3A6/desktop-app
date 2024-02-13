package edu.esprit.services;

import edu.esprit.entities.Personne;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServicePersonne implements IService<Personne> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Personne p) {
        /*String req = "INSERT INTO `personne`(`nom`, `prenom`) VALUES ('"+p.getNom()+"','"+p.getPrenom()+"')";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Personne added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

        String req = "INSERT INTO `personne`(`nom`, `prenom`) VALUES (?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(2,p.getPrenom());
            ps.setString(1,p.getNom());
            ps.executeUpdate();
            System.out.println("Personne added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Personne p) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Personne getOneById(int id) {
        return null;
    }

    @Override
    public Set<Personne> getAll() {
        Set<Personne> personnes = new HashSet<>();

        String req = "Select * from personne";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt(1);
                String nom = res.getString("nom");
                String prenom = res.getString(3);
                Personne p = new Personne(id,nom,prenom);
                personnes.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return personnes;
    }
}
