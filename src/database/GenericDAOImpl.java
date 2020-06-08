package database;

import java.io.Serializable;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.Column;
import util.DatabaseException;
import util.Table;

public class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

    Logger logger;
    private Class<T> oClass;
    protected TransactionDB transactionDB;

    public TransactionDB getTransaction() {
        return transactionDB;
    }

    public void setTransactionDB(TransactionDB transactionDB) {
        this.transactionDB = transactionDB;
    }

    /**
     * Class to be persisted
     * 
     * @return oClass
     */
    public Class<T> getObjectClass() {
        return oClass;
    }

    /**
     * Get instance of the class
     * 
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public T newInstance() throws InstantiationException, IllegalAccessException {
        return oClass.newInstance();
    }

    /**
     * Constructor
     * 
     */
    @SuppressWarnings("unchecked")
    public GenericDAOImpl() {
        oClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * include/create object T in database
     */
    public T create(T object) throws DatabaseException {
        Statement statement = null;

        try {
            StringBuilder sqlBuilder = new StringBuilder();

            sqlBuilder.append("INSERT INTO ");
            sqlBuilder.append(getTableName());
            sqlBuilder.append(createInsertInstruction(object));

            System.out.println(sqlBuilder.toString());
            statement = transactionDB.createStatement(sqlBuilder.toString());
            statement.execute(sqlBuilder.toString());
            // FIX NULLLLLLL
            return null;
        } catch (Exception e) {
            throw new DatabaseException(e, "Cant execute 'create' in database " + e.getMessage());
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, e);
            }
        }

    }

    /**
     * list object T in database
     */
    public T retrieve(Integer id) throws DatabaseException {
        Statement statement = null;
        ResultSet resultSet = null;
        T object = null;

        try {
            StringBuilder sqlBuilder = new StringBuilder();

            sqlBuilder.append("SELECT * FROM ");
            sqlBuilder.append(getTableName());
            sqlBuilder.append(" WHERE ID = ");
            sqlBuilder.append(id);

            System.out.println(sqlBuilder.toString());
            statement = transactionDB.createStatement(sqlBuilder.toString());

            resultSet = statement.executeQuery(sqlBuilder.toString());

            if (resultSet != null && resultSet.next()) {
                object = mapResultSetInObject(resultSet);
            } else {
                throw new DatabaseException(new Exception(),
                        "There's no register in database with this id" + sqlBuilder.toString());
            }
        } catch (Exception e) {
            throw new DatabaseException(e, "Could not query the database" + e.getMessage());
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
            } catch (Exception e) {
                logger.log(Level.ERROR, e);
            }
        }

        return object;
    }

    /**
     * update object T in database
     */
    public T update(T object) throws DatabaseException {
        Statement statement = null;

        try {
            StringBuilder sqlBuilder = new StringBuilder();

            sqlBuilder.append("UPDATE ");
            sqlBuilder.append(getTableName());
            sqlBuilder.append(" SET ");
            sqlBuilder.append(createUpdateInstruction(object));
            sqlBuilder.append(" WHERE ID = ");
            sqlBuilder.append(getIdValue(object));

            System.out.println(sqlBuilder.toString());
            statement = transactionDB.createStatement(sqlBuilder.toString());
            statement.execute(sqlBuilder.toString());
        } catch (Exception e) {
            throw new DatabaseException(e, "Cant execute 'update' in database" + e.getMessage());
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (Exception e) {
                logger.log(Level.ERROR, e);
            }
        }
        // FIX NULLLLLLLLLLL
        return null;
    }

    /**
     * delete object T in database
     */
    public T delete(Integer id) throws DatabaseException {
        Statement statement = null;

        try {
            StringBuilder sqlBuilder = new StringBuilder();

            sqlBuilder.append("DELETE FROM ");
            sqlBuilder.append(getTableName());
            sqlBuilder.append(" WHERE ID = ");
            sqlBuilder.append(id);

            System.out.println(sqlBuilder.toString());
            statement = transactionDB.createStatement(sqlBuilder.toString());
            statement.execute(sqlBuilder.toString());
        } catch (Exception e) {
            throw new DatabaseException(e, "Cant delete in database" + e.getMessage());
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (Exception e) {
                logger.log(Level.ERROR, e);
            }
        }
        // FIX NULLLLLLLLLLLL
        return null;
    }

    /**
     * list object T in database
     */
    public List<T> list() throws DatabaseException {
        List<T> list = new ArrayList<T>();
        Statement statement = null;
        ResultSet resultSet = null;
        T objectT = null;

        try {
            StringBuilder sqlBuilder = new StringBuilder();

            sqlBuilder.append("SELECT * FROM ");
            sqlBuilder.append(getTableName());
            sqlBuilder.append(" ORDER BY 1 ");

            System.out.println(sqlBuilder.toString());
            statement = transactionDB.createStatement(sqlBuilder.toString());

            resultSet = statement.executeQuery(sqlBuilder.toString());

            while (resultSet != null && resultSet.next()) {
                objectT = mapResultSetInObject(resultSet);
                list.add(objectT);
            }
        } catch (Exception e) {
            throw new DatabaseException(e, "Cant list in database" + e.getMessage());
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
            } catch (Exception e) {
                logger.log(Level.ERROR, e);
            }
        }
        return list;
    }

    /**
     * get table name in database
     * 
     * @return tableName == patientform in database
     */
    private String getTableName() {
        String result = oClass.getAnnotation(Table.class).tableName();
        return result;
    }

    /**
     * create instruction VALUES in INSERT with object values
     * 
     * @param object
     * @return
     */
    private String createInsertInstruction(T object) {
        StringBuilder sql = new StringBuilder();
        StringBuilder fields = new StringBuilder();
        StringBuilder values = new StringBuilder();

        try {
            fields.append("(");
            values.append("(");

            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);

                if (!"id".equalsIgnoreCase(column.columnName())) {
                    if (fields.length() > 1) {
                        fields.append(", ");
                    }
                    if (values.length() > 1) {
                        values.append(", ");
                    }
                }

                fields.append(column.columnName());
                if (isUsingQuotes(field.getType())) {
                    values.append("'");
                    values.append(field.get(object));
                    values.append("'");
                } else {
                    values.append(field.get(object));
                }
            }
            fields.append(")");
            values.append(")");

            sql.append(fields);
            sql.append("VALUES");
            sql.append(values);
        } catch (Exception e) {
            logger.log(Level.ERROR, "error: " + e);
        }

        return sql.toString();
    }

    /**
     * Create instruction SET in UPDATE with object values
     * 
     * @param object
     * @return
     */
    private String createUpdateInstruction(T object) {
        StringBuilder sqlBuilder = new StringBuilder();
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);

                if (sqlBuilder.length() > 1) {
                    sqlBuilder.append(", ");
                }
                sqlBuilder.append(column.columnName());
                sqlBuilder.append(" = ");

                if (isUsingQuotes(field.getType())) {
                    sqlBuilder.append("'");
                    sqlBuilder.append(field.get(object));
                    sqlBuilder.append("'");
                } else {
                    sqlBuilder.append(field.get(object));
                }
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, "error: " + e);
        }
        return sqlBuilder.toString();
    }

    /**
     * Map fields in resultSet into respective props in object T
     * 
     * Columns in resultSet need to have same name in Object T attributes
     * 
     * @param resultSet
     * @return object T
     */
    private T mapResultSetInObject(ResultSet resultSet) {
        T object = null;

        try {
            object = (T) oClass.newInstance();

            for (Field field : oClass.getDeclaredFields()) {
                field.setAccessible(true);

                Column column = field.getAnnotation(Column.class);
                Object value = resultSet.getObject(column.columnName());
                Class<?> type = field.getType();

                if (isPrimitive(type)) {
                    Class<?> boxed = mapPrimitiveClass(type);
                    value = boxed.cast(value);
                }
                field.set(object, value);
            }
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            logger.log(Level.ERROR, e);
        }
        return object;
    }

    /**
     * get the value of the object's id field
     * 
     * @param object
     * @return
     */
    private String getIdValue(T object) {
        StringBuilder sqlBuilder = new StringBuilder();

        try {
            Field field = object.getClass().getDeclaredField("id");
            field.setAccessible(true);
            sqlBuilder.append(field.get(object));
        } catch (Exception e) {
            logger.log(Level.ERROR, e);
        }

        return sqlBuilder.toString();
    }

    private boolean isPrimitive(Class<?> type) {
        return (type == int.class || type == long.class || type == double.class || type == float.class
                || type == boolean.class || type == byte.class || type == char.class || type == short.class
                || type == Date.class);
    }

    private boolean isUsingQuotes(Class<?> type) {
        if (type == int.class || type == long.class || type == double.class || type == float.class || type == Date.class
                || type == Integer.class || type == Long.class || type == Double.class || type == Float.class) {
            return false;
        } else {
            return true;
        }
    }

    private Class<?> mapPrimitiveClass(Class<?> type) {
        if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == double.class) {
            return Double.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == boolean.class) {
            return Boolean.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == short.class) {
            return Short.class;
        } else if (type == Date.class) {
            return Date.class;
        } else {
            String string = "Class '" + type.getName() + "' is not a primitive";
            throw new IllegalArgumentException(string);
        }
    }

}