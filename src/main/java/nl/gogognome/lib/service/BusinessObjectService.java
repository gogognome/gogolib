/*
   Copyright 2011 Sander Kooijmans

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package nl.gogognome.lib.service;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.gogognome.lib.businessobject.BusinessObject;
import nl.gogognome.lib.businessobject.PrimaryKey;
import nl.gogognome.lib.dao.AbstractBusinessObjectDAO;

/**
 * This class implements a service for finding, updating and deleting BOs.
 *
 * @author Sander Kooijmans
 */
public class BusinessObjectService extends AbstractService {

    private final static Map<Class<?>, Class<?>> PK_CLASS_TO_DAO_MAP =
    		new HashMap<Class<?>, Class<?>>();

    private final static Map<Class<?>, Class<?>> BO_CLASS_TO_DAO_MAP =
    		new HashMap<Class<?>, Class<?>>();

    public BusinessObjectService() throws Exception {
        super();
    }

    public static void registerBO(Class<?> boClass, Class<?> pkClass, Class<?> daoClass) {
        PK_CLASS_TO_DAO_MAP.put(pkClass, daoClass);
        BO_CLASS_TO_DAO_MAP.put(boClass, daoClass);
    }

    /**
     * Finds a business object by its PK.
     * @param pk the PK
     * @return the business object
     * @throws ServiceException if a problem occurs while searching for the business object
     */
    public <P extends PrimaryKey, T extends BusinessObject<P>> T findByPK(P pk) throws ServiceException {
    	Class<AbstractBusinessObjectDAO<P, T>> daoClass =
    			(Class<AbstractBusinessObjectDAO<P, T>>) PK_CLASS_TO_DAO_MAP.get(pk.getClass());
        if (daoClass == null) {
            throw new ServiceException("No DAO registered for PK class " + pk.getClass() + '.');
        }

        try {
        	Connection connection = createConnection();
            AbstractBusinessObjectDAO<P, T> dao = getDAO(daoClass, connection);

            T bo = dao.findByPK(pk);
            success = true;
            return bo;
        } catch (Exception e) {
        	throw new ServiceException("A problem occurred while searching for " + pk.getPresentationName() + '.');
        } finally {
            releaseConnection(success);
        }
    }

	public <P extends PrimaryKey, T extends BusinessObject<P>> T createBO(T bo) throws ServiceException {
    	Class<AbstractBusinessObjectDAO<P, T>> daoClass =
    			(Class<AbstractBusinessObjectDAO<P, T>>) BO_CLASS_TO_DAO_MAP.get(bo.getClass());
        if (daoClass == null) {
            throw new ServiceException("No DAO registered for business object class " + bo.getClass() + '.');
        }

        try {
        	Connection connection = createConnection();
            AbstractBusinessObjectDAO<P, T> dao = getDAO(daoClass, connection);

            bo = dao.createBO(bo);
            success = true;
            return bo;
        } catch (Exception e) {
       		throw new ServiceException("A problem occurred while creating business object " + bo, e);
        } finally {
            releaseConnection(success);
        }
	}

	private <P extends PrimaryKey, T extends BusinessObject<P>> AbstractBusinessObjectDAO<P, T>
			getDAO(Class<AbstractBusinessObjectDAO<P, T>> daoClass, Connection connection)
			throws SQLException {
        Constructor<?> constructor;
        try {
            constructor = daoClass.getConstructor(Connection.class);
        } catch (Exception e) {
            throw new SQLException("Can not create DAO " + daoClass.getName() + '.', e);
        }

        AbstractBusinessObjectDAO<P, T> dao;
        try {
            dao = (AbstractBusinessObjectDAO<P, T>) constructor.newInstance(connection);
        } catch (Exception e) {
            throw new SQLException("Can not create DAO " + daoClass.getName() + '.', e);
        }

        return dao;
	}

    /**
     * Creates tables using all registered DAOs.
     * @throws ServiceException if a problem occurs while creating  a table.
     */
    public void createRegisteredTables() throws ServiceException {
        try {
        	Connection connection = createConnection();
            List<AbstractBusinessObjectDAO> sortedDAOs = getSortedDAOs(connection);
            for (AbstractBusinessObjectDAO dao : sortedDAOs) {
                dao.createTable();
            }
            success = true;
        } catch (Exception e) {
            throw new ServiceException("Problem occurred while creating tables", e);
        } finally {
            releaseConnection(success);
        }
    }

    /**
     * Drops all tables using all registered DAOs.
     * @throws ServiceException if a problem occurs while dropping a table.
     */
    public void dropRegisteredTables() throws ServiceException {
        // TODO: take dependencies between tables into account!
        try {
        	Connection connection = createConnection();
            List<AbstractBusinessObjectDAO> sortedDAOs = getSortedDAOs(connection);
            for (AbstractBusinessObjectDAO dao : sortedDAOs) {
                dao.dropTable();
            }
    		success = true;
        } catch (Exception e) {
            throw new ServiceException("Problem occurred while dropping tables", e);
        } finally {
            releaseConnection(success);
        }
    }

    private List<AbstractBusinessObjectDAO> getSortedDAOs(Connection connection)
    		throws ServiceException {
        List<AbstractBusinessObjectDAO> daos = new ArrayList<AbstractBusinessObjectDAO>();
        for (Class<?> daoClass : PK_CLASS_TO_DAO_MAP.values()) {
            try {
                Constructor<?> constructor = daoClass.getConstructor(Connection.class);
                AbstractBusinessObjectDAO<? extends PrimaryKey, ? extends BusinessObject> dao =
                		(AbstractBusinessObjectDAO) constructor.newInstance(connection);
                daos.add(dao);
            } catch (Exception e) {
                throw new ServiceException("Problem occurred while instantiating DAO "
                    + daoClass.getName(), e);
            }
        }

        return daos;
    }

}
