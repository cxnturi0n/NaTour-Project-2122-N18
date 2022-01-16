package com.cinamidea.natour_2022.auth_callbacks;

public interface AuthenticationCallback{

     void handleStatus200(String response);

     void handleStatus400(String response);

     void handleStatus401(String response);

     void handleStatus500(String response);

     void handleRequestException(String message);

}