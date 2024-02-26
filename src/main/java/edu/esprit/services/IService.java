package edu.esprit.services;

import edu.esprit.entities.Abonnement;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
public interface IService <T>{
    public void ajouter(T ab)throws SQLException;
    public void modifier(T ab)throws SQLException;
    public void supprimer(int id)throws SQLException;
    public T getOneById(int id)throws SQLException;
    public List<T> getAll() throws SQLException;
}