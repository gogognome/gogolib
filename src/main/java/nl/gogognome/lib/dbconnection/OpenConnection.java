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
package nl.gogognome.lib.dbconnection;

import java.sql.Connection;
import java.util.Date;

/**
 * This class represents an open connection.
 *
 * @author Sander Kooijmans
 */
public class OpenConnection {

    private String database;
    private Connection connection;
    private long creationTime = System.currentTimeMillis();
    private long lastUsedTime = creationTime;
    private boolean inUse;

    public OpenConnection(String database, Connection connection) {
        this.database = database;
        this.connection = connection;
    }

    public long getAgeInSeconds() {
        return (System.currentTimeMillis() - creationTime) / 1000;
    }

    public String getDatabase() {
        return database;
    }

    public Connection getConnection() {
        return connection;
    }

    public void touch() {
        lastUsedTime = System.currentTimeMillis();
    }

    boolean isInUse() {
        return inUse;
    }

    void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    @Override
	public String toString() {
        return "OpenConnection[" + database + "; " + connection + "; created at "
            + new Date(creationTime) + "; last used at "
            + new Date(lastUsedTime) + ']';
    }
}
