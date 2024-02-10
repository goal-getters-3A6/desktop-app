package edu.esprit.services;
import java.util.Set;

public interface IClientService<T> {
    //Impl Client service
    public void ajouterClient(T p);
    public void modifierClient(T p);
    public void supprimerClient(int id);
    public T getClientById(int id);
    public Set<T> getAllClients();
}
