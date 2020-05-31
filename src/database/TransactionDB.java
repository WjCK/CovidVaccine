package database;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TransactionDB {

    Logger logger;
    Connection connection = null;
    String url = "jdbc:mariadb://localhost:3306/covid";
    String user = "root";
    String password = "drug1";

    /**
     * Connect in database
     * 
     * @throws Exception
     */
    private void connect() throws Exception {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Fail when opening database connection" + e);
            throw e;
        }
    }

    /**
     * Close connection in database (safe mode)
     * 
     * @throws Exception
     */
    private void disconnect() throws Exception {
        try {
            if (connection != null && connection.isClosed())
                connection.close();
        } catch (Exception e) {
            logger.log(Level.ERROR, e);
            throw e;
        }
    }

    /**
     * Open transaction in database. Can read only or write in database
     * 
     * @param reader
     * @throws Exception
     */
    public void openTransaction(boolean reader) throws Exception {
        if (connection == null) {
            connect();
        }
        connection.setReadOnly(reader);

        if (connection != null && !connection.isClosed()) {
            connection.setAutoCommit(false);
        } else {
            throw new Exception("Cant open transaction");
        }
    }

    /**
     * Close transaction (safe mode) with commit
     * 
     * @throws Exception
     */
    public void closeTransaction() throws Exception {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.commit();
                connection.setAutoCommit(true);
                disconnect();
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, e);
            throw e;
        }
    }

    /**
     * Create statement in the SQL connection with execution instructions. Criar
     * instrução na conexão SQL com instruções de execução
     * 
     * @param sql
     * @return
     * @throws Exception
     */
    public Statement createStatement(String sql) throws Exception {
        if (connection != null && !connection.isClosed())
            return connection.createStatement();
        else
            throw new Exception("It was not possible to open the transaction");
    }
}