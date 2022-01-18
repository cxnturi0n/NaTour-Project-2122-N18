package com.cinamidea.natour_2022.routes_callbacks;

public interface RoutesCallback {

    void handleStatus200(String response);

    void handleStatus400(String response);

    void handleStatus401(String response);

    void handleStatus500(String response);

    void handleRequestException(String message);

}