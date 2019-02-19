package ru.itpark.concertcalendar.repository;

import ru.itpark.concertcalendar.domain.Product;
import ru.itpark.concertcalendar.exception.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository (одновремнно два репозитория делать не нужно)
public class ProductRepositoryInMemoryImpl implements ProductRepository {
    private final List<Product> items
            = new ArrayList<>();
    private int nextId = 1;

    @Override
    public List<Product> getAll() {
        return items;
    }

    @Override
    public Optional<Product> getById(int id) {
        return items.stream()
                .filter(o -> o.getId() == id)
                .findFirst();
    }

    @Override
    public void save(Product item) {
        if (item.getId() == 0) {
            // добавление
            item.setId(nextId++);
            items.add(item);
            return;
        }

        Product product = getById(item.getId())
                .orElseThrow(ProductNotFoundException::new);

        product.setName(item.getName());
        product.setDescription(item.getDescription());
        product.setPrice(item.getPrice());
        product.setUrl(item.getUrl());
    }

    @Override
    public void removeById(int id) {
        items.removeIf(o -> o.getId() == id); // lambda
    }
}
