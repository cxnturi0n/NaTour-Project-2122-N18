package org.natour.daos;

import org.natour.entities.LatLng;
import org.natour.entities.Route;
import org.natour.exceptions.PersistenceException;

import java.util.List;

public interface RouteDAO {

    public void insert(Route route) throws PersistenceException;

    public List<Route> getAll() throws PersistenceException;


}
