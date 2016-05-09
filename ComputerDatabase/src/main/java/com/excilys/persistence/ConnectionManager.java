package com.excilys.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Gives an access to the computer database.
 */
public enum ConnectionManager {
    INSTANCE;

    private static final String PROPERTY_FILE = "db.properties";
    private static final Properties PROPS = new Properties();
    private static HikariDataSource ds = null;
    private ThreadLocal<Connection> localConnection = new ThreadLocal<Connection>();
    private Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    static {
        try {
            PROPS.load(ConnectionManager.class.getClassLoader().getResourceAsStream(PROPERTY_FILE));
            Class.forName(PROPS.getProperty("DB_DRIVER"));
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(PROPS.getProperty("DB_URL"));
            config.setUsername(PROPS.getProperty("DB_USERNAME"));
            config.setPassword(PROPS.getProperty("DB_PASSWORD"));
            config.setMaximumPoolSize(300);
            ds = new HikariDataSource(config);
        } catch (ClassNotFoundException e) {
            throw new ConnectionException();
        } catch (IOException e) {
            throw new ConnectionException();
        }
    }

    /**
     * Returns a fresh connection to the database.
     * @return a fresh connection to the database
     * @throws ConnectionException
     * @throws ConnectionException if no connection is reachable
     */
    private Connection getFromDB() {
        try {
            return ds.getConnection();
        } catch (SQLException | SecurityException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
            throw new ConnectionException(e);
        }
    }

    public void initConnection() {
        localConnection.set(getFromDB());
    }

    public void initTransaction() {
        try {
            Connection connect = getFromDB();
            connect.setAutoCommit(false);
            localConnection.set(connect);
        } catch (SQLException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
            throw new ConnectionException(e);
        }
    }

    public Connection getConnection() {
        return localConnection.get();
    }

    public void commit() {
        try {
            localConnection.get().commit();
        } catch (SQLException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
            rollBack();
        }
    }

    public void rollBack() {
        try {
            localConnection.get().rollback();
        } catch (SQLException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            localConnection.get().close();
        } catch (Exception e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
        } finally {
            localConnection.remove();
        }
    }

}
