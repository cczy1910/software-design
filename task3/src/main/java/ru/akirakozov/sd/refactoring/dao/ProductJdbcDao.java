package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.connection.ConnectionProvider;
import ru.akirakozov.sd.refactoring.domain.Product;
import ru.akirakozov.sd.refactoring.util.ThrowingFunction;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductJdbcDao implements ProductDao {

    private final ConnectionProvider connectionProvider;

    public ProductJdbcDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public List<Product> getAll() throws IOException {
        return executeQuery("select * from product", rs -> {
            List<Product> result = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("name");
                long price = rs.getLong("price");
                result.add(new Product(name, price));
            }
            return result;
        });
    }

    @Override
    public void addProduct(Product product) throws IOException {
        try (var cnn = connectionProvider.getConnection()) {
            String sql = "insert into product " +
                    "(name, price) values (\"" + product.getName() + "\"," +
                    product.getPrice() + ")";
            try (var stmt = cnn.createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new IOException("Error while executing sql query", e);
        }
    }

    @Override
    public Product getProductWithMaxPrice() throws IOException {
        return executeQuery("select * from product order by price desc limit 1", rs -> {
            Product result = null;
            while (rs.next()) {
                String name = rs.getString("name");
                long price = rs.getLong("price");
                result = new Product(name, price);;
            }
            return result;
        });
    }

    @Override
    public Product getProductWithMinPrice() throws IOException {
        return executeQuery("select * from product order by price limit 1", rs -> {
            Product result = null;
            while (rs.next()) {
                String name = rs.getString("name");
                long price = rs.getLong("price");
                result = new Product(name, price);;
            }
            return result;
        });
    }

    @Override
    public long getSumPrice() throws IOException {
        return executeQuery("select sum(price) from product", rs -> rs.getLong(1));
    }

    @Override
    public int getProductsCount() throws IOException {
        return executeQuery("select count(*) from product", rs -> rs.getInt(1));
    }

    private <T> T executeQuery(String sql, ThrowingFunction<ResultSet, T> mapper) throws IOException {
        try (var cnn = connectionProvider.getConnection()) {
            try (var stmt = cnn.createStatement()) {
                try (var rs = stmt.executeQuery(sql)) {
                    return mapper.apply(rs);
                }
            }
        } catch (Exception e) {
            throw new IOException("Error while executing sql query", e);
        }
    }
}
