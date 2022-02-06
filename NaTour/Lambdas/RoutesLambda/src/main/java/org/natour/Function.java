package org.natour;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import org.natour.daos.RouteDAO;
import org.natour.daosimpl.RouteDAOMySql;
import org.natour.entities.QueryFilters;
import org.natour.entities.Route;
import org.natour.exceptions.PersistenceException;
import org.natour.s3.NatourS3Bucket;
import org.natour.tokens.CognitoTokens;
import org.natour.tokens.GoogleAuth;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.List;


public class Function implements RequestHandler<Event, String> {


    @Override
    public String handleRequest(Event event, Context context) {


        String authorization_header = event.getId_token();

        if (authorization_header.contains("Cognito"))
            CognitoTokens.verifyIdToken(authorization_header.substring(7));
        else if (authorization_header.contains("Google")) {
            try {
                if (!GoogleAuth.isIdTokenValid(authorization_header.substring(6)))
                    throw new RuntimeException("Invalid Session");
            } catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        RouteDAO r = new RouteDAOMySql();

        String action = event.getAction();

        switch (action) {

            case "GET": //

               try{
                   Gson gson = new Gson();
                   List<Route> routes = null;
                   QueryFilters query_filters = event.getQuery_filters();

                    if(!query_filters.getLevel().equals(""))//Get routes by difficulty level
                        routes = r.getRoutesByLevel(query_filters.getLevel());
                    else if(!query_filters.getCreator_username().equals(""))//Get routes created by user
                        routes = r.getUserRoutes(query_filters.getCreator_username());
                    else if(query_filters.getEnd()==0)//Get all routes
                        routes = r.getAll();
                    else if(query_filters.getEnd()!=0) //Get only a subset of routes
                        routes = r.getN(query_filters.getStart(), query_filters.getEnd());

                    String json_routes = gson.toJson(routes);

                    return json_routes;

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "GET_USER_FAVOURITES": //

                try {

                    Gson gson = new Gson();
                    List<Route> routes = r.getUserFavourites(event.getQuery_filters().getUsername());

                    String json_routes = gson.toJson(routes);

                    return json_routes;

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "GET_PERSONAL_FAVOURITES_NAMES": //

                try {

                    Gson gson = new Gson();
                    List<Route> routes = r.getUserFavouritesNames(event.getQuery_filters().getUsername());

                    String json_routes = gson.toJson(routes);

                    return json_routes;

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "GET_USER_TOVISIT": //

                try {

                    Gson gson = new Gson();
                    List<Route> routes = r.getUserToVisit(event.getQuery_filters().getUsername());

                    String json_routes = gson.toJson(routes);

                    return json_routes;

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "INSERT_PROFILE_IMAGE": //

                try{

                    NatourS3Bucket bucket = new NatourS3Bucket();

                    byte image_as_byte_array[] = Base64.getDecoder().decode(event.getProfile_image_base64());

                    bucket.putUserProfileImage(event.getQuery_filters().getUsername(), image_as_byte_array);

                    return "User profile image inserted successfully";

                }catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "GET_PROFILE_IMAGE": //

                try{

                    NatourS3Bucket bucket = new NatourS3Bucket();

                    byte image_as_byte_array [] = bucket.fetchUserProfileImage(event.getQuery_filters().getUsername());

                    return Base64.getEncoder().encodeToString(image_as_byte_array);

                }catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "INSERT": //

                try {

                    Route route = event.getRoute();
                    r.insert(route);

                    return "Route inserted successfully";

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "INSERT_USER_FAVOURITE": //

                try {

                    r.insertFavourite(event.getQuery_filters().getUsername(), event.getQuery_filters().getRoute_name());

                    return "Route inserted successfully";

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }


            case "INSERT_USER_TOVISIT": //

                try {

                    r.insertToVisit(event.getQuery_filters().getUsername(), event.getQuery_filters().getRoute_name());

                    return "Route inserted successfully";

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "INSERT_REPORT": //

                try {

                    r.insertReport(event.getReport());

                    return "Route inserted successfully";

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "DELETE_USER_FAVOURITE" : //

                try {

                    r.deleteFavourite(event.getQuery_filters().getUsername(), event.getQuery_filters().getRoute_name());

                    return "Favourite route deleted successfully";

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "DELETE_USER_TOVISIT" : //

                try {

                    r.deleteToVisit(event.getQuery_filters().getUsername(), event.getQuery_filters().getRoute_name());

                    return "Favourite route deleted successfully";

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }


            default:
                return "WRONG ACTION";

        }

    }
}





//public class Function{

 //   public static void main(String [] args) throws IOException {

/*
    GET ALL ROUTES
        Query parameters: type = "ALL"  ; Headers : Authorization:<UserType><id_token>

GET N
        Query parameters: type = "SUB", start, end  ;    Headers : Authorization:<UserType><id_token>

GET CREATED ROUTES
        Query parameters: type = "OWNED", creator_username ;    Headers : Authorization:<UserType><id_token>

GET USER FAVOURITES
        Query parameters: type = "FAVOURITES", username  ;    Headers : Authorization:<UserType><id_token>

GET USER TO VISIT
        Query parameters: type = "TO-VISIT" username  ;    Headers : Authorization:<UserType><id_token>
s
        GET BY LEVEL

        Query parameters: level  ;    Headers : Authorization:<UserType><id_token>


"report":{
        "route_name":$input.json('$.route_name'),
        "title":$input.json('$.title'),
        "description":$input.json('$.description'),
        "type":$input.json('$.type'),
        "issuer":$input.json('$.issuer')
        }
*/




//NatourS3Bucket s = new NatourS3Bucket();
/*
        Random rd = new Random();
        byte[] arr = new byte[100000];
        rd.nextBytes(arr);

        //Convert image to byte array
        BufferedImage bImage = ImageIO.read(new File("C:/Users/Utente/Desktop/okey.png"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos );
        byte [] data = bos.toByteArray();

        String encoded = Base64.getEncoder().encodeToString(data);
        System.out.println(encoded.length());


        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = ImageIO.read(bis);
        ImageIO.write(bImage2, "jpg", new File("output.jpg"));
*/



      /*  ByteArrayInputStream bais = new ByteArrayInputStream(s.fetchImage("mrincredible.jpg"));
        try {
            BufferedImage image = ImageIO.read(bais);
            File outputfile = new File("C:/Users/Utente/Desktop/okey.png");
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

/*    }
}*/


