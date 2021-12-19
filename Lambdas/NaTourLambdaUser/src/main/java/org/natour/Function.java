package org.natour;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.natour.daos.UserDAO;
import org.natour.daos_impl.UserDaoMySql;
import org.natour.exceptions.PersistenceException;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.natour.models.User;

public class Function implements RequestHandler<Request, Object> {
    @Override
    public Object handleRequest(Request request, Context context) {

        UserDAO user_dao;
        try {
            user_dao = new UserDaoMySql();
        } catch (PersistenceException e) {
            return "Couldn't connect with Database";
        }

        switch (request.getHttpMethod()) {
            case "GET":
                try {
                    User user = user_dao.getUserByUsername(request.getUsername());
                    return user;
                } catch (PersistenceException e) {
                    return "Couldn't fetch User!";
                }
            case "POST":
                try {
                    user_dao.saveUser(request.getUser());
                } catch (PersistenceException e) {
                    return "Couldn't save User!";
                }
        }
        return null;
    }

}

