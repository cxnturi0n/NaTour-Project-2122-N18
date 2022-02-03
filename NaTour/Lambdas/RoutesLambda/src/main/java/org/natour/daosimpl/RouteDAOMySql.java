package org.natour.daosimpl;

import org.natour.daos.RouteDAO;
import org.natour.database.MySqlDB;
import org.natour.entities.LatLng;
import org.natour.entities.Route;
import org.natour.exceptions.PersistenceException;
import org.natour.s3.NatourS3Bucket;

import java.sql.PreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class RouteDAOMySql implements RouteDAO {

    private Connection connection;

    public RouteDAOMySql() {
        connection = MySqlDB.getConnection();
    }

    @Override
    public void insert(Route route) throws PersistenceException {

        String query_route = "INSERT INTO Routes (name,creator_username,description,level, duration,report_count,disability_access, tags, length)" + "VALUES (?,?,?,?,?,?,?,?,?)";

        String query_coordinates = "INSERT INTO Coordinates (latitude, longitude, route_name, seq_num) VALUES (?,?,?,?)";

        PreparedStatement prepared_statement = null;

        String route_name = route.getName();

        NatourS3Bucket natour_bucket = new NatourS3Bucket();

        try {

            //Insert into route table
            prepared_statement = connection.prepareStatement(query_route);
            prepared_statement.setString(1, route_name);
            prepared_statement.setString(2, route.getCreator_username());
            prepared_statement.setString(3, route.getDescription());
            prepared_statement.setString(4, route.getLevel());
            prepared_statement.setFloat(5, route.getDuration());
            prepared_statement.setInt(6, route.getReport_count());
            prepared_statement.setBoolean(7, route.isDisability_access());
            prepared_statement.setString(8, route.getTags());
            prepared_statement.setFloat(9, route.getLength());

            byte decoded_image[] = Base64.getDecoder().decode(route.getImage_base64());

            natour_bucket.putRouteImage(route.getName(), decoded_image);

            prepared_statement.execute();

            //Insert into coordinate table
            prepared_statement = connection.prepareStatement(query_coordinates);

            int count = 0;
            for (LatLng coordinates : route.getCoordinates()) {
                prepared_statement.setFloat(1, coordinates.getLatitude());
                prepared_statement.setFloat(2, coordinates.getLongitude());
                prepared_statement.setString(3, route_name);
                prepared_statement.setInt(4, count++);
                prepared_statement.addBatch();
            }

            prepared_statement.executeBatch();

        } catch (SQLException e) {
            throw new PersistenceException("Insert Error");
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                return;
            }
        }
    }

    @Override
    public List<Route> getAll() throws PersistenceException {

        String query = "select * from Routes r JOIN Coordinates c ON c.route_name=r.name order by r.name,c.seq_num";

        List<Route> routes = new ArrayList<>();

        String old_route_name = "";

        String new_route_name;

        Route route = null;

        List<LatLng> coordinates = null;

        try {

            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) //Scorro riga per riga
            {
                new_route_name = rs.getString("name");

                if (old_route_name.equals(new_route_name)) { //Se l itinerario precedentemente letto è uguale a quello corrente (Alla prima iterazione sono sempre diversi)

                    //L oggetto route è gia stato creato, devo solo aggiungere le coordinate
                    coordinates.add(new LatLng(rs.getFloat("latitude"), rs.getFloat("longitude")));

                } else {//Se iniziano i record di un itinerario diverso

                    NatourS3Bucket natour_bucket = new NatourS3Bucket();

                    byte image_as_byte_array[] = natour_bucket.fetchRouteImage(new_route_name);

                    String image_base64 = Base64.getEncoder().encodeToString(image_as_byte_array);

                    //Creo l oggetto route
                    route = new Route(new_route_name, rs.getString("description"), rs.getString("creator_username"), rs.getString("level"), rs.getFloat("duration"),
                            rs.getInt("report_count"), rs.getBoolean("disability_access"), rs.getString("tags"), image_base64, rs.getFloat("length"), rs.getInt("likes"));

                    //coordinates punta all array list interno dell oggetto route appena creato
                    coordinates = route.getCoordinates();

                    //aggiungo le coordinate in route
                    coordinates.add(new LatLng(rs.getFloat("latitude"), rs.getFloat("longitude")));

                    //aggiungo route all array list
                    routes.add(route);

                }

                old_route_name = new_route_name;

            }

            return routes;

        } catch (SQLException e) {

            throw new PersistenceException(e.getMessage());

        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                return null;
            }
        }

    }

    @Override
    public List<Route> getN(int start, int end) throws PersistenceException {

        String query_routes = "SELECT * FROM Routes LIMIT " + start + ", " + end;

        PreparedStatement prepared_statement = null;

        List<Route> routes = new ArrayList<>();


        try {
            prepared_statement = connection.prepareStatement(query_routes);

            ResultSet rs = prepared_statement.executeQuery();

            while (rs.next()) {

                String route_name = rs.getString("name");

                NatourS3Bucket natour_bucket = new NatourS3Bucket();

                byte image_as_byte_array[] = natour_bucket.fetchRouteImage(route_name);

                String image_base64 = Base64.getEncoder().encodeToString(image_as_byte_array);

                Route route = new Route(route_name, rs.getString("description"), rs.getString("creator_username"), rs.getString("level"), rs.getFloat("duration"),
                        rs.getInt("report_count"), rs.getBoolean("disability_access"), rs.getString("tags"), image_base64, rs.getFloat("length"), rs.getInt("likes"));

                List<LatLng> coordinates = route.getCoordinates();

                String query_coordinates = "SELECT latitude, longitude FROM Coordinates WHERE route_name = " + "\"" + route_name + "\"" + " ORDER BY seq_num,route_name";

                PreparedStatement prepared_statement_1 = connection.prepareStatement(query_coordinates);

                ResultSet rs1 = prepared_statement_1.executeQuery();

                while (rs1.next())
                    coordinates.add(new LatLng(rs1.getFloat("latitude"), rs1.getFloat("longitude")));

                routes.add(route);
            }

            return routes;

        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                return null;
            }
        }

    }


    @Override
    public List<Route> getUserRoutes(String username) throws PersistenceException {

        String query = "SELECT * FROM Routes WHERE creator_username=" + "\"" + username + "\"";

        List<Route> routes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                String route_name = rs.getString("name");

                NatourS3Bucket natour_bucket = new NatourS3Bucket();

                byte image_as_byte_array[] = natour_bucket.fetchRouteImage(route_name);

                String image_base64 = Base64.getEncoder().encodeToString(image_as_byte_array);

                Route route = new Route(route_name, rs.getString("description"), rs.getString("creator_username"), rs.getString("level"), rs.getFloat("duration"),
                        rs.getInt("report_count"), rs.getBoolean("disability_access"), rs.getString("tags"), image_base64, rs.getFloat("length"), rs.getInt("likes"));


                List<LatLng> coordinates = route.getCoordinates();

                String query_coordinates = "SELECT latitude, longitude FROM Coordinates WHERE route_name = " + "\"" + route_name + "\"" + " ORDER BY seq_num,route_name";

                PreparedStatement prepared_statement_1 = connection.prepareStatement(query_coordinates);

                ResultSet rs1 = prepared_statement_1.executeQuery();

                while (rs1.next())
                    coordinates.add(new LatLng(rs1.getFloat("latitude"), rs1.getFloat("longitude")));

                routes.add(route);
            }

            return routes;

        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                return null;
            }
        }

    }

    @Override
    public List<Route> getUserFavourites(String username) throws PersistenceException {

        String query = "SELECT * FROM Favourites JOIN Routes ON Favourites.route_name=Routes.name WHERE Favourites.username=" + "\"" + username + "\"";

        List<Route> routes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                String route_name = rs.getString("name");

                NatourS3Bucket natour_bucket = new NatourS3Bucket();

                byte image_as_byte_array[] = natour_bucket.fetchRouteImage(route_name);

                String image_base64 = "";

                if (image_as_byte_array != null)
                    image_base64 = Base64.getEncoder().encodeToString(image_as_byte_array);

                Route route = new Route(route_name, rs.getString("description"), rs.getString("creator_username"), rs.getString("level"), rs.getFloat("duration"),
                        rs.getInt("report_count"), rs.getBoolean("disability_access"), rs.getString("tags"), image_base64, rs.getFloat("length"), rs.getInt("likes"));

                List<LatLng> coordinates = route.getCoordinates();

                String query_coordinates = "SELECT latitude, longitude FROM Coordinates WHERE route_name = " + "\"" + route_name + "\"" + " ORDER BY seq_num,route_name";

                PreparedStatement prepared_statement_1 = connection.prepareStatement(query_coordinates);

                ResultSet rs1 = prepared_statement_1.executeQuery();

                while (rs1.next())
                    coordinates.add(new LatLng(rs1.getFloat("latitude"), rs1.getFloat("longitude")));

                routes.add(route);
            }

            return routes;

        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                return null;
            }
        }
    }

    @Override
    public List<Route> getUserFavouritesNames(String username) throws PersistenceException {

        String query = "SELECT name FROM Favourites JOIN Routes ON Favourites.route_name=Routes.name WHERE Favourites.username=" + "\"" + username + "\"";

        List<Route> routes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                String route_name = rs.getString("name");

                Route route = new Route();

                route.setName(route_name);

                routes.add(route);
            }

            return routes;

        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                return null;
            }
        }
    }

    @Override
    public List<Route> getUserToVisit(String username) throws PersistenceException {

        String query = "SELECT * FROM ToVisit JOIN Routes ON ToVisit.route_name=Routes.name WHERE ToVisit.username=" + "\"" + username + "\"";

        List<Route> routes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                String route_name = rs.getString("name");


                NatourS3Bucket natour_bucket = new NatourS3Bucket();

                byte image_as_byte_array[] = natour_bucket.fetchRouteImage(route_name);

                String image_base64 = Base64.getEncoder().encodeToString(image_as_byte_array);

                Route route = new Route(route_name, rs.getString("description"), rs.getString("creator_username"), rs.getString("level"), rs.getFloat("duration"),
                        rs.getInt("report_count"), rs.getBoolean("disability_access"), rs.getString("tags"), image_base64, rs.getFloat("length"), rs.getInt("likes"));

                List<LatLng> coordinates = route.getCoordinates();

                String query_coordinates = "SELECT latitude, longitude FROM Coordinates WHERE route_name = " + "\"" + route_name + "\"" + " ORDER BY seq_num,route_name";

                PreparedStatement prepared_statement_1 = connection.prepareStatement(query_coordinates);

                ResultSet rs1 = prepared_statement_1.executeQuery();

                while (rs1.next())
                    coordinates.add(new LatLng(rs1.getFloat("latitude"), rs1.getFloat("longitude")));

                routes.add(route);
            }

            return routes;

        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                return null;
            }
        }
    }

    @Override
    public List<Route> getRoutesByLevel(String level) throws PersistenceException {

        String query = "SELECT * FROM Routes WHERE level=?";

        List<Route> routes = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, level);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                String route_name = rs.getString("name");

                NatourS3Bucket natour_bucket = new NatourS3Bucket();

                byte image_as_byte_array[] = natour_bucket.fetchRouteImage(route_name);

                String image_base64 = "";

                if (image_as_byte_array != null)
                    image_base64 = Base64.getEncoder().encodeToString(image_as_byte_array);

                Route route = new Route(route_name, rs.getString("description"), rs.getString("creator_username"), rs.getString("level"), rs.getFloat("duration"),
                        rs.getInt("report_count"), rs.getBoolean("disability_access"), rs.getString("tags"), image_base64, rs.getFloat("length"), rs.getInt("likes"));

                List<LatLng> coordinates = route.getCoordinates();

                String query_coordinates = "SELECT latitude, longitude FROM Coordinates WHERE route_name = " + "\"" + route_name + "\"" + " ORDER BY seq_num,route_name";

                PreparedStatement prepared_statement_1 = connection.prepareStatement(query_coordinates);

                ResultSet rs1 = prepared_statement_1.executeQuery();

                while (rs1.next())
                    coordinates.add(new LatLng(rs1.getFloat("latitude"), rs1.getFloat("longitude")));

                routes.add(route);
            }

            return routes;

        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                return null;
            }
        }
    }

    public void deleteFavourite(String username, String route_name) throws PersistenceException{

        String delete_route = "DELETE FROM Favourites WHERE username=? AND route_name=?";
        String update = "UPDATE Routes SET likes=likes - 1 WHERE name =?";

        try {
            PreparedStatement statement = connection.prepareStatement(delete_route);
            statement.setString(1, username);
            statement.setString(2, route_name);
            statement.executeUpdate();

            statement = connection.prepareStatement(update);
            statement.setString(1, route_name);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                return;
            }
        }

    }

    public void deleteToVisit(String username, String route_name) throws PersistenceException{

        String delete_route = "DELETE FROM ToVisit WHERE username=? AND route_name=?";

        try {
            PreparedStatement statement = connection.prepareStatement(delete_route);
            statement.setString(1, username);
            statement.setString(2, route_name);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                return;
            }
        }

    }

    @Override
    public void insertToVisit(String username, String route_name) throws PersistenceException {

        String query_route = "INSERT INTO ToVisit (username, route_name) VALUES (?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query_route);

            statement.setString(1, username);
            statement.setString(2, route_name);

            statement.execute();

        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                return;
            }
        }
    }

    public boolean hasUserAlreadyLiked(String username, String route_name) throws SQLException {

        String query = "SELECT * FROM Favourites WHERE username =? AND route_name =?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, route_name);

        ResultSet rs = statement.executeQuery();

        return rs.next();

    }

    @Override
    public void insertFavourite(String username, String route_name) throws PersistenceException {


        String insert = "INSERT INTO Favourites VALUES (?,?)";
        String update = "UPDATE Routes SET likes=likes + 1 WHERE name =?";

        try {

            if (hasUserAlreadyLiked(username, route_name))
                return;

            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, route_name);
            statement.executeUpdate();

            statement = connection.prepareStatement(insert);
            statement.setString(1, username);
            statement.setString(2, route_name);
            statement.execute();

        }catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                return;
            }
        }

    }

}