package org.natour;

import org.natour.entities.User;

public class Response {
    private String error_message;
    private User user;

    public Response(){}
    public Response(String message) {
        this.error_message = message;
    }
    public Response(User user) {
        this.user = user;
    }
}
