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
package nl.gogognome.lib.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * DAO for MySQL specific issues.
 *
 * @author Sander Kooijmans
 */
public class MySqlDAOPlugin extends AbstractDAO implements DBMSSpecificDAOPlugin {

	public MySqlDAOPlugin(Connection connection, Table table) {
		super(connection, table);
	}

	@Override
	public void createTable() throws SQLException {
        PreparedStatement statement = null;
        try {
            StringBuilder sb = new StringBuilder(1000);
            sb.append("CREATE TABLE ").append(table.getName()).append(" (\n");
            for (Iterator<TableColumn> iter = table.getColumns().iterator(); iter.hasNext(); ) {
            	TableColumn column = iter.next();
                sb.append("    `").append(column.getName()).append("` ");
                switch (column.getType()) {
                case TableColumn.STRING:
                    if (column.getLength() < 256) {
                        sb.append("VARCHAR(").append(column.getLength()).append(") ");
                    } else if (column.getLength() < 65536) {
                        sb.append("TEXT ");
                    } else if (column.getLength() < 16777216) {
                        sb.append("MEDIUMTEXT ");
                    } else {
                        sb.append("LONGTEXT ");
                    }
                    break;

                case TableColumn.INTEGER:
                    if (column.getLength() < 10) {
                        sb.append("INT(").append(column.getLength()).append(") ");
                    } else {
                        sb.append("INT ");
                    }
                    break;

                case TableColumn.LONG:
                    if (column.getLength() < 10) {
                        sb.append("INT(").append(column.getLength()).append(") ");
                    } else if (column.getLength() < 20) {
                        sb.append("BIGINT(").append(column.getLength()).append(") ");

                    } else {
                        sb.append("BIGINT ");
                    }
                    break;

                case TableColumn.DATE:
                    sb.append("DATE ");
                    break;

                case TableColumn.DOUBLE:
                    sb.append("DOUBLE ");
                    break;

                case TableColumn.BOOLEAN:
                    sb.append("BOOLEAN ");
                    break;

                default:
                    throw new SQLException("Unknown column type: " + column.getType()
                        + " for column " + column.getName());
                }

                if (column.isNotNull()) {
                    sb.append("NOT NULL ");
                }

                if (column.isAutoIncrement()) {
                    sb.append("AUTO_INCREMENT ");
                }

                if (iter.hasNext()) {
                	sb.append(",\n");
                }
            }

            if (!table.getPrimaryKeyColumns().isEmpty()) {
                sb.append(",\n    PRIMARY KEY (`");
	            for (Iterator<TableColumn> iter = table.getPrimaryKeyColumns().iterator(); iter.hasNext(); ) {
	            	TableColumn column = iter.next();
		            sb.append(column.getName());
		            if (iter.hasNext()) {
		            	sb.append("`, `");
		            }
	            }
	            sb.append("`)");
            }

            // Add for foreign keys
            for (TableColumn column : table.getColumns()) {
                if (column.isForeignKey()) {
                    sb.append(",\n    CONSTRAINT fk_").append(column.getForeignKeyTable().getName());
                    sb.append(" FOREIGN KEY (" + column.getName() + ") REFERENCES");
                    sb.append(' ').append(column.getForeignKeyTable().getName());
                    sb.append('(').append(column.getForeignKeyTable().getName()).append(')');
                }
            }

            sb.append(") ENGINE = MyISAM");
            statement = prepareStatement(sb.toString());

            logger.debug("createTable(): statement = " + statement.toString());
            statement.executeUpdate();
        } finally {
        	closeStatement(statement);
        }
	}

    @Override
	public void dropTable() throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = prepareStatement("DROP TABLE " + table.getName());
            logger.debug("dropTable(): statement = " + statement.toString());
            statement.executeUpdate();
        } finally {
        	closeStatement(statement);
        }
    }

    @Override
    public ColumnValuePairs getGeneratedValues(PreparedStatement statement) throws SQLException {
    	ColumnValuePairs cvp = new ColumnValuePairs();
        ResultSet result = statement.getGeneratedKeys();
    	if (result.next()) {
    		int index = 1;
    		for (TableColumn column : table.getColumns()) {
    			if (column.isAutoIncrement()) {
    				Object o = result.getObject(index++);

    				if (column.getType() == TableColumn.INTEGER) {
    					o = ((Number) o).intValue();
    				}

    				cvp.add(column, o);
    			}
    		}
    	}
    	return cvp;
    }

	@Override
	public void replaceNullValueForAutoIncrementColumns(ColumnValuePairs colValues) {
    	List<TableColumn> columnsToBeRemoved = new ArrayList<TableColumn>();

		for (ColumnValuePair cvp : colValues) {
			if (cvp.getColumn().isAutoIncrement() && cvp.getValue() == null) {
				columnsToBeRemoved.add(cvp.getColumn());
			}
		}

		for (TableColumn column : columnsToBeRemoved) {
			colValues.remove(column);
		}
	}
}
