package com.hrm.bookstore.data.connection.impl;

import com.hrm.bookstore.data.connection.ConnectionManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.sql.Connection;


@Log4j2
@Component
public class ConnectionManagerImpl implements Closeable, ConnectionManager {

    private ConnectionPool connectionPool;

    @Value("${db.url}")
    private String url;

    @Value("${db.password}")
    private String password;

    @Value("${db.user}")
    private String user;

    @Value("${db.driver}")
    private String driver;

    @Value("${db.poolSize}")
    private int poolSize;

    public ConnectionManagerImpl() {
    }

    @PostConstruct
    public void init() {
        connectionPool = new ConnectionPool(driver, url, user, password, poolSize);
        log.info("Connection pool initialized");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setDriver(String driver) {
        this.driver = driver;
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

    @PreDestroy
    @Override
    public void close() {
        if (connectionPool != null) {
            connectionPool.destroyPool();
            connectionPool = null;
            log.info("Connection pool destroyed");
        }

    }

}
