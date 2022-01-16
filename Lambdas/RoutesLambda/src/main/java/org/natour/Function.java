package org.natour;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.natour.daos.RouteDAO;
import org.natour.daosimpl.RouteDAOMySql;
import org.natour.database.MySqlDB;
import org.natour.entities.Route;
import org.natour.exceptions.PersistenceException;

/*
public class Function implements RequestHandler<String, String> {
    @Override
    public String handleRequest(String request, Context context) {

        return null;
    }
}*/

public class Function{

    public static void main(String [] args){

        Route route = new Route(0, "Ciao","Desc","Fabio","Hard",20,"boh",12,true);

        RouteDAO r = new RouteDAOMySql();
        try {
            r.insert(route);
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }

    }
}
