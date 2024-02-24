package edu.esprit.services;

import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.SQLException;


public interface IOtherServices <T>{
    public T getOneByEmail(String email) throws SQLException;
}