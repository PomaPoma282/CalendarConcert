package ru.itpark.concertcalendar.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.itpark.concertcalendar.domain.Product;

import java.util.List;
import java.util.Optional;

//@Repository
public class ProductRepositoryJdbcTemplateImpl implements ProductRepository {
    private final JdbcTemplate template;

    // DI
    public ProductRepositoryJdbcTemplateImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Product> getAll() {
        return template.query(
                "SELECT id, name, description, price, url FROM products",
                (rs, i) -> new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getString("url")
                )
        );
    }

    @Override
    public Optional<Product> getById(int id) {
        return template.query(
                "SELECT id, name, description, price, url FROM products WHERE id = ? LIMIT 1",
                (rs, i) -> new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getString("url")
                ),
                id
        ).stream().findFirst();
    }

    @Override
    public void save(Product item) {
        if (item.getId() == 0) {
            // INSERT
            template.update(
                    "INSERT INTO products(name, description, price, url) VALUES (?, ?, ?, ?)",
                    item.getName(), item.getDescription(), item.getPrice(), item.getUrl()
            );
            return;
        }

        // UPDATE
        template.update(
                "UPDATE products SET name = ?, description = ?, price = ?, url = ? WHERE id = ?",
                item.getName(),item.getDescription(), item.getPrice(), item.getUrl(), item.getId()
        );
    }

    @Override
    public void removeById(int id) {
        template.update(
                "DELETE FROM products WHERE id = ?",
                id
        );
    }
}
