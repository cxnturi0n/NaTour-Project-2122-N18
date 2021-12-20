package org.natour.dao_factories;

import org.natour.daos.UserDAO;
import org.natour.daos_impl.UserDaoMySql;
import org.natour.exceptions.PersistenceException;

public class UserDAOFactory {

    public static UserDAO getUserDAO(String type) throws PersistenceException {
        if (type.equalsIgnoreCase("mysql")) {
            return new UserDaoMySql();
        } else {
            return null;
        }
    }

}
