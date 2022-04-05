package ru.akirakozov.sd.refactoring;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import ru.akirakozov.sd.refactoring.config.AppConfig;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseTest {
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    public static final AppConfig appConfig = new AppConfig("jdbc:sqlite:test.db", 8082);

    @BeforeClass
    public static void runServer() {
        executorService.submit(() -> {
            try {
                Main.runServer(appConfig);
            } catch (Throwable e) {
                System.err.println("Server failed during test execution. Stack trace:");
                e.printStackTrace();
            }
        });
    }

    @AfterClass
    public static void shutdownServer() {
        executorService.shutdown();
    }

    @After
    public void dropProductsTable() {
        try (var cnn = DriverManager.getConnection(appConfig.getDbUrl())) {
            try (var stmt = cnn.createStatement()) {
                stmt.executeUpdate("delete from product;");
            }
        } catch (SQLException e) {
            System.err.println("Failed to clean 'PRODUCT' table after test execution. Stack trace:");
            e.printStackTrace();
        }
    }
}
