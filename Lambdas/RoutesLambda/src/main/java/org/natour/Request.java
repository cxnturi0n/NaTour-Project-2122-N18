package org.natour;

import org.natour.entities.Route;

public class Request {

    private String action;
    private String id_token;
    private Route route;

    public Request(){

    }

    public Request(String action, String id_token, Route route) {
        this.action = action;
        this.id_token = id_token;
        this.route = route;
    }

    public String getAction() {
        return action;
    }

    public String getId_token() {
        return id_token;
    }

    public Route getRoute() {
        return route;
    }
}
