package uz.BTService.btservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Getter
public class DataSourceConfig {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    private Connection connection;

    /**
     * if connection is null or closed then it will return a new instance
     *
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed())
            connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
        return connection;
    }
}