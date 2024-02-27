package edu.esprit.services;

import java.util.List;
import java.util.Set;
import java.sql.SQLException;
public interface IService <T>{
    public void ajouter (T Rec) throws SQLException;
    public void modifier(T Rec) throws SQLException;
    public void supprimer(int id) throws SQLException;
    public T getOneById(int id)throws SQLException;
    public List<T> getAll() throws SQLException;
}