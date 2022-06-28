package com.nicoleozkan.pantrypal.repository;

import com.nicoleozkan.pantrypal.model.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProductRepository extends JpaRepository<UserProduct, Long>
{
}
