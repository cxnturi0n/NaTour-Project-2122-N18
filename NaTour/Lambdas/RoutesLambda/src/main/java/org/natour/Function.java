package org.natour;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import org.natour.daos.RouteDAO;
import org.natour.daosimpl.RouteDAOMySql;
import org.natour.entities.Route;
import org.natour.exceptions.PersistenceException;
import org.natour.s3.NatourS3Bucket;
import org.natour.tokens.CognitoTokens;
import org.natour.tokens.GoogleAuth;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.List;


public class Function implements RequestHandler<Request, String> {


    @Override
    public String handleRequest(Request request, Context context) {


        String id_token = request.getId_token();

        if (request.getUser_type().equalsIgnoreCase("Cognito"))
            //Throws runtime exception in case of problems
            CognitoTokens.verifyIdToken(id_token);
        else if (request.getUser_type().equalsIgnoreCase("Google")) {
            try {
                if (!GoogleAuth.isIdTokenValid(id_token))
                    throw new RuntimeException("Invalid Session");
            } catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        RouteDAO r = new RouteDAOMySql();

        String action = request.getAction();

        switch (action) {

            case "GET_ALL":

                try {

                    Gson gson = new Gson();
                    List<Route> routes = r.getAll();

                    String json_routes = gson.toJson(routes);

                    return json_routes;

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "GET_N":

                try {

                    Gson gson = new Gson();
                    List<Route> routes = r.getN(request.getStart(), request.getEnd());

                    String json_routes = gson.toJson(routes);

                    return json_routes;

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "GET_PERSONAL":

                try {

                    Gson gson = new Gson();
                    List<Route> routes = r.getUserRoutes(request.getRoute().getCreator_username());

                    String json_routes = gson.toJson(routes);

                    return json_routes;

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "GET_PERSONAL_FAVOURITES":

                try {

                    Gson gson = new Gson();
                    List<Route> routes = r.getUserFavourites(request.getUsername());

                    String json_routes = gson.toJson(routes);

                    return json_routes;

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "GET_BY_LEVEL":

                try {

                    Gson gson = new Gson();

                    List<Route> routes = r.getRoutesByLevel(request.getRoute().getLevel());

                    String json_routes = gson.toJson(routes);

                    return json_routes;

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "GET_PERSONAL_FAVOURITES_NAMES":

                try {

                    Gson gson = new Gson();
                    List<Route> routes = r.getUserFavouritesNames(request.getUsername());

                    String json_routes = gson.toJson(routes);

                    return json_routes;

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "GET_PERSONAL_TOVISIT":

                try {

                    Gson gson = new Gson();
                    List<Route> routes = r.getUserToVisit(request.getUsername());

                    String json_routes = gson.toJson(routes);

                    return json_routes;

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "INSERT_USER_PROFILE_IMAGE":

                try{

                    NatourS3Bucket bucket = new NatourS3Bucket();

                    byte image_as_byte_array[] = Base64.getDecoder().decode(request.getProfile_image_base64());

                    bucket.putUserProfileImage(request.getUsername(), image_as_byte_array);

                    return "User profile image inserted successfully";

                }catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "GET_USER_PROFILE_IMAGE":

                try{

                    NatourS3Bucket bucket = new NatourS3Bucket();

                    byte image_as_byte_array [] = bucket.fetchUserProfileImage(request.getUsername());

                    return Base64.getEncoder().encodeToString(image_as_byte_array);

                }catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "INSERT":

                try {

                    Route route = request.getRoute();
                    r.insert(route);

                    return "Route inserted successfully";

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "INSERT_FAVOURITE":

                try {

                    r.insertFavourite(request.getUsername(), request.getRoute().getName());

                    return "Route inserted successfully";

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "INSERT_TOVISIT":

                try {

                    r.insertToVisit(request.getUsername(), request.getRoute().getName());

                    return "Route inserted successfully";

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "DELETE_FAVOURITE" :

                try {

                    r.deleteFavourite(request.getUsername(), request.getRoute().getName());

                    return "Favourite route deleted successfully";

                } catch (PersistenceException e) {
                    throw new RuntimeException(e.getMessage());
                }

            case "DELETE_TOVISIT" :

                try {

                    r.deleteToVisit(request.getUsername(), request.getRoute().getName());

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


