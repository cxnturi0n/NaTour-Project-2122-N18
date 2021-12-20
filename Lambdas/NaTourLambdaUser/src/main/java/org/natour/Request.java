package org.natour;

import org.natour.entities.User;

public class Request {

    private String httpMethod;

    private String username;

    private User user;

    public Request(){}

    public Request(String httpMethod, String username, User user) {
        this.httpMethod = httpMethod;
        this.username = username;
        this.user = user;
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

}
