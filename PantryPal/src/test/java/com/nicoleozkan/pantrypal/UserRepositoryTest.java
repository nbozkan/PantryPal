package com.nicoleozkan.pantrypal;

import com.nicoleozkan.pantrypal.model.User;
import com.nicoleozkan.pantrypal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Rollback(false)
public class UserRepositoryTest
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void testCreateUser()
    {
        // Create user
        User user = new User();
        user.setEmail("alex@gmail.com");
        user.setPassword("alex2020");

        // Save user to database
        User savedUser = userRepository.save(user);

        // Create duplicate
        User existUser = entityManager.find(User.class, savedUser.getUserId());

        // Confirm duplicate
        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testFindUserByEmail()
    {
        String email = "alex@gmail.com";

        User user = userRepository.findByEmail(email);

        assertThat(user).isNotNull();
    }
}