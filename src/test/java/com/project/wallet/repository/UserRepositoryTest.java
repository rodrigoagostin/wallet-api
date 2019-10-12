package com.project.wallet.repository;

import com.project.wallet.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    private static String EMAIL = "email@test.com";

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() {
        User u = new User();
        u.setName("Set Up User");
        u.setPassword("123456");
        u.setEmail(EMAIL);
        userRepository.save(u);
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testSave_User() {
        User user = new User();
        user.setName("Test Name");
        user.setPassword("123456");
        user.setEmail("teste@teste.com");

        User response = userRepository.save(user);
        assertNotNull(response);
    }

    @Test
    public void testFindByEmail_User() {
        Optional<User> response = userRepository.findByEmailEquals(EMAIL);
        assertTrue(response.isPresent());
        assertEquals(response.get().getEmail(), EMAIL);
    }
}
