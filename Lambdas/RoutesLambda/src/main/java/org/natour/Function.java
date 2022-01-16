package org.natour;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.natour.daos.RouteDAO;
import org.natour.daosimpl.RouteDAOMySql;
import org.natour.entities.LatLng;
import org.natour.entities.Route;
import org.natour.exceptions.PersistenceException;

public class Function implements RequestHandler<Request, String> {
    @Override
    public String handleRequest(Request request, Context context) {

        Route route = request.getRoute();

        return request.getAction()+request.getId_token();

        /*RouteDAO r = new RouteDAOMySql();

        try {
            r.insert(route);
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }
        return null;*/
    }
}

/*
public class Function{

    public static void main(String [] args){

        List<LatLng> coordinates = new ArrayList<>();

        coordinates.add(new LatLng(12.4,13));
        coordinates.add(new LatLng(45.1234,90));
        coordinates.add(new LatLng(23,29.001));

        Route route = new Route("Sentiero 2", "Ciao","Fabio","Hard",45.2f,20,true,coordinates);

        RouteDAO r = new RouteDAOMySql();
        try {
            r.insert(route);
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }

    }
}
*/
