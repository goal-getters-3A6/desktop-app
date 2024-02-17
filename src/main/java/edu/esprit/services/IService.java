package edu.esprit.services;

import java.util.Set;

public interface IService<T>{
    //Impl Admin Service
    public void ajouterAdmin(T p);
    public void modifierAdmin(T p);
    public void supprimerAdmin(int id);
    public T getAdminById(int id);
    public Set<T> getAllAdmins();
    //Impl Client Service
    public void ajouterClient(T p);
    public void modifierClient(T p);
    public void supprimerClient(int id);
    public T getClientById(int id);
    public Set<T> getAllClients();
    //Impl UserService
    public boolean login(String email, String password);

}
