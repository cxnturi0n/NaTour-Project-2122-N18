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
                //Signup(and return)
                if (request.getUser() != null) {
                    try {
                        User new_user = request.getUser();

                        Cognito.signUpUser(new_user.getEmail(), new_user.getPassword(), new_user.getUsername());

                        user_dao.saveUser(request.getUser());
                        return getJsonResponse("User saved successfully", null);
                    } catch (PersistenceException e) {
                        return getJsonResponse(e.getMessage(), null);
                    }
                }
                //Confirm signup
                try {
                    Cognito.confirmUser(request.getUsername(), request.getConfirmation_code());
                    return getJsonResponse("User confirmed", null);
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

