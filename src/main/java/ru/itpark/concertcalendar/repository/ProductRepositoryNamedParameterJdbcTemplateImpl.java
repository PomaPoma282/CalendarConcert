package ru.itpark.concertcalendar.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itpark.concertcalendar.domain.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductRepositoryNamedParameterJdbcTemplateImpl implements ProductRepository {
    private final NamedParameterJdbcTemplate template;

    public ProductRepositoryNamedParameterJdbcTemplateImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Product> getAll() {
        return template.query(
                "SELECT id, name, description, price, url FROM products",
                (rs, i) -> {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    String url = rs.getString("url");
                    return new Product(id, name, description, price, url);
                }
        );
    }

    @Override
    public Optional<Product> getById(int id) {
        return template.query(
                "SELECT id, name, description, price, url FROM products WHERE id = :id LIMIT 1",
                Map.of("id", id),
                (rs, i) -> new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getString("url")
                )
        ).stream().findFirst();
    }

    @Override
    public void save(Product item) {
        if (item.getId() == 0) {
            // INSERT
            template.update(
                    "INSERT INTO products(name, description, price, url) VALUES (:name, :description, :price, :url)",
                    Map.of(
                            "name", item.getName(),
                            "description", item.getDescription(),
                            "price", item.getPrice(),
                            "url", item.getUrl()
                    )
            );
            return;
        }

        // UPDATE
        template.update(
                "UPDATE products SET name = :name, description = :description, price = :price, url = :url WHERE id = :id",
                Map.of(
                        "id", item.getId(),
                        "name", item.getName(),
                        "description", item.getDescription(),
                        "price", item.getPrice(),
                        "url", item.getUrl()
                )
        );
    }

    @Override
    public void removeById(int id) {
        template.update(
                "DELETE FROM products WHERE id = :id",
                Map.of("id", id)
        );
    }
}
