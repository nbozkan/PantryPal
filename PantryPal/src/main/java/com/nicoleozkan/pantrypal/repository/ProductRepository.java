package com.nicoleozkan.pantrypal.repository;

import com.nicoleozkan.pantrypal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    // Returns product that matches given productName
    @Query("SELECT p FROM Product p WHERE p.productName = ?1")
    Product getProductByProductName(String productName);
}