package com.hrm.bookstore.data.connection.impl;

import com.hrm.bookstore.data.connection.ConnectionManager;
import lombok.extern.log4j.Log4j2;

import java.io.Closeable;
import java.sql.Connection;

@Log4j2
public class ConnectionManagerImpl implements Closeable, ConnectionManager {
    private ConnectionPool connectionPool;
    private final String url;
    private final String password;
    private final String user;
    private final String driver;
    private int poolSize = 16;

    public ConnectionManagerImpl(String url, String password, String user, String driver) {
        this.url = url;
        this.password = password;
        this.user = user;
        this.driver = driver;
        connectionPool = new ConnectionPool(driver, url, user, password, poolSize);
        log.info("Connection pool initialized");
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    @Override
    public Connection getConnection() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool(driver, url, user, password, poolSize);
            log.info("Connection pool initialized");
        }
        return connectionPool.getConnection();
    }

    @Override
    public void close() {
        if (connectionPool != null) {
            connectionPool.destroyPool();
            connectionPool = null;
            log.info("Connection pool destroyed");
        }

    }

}
