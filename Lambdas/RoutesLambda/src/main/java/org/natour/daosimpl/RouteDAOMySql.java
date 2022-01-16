package org.natour.daosimpl;

import org.natour.daos.RouteDAO;
import org.natour.database.MySqlDB;
import org.natour.entities.LatLng;
import org.natour.entities.Route;
import org.natour.exceptions.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public List<LatLng> getUserRoutes(String username){
        return null;
    }

  /*
    public Timestamp stringToTimestamp(String st_timestamp){

        Timestamp timestamp = null;

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy hh:mm:ss");
            Date parsedDate = sdf.parse(st_timestamp);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
            return timestamp;
        }catch(Exception e){
            return null;
        }
    }*/
}
