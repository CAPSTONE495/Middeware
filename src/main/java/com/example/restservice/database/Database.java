package com.example.restservice.database;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.beans.PropertyVetoException;

@Service
public class Database {
    private final String JDBC_DRIVER;
    private final String DB_URL;
    private final String USER;
    private final String PASS;
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();

    @Autowired
    public Database(@Value("${spring.datasource.driverClassName}") String driver,
                    @Value("${spring.datasource.url}")String url,
                    @Value("${spring.datasource.username}")String user,
                    @Value("${spring.datasource.password}")String pass) throws ClassNotFoundException, PropertyVetoException {
        this.JDBC_DRIVER = driver;
        this.DB_URL = url;
        this.USER = user;
        this.PASS = pass;
        Class.forName(JDBC_DRIVER);
        //registering driver here since it should only occur once....could be wrong
        cpds.setDriverClass(JDBC_DRIVER);
        cpds.setJdbcUrl(DB_URL);
        cpds.setUser(USER);
        cpds.setPassword(PASS);
        cpds.setMaxPoolSize(20);
        cpds.setMinPoolSize(10);
        cpds.setInitialPoolSize(5);

    }

    public static void main(String[] aug){

    }

}
