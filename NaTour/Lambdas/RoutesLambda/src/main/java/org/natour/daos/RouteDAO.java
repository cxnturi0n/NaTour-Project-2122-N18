package org.natour.daos;

import org.natour.entities.Route;
import org.natour.exceptions.PersistenceException;

import java.util.List;

public interface RouteDAO {

    public void insert(Route route) throws PersistenceException;

    public void insertFavourite(String username, String route_name) throws PersistenceException;

    public void insertToVisit(String username, String route_name) throws PersistenceException;

    public List<Route> getAll() throws PersistenceException;

    public List<Route> getN(int start, int end) throws PersistenceException;

    public List<Route> getUserRoutes(String username) throws PersistenceException;

    public List<Route> getUserFavourites(String username) throws PersistenceException;

    public List<Route> getUserToVisit(String username) throws PersistenceException;

}
