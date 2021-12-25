package org.natour;

import org.natour.entities.User;

public class Response {
    private String message;
    private User user;

    public Response(){}
    public Response(String message) {
        this.message = message;
    }
    public Response(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
