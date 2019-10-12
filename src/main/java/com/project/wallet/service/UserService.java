package com.project.wallet.service;

import com.project.wallet.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {
    User save(User user);
    Optional<User> findByEmail(String email);
}
