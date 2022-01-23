package org.natour.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlDB {

/*    String myDriver = "org.gjt.mm.mysql.Driver";
    String myUrl = "jdbc:mysql://localhost/test";
    Class.forName(myDriver);

    Connection conn = DriverManager.getConnection(myUrl, "root", "");*/
        // The newInstance() call is a work around for some
        // broken Java implementations
public static Connection getConnection() {
    try {

        String URL = "jdbc:mysql://natour-db.c8r5sn2iekjw.eu-central-1.rds.amazonaws.com/natour";
        Properties info = new Properties( );
        info.put( "user", "admin" );
        info.put( "password", "cinamidea2022" );
        Connection conn = DriverManager.getConnection(URL, info);
        return conn;
        // Do something with the Connection

    } catch (SQLException ex) {
        return null;
    }
}
}
