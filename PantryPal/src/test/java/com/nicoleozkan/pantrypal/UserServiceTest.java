package com.nicoleozkan.pantrypal;

import com.nicoleozkan.pantrypal.model.User;
import com.nicoleozkan.pantrypal.repository.UserRepository;
import com.nicoleozkan.pantrypal.repository.UserService;
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
public class UserServiceTest
{
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testRegisterUser()
    {
        // Create user
        User user = new User();
        user.setEmailAddress("alex@gmail.com");
        user.setPassword("alex2020");

        // Save user to database
        userService.registerUser(user);
        User savedUser = userRepository.findByEmail(user.getEmailAddress());

        // Create duplicate
        User existUser = entityManager.find(User.class, savedUser.getUserId());

        // Confirm duplicate
        assertThat(existUser.getEmailAddress()).isEqualTo(user.getEmailAddress());
    }
}
