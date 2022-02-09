package org.natour.daos;

import org.natour.entities.LatLng;
import org.natour.entities.QueryFilters;
import org.natour.entities.Report;
import org.natour.entities.Route;
import org.natour.exceptions.PersistenceException;

import java.sql.SQLException;
import java.util.List;

public interface RouteDAO {

    public void insert(Route route) throws PersistenceException;

    public void insertFavourite(String username, String route_name) throws PersistenceException;

    public void insertToVisit(String username, String route_name) throws PersistenceException;

    public void insertReport(Report report) throws PersistenceException;

    public List<Route> getAll() throws PersistenceException;

    public List<Route> getN(int start, int end) throws PersistenceException;

    public List<Route> getUserRoutes(String username) throws PersistenceException;

    public List<Route> getUserFavourites(String username) throws PersistenceException;

    public List<Route> getUserFavouritesNames(String username) throws PersistenceException;

    public List<Route> getUserToVisit(String username) throws PersistenceException;

    public List<Route> getFilteredRoutes(QueryFilters query_filters) throws PersistenceException;

    public List<Route> getRoutesByLevel(String level) throws PersistenceException;

    public boolean hasUserAlreadyLiked(String username, String route_name) throws PersistenceException, SQLException;

    public void deleteFavourite(String username, String route_name) throws PersistenceException;

    public void deleteToVisit(String username, String route_name) throws PersistenceException;

}
