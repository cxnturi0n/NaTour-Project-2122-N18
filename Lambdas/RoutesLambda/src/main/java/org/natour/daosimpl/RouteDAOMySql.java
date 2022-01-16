package org.natour.daosimpl;

import org.natour.daos.RouteDAO;
import org.natour.database.MySqlDB;
import org.natour.entities.LatLng;
import org.natour.entities.Route;
import org.natour.exceptions.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RouteDAOMySql implements RouteDAO {

    private Connection connection;

    public RouteDAOMySql(){
        connection = MySqlDB.getConnection();
    }
    @Override
    public void insert(Route route) throws PersistenceException {

        // the mysql insert statement
        String query = " insert into Routes (name,creator_username,description,level, duration,report_count,disability_access)"
                + " values (?,?,?,?,?,?,?)";

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString (1, route.getName());
            preparedStmt.setString (2, route.getCreator_username());
            preparedStmt.setString (3, route.getDescription());
            preparedStmt.setString (4, route.getLevel());
            preparedStmt.setFloat  (5, route.getDuration());
            preparedStmt.setInt    (6, route.getReport_count());
            preparedStmt.setBoolean(7, route.isDisability_access());

            preparedStmt.execute();
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
    public List<LatLng> getAll(Route route) throws PersistenceException {
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
