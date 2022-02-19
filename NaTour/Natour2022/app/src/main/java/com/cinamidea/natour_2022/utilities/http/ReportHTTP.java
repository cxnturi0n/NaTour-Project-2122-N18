package com.cinamidea.natour_2022.utilities.http;

import com.cinamidea.natour_2022.entities.Report;

import okhttp3.Headers;
import okhttp3.Request;

public class ReportHTTP extends OkHTTPRequest {


    public static Request insertReport(Report report, String id_token) {

        String url = "https://t290f5jgg8.execute-api.eu-central-1.amazonaws.com/api/routes/report";

        String request_body = "{\"route_name\":" + report.getRoute_name() + ",\"title\":" + report.getTitle() +
                ",\"description\":" + report.getDescription() +
                ",\"type\":" + report.getType() +
                ",\"issuer\":" + report.getIssuer() + "}";

        Headers header = new Headers.Builder().add("Authorization", "\"" + id_token + "\"").build();

        return getPostRequest(url, request_body, header);

    }


}