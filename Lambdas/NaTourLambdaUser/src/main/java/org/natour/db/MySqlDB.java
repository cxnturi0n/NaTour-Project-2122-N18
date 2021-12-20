package org.natour.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.natour.exceptions.PersistenceException;


public class MySqlDB {
    public static final String URL = "jdbc:mysql://mysql0.c8r5sn2iekjw.eu-central-1.rds.amazonaws.com/natourdb?" +
            "user=admin&password=fabioangelo98";

    public static Connection getConnection() throws PersistenceException {
        Connection conn;
        Connection con;
        try {
            conn =
                    DriverManager.getConnection(URL);
            return conn;
        } catch (SQLException ex) {
            // handle any errors
            throw new PersistenceException(ex.getMessage());
        }
    }
}
