package Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IService <T>{
    public void ajouter(T p) throws SQLException;

    public void modifier(T p)throws SQLException;
    public void supprimer(int id_eve) throws SQLException;
    public T getOneById(int id);
    public Set<T> getAll() throws SQLException;

}
