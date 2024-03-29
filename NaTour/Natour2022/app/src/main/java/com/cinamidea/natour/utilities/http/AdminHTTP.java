package com.cinamidea.natour.utilities.http;


import okhttp3.Headers;
import okhttp3.Request;

public class AdminHTTP extends OkHTTPRequest {


    public static Request sendMail(String admin_name, String id_token, String subject, String body) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/admin/promotional-mail";

        String request_body = "{\"subject\":" + subject + ",\"body\":" + body + ",\"admin_name\":" + admin_name +"}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

       return getPostRequest(url, request_body, header);

    }

}