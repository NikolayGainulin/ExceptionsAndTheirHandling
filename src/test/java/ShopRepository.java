package ru.shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shop.exception.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShopRepositoryTest {
    private ShopRepository repository;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        repository = new ShopRepository();
        product1 = new Product(1, "Product 1", 100);
        product2 = new Product(2, "Product 2", 200);
        repository.add(product1);
        repository.add(product2);
    }

    @Test
    void removeExistingProductShouldDeleteIt() {
        repository.remove(1);
        Product[] products = repository.findAll();
        assertThat(products).containsExactly(product2);
    }

    @Test
    void removeNonExistingProductShouldThrow() {
        assertThatThrownBy(() -> repository.remove(3))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Element with id: 3 not found");
    }

    @Test
    void findByIdShouldReturnProductWhenExists() {
        assertThat(repository.findById(1)).isEqualTo(product1);
    }

    @Test
    void findByIdShouldReturnNullWhenNotExists() {
        assertThat(repository.findById(3)).isNull();
    }
}
