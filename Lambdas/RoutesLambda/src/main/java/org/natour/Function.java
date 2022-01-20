package org.natour;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import org.natour.daos.RouteDAO;
import org.natour.daosimpl.RouteDAOMySql;
import org.natour.entities.Route;
import org.natour.exceptions.PersistenceException;
import org.natour.tokens.CognitoTokens;
import org.natour.tokens.GoogleAuth;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;


/*public class Function implements RequestHandler<Request, String> {

    @Override
    public String handleRequest(Request request, Context context) {


        String id_token = request.getId_token();

        if (request.getUser_type().equalsIgnoreCase("Cognito"))
            //Throws runtime exception in case of problems
            CognitoTokens.verifyIdToken(id_token);
        else if (request.getUser_type().equalsIgnoreCase("Google")) {
            try {
                if (!GoogleAuth.isIdTokenValid(id_token))
                    throw new RuntimeException("Invalid Session");
            }catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        RouteDAO r = new RouteDAOMySql();

        String action = request.getAction();

        switch (action) {

            case "INSERT":

                Route route = request.getRoute();
                try {
                    r.insert(route);

                    return "Route inserted successfully";

                } catch (PersistenceException e) {
                    return e.getMessage();
                }

            case "GET_ALL":

                try {

                    Gson gson = new Gson();
                    List<Route> routes = r.getAll();

                    String json_routes = gson.toJson(routes);

                    return json_routes;

                } catch (PersistenceException e) {
                    return e.getMessage();
                }

            default:
                return "WRONG ACTION";

        }

    }
}*/



public class Function{

    public static void main(String [] args){



        RouteDAOMySql r = new RouteDAOMySql();
        try {
            List<Route> l = r.getN(0,3);

            for (Route x : l){
                System.out.println(x.toString());
            }

        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }

    }

}

