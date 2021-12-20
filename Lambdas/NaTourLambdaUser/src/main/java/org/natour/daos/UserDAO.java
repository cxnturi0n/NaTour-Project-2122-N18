package org.natour.daos;

import org.natour.exceptions.PersistenceException;
import org.natour.models.User;

public interface UserDAO {

    public void saveUser(User user)throws PersistenceException;
    public User getUserByUsername(String username)throws PersistenceException;

}
