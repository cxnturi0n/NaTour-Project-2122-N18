package org.natour;

import org.natour.entities.User;

public class Request {

    private String httpMethod;

    private User user;

    private String confirmation_code;

    private String action;

    public Request(){}

    public Request(String httpMethod, User user, String confirmation_code, String action) {
        this.httpMethod = httpMethod;
        this.user = user;
        this.confirmation_code = confirmation_code;
        this.action = action;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getConfirmation_code() {
        return confirmation_code;
    }

    public void setConfirmation_code(String confirmation_code) {
        this.confirmation_code = confirmation_code;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Request{" +
                "httpMethod='" + httpMethod + '\'' +
                ", user=" + user.toString() +
                ", confirmation_code='" + confirmation_code + '\'' +
                '}';
    }
}
