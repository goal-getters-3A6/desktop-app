package edu.esprit.services;

import java.util.Set;

public interface IService<T>{
    //Impl Admin Service
    public void ajouterAdmin(T p);
    public void modifierAdmin(T p);
    public void supprimerAdmin(int id);
    public T getAdminById(int id);
    public Set<T> getAllAdmins();

}
