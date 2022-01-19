package org.natour.daosimpl;

import org.natour.daos.RouteDAO;
import org.natour.database.MySqlDB;
import org.natour.entities.LatLng;
import org.natour.entities.Route;
import org.natour.exceptions.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteDAOMySql implements RouteDAO {

    private Connection connection;

    public RouteDAOMySql(){
        connection = MySqlDB.getConnection();
    }
    @Override
    public void insert(Route route) throws PersistenceException {

        String query_route = "INSERT INTO Routes (name,creator_username,description,level, duration,report_count,disability_access)" + "VALUES (?,?,?,?,?,?,?)";

        String query_coordinates = "INSERT INTO Coordinates (latitude, longitude, route_name, seq_num) VALUES (?,?,?,?)";

        PreparedStatement prepared_statement = null;

        String route_name = route.getName();

        try {

            //Insert into route table
            prepared_statement = connection.prepareStatement(query_route);
            prepared_statement.setString (1, route_name);
            prepared_statement.setString (2, route.getCreator_username());
            prepared_statement.setString (3, route.getDescription());
            prepared_statement.setString (4, route.getLevel());
            prepared_statement.setFloat  (5, route.getDuration());
            prepared_statement.setInt    (6, route.getReport_count());
            prepared_statement.setBoolean(7, route.isDisability_access());

            prepared_statement.execute();


            //Insert into coordinate table
            prepared_statement = connection.prepareStatement(query_coordinates);

            int count = 0;
            for(LatLng coordinates : route.getCoordinates()) {
                prepared_statement.setFloat(1, coordinates.getLatitude());
                prepared_statement.setFloat(2, coordinates.getLongitude());
                prepared_statement.setString(3, route_name);
                prepared_statement.setInt(4, count++);
                prepared_statement.addBatch();
            }

            prepared_statement.executeBatch();

        } catch (SQLException e) {
            throw new PersistenceException("Insert Error");
        }finally{
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

        PreparedStatement prepared_statement = null;

        List<Route> routes = new ArrayList<>();

        String old_route_name = "";

        String new_route_name;

        Route route = null;

        List<LatLng> coordinates = null;

        try {

            prepared_statement = connection.prepareStatement(query);

            ResultSet rs = prepared_statement.executeQuery();


            while(rs.next()) //Scorro riga per riga
            {
                new_route_name = rs.getString("name");

                if(old_route_name.equals(new_route_name)){ //Se l itinerario precedentemente letto è uguale a quello corrente (Alla prima iterazione sono sempre diversi)

                    //L oggetto route è gia stato creato, devo solo aggiungere le coordinate
                    coordinates.add(new LatLng(rs.getFloat("latitude"), rs.getFloat("longitude")));


                }else{//Se iniziano i record di un itinerario diverso

                    //Creo l oggetto route
                    route = new Route(new_route_name, rs.getString("description"), rs.getString("creator_username"), rs.getString("level"), rs.getFloat("duration"),
                            rs.getInt("report_count"), rs.getBoolean("disability_access"));

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

        }

    }

    public List<Route> getN(int n) throws PersistenceException {

        String query = "select * from Coordinates order by route_name,seq_num";

        return null;
    }


    public List<LatLng> getUserRoutes(String username) throws PersistenceException {
        return null;
    }


}
