package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.config.AppConfig;
import ru.akirakozov.sd.refactoring.connection.ConnectionProvider;
import ru.akirakozov.sd.refactoring.connection.JdbcConnectionProvider;
import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.dao.ProductJdbcDao;
import ru.akirakozov.sd.refactoring.response.ProductHtmlResponseBuilder;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class Main {
    public static void runServer(AppConfig appConfig) throws Exception {
        ConnectionProvider connectionProvider = new JdbcConnectionProvider(appConfig);
        try (Connection c = connectionProvider.getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }

        Server server = new Server(appConfig.getServerPort());

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ProductDao productDao = new ProductJdbcDao(connectionProvider);
        ProductHtmlResponseBuilder htmlResponseBuilder = new ProductHtmlResponseBuilder();

        context.addServlet(new ServletHolder(new AddProductServlet(productDao, htmlResponseBuilder)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(productDao, htmlResponseBuilder)),"/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(productDao, htmlResponseBuilder)),"/query");

        server.start();
        server.join();
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Expected only one argument.");
            return;
        }

        String environment = args[0];

        AppConfig appConfig;
        switch (environment) {
            case "test":
                appConfig = new AppConfig("jdbc:sqlite:test.db", 8081);
                break;
            case "production":
                appConfig = new AppConfig("jdbc:sqlite:production.db", 8082);
                break;
            default:
                System.err.println("Unknown environment. Expected \"test\" or \"production\"");
                return;

        }

        runServer(appConfig);
    }
}
