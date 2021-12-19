package org.natour.daos_impl;

import org.natour.daos.UserDAO;
import org.natour.db.MySqlDB;
import org.natour.exceptions.PersistenceException;
import org.natour.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoMySql implements UserDAO {
    private Connection connection;

    public UserDaoMySql() throws PersistenceException {

       if((connection = MySqlDB.getConnection())==null){
           throw new PersistenceException("Connection error");
       }
    }
    @Override
    public void saveUser(User user) throws PersistenceException {
        String query = " insert into User (username, email, password)"
                + " values (?,?,?)";
        try {
            PreparedStatement prepared_st = connection.prepareStatement(query);
            prepared_st.setString(1, user.getUsername());
            prepared_st.setString(2, user.getEmail());
            prepared_st.setString(3, user.getPassword());
            prepared_st.execute();
            connection.close();
        }catch(SQLException s){
            throw new PersistenceException("An error has occurred while trying to save the user");
        }
    }

    @Override
    public User getUserByUsername(String username) throws PersistenceException {
        String query = "select * from User where username = ?";
        try {
            PreparedStatement prepared_st = connection.prepareStatement(query);
            prepared_st.setString(1, username);
            ResultSet rs = prepared_st.executeQuery();
            rs.next();
            String email = rs.getString("email");
            String pwd = rs.getString("password");
            connection.close();
            User user = new User(username, email,pwd);
            return user;
        }catch(SQLException s){
            throw new PersistenceException("Read error");
        }
    }
}
