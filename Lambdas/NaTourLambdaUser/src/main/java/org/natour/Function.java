package org.natour;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.natour.dao_factories.UserDAOFactory;
import org.natour.daos.UserDAO;
import org.natour.daos_impl.UserDaoMySql;
import org.natour.exceptions.PersistenceException;
import org.natour.entities.User;

public class Function implements RequestHandler<Request, String> {
    @Override
    public String handleRequest(Request request, Context context) {
        UserDAO user_dao;
        Response response_object;
        String response;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            user_dao = UserDAOFactory.getUserDAO("mysql");
        } catch (PersistenceException e) {
            return e.getMessage();
        }
        switch (request.getHttpMethod()) {
            case "GET":
                try {
                    User user = user_dao.getUserByUsername(request.getUsername());
                    response_object = new Response(user);
                    response = gson.toJson(response_object);
                    return response;

                } catch (PersistenceException e) {
                    response_object = new Response(e.getMessage());
                    response = gson.toJson(response_object);
                    return response;
                }
            case "POST":
                try {
                    user_dao.saveUser(request.getUser());
                    response_object = new Response("User saved successfully");
                    response = gson.toJson(response_object);
                    return response;
                } catch (PersistenceException e) {
                    response_object = new Response(e.getMessage());
                    response = gson.toJson(response_object);
                    return response;
                }
        }
        return null;
    }


}

