package com.nicoleozkan.pantrypal.repository;

import com.nicoleozkan.pantrypal.model.Product;
import com.nicoleozkan.pantrypal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    @Query("SELECT u FROM User u WHERE u.emailAddress = ?1")
    User findByEmail(String emailAddress);
}