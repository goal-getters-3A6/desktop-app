package edu.esprit.services;

import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.util.List;
import java.util.Set;
import java.sql.SQLException;
public interface IService <T>{
    static final Connection cnx = DataSource.getInstance().getCnx();

    public void ajouter (T x) throws SQLException;
    public void modifier(T x) throws SQLException;
    public void supprimer(int id) throws SQLException;
    public T getOneById(int id)throws SQLException;
    public List<T> getAll() throws SQLException;

}