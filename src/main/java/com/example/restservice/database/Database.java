package com.example.restservice.database;


import com.example.restservice.Constants.Constants;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class Database {
    private final String JDBC_DRIVER;
    private final String DB_URL;
    private final String USER;
    private final String PASS;
    private static ComboPooledDataSource cpds;



    @Autowired
    public Database(@Value("${spring.datasource.driverClassName}") String driver,
                    @Value("${spring.datasource.url}")String url,
                    @Value("${spring.datasource.username}")String user,
                    @Value("${spring.datasource.password}")String pass) throws ClassNotFoundException, PropertyVetoException {
        this.JDBC_DRIVER = driver;
        this.DB_URL = url;
        this.USER = user;
        this.PASS = pass;
        if(cpds!=null)
            return;
        Class.forName(JDBC_DRIVER);
        //registering driver here since it should only occur once....could be wrong
        cpds =  new ComboPooledDataSource();
        cpds.setDriverClass(JDBC_DRIVER);
        cpds.setJdbcUrl(DB_URL);
        cpds.setUser(USER);
        cpds.setPassword(PASS);
        cpds.setMaxPoolSize(20);
        cpds.setMinPoolSize(10);
        cpds.setInitialPoolSize(5);
    }

    public static Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

    public boolean addUser(String email){
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Database.getConnection();
            statement = connection.createStatement();

            statement.executeUpdate(String.format(Constants.SQLstatements.ADDUSER,email));

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("sql error: "+e.getLocalizedMessage());
        }finally {
            closeConnection(connection);
            closeConnection(statement);
        }
        return true;
    }

    private void closeConnection(Connection connection){
        if(connection!=null) {
            try {
                connection.close();
            } catch (SQLException e) {}
        }
    }
    private void closeConnection(Statement statement){
        if(statement!=null) {
            try {
                statement.close();
            } catch (SQLException e) {}
        }
    }

}
