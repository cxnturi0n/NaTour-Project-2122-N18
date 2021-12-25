package org.natour;

import org.natour.entities.User;

public class Request {

    private String httpMethod;

    private String username;

    private User user;

    private String confirmation_code;

    public Request(){}

    public Request(String httpMethod, String username, User user, String confirmation_code) {
        this.httpMethod = httpMethod;
        this.username = username;
        this.user = user;

        this.confirmation_code = confirmation_code;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
