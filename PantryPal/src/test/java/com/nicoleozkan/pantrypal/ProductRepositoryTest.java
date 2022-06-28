package com.nicoleozkan.pantrypal;

import com.nicoleozkan.pantrypal.model.Product;
import com.nicoleozkan.pantrypal.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Rollback(false)
public class ProductRepositoryTest
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    TestEntityManager entityManager;

    // Tests findProductByProductName method in Product Repository
    @Test
    public void testFindProductByProductName()
    {
        // Create new product
        Product product = new Product();
        product.setProductName("milk");
        productRepository.save(product);

        // Create duplicate
        Product product2 = productRepository.getProductByProductName("milk");

        // Confirm they are the same object
        assertEquals(product.getProductName(), product2.getProductName());
    }
}
