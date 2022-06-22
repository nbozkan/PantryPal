package com.nicoleozkan.pantrypal.repository;

import com.nicoleozkan.pantrypal.model.Product;
import com.nicoleozkan.pantrypal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    @Query("SELECT p FROM Product p WHERE p.productName = ?1")
    Product getProductByProductName(String productName);
}