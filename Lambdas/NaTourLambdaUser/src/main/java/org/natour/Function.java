package org.natour;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.natour.exceptions.PersistenceException;
import org.natour.idps.Cognito;

public class Function implements RequestHandler<Request, String> {
    @Override
    public String handleRequest(Request request, Context context) {
     /*   UserDAO user_dao;
        try {
            user_dao = UserDAOFactory.getUserDAO("mysql");
        } catch (PersistenceException e) {
            throw new RuntimeException(e.getMessage());
        }*/
        switch (request.getHttpMethod()) {
          /*  case "GET":
                try {
                    String username = request.getUser().getUsername();
                    User user = user_dao.getUserByUsername(username);
                    return user!=null ? username+" is already used" : "";
                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }*/
            case "POST":
                //Signup(and return)
                if (request.getAction().equalsIgnoreCase("SIGNUP")) {
                    try {
                        Cognito.signUpUser(request.getUser());
                        //user_dao.saveUser(new_user);
                        return "User signed up successfully";
                    } catch (PersistenceException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                } else if (request.getAction().equalsIgnoreCase("CONFIRM")) {
                    //Confirm signup
                    try {
                        Cognito.confirmUser(request.getUser().getUsername(), request.getConfirmation_code());
                        return "User confirmed";
                    } catch (PersistenceException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                } else if (request.getAction().equalsIgnoreCase("SIGNIN")) {
                    try {
                        Cognito.signInUser(request.getUser().getUsername(), request.getUser().getPassword());
                        return "User signed in successfully";
                    } catch (PersistenceException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                } else if (request.getAction().equalsIgnoreCase("RESET_PWD")) {

                }
        }
        return null;
    }


}