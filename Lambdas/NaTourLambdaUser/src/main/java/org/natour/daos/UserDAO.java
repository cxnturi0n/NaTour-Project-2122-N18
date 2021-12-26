package org.natour.daos;

import org.natour.exceptions.PersistenceException;
import org.natour.entities.User;

public interface UserDAO {

    public void saveUser(User user)throws PersistenceException;
    public User getUserByUsername(String username)throws PersistenceException;
    public User updateUserPassword(String username, String new_password) throws PersistenceException;
}
