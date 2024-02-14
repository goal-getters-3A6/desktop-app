package edu.esprit.services;

import edu.esprit.entities.Abonnement;
import java.util.Set;
public interface IService <T>{
    public void ajouter(T ab);
    public void modifier(T ab);
    public void supprimer(int id);
    public T getOneById(int id);
    public Set<T> getAll();
}