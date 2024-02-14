package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import java.util.Set;
public interface IService <T>{
    public void ajouter(T Rec);
    public void modifier(T Rec);
    public void supprimer(int id);
    public T getOneById(int id);
    public Set<T> getAll();
}