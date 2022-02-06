package org.natour.entities;

public class Report {

    private String route_name;
    private String title;
    private String description;
    private String issuer;
    private String type;

    public Report(){

    }

    public Report(String route_name, String title, String description, String issuer, String type) {
        this.route_name = route_name;
        this.title = title;
        this.description = description;
        this.issuer = issuer;
        this.type = type;
    }

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
