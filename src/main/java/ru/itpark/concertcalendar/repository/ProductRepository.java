package ru.itpark.concertcalendar.repository;

import ru.itpark.concertcalendar.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getAll();

    Optional<Product> getById(int id);

    void save(Product item);

    void removeById(int id);
}
