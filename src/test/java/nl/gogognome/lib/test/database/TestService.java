/**
 *
 */
package nl.gogognome.lib.test.database;

import java.sql.Connection;
import java.util.List;

import nl.gogognome.lib.service.AbstractService;
import nl.gogognome.lib.service.ServiceException;

/**
 * This class implements a service for TestBOs.
 */
public class TestService extends AbstractService {

	public List<TestBO> findAllTestBOs() throws ServiceException {
		List<TestBO> bos;
	    try {
	    	Connection connection = createConnection();
	        TestDAO testDao = new TestDAO(connection);

	        bos = testDao.findAllBOs();
	        success = true;
	    } catch (Exception e) {
	   		throw new ServiceException("A problem occurred while fetching all TestBOs", e);
	    } finally {
	        releaseConnection(success);
	    }
        return bos;
	}
}
