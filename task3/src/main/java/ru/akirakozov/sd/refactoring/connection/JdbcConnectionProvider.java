package ru.akirakozov.sd.refactoring.connection;

import ru.akirakozov.sd.refactoring.config.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionProvider implements ConnectionProvider {
    private final String dbUrl;

    public JdbcConnectionProvider(AppConfig appConfig) {
        this.dbUrl = appConfig.getDbUrl();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl);
    }
}
