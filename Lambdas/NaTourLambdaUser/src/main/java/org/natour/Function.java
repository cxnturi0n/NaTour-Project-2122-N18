package org.natour;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.natour.dao_factories.UserDAOFactory;
import org.natour.daos.UserDAO;
import org.natour.exceptions.PersistenceException;
import org.natour.entities.User;
import org.natour.idps.Cognito;

public class Function implements RequestHandler<Request, String> {
    @Override
    public String handleRequest(Request request, Context context) {
        UserDAO user_dao;
        try {
            user_dao = UserDAOFactory.getUserDAO("mysql");
        } catch (PersistenceException e) {
            return getJsonResponse(e.getMessage(), null);
        }
        switch (request.getHttpMethod()) {
            case "GET":
                try {
                    User user = user_dao.getUserByUsername(request.getUsername());
                    return getJsonResponse(null, user);

                } catch (PersistenceException e) {
                    return getJsonResponse(e.getMessage(), null);
                }
            case "POST":
                try {
                    String email = request.getUser().getEmail();
                    String username = request.getUser().getUsername();
                    String password = request.getUser().getPassword();

                    Cognito.signUpUser(email, password, username);

                    user_dao.saveUser(request.getUser());
                    return getJsonResponse("User saved successfully", null);
                } catch (PersistenceException e) {
                    return getJsonResponse(e.getMessage(), null);
                }
        }
        return null;
    }

    public String getJsonResponse(String message, User user) {
        Response response = new Response();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json;
        if (message != null)
            response.setMessage(message);
        if (user != null)
            response.setUser(user);
        json = gson.toJson(response);
        return json;
    }

}

