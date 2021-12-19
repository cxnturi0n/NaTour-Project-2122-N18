package org.natour;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.JsonParser;
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
            return e.getMessage();
        }

        switch (request.getHttpMethod()) {
            case "GET":
                try {
                    User user = user_dao.getUserByUsername(request.getUsername());
                    return user;
                } catch (PersistenceException e) {
                    return e.getMessage();
                }
            case "POST":
                try {
                    user_dao.saveUser(request.getUser());
                    Response response = new Response("User saved successfully");
                    return response;
                } catch (PersistenceException e) {
                    return e.getMessage();
                }
        }
        return null;
    }
}

