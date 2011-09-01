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

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * This class defines a column in a table.
 *
 * @author Sander Kooijmans
 */
public class TableColumn {

    public final static int STRING = 1;

    public final static int INTEGER = 2;

    public final static int LONG = 3;

    public final static int DATE = 4;

    public final static int DOUBLE = 5;

    public final static int BOOLEAN = 6;

    private final static Class<?>[] JAVA_CLASSES = new Class<?>[BOOLEAN + 1];

    static {
        JAVA_CLASSES[STRING] = String.class;
        JAVA_CLASSES[INTEGER] = Integer.class;
        JAVA_CLASSES[LONG] = Long.class;
        JAVA_CLASSES[DATE] = Date.class;
        JAVA_CLASSES[DOUBLE] = Double.class;
        JAVA_CLASSES[BOOLEAN] = Boolean.class;
    }

    private final String name;

    private final int type;

    private final int length;

    private final boolean notNull;

    private final boolean autoIncrement;

    private final Table foreignKeyTable;

    public static TableColumn createStringColumn(String name, int length, boolean notNull) {
        return new TableColumn(name, STRING, length, notNull, false);
    }

    public static TableColumn createIntegerColumn(String name, int length, boolean autoIncrement) {
        if (length > 10) {
            throw new IllegalArgumentException("Maximum length is 10 but length was " + length);
        }

        return new TableColumn(name, INTEGER, length, true, autoIncrement);
    }

    public static TableColumn createLongColumn(String name, int length, boolean autoIncrement) {
        if (length > 20) {
            throw new IllegalArgumentException("Maximum length is 20 but length was " + length);
        }

        return new TableColumn(name, LONG, length, false, autoIncrement);
    }

    public static TableColumn createBooleanColumn(String name) {
        return new TableColumn(name, BOOLEAN, 0, true, false);
    }

    public static TableColumn createDateColumn(String name, boolean notNull) {
        return new TableColumn(name, DATE, 0, notNull, false);
    }

    /**
     * Creates a table column that contains a foreign key.
     * @param table the table to which the foreign key refers.
     * @return the table column
     */
    public static TableColumn createForeignKey(Table table) {
    	List<TableColumn> pkColumns = table.getPrimaryKeyColumns();
    	if (pkColumns.size() != 1) {
    		throw new IllegalArgumentException("Only foreign keys of 1 column are supported. The foreign key " +
    				"for the table " + table.getName() + " consists of " + pkColumns.size() + " columns.");
    	}
    	TableColumn pkColumn = pkColumns.get(0);
        return new TableColumn(table.getName() + pkColumn.getName(),
        		pkColumn.getType(), pkColumn.getLength(), true, false, table);
    }

    /**
     * Constructor.
     * @param name the name of the column
     * @param type the type of the column
     * @param length the length of the column, needed for some types.
     * @param notNull indicates whether the column never accepts <code>null</code> as value
     * @param autoIncrement indicates whether the column automatically generates its value
     */
    protected TableColumn(String name, int type, int length, boolean notNull, boolean autoIncrement) {
        this(name, type, length, notNull, autoIncrement, null);
    }

    /**
     * Constructor.
     * @param name the name of the column
     * @param type the type of the column
     * @param length the length of the column, needed for some types.
     * @param notNull indicates whether the column never accepts <code>null</code> as value
     * @param autoIncrement indicates whether the column automatically generates its value
     * @param foreignKeyTable if not <code>null</code>, then this column is a foreign key to that table.
     */
    protected TableColumn(String name, int type, int length, boolean notNull, boolean autoIncrement,
            Table foreignKeyTable) {
        this.name = name;
        this.autoIncrement = autoIncrement;
        this.length = length;
        this.notNull = notNull;
        this.type = type;
        this.foreignKeyTable = foreignKeyTable;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    /**
     * Checks whether this column is a foreign key.
     * @return <code>true</code> if this column is a foreign key; <code>false</code> otherwise
     */
    public boolean isForeignKey() {
        return foreignKeyTable != null;
    }

    /**
     * Gets the table to which the foreign key in this column refers.
     * @return the table or <code>null</code> if <code>! isForeignKey()</code>.
     */
    public Table getForeignKeyTable() {
        return foreignKeyTable;
    }

    /**
     * Checks whether this table column can accept the specified value.
     * If this method does not throw an {@link Exception}, then this column accepts
     * the specified value.
     * @param value the value
     * @throws SQLException if this table column does not accept the specified value.
     */
    public void validateValue(Object value) throws SQLException {
        if (value == null) {
            if (isNotNull()) {
                throw new SQLException("Column " + getName() + " can not be null.");
            }
        } else {
            if (!JAVA_CLASSES[getType()].isAssignableFrom(value.getClass())) {
                throw new SQLException("Column " + getName() + " accepts values of type "
                    + JAVA_CLASSES[getType()] + ", not of type " + value.getClass() + '.');
            }
        }

    }
}
