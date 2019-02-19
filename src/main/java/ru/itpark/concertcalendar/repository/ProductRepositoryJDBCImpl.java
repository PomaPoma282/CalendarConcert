package ru.itpark.concertcalendar.repository;

import ru.itpark.concertcalendar.domain.Product;
import ru.itpark.concertcalendar.exception.DbException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository
public class ProductRepositoryJDBCImpl implements ProductRepository {
    private final DataSource dataSource;

    public ProductRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        // Шаблонный код -> Template
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement();) {
                try (ResultSet resultSet = statement.executeQuery("SELECT id, name, description, price, url FROM products");) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");
                        int price = resultSet.getInt("price");
                        String url = resultSet.getString("url");
                        products.add(new Product(id, name, description, price, url));
                    }

                    return products;
                }
            }
        } catch (SQLException e) {
            // rethrow exception:
            // выкидываем non-checked exception + заворачивает старое
            throw new DbException(e);
        }
    }

    @Override
    public Optional<Product> getById(int id) {
        // Шаблонный код -> Template
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT id, name, description, price, url FROM products WHERE id = ?");) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery();) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");
                        int price = resultSet.getInt("price");
                        String url = resultSet.getString("url");
                        return Optional.of(new Product(id, name, description, price, url));
                    }

                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public void save(Product item) {
        // Шаблонный код -> Template
        try (Connection connection = dataSource.getConnection()) {
            if (item.getId() == 0) {
                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO products (name, description, price, url) VALUES (?, ?, ?, ?)");) {
                    statement.setString(1, item.getName());
                    statement.setString(2, item.getDescription());
                    statement.setInt(3, item.getPrice());
                    statement.setString(4, item.getUrl());
                    statement.executeUpdate();
                }
                return;
            }

            try (PreparedStatement statement = connection.prepareStatement("UPDATE products SET name = ?, description = ?, price = ?, url = ? WHERE id = ?");) {
                statement.setString(1, item.getName());
                statement.setString(2, item.getDescription());
                statement.setInt(3, item.getPrice());
                statement.setString(4, item.getUrl());
                statement.setInt(5, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public void removeById(int id) {
        // Шаблонный код -> Template
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE id = ?");) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
}
