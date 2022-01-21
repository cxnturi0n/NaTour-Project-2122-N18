package org.natour;

import org.natour.entities.Route;

public class Request {

    private String action;
    private String id_token;
    private Route route;
    private String user_type;

    public Request(){

    }

    public Request(String action, String id_token, Route route, String user_type) {
        this.action = action;
        this.id_token = id_token;
        this.route = route;
        this.user_type = user_type;
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

    public void setAction(String action) {
        this.action = action;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
