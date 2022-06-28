package com.nicoleozkan.pantrypal.repository;

import com.nicoleozkan.pantrypal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    // Returns user with matching email field
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);
}